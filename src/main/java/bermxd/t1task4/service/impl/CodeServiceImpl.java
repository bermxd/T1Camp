package bermxd.t1task4.service.impl;

import bermxd.t1task4.customException.CodeException;
import bermxd.t1task4.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {
    private final RestTemplate restTemplate;

    @Value("${default.url}")
    private String url;

    public String getCode(String email) {
        String codeUrl = String.format(url + "/api/get-code?email=%s", email);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(codeUrl, String.class);
            String responseBody = response.getBody();
            if (responseBody != null && responseBody.length() > 1) {
                return responseBody.substring(1, responseBody.length() - 1);
            } else {
                throw new CodeException("Invalid response body");
            }
        } catch (RestClientException e) {
            throw new CodeException("Failed to get code", e);
        }
    }
}
