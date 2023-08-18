package com.english.dto;

import com.google.gson.Gson;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ChatGPTApiClient {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String BODY="{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"assistant\", \"content\": %s}]}";
    @Value("${openai.apiKey}")
    private String OPEN_API_KEY;

    private final HttpClient httpClient = HttpClients.createDefault();

    public String getChatGPTResponse(String message) throws Exception {
        HttpPost request = new HttpPost(API_ENDPOINT);
        request.setHeader("Authorization", "Bearer "+OPEN_API_KEY);
        request.setHeader("Content-Type", "application/json");

        String postBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"assistant\", \"content\":"+message+ "}]}";
        request.setEntity(new StringEntity(postBody, ContentType.APPLICATION_JSON));

        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request);

        try {
            String rs = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            ChatCompletion completion = gson.fromJson(rs,ChatCompletion.class);
            return completion.getChoices().get(0).getMessage().getContent();
        } finally {
            response.close();
        }
    }
}

