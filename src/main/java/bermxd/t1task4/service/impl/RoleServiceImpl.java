package bermxd.t1task4.service.impl;

import bermxd.t1task4.customException.RoleServiceException;
import bermxd.t1task4.dto.RolesDto;
import bermxd.t1task4.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RoleServiceImpl implements RoleService {
    private final RestTemplate restTemplate;
    @Value("${default.url}")
    private String url;

    @Override
    public List<String> getRoles() {
        String rolesUrl = url + "/api/get-roles";
        try {
            ResponseEntity<RolesDto> rolesResponse = restTemplate.getForEntity(rolesUrl, RolesDto.class);
            if (rolesResponse.getStatusCode() != HttpStatus.OK) {
                throw new RoleServiceException("Failed to fetch roles, status code: " + rolesResponse.getStatusCode());
            }
            RolesDto body = rolesResponse.getBody();
            if (body == null || CollectionUtils.isEmpty(body.getRoles())) {
                throw new RoleServiceException("Invalid response body");
            }
            return body.getRoles();
        } catch (RestClientException e) {
            throw new RoleServiceException("Error occurred while fetching roles with message: " + e.getMessage());
        }
    }
}

