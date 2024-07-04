package bermxd.t1task4.api;

import bermxd.t1task4.dto.UserStatusDto;
import bermxd.t1task4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @PutMapping("/status")
    public ResponseEntity<String> setStatus(@RequestBody @Valid UserStatusDto statusDto) {
        return new ResponseEntity<>(userService.setStatus(statusDto), HttpStatus.OK);
    }
}