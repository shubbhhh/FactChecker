package factChecker_backend.factchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import factChecker_backend.factchecker.GeminiClient.ApiRequestBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
@RestController
public class FactcheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactcheckerApplication.class, args);
	}

	@PostMapping("/gemini")
	public String apiRouter(@RequestBody ApiRequestBody body) {
		try {
			GeminiClient client = new GeminiClient();
			
			String result = client.checkFact(body.getText(), body.getContextText(), body.getUrl(), body.getApiKey());

			return result;
		} catch(Exception e) {
			return "Something went wrong";
		}
	}
}
