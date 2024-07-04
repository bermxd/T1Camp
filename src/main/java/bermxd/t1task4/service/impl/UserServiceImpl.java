package bermxd.t1task4.service.impl;

import bermxd.t1task4.dto.UserStatusDto;
import bermxd.t1task4.service.RoleService;
import bermxd.t1task4.service.SignUpService;
import bermxd.t1task4.service.StatusService;
import bermxd.t1task4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final SignUpService signUpService;
    private final StatusService statusService;

    public String setStatus(UserStatusDto userStatusDto) {
        List<String> roles = roleService.getRoles();
        signUpService.signUp(userStatusDto, roles);
        return statusService.setStatus(userStatusDto.getEmail());
    }
}