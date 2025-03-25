package factChecker_backend.factchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
@RestController
public class FactcheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactcheckerApplication.class, args);
	}

	@GetMapping("/api")
	public String demo() {
		GeminiClient client = new GeminiClient();
		String prompt = client.getPrompt("ABC", "DEF", "GHI");
		// System.out.println(prompt);
		return prompt;
	}

	@PostMapping("/api")
	public String apiRouter() {
		try {
			GeminiClient client = new GeminiClient();
			
			String result = client.checkFact(null, null, null, null);

			return result;
		} catch(Exception e) {
			return "Something went wrong";
		}
	}
}
