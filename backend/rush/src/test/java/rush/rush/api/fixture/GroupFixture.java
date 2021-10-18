package rush.rush.api.fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rush.rush.api.util.LocationHeaderUtil;
import rush.rush.dto.GroupResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GroupFixture {

    /**
     * @return 그룹 id
     */
    public static Long createGroup(String groupName, String token) {
        Map<String, String> body = new HashMap<>();
        body.put("name", groupName);

        String location =
            given()
                .header("Authorization", "Bearer " + token)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post("/api/groups")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");

        return LocationHeaderUtil.extractIdFrom(location);
    }

    public static String findInvitationCode(Long groupId, String groupMemberToken) {
        GroupResponse groupResponse =
            given()
                .header("Authorization", "Bearer " + groupMemberToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .get("/api/groups/" + groupId)
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(GroupResponse.class);

        return groupResponse.getInvitationCode();
    }

    public static void join(String invitationCode, String token) {
        given()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/api/groups/join?invitation_code=" + invitationCode)
            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    public static void withdraw(Long groupId, String token) {
        given()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .delete("/api/groups/" + groupId)
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
