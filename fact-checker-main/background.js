chrome.runtime.onInstalled.addListener(() => {
  chrome.contextMenus.create({
    id: "factCheckAI",
    title: "Fact check with AI",
    contexts: ["selection"]
  });
});

chrome.contextMenus.onClicked.addListener((info, tab) => {
  if (info.menuItemId === "factCheckAI") {
    chrome.tabs.sendMessage(tab.id, { action: "checkInjection" }, (response) => {
      if (chrome.runtime.lastError || !response || !response.injected) {
        chrome.scripting.executeScript({
          target: { tabId: tab.id },
          files: ['content.js']
        }, () => {
          if (chrome.runtime.lastError) {
            console.error('Error injecting script: ' + chrome.runtime.lastError.message);
            return;
          }
          sendFactCheckMessage(tab.id, info.selectionText, tab.url);
        });
      } else {
        sendFactCheckMessage(tab.id, info.selectionText, tab.url);
      }
    });
  }
});

function sendFactCheckMessage(tabId, text, url) {
  chrome.tabs.sendMessage(tabId, { action: "showLoading" });

  chrome.storage.sync.get('apiKey', async (data) => {
    if (data.apiKey) {
      try {
        const contextText = await fetchPageContent(tabId);
        const response = await factCheckWithAI(text, contextText, url, data.apiKey);
        console.log('Sending fact check result to content script:', response);
        chrome.tabs.sendMessage(tabId, {
          action: "factCheckResult",
          data: response
        });
      } catch (error) {
        console.error('Error in fact checking:', error);
        chrome.tabs.sendMessage(tabId, {
          action: "factCheckError",
          error: error.message
        });
      }
    } else {
      chrome.tabs.sendMessage(tabId, {
        action: "factCheckError",
        error: "API Key not found. Please set it in the extension popup."
      });
    }
  });
}

async function fetchPageContent(tabId) {
  try {
    const [{ result }] = await chrome.scripting.executeScript({
      target: { tabId: tabId },
      func: () => document.body.innerText
    });
    return result;
  } catch (error) {
    console.error('Error fetching page content:', error);
    return '';
  }
}

async function factCheckWithAI(text, contextText, url, apiKey) {
  try {
    const response = await fetch(`http://localhost:8080/gemini`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        text,
        contextText,
        url,
        apiKey
      })
    });
    
    
    if (response.ok) {
      const result = await response.text();
      console.log('Gemini API response:', result);
      return result;
    } else {
      return 'Invalid response from Gemini API';
    }
  }
  catch(error) {
    console.error("Failed to fetch:", error)
    throw new Error("Failed to fetch")
  }
}