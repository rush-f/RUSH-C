package rush.rush.api.fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rush.rush.api.util.LocationHeaderUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class CreateArticleFixture {

    public static Long createArticle(String token, String title, String content, double latitude, double longitude,
            boolean publicMap, boolean privateMap, List<Long> groupIds) {
        Map<String, Object> body = new HashMap<>();

        body.put("title", title);
        body.put("content", content);
        body.put("latitude", latitude);
        body.put("longitude", longitude);
        body.put("publicMap", publicMap);
        body.put("privateMap", privateMap);

        if (Objects.nonNull(groupIds) && !groupIds.isEmpty()) {
            body.put("groupIdsToBeIncluded", groupIds);
        }
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

        return LocationHeaderUtil.extractIdFrom(location);
    }
}
