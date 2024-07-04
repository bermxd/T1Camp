package bermxd.t1task4.service.impl;

import bermxd.t1task4.exception.SignUpException;
import bermxd.t1task4.dto.UserStatusDto;
import bermxd.t1task4.service.SignUpService;
import bermxd.t1task4.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final RestTemplate restTemplate;
    @Value("${default.url}")
    private String url;

    public void signUp(UserStatusDto userStatusDto, List<String> roles) {
        try {
            if (!roles.contains(userStatusDto.getRole())){
                throw new SignUpException("Invalid role: " + userStatusDto.getRole());
            }
            String signUpUrl = url + "/api/sign-up/";
            ResponseEntity<String> response = restTemplate.postForEntity(signUpUrl, HttpUtils.createHttpEntity(userStatusDto), String.class);
            if (!response.getStatusCode().is2xxSuccessful()){
                throw new SignUpException("Sign up failed with status code: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new SignUpException("Sign up failed", e);
        }
    }
}