package rush.rush.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.User;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.exception.NotIncludedMapException;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CreateArticleServiceTest {

    @Autowired
    private CreateArticleService createArticleService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void create() {
        // given
        User user = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("test password")
                .nickName("test")
                .provider(AuthProvider.local)
                .build()
        );

        // when & then
        CreateArticleRequest createArticleRequest = new CreateArticleRequest(
            "af", "sdf", 0, 0, true, true, null);
        assertThat(createArticleService.create(createArticleRequest, user)).isNotNull();

        // when & then   어느지도에도 속하지 않는 경우
        CreateArticleRequest wrongRequest = new CreateArticleRequest(
            "af", "sdf", 0, 0, false, false, null);
        assertThatThrownBy(
            () -> createArticleService.create(wrongRequest, user))
            .isInstanceOf(NotIncludedMapException.class);
    }
}
