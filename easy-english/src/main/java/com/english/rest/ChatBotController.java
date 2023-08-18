package com.english.rest;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.english.dto.ChatGPTApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/chatbot")
public class ChatBotController {

    @Value("${openai.apiKey}")
    private String OPEN_API_KEY;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private ChatGPTApiClient chatGPTApiClient;


    @GetMapping("/conversation")
    public ResponseEntity<String> chatWith(@RequestParam String prompt){
        OpenAiService service = new OpenAiService(OPEN_API_KEY,400000);

        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt)
                .maxTokens(1000)
                .build();
        String result = service.createCompletion(request).getChoices().get(0).getText();

        return new ResponseEntity<>(result, (result != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("")
    public ResponseEntity<String> chat(@RequestParam String message) throws Exception {
            String response = chatGPTApiClient.getChatGPTResponse(message);
        return new ResponseEntity<>(response, (response != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);

    }

}
