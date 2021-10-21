package rush.rush.api.article.create;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rush.rush.api.ApiTest;
import rush.rush.api.fixture.AuthFixture;
import rush.rush.api.fixture.FindArticleFixture;
import rush.rush.api.util.LocationHeaderUtil;
import rush.rush.dto.ArticleResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("온누리 발자국에 글쓰기")
public class CreatePublicMapArticleTest extends ApiTest {

    private String token;

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
    @CsvSource({
        "a,a,-90,-180",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,-90,-180",
        "a,a,90,180",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,0,0",
        "a,a,90,180",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,0,0"
    })
    void createPublicArticle(String title, String content, double latitude, double longitude) {
        /*
         * POST /api/articles
         * Content-Type: application/json
         * Authorization: Bearer 토큰값
         *
         * {
         *   "title": "글제목",  // 1 이상 무한대 이하
         *   "content": "글내용입니다.", // 1 이상 무한대
         *   "latitude": 0,  // -90 이상 90 이하
         *   "longitude": 0, // -180 이상 180 이하
         *   "publicMap": "true",
         *   "privateMap": "false"
         * }
         */
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

        // when : location 헤더 값을 가지고 온누리 발자국 글을 조회한다.
        ArticleResponse savedArticle = FindArticleFixture.findPublicArticle(
            LocationHeaderUtil.extractIdFrom(location));

        // then : 방금 작성한 글의 글 상세가 조회된다.
        assert savedArticle != null;
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo(title);
        assertThat(savedArticle.getContent()).isEqualTo(content);
        assertThat(savedArticle.getAuthor()).isNotNull();
        assertThat(savedArticle.getAuthor().getId()).isNotNull();
        assertThat(savedArticle.getLatitude()).isEqualTo(latitude);
        assertThat(savedArticle.getLongitude()).isEqualTo(longitude);
        assertThat(savedArticle.getTotalLikes()).isZero();
    }

    @DisplayName("위도 or 경도가 너무 클 때 값 자동조정")
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

        ArticleResponse savedArticle = FindArticleFixture.findPublicArticle(
                LocationHeaderUtil.extractIdFrom(location));

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
    @DisplayName("토큰이 잘못된 경우 401 Unauthorized 응답")
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
}
