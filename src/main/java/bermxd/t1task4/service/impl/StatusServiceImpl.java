package bermxd.t1task4.service.impl;

import bermxd.t1task4.customException.StatusFailedException;
import bermxd.t1task4.dto.StatusDto;
import bermxd.t1task4.service.CodeService;
import bermxd.t1task4.service.StatusService;
import bermxd.t1task4.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final RestTemplate restTemplate;
    private final CodeService codeService;

    @Value("${default.url}")
    private String url;
    @Value("${default.status:increased}")
    private String status;

    @Override
    public String setStatus(String email) {
        String code = codeService.getCode(email);
        String statusUrl = url + "/api/set-status/";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(statusUrl, HttpUtils.createHttpEntity(createStatusDto(email, code)), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
            throw new StatusFailedException("Failed to set status: received status code " + response.getStatusCode());
        } catch (RestClientException e) {
            throw new StatusFailedException("Failed to set status", e);
        }
    }

    private StatusDto createStatusDto(String email, String code) {
        StatusDto statusDto = new StatusDto();
        statusDto.setToken(Base64.getEncoder().encodeToString((email + ":" + code).getBytes()));
        statusDto.setStatus(status);
        return statusDto;
    }

}
