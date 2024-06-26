package bermxd.t1task4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/execute-sequence")
    public String executeSequence(@RequestParam String initials, @RequestParam String surname, @RequestParam int testNumber) {
        String email = String.format("%s-%s-%d@example.ru", initials, surname, testNumber);
        return apiService.executeApiSequence(email);
    }
}