package bermxd.t1task4.api;

import bermxd.t1task4.dto.UserStatusDto;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<String> setStatus(UserStatusDto userStatusDto);
}