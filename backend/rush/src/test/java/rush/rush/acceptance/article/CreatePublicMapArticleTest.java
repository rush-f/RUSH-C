package rush.rush.acceptance.article;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.acceptance.setup.AuthSetUp;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CreatePublicMapArticleTest {

    private String token;

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        final String nickName = "김예림";
        final String email = "test@email.com";
        final String password = "password";

        AuthSetUp.signUp(nickName, email, password);
        token = AuthSetUp.login(email, password);
    }

    @Test
    @DisplayName("전체지도에 글쓰기")
    void createPublicArticle() {
        assertThat(token).isNotNull();
    }
}
