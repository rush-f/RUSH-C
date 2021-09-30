package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rush.rush.exception.WrongInputException;

class UserBuilderTest {

    private static final String TEST_EMAIL = "email@email.com";

    @Test
    @DisplayName("Builder - Collection 필드를 주입하지 않을 경우 빈 컬렉션을 가짐")
    void builder_IfNotInjectCollectionFields_EmptyCollection() {
        User user = User.builder()
            .id(1L)
            .email(TEST_EMAIL)
            .password("password")
            .nickName("nickname")
            .provider(AuthProvider.local)
            .build();

        assertThat(user.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(user.getUserGroups()).isNotNull();
        assertThat(user.getImageUrl()).isNull();
        assertThat(user.getUserGroups()).isNotNull();
        assertThat(user.getUserGroups()).hasSize(0);
    }

    @Test
    @DisplayName("Builder - 이메일 형식이 잘못됐을 때 예외처리")
    void builder_IfEmailFormatIsWrong_ThrowException() {
        assertThatThrownBy(() -> User.builder()
                .id(1L)
                .email("wrong_email")
                .password("password")
                .nickName("nickname")
                .provider(AuthProvider.local)
                .build())
            .isInstanceOf(WrongInputException.class);
    }
}