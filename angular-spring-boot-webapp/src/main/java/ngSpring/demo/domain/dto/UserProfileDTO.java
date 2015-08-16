package ngSpring.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {

    private String userId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirmation;
}
