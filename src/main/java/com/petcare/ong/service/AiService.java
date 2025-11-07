package com.petcare.ong.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.petcare.ong.model.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(AiService.class);
    private final String apiKey;

    public AiService(RestTemplate restTemplate, @Value("${openai.api-key:}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey == null ? "" : apiKey;
    }

    /**
     * Gera uma recomendação de cuidados para um pet usando a OpenAI Chat Completions API.
     * Se a variável de ambiente OPENAI_API_KEY não estiver definida, retorna uma mensagem padrão.
     */
    public String gerarRecomendacao(Pet pet) {
        if (apiKey.isEmpty()) {
            logger.warn("OPENAI API key não definido — retornando mensagem fallback");
            return "Dica padrão: mantenha o pet alimentado, visite o veterinário regularmente e ofereça carinho e exercício adequados à espécie.";
        }

        String prompt = String.format("Gere uma recomendação curta e amigável de cuidados para um animal com as seguintes características:\nNome: %s\nEspécie: %s\nRaça: %s\nIdade: %s\nDescrição: %s\nSeja objetivo (3-4 sentenças), linguagem acolhedora, e inclua 2 dicas práticas.",
                safe(pet.getNome()), safe(pet.getEspecie()), safe(pet.getRaca()), String.valueOf(pet.getIdade()), safe(pet.getDescricao()));

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", new Object[]{message});
        request.put("max_tokens", 300);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("Content-Type", "application/json");
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    "https://api.openai.com/v1/chat/completions",
                    HttpMethod.POST,
                    entity,
                    JsonNode.class
            );

            JsonNode body = response.getBody();
            if (body != null && body.has("choices")) {
                JsonNode first = body.get("choices").get(0);
                if (first != null && first.has("message") && first.get("message").has("content")) {
                    return first.get("message").get("content").asText();
                }
            }
        } catch (Exception ex) {
            logger.error("Erro ao chamar OpenAI: {}", ex.getMessage(), ex);
        }

        return "Não foi possível gerar a recomendação no momento. Tente novamente mais tarde.";
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
