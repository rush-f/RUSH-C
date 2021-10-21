package rush.rush.api.article.findList;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import rush.rush.api.ApiTest;
import rush.rush.api.fixture.AuthFixture;
import rush.rush.api.fixture.CreateArticleFixture;
import rush.rush.dto.ArticleSummaryResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("온누리 발자국 글 목록 조회")
public class FindPublicArticlesTest extends ApiTest {

    private List<String> tokens = new ArrayList<>();

    @BeforeEach
    void setUp() {
        RestAssured.port = port;    // Todo: 뺴볼것

        List<String> tokens = new ArrayList<>();

        // 여러 사람들
        for (int i = 0; i < 10; i++) {
            final String nickName = "user" + i;
            final String email = nickName + "@email.com";
            final String password = "password" + i;

            AuthFixture.signUp(nickName, email, password);        // 회원가입
            String token = AuthFixture.login(email, password);    // 로그인
            tokens.add(token);

            /*
             * 글쓰기
             * (위도 -90 & 경도 -180), (위도 -70 & 경도 -140), (위도 -50 & 경도 -100),
             * (위도 -30 & 경도  -60), (위도 -10 & 경도  -20), (위도  10 & 경도   20),
             * (위도  30 & 경도   60), (위도  50 & 경도  100), (위도  70 & 경도  140),
             * (위도  90 & 경도  180)
             */
            double latitude = -90 + 20 * i;
            double longitude = -180 + 40 * i;
            CreateArticleFixture.createArticle(token, "제목" + i, "내용" + "i", latitude, longitude, true, false, null);
        }
        this.tokens = Collections.unmodifiableList(tokens);
    }

    @ParameterizedTest
    @CsvSource({
        "0,90,0,180,10",       // 전체
        "0,70,0,360,8",        // 위도  -70 이상  70 이하
        "0,50,0,360,6",        // 위도  -50 이상  50 이하
        "-5,35,0,360,4",       // 위도  -40 이상  30 이하
        "0,20,0,360,2",        // 위도  -20 이상  20 이하
        "0,90,0,140,8",        // 경도 -140 이상 140 이하
        "0,90,0,100,6",        // 경도 -100 이상 100 이하
        "0,90,10,70,4",        // 경도  -60 이상  80 이하
        "0,90,10,70,4",        // 경도  -60 이상  80 이하
        "0,90,0,20,2",         // 경도  -20 이상  20 이하
    })
    void findPublicArticle(double latitude, double latitudeRange, double longitude, double longitudeRange, int articleCount) {
        List<ArticleSummaryResponse> result =
            given()
            .when()
                .get("/api/articles/public?latitude=" + latitude + "&latitudeRange=" + latitudeRange
                        + "&longitude=" + longitude + "&longitudeRange=" + longitudeRange)
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("", ArticleSummaryResponse.class);

        assertThat(result).hasSize(articleCount);
    }
}
