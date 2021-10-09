package rush.rush.acceptance.article;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rush.rush.acceptance.fixture.ArticleFixture;
import rush.rush.acceptance.fixture.AuthFixture;
import rush.rush.acceptance.fixture.UserFixture;
import rush.rush.dto.ArticleResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

        AuthFixture.signUp(nickName, email, password);
        token = AuthFixture.login(email, password);
    }

    @ParameterizedTest
    @DisplayName("전체지도에만 글쓰기")
    @CsvSource({
        // 제목&내용 : 최소 글자수(1자, 공백X), 위도&경도 : 최솟값
        "a,a,-90,-180",
        // 제목&내용 : 글자수 적당히, 위도&경도 : 최솟값
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,-90,-180",
        // 제목&내용 : 최소 글자수, 위도&경도 : 최댓값
        "a,a,90,180",
        // 제목&내용 : 글자수 적당히, 위도&경도 : 최댓값
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,0,0",
        // 제목&내용 : 최소 글자수, 위도&경도 : 중앙값
        "a,a,90,180",
        // 제목&내용 : 글자수 많이, 위도&경도 : 중앙값
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,0,0"
    })
    void createPublicArticle(String title, String content, double latitude, double longitude) {
        Map<String, Object> body = new HashMap<>();

        body.put("title", title);
        body.put("content", content);
        body.put("latitude", latitude);
        body.put("longitude", longitude);
        body.put("publicMap", true);
        body.put("privateMap", false);

        String location =
            given()
                .header("Authorization", "Bearer " + token)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/articles")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");

        ArticleResponse savedArticle = ArticleFixture.findPublicArticleById(
            extractArticleIdFrom(location));

        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo(title);
        assertThat(savedArticle.getContent()).isEqualTo(content);
        assertThat(savedArticle.getAuthor()).isNotNull();
        assertThat(savedArticle.getAuthor().getId()).isNotNull();
        assertThat(savedArticle.getLatitude()).isEqualTo(latitude);
        assertThat(savedArticle.getLongitude()).isEqualTo(longitude);
        assertThat(savedArticle.getTotalLikes()).isZero();
    }

    @DisplayName("전체지도에만 글쓰기 - 위도 or 경도가 너무 클 때 값 자동조정")
    @ParameterizedTest
    @CsvSource({
        "-91, -90, -100, -100",      // 위도가 너무 작음 -> 위도 -90으로 자동조정
        "91, 90, -100, -100",        // 위도가 너무 큼 -> 위도 90으로 자동조정
        "10, 10, -181, -180",        // 경도가 너무 작음 -> 경도 -180으로 자동조정
        "10, 10, 181, 180",          // 경도가 너무 큼 -> 경도 180으로 자동조정
        "100, 90, 181, 180",         // 위도와 경도가 너무 큼 -> 위도 90, 경도 180
        "-100, -90, -200, -180",     // 위도와 경도가 너무 작음 -> 위도 -90, 경도 -180
        "-100, -90, 181, 180",       // 위도가 너무 작고 경도가 너무 큼 -> 위도 -90, 경도 180
        "100, 90, -181, -180",       // 위도가 너무 크고 경도가 너무 작음 -> 위도 90, 경도 -180
    })
    void createPublicArticle_IfLatitudeOrLongitudeOver_AutomaticallyAdjusted(
            double latitude, double expectedLatitude, double longitude, double expectedLongitude) {
        final String title = "글제목";
        final String content = "글 내용입니다.ㅎ";

        Map<String, Object> body = new HashMap<>();

        body.put("title", title);
        body.put("content", content);
        body.put("latitude", latitude);
        body.put("longitude", longitude);
        body.put("publicMap", true);
        body.put("privateMap", false);

        String location =
            given()
                .header("Authorization", "Bearer " + token)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/articles")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");

        ArticleResponse savedArticle = ArticleFixture.findPublicArticleById(
            extractArticleIdFrom(location));

        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo(title);
        assertThat(savedArticle.getContent()).isEqualTo(content);
        assertThat(savedArticle.getAuthor()).isNotNull();
        assertThat(savedArticle.getAuthor().getId()).isNotNull();
        assertThat(savedArticle.getLatitude()).isEqualTo(expectedLatitude);
        assertThat(savedArticle.getLongitude()).isEqualTo(expectedLongitude);
        assertThat(savedArticle.getTotalLikes()).isZero();
    }

    @ParameterizedTest
    @DisplayName("전체지도에만 글쓰기 - 토큰이 잘못된 경우 401 Unauthorized 응답")
    @ValueSource(strings = {"", "말도 안되는 토큰"})
    void createPublicArticle_IfTokenIsWrong_Response401(String wrongToken) {
        final String title = "글제목";
        final String content = "글내용";
        final double latitude = 45.0;
        final double longitude = 132.0;

        Map<String, Object> body = new HashMap<>();

        body.put("title", title);
        body.put("content", content);
        body.put("latitude", latitude);
        body.put("longitude", longitude);
        body.put("publicMap", true);
        body.put("privateMap", false);

        given()
            .header("Authorization", "Bearer " + wrongToken)
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/api/articles")
        .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private Long extractArticleIdFrom(String locationHeader) {
        String[] split = locationHeader.split("/");

        return Long.parseLong(split[split.length - 1]);
    }

    @AfterEach
    void rollBack() {
        UserFixture.withdraw(token);
    }
}
