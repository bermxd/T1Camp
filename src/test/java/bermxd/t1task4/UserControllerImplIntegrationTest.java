package bermxd.t1task4;
import bermxd.t1task4.dto.UserStatusDto;
import bermxd.t1task4.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static bermxd.t1task4.utils.TestUtils.randomString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = T1task4Application.class)
@AutoConfigureMockMvc
public class UserControllerImplIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testSetStatus() throws Exception {
        UserStatusDto statusDto = new UserStatusDto().setEmail(randomString())
                .setRole(randomString())
                .setFirstName(randomString())
                .setFirstName(randomString());
        String expected = randomString();
        when(userService.setStatus(statusDto)).thenReturn(expected);

        mvc.perform(put("/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        verify(userService).setStatus(statusDto);
    }
}