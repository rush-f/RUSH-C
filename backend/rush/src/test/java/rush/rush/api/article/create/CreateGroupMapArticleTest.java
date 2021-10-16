package rush.rush.api.article.create;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rush.rush.api.fixture.ArticleFixture;
import rush.rush.api.fixture.AuthFixture;
import rush.rush.api.fixture.Database;
import rush.rush.api.fixture.GroupFixture;
import rush.rush.api.util.LocationHeaderUtil;
import rush.rush.dto.ArticleResponse;
import rush.rush.repository.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("우리누리 발자국에 글쓰기")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateGroupMapArticleTest {

    private String writerToken;
    private String groupMemberToken;
    private String anotherToken;
    private Long groupId;

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        /* 글 작성자 */
        final String nickName = "김예림";
        final String email = "test@email.com";
        final String password = "password";

        AuthFixture.signUp(nickName, email, password);
        writerToken = AuthFixture.login(email, password);

        /* 그룹 멤버 */
        final String groupMemberNickName = "이종성";
        final String groupMemberEmail = "test1@email.com";
        final String groupMemberPassword = "testPassword";

        AuthFixture.signUp(groupMemberNickName, groupMemberEmail, groupMemberPassword);
        groupMemberToken = AuthFixture.login(groupMemberEmail, groupMemberPassword);

        /* 그룹 결성 */
        groupId = GroupFixture.createGroup("그룹이름", groupMemberToken);
        String invitationCode = GroupFixture.findInvitationCode(groupId, groupMemberToken);
        GroupFixture.join(invitationCode, writerToken);

        /* 완벽한 타인 */
        final String anotherNickName = "홍길동";
        final String anotherEmail = "gildong@seoultech.ac.kr";
        final String anotherPassword = "tthhhhhh";

        AuthFixture.signUp(anotherNickName, anotherEmail, anotherPassword);
        anotherToken = AuthFixture.login(anotherEmail, anotherPassword);
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
    void createGroupArticle(String title, String content, double latitude, double longitude) {
        Map<String, Object> body = new HashMap<>();

        body.put("title", title);
        body.put("content", content);
        body.put("latitude", latitude);
        body.put("longitude", longitude);
        body.put("publicMap", false);
        body.put("privateMap", false);
        body.put("groupIdsToBeIncluded", List.of(groupId));

        String location =
            given()
                .header("Authorization", "Bearer " + writerToken)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .log().all()
                .post("/api/articles")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header("location");

        Long articleId = LocationHeaderUtil.extractIdFrom(location);

        // when 글 작성자가 조회 시도, then 조회 성공
        ArticleResponse savedArticle = ArticleFixture.findGroupArticle(articleId, writerToken);

        assert savedArticle != null;
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo(title);
        assertThat(savedArticle.getContent()).isEqualTo(content);
        assertThat(savedArticle.getAuthor()).isNotNull();
        assertThat(savedArticle.getAuthor().getId()).isNotNull();
        assertThat(savedArticle.getLatitude()).isEqualTo(latitude);
        assertThat(savedArticle.getLongitude()).isEqualTo(longitude);
        assertThat(savedArticle.getTotalLikes()).isZero();

        // when 그룹원이 조회 시도, then 조회 성공
        savedArticle = ArticleFixture.findGroupArticle(articleId, groupMemberToken);

        assert savedArticle != null;
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo(title);
        assertThat(savedArticle.getContent()).isEqualTo(content);
        assertThat(savedArticle.getAuthor()).isNotNull();
        assertThat(savedArticle.getAuthor().getId()).isNotNull();
        assertThat(savedArticle.getLatitude()).isEqualTo(latitude);
        assertThat(savedArticle.getLongitude()).isEqualTo(longitude);
        assertThat(savedArticle.getTotalLikes()).isZero();

        // when 타인이 조회 시도  then 조회 실패
        given()
            .header("Authorization", "Bearer " + anotherToken)
        .when()
            .log().all()
            .get("/api/articles/grouped/" + articleId)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @AfterEach
    void rollBack(@Autowired ArticleGroupRepository articleGroupRepository,
                  @Autowired UserGroupRepository userGroupRepository,
                  @Autowired CommentLikeRepository commentLikeRepository,
                  @Autowired ArticleLikeRepository articleLikeRepository,
                  @Autowired CommentRepository commentRepository,
                  @Autowired ArticleRepository articleRepository,
                  @Autowired GroupRepository groupRepository,
                  @Autowired UserRepository userRepository) {
        Database.clearAll(articleGroupRepository);
        Database.clearAll(userGroupRepository);
        Database.clearAll(commentLikeRepository);
        Database.clearAll(articleLikeRepository);
        Database.clearAll(commentRepository);
        Database.clearAll(articleRepository);
        Database.clearAll(groupRepository);
        Database.clearAll(userRepository);
    }
}
