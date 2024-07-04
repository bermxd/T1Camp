package bermxd.t1task4.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RolesDto {
    private List<String> roles;
}