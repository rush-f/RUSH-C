package rush.rush.api.fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class UserFixture {

    public static void withdraw(String token) {
        given()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .delete("/api/users/me")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
