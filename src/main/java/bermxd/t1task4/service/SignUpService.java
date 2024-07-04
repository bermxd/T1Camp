package bermxd.t1task4.service;

import bermxd.t1task4.dto.UserStatusDto;

import java.util.List;

public interface SignUpService {
    void signUp(UserStatusDto userStatusDto, List<String> roles);
}