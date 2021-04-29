package rush.rush.acceptance;

import static io.restassured.RestAssured.given;

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
     *
     * when 글을 쓴다. then 글이 저장된다.
     */
    @Test
    @DisplayName("글을 쓴다.")
    void write() {
        Map<String, String> body = new HashMap<>();

        body.put("title", "제목제목");
        body.put("content", "내용내용");

        given()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/articles")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }
}
