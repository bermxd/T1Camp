package bermxd.t1task4;

import bermxd.t1task4.dto.UserStatusDto;
import bermxd.t1task4.service.RoleService;
import bermxd.t1task4.service.SignUpService;
import bermxd.t1task4.service.StatusService;
import bermxd.t1task4.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static bermxd.t1task4.utils.TestUtils.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {
    @Mock
    private RoleService roleService;
    @Mock
    private SignUpService signUpService;

    @Mock
    private StatusService statusService;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void givenValidUser_whenSetStatus_thenSucceed() {
        UserStatusDto userStatusDto = new UserStatusDto().setEmail(randomString())
                .setRole(randomString())
                .setFirstName(randomString())
                .setFirstName(randomString());
        String expected = randomString();
        List<String> roles = List.of(randomString(), randomString());
        when(roleService.getRoles()).thenReturn(roles);
        doNothing().when(signUpService).signUp(userStatusDto, roles);
        when(statusService.setStatus(userStatusDto.getEmail())).thenReturn(expected);

        String actual = userService.setStatus(userStatusDto);

        verify(roleService).getRoles();
        verify(signUpService).signUp(userStatusDto, roles);
        verify(statusService).setStatus(userStatusDto.getEmail());
        assertEquals(expected, actual);
    }
}
