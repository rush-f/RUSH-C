package rush.rush.api;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import rush.rush.api.fixture.Database;
import rush.rush.repository.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ApiTest {

    @LocalServerPort
    protected int port;

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
