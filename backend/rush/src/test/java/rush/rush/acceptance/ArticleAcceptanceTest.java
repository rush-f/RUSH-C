package rush.rush.acceptance;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.AuthResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ArticleAcceptanceTest {

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    /**
     * feature : 전체지도에 글을 쓴다
     *
     * when 회원가입 한다. then 회원가입된다.
     *
     * when 로그인한다. then 로그인된다.
     *
     * when 전체지도에 글을 쓴다. then 글이 저장된다.
     *
     * when 쓴 글을 조회한다. then 글이 조회된다.
     */
    @Test
    void usePublicArticle() {
        signUp();
        String token = login();
        String location = writePublicArticle(token);

        ArticleResponse articleResponse = findArticle(location);

        Long expectedId = extractIdFromLocation(location);

        assertThat(articleResponse.getId()).isEqualTo(expectedId);
    }

    private void signUp() {
        Map<String, String> body = new HashMap<>();
        body.put("nickName", "하이하이");
        body.put("email", "dsadsadas@naver.com");
        body.put("password", "dfsfasdsafds");

        given()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/auth/signup")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    private String login() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "dsadsadas@naver.com");
        body.put("password", "dfsfasdsafds");

        AuthResponse authResponse = given()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/auth/login")
            .then()
            .extract()
            .body()
            .as(AuthResponse.class);

        return authResponse.getAccessToken();
    }

    private String writePublicArticle(String accessToken) {
        Map<String, String> body = new HashMap<>();

        body.put("title", "제목제목");
        body.put("content", "내용내용");
        body.put("latitude", "37");
        body.put("longitude", "127");
        body.put("publicMap", "true");
        body.put("privateMap", "false");

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/articles")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");
    }

    private ArticleResponse findArticle(String location) {
        return given()
            .when()
                .get(location + "?latitude=35&latitudeRange=10&longitude=125&longitudeRange=10")
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ArticleResponse.class);
    }

    private Long extractIdFromLocation(String location) {
        String[] split = location.split("/");

        return Long.parseLong(split[split.length - 1]);
    }
}
