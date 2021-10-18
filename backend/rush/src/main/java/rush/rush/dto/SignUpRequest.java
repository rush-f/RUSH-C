package rush.rush.dto;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class SignUpRequest {

    @NotBlank
    private String nickName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}