package rush.rush.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .email("test@test.com")
            .password("test password")
            .invitationCode("test invitation Code")
            .nickName("test")
            .provider(AuthProvider.local)
            .build();
        savedUser = userRepository.save(user);
    }

    @Test
    void create() {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest("af", "sdf", 0, 0);
        assertThat(articleService.create(createArticleRequest, savedUser)).isNotNull();
    }
}
