package rush.rush.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.dto.UserImageResponse;
import rush.rush.dto.UserResponse;
import rush.rush.exception.NotUserExistsException;
import rush.rush.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest extends ServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
            User.builder()
                .email("articleAuthor@test.com")
                .password("test password")
                .nickName("test")
                .imageUrl("이미지 주소")
                .provider(AuthProvider.local)
                .build()
        );
    }

    @Test
    @DisplayName("유저 이미지 찾기")
    void findUserImageUrl() {
        // when
        UserImageResponse userImageResponse = userService.findUserImageUrl(user);

        // then
        assertThat(userImageResponse.getImageUrl()).isEqualTo(user.getImageUrl());
    }

    @Test
    @DisplayName("유저 이미지 찾기 - 해당 유저가 없는 경우")
    void findUserImageUrl_IfNotUserExists() {
        // when
        userRepository.deleteAll();
        // when & then
        assertThatThrownBy(() -> userService.findUserImageUrl(user))
            .isInstanceOf(NotUserExistsException.class);
    }

    @Test
    @DisplayName("유저 찾기")
    void findUser() {
        // when
        UserResponse userResponse = userService.findUser(user);

        // then
        assertThat(userResponse.getUserId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저 찾기 - 해당 유저가 없는 경우")
    void findUser_IfNotUserExists() {
        // when
        userRepository.deleteAll();
        // when & then
        assertThatThrownBy(() -> userService.findUser(user))
            .isInstanceOf(NotUserExistsException.class);
    }

    @Test
    @DisplayName("회원탈퇴")
    void withdraw() {
        // when
        userService.withdraw(user);

        // then
        assertThat(userRepository.findAll()).hasSize(0);

        // when & then  회원 탈퇴시 해당하는 유저가 없는 경우
        assertThatThrownBy(() -> userService.withdraw(user))
            .isInstanceOf(NotUserExistsException.class);
    }
}