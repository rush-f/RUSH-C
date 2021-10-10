package rush.rush.api.fixture;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rush.rush.dto.AuthResponse;

public class AuthFixture {

    public static void signUp(String nickName, String email, String password) {
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

    public static String login(String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        AuthResponse authResponse =
            given()
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
