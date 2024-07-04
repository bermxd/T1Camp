package bermxd.t1task4;

import bermxd.t1task4.dto.RolesDto;
import bermxd.t1task4.exception.RoleServiceException;
import bermxd.t1task4.service.impl.RoleServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bermxd.t1task4.utils.TestUtils.randomInt;
import static bermxd.t1task4.utils.TestUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplUnitTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Value("${default.url}")
    private String url;

    @Test
    public void givenRolesUrl_whenStatusIsOk_thenReturnSucceed() {
        int expectedRoleCount = randomInt();
        List<String> randomRoles = generateRandomRoles(expectedRoleCount);
        RolesDto rolesDto = new RolesDto();
        rolesDto.setRoles(randomRoles);
        ResponseEntity<RolesDto> responseEntity = new ResponseEntity<>(rolesDto, HttpStatus.OK);
        when(restTemplate.getForEntity(url + "/api/get-roles", RolesDto.class)).thenReturn(responseEntity);

        List<String> roles = roleService.getRoles();

        assertNotNull(roles);
        assertEquals(expectedRoleCount, roles.size());
        assertTrue(roles.containsAll(randomRoles));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void givenRolesUrl_whenBodyNullOrRolesEmpty_thenReturnRoleServiceException(boolean isNull) {
        RolesDto rolesDto = new RolesDto().setRoles(isNull ? null : List.of());
        ResponseEntity<RolesDto> responseEntity = new ResponseEntity<>(rolesDto, HttpStatus.OK);
        when(restTemplate.getForEntity(url + "/api/get-roles", RolesDto.class)).thenReturn(responseEntity);

        RoleServiceException exception = assertThrows(RoleServiceException.class, () -> {
            roleService.getRoles();
        });

        assertEquals("Invalid response body", exception.getMessage());
    }

    @ParameterizedTest
    @EnumSource(value = HttpStatus.class, names = {"BAD_REQUEST", "NOT_FOUND", "INTERNAL_SERVER_ERROR"})
    public void givenRolesUrl_whenStatusIsNotOk_thenReturnRoleServiceException(HttpStatus status) {
        ResponseEntity<RolesDto> responseEntity = new ResponseEntity<>(null, status);
        when(restTemplate.getForEntity(url + "/api/get-roles", RolesDto.class)).thenReturn(responseEntity);

        RoleServiceException exception = assertThrows(RoleServiceException.class, () -> {
            roleService.getRoles();
        });

        assertEquals("Failed to fetch roles, status code: " + responseEntity.getStatusCode(), exception.getMessage());
    }

    @Test
    public void givenRolesUrl_whenCatchRestClientException_thenReturnRoleServiceException() {
        String exceptionText = randomString();
        when(restTemplate.getForEntity(url + "/api/get-roles", RolesDto.class)).thenThrow(new RestClientException(exceptionText));

        RoleServiceException exception = assertThrows(RoleServiceException.class, () -> {
            roleService.getRoles();
        });

        assertEquals("Error occurred while fetching roles with message: " + exceptionText, exception.getMessage());
    }

    private List<String> generateRandomRoles(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> RandomStringUtils.randomAlphabetic(10))
                .collect(Collectors.toList());
    }
}
