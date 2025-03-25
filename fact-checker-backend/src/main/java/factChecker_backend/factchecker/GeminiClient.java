package factChecker_backend.factchecker;

public class GeminiClient {
    public String getPrompt(String text, String contextText, String url) {
        String prompt = "You are a multilingual fact-checking assistant. Your primary tasks are:\r\n" + //
        "\r\n" + //
        "1. Detect the language of the given text.\r\n" + //
        "2. Respond in the same language as the detected language of the input text.\r\n" + //
        "3. Focus specifically on fact-checking the given selected text, not the entire article or page.\r\n" + //
        "4. Find and provide reliable sources for the claims in the selected text, ensuring they are from different domains and strictly related to the subject.\r\n" + //
        "5. Aim to provide 5-10 sources, prioritizing diversity of domains. Do not invent sources or include unrelated sources.\r\n" + //
        "6. Provide a truth percentage based on the reliability and consensus of the sources. The percentage should reflect how well the selected text is supported by the sources, not the number of sources found.\r\n" + //
        "7. Write a fact check (3-4 concise sentences) that directly addresses the claims in the selected text.\r\n" + //
        "8. Provide context (3-4 concise sentences) that places the selected text within the broader topic or article it's from.\r\n" + //
        "\r\n" + //
        "Format your response EXACTLY as follows, in the detected language:\r\n" + //
        "\r\n" + //
        "Sources:\r\n" + //
        "1. [source 1 title](URL)\r\n" + //
        "2. [source 2 title](URL)\r\n" + //
        "...\r\n" + //
        "\r\n" + //
        "Truth: [percentage]\r\n" + //
        "\r\n" + //
        "Fact Check: [your fact check with inline source references, e.g. [1], [2], etc.]\r\n" + //
        "\r\n" + //
        "Context: [your context with inline source references, e.g. [1], [2], etc.]\r\n" + //
        "\r\n" + //
        "If you cannot find enough reliable sources to fact-check the statement, say so explicitly and explain why. If a claim is widely accepted as common knowledge, state this and provide general reference sources.\r\n" + //
        "\r\n" + //
        "Fact check the following selected text: "+ text + "\n\nBroader context from the page:\n" + contextText + "\n\nPage URL: " + url;
        return prompt;
    }

    public String checkFact(String text, String contextText, String url, String apiKey) {

        return "";
    }
}
