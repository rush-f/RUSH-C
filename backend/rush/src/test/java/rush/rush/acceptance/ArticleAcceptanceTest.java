package rush.rush.acceptance;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rush.rush.dto.ArticleResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleAcceptanceTest {

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    /**
     * feature : 글을 쓴다
     * <p>
     * when 글을 쓴다. then 글이 저장된다.
     * <p>
     * when 쓴 글을 조회한다. then 글이 조회된다.
     */
    @Test
    @DisplayName("글을 쓴다.")
    void useArticle() {
        Map<String, String> body = new HashMap<>();

        body.put("title", "제목제목");
        body.put("content", "내용내용");

        String location = write(body);

        ArticleResponse articleResponse = findOne(location);

        Long expectedId = extractIdFromLocation(location);

        assertThat(articleResponse.getId()).isEqualTo(expectedId);
    }

    private String write(Map<String, String> body) {
        return given()
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

    private ArticleResponse findOne(String location) {
        return given()
            .when()
                .get(location)
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
