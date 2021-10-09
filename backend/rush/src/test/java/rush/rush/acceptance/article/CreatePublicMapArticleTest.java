package rush.rush.acceptance.article;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.dto.AuthResponse;

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

        signUp(nickName, email, password);
        token = login(email, password);
    }

    @Test
    @DisplayName("전체지도에 글쓰기")
    void createPublicArticle() {
        assertThat(token).isNotNull();
    }

    private void signUp(String nickName, String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("nickName", nickName);
        body.put("email", email);
        body.put("password", password);

        given()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/api/auth/signup")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    private String login(String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        AuthResponse authResponse = given()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/auth/login")
            .then()
            .extract()
            .body()
            .as(AuthResponse.class);

        return authResponse.getAccessToken();
    }
}
