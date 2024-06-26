package bermxd.t1task4;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class ApiService {
    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public final String address = "http://193.19.100.32:7000";

    public String executeApiSequence(String email) {
        try {
            // Get Roles
            ResponseEntity<String> rolesResponse = restTemplate.getForEntity(address + "/api/get-roles", String.class);
            JsonNode rolesNode = objectMapper.readTree(rolesResponse.getBody());
            // Find the specific role, e.g., "Разработчик Java"
            String desiredRole = "Разработчик Java";
            String selectedRole = null;
            for (JsonNode roleNode : rolesNode) {
                if (roleNode.asText().equals(desiredRole)) {
                    selectedRole = roleNode.asText();
                    break;
                }
            }

            if (selectedRole == null) {
                throw new RuntimeException("Роль не найдена");
            }

            // Sign Up
            String signUpUrl = address + "/api/sign-up";
            String candidateJson = String.format("{\"firstName\":\"Максим\",\"lastName\":\"Берляков\",\"role\":\"%s\",\"email\":\"%s\"}", selectedRole, email);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> signUpRequest = new HttpEntity<>(candidateJson, headers);
            restTemplate.postForEntity(signUpUrl, signUpRequest, String.class);

            // Get Code
            String getCodeUrl = String.format(address + "/api/get-code?email=%s", email);
            ResponseEntity<String> codeResponse = restTemplate.getForEntity(getCodeUrl, String.class);
            String code = codeResponse.getBody();

            // Set Status
            String setStatusUrl = address + "/api/set-status";
            if (isStringBlank(email) || isStringBlank(code)) {
                throw new RuntimeException("Невозможно установить статус");
            }
            String token = Base64.getEncoder().encodeToString((email + ":" + code).getBytes());
            String statusJson = String.format("{\"token\":\"%s\"}", token);
            HttpEntity<String> setStatusRequest = new HttpEntity<>(statusJson, headers);
            restTemplate.postForEntity(setStatusUrl, setStatusRequest, String.class);

            return "Запрос выполнен";
        } catch (Exception e) {
            e.printStackTrace();
            return "Возникла ошибка";
        }
    }

    private boolean isStringBlank(String str) {
        return str == null || str.isBlank();
    }
}