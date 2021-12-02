package rush.rush.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rush.rush.dto.LoginRequest;
import rush.rush.dto.SignUpRequest;
import rush.rush.exception.AlreadyExistedEmailException;
import rush.rush.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest extends ServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Test
    @DisplayName("회원가입 후 로그인")
    void login() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("testName", "test@naver.com", "1111");
        authService.signUp(signUpRequest);
        LoginRequest loginRequest = new LoginRequest("test@naver.com", "1111");

        //when & then
        assertThat(authService.login(loginRequest)).isNotNull();

    }

    @Test
    @DisplayName("회원가입")
    void signUp() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("testName", "test@naver.com", "1111");

        //when
        authService.signUp(signUpRequest);

        //then
        assertThat(userRepository.findAll().get(0).getEmail()).isEqualTo(signUpRequest.getEmail());

        //when & then  회원가입시 이미 동일한 이메일의 유저가 있는경우
        assertThatThrownBy(() -> authService.signUp(signUpRequest))
            .isInstanceOf(AlreadyExistedEmailException.class);
    }
}
