package rush.rush.api.fixture;

import static io.restassured.RestAssured.given;

import org.springframework.http.HttpStatus;
import rush.rush.dto.ArticleResponse;

public class ArticleFixture {

    public static ArticleResponse findPublicArticleById(Long articleId) {
        return
            given()
            .when()
                .get("/api/articles/public/" + articleId)
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ArticleResponse.class);
    }
}
