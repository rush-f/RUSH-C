package rush.rush.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.ArticleSummaryResponse;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.repository.ArticleGroupRepository;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FindArticleServiceTest {

    @Autowired
    FindArticleService findArticleService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    ArticleGroupRepository articleGroupRepository;

    private User user;
    private Article publicArticle;
    private Article privateArticle;
    private Article groupedArticle;
    private Group group;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("test password")
                .nickName("test")
                .provider(AuthProvider.local)
                .build()
        );
        publicArticle = articleRepository.save(
            Article.builder()
                .user(user)
                .title("글제목")
                .content("내용내용")
                .latitude(70.0)
                .longitude(100.0)
                .publicMap(true)
                .privateMap(false)
                .build()
        );
        privateArticle = articleRepository.save(
            Article.builder()
                .user(user)
                .title("글제목")
                .content("내용내용")
                .latitude(70.0)
                .longitude(100.0)
                .publicMap(false)
                .privateMap(true)
                .build()
        );
        groupedArticle = articleRepository.save(
            Article.builder()
                .user(user)
                .title("글제목")
                .content("내용내용")
                .latitude(70.0)
                .longitude(100.0)
                .publicMap(false)
                .privateMap(false)
                .build()
        );
        group = groupRepository.save(
            Group.builder()
                .name("test")
                .build()
        );
        articleGroupRepository.save(
            ArticleGroup.builder()
                .article(groupedArticle)
                .group(group)
                .build()
        );
        userGroupRepository.save(
            UserGroup.builder()
                .group(group)
                .user(user)
                .important(false)
                .build()
        );
    }

    @Test
    @Transactional
    @DisplayName("전체 게시글 찾기")
    void findPublicArticle() {
        //when
        ArticleResponse articleResponse = findArticleService.findPublicArticle(
            publicArticle.getId());

        //then
        assertThat(articleResponse.getId()).isEqualTo(publicArticle.getId());
    }

    @Test
    @Transactional
    @DisplayName("전체 게시글 찾기 - 해당되는 게시글이 존재하지 않는 경우")
    void findPublicArticle_IfNotArticleExists() {
        //when & then
        assertThatThrownBy(
            () -> findArticleService.findPublicArticle(100L))
            .isInstanceOf(NotArticleExistsException.class);
    }

    @Test
    @Transactional
    @DisplayName("개인 게시글 찾기")
    void findPrivateArticle() {
        //when
        ArticleResponse articleResponse = findArticleService.findPrivateArticle(
            privateArticle.getId(), user);

        //then
        assertThat(articleResponse.getId()).isEqualTo(privateArticle.getId());
    }

    @Test
    @Transactional
    @DisplayName("개인 게시글 찾기 - 해당되는 게시글이 존재하지 않는 경우")
    void findPrivateArticle_IfNotArticleExists() {
        //when & then
        assertThatThrownBy(
            () -> findArticleService.findPrivateArticle(100L, user))
            .isInstanceOf(NotArticleExistsException.class);
    }

    @Test
    @Transactional
    @DisplayName("그룹 게시글 찾기")
    void findGroupArticle() {
        //when
        ArticleResponse articleResponse = findArticleService.findGroupArticle(
            groupedArticle.getId(), user);

        //then
        assertThat(articleResponse.getId()).isEqualTo(groupedArticle.getId());
    }

    @Test
    @Transactional
    @DisplayName("그룹 게시글 찾기 - 해당되는 게시글이 존재하지 않는 경우")
    void findGroupArticle_IfNotArticleExists() {
        //when & then
        assertThatThrownBy(
            () -> findArticleService.findGroupArticle(100L, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
    @Transactional
    @DisplayName("그룹 게시글 찾기 - 권한이 없는 유저인 경우")
    void findGroupArticle_IfNotAuthorized() {
        //given
        User another = userRepository.save(
            User.builder()
                .email("test2@test.com")
                .password("test password")
                .nickName("test2")
                .provider(AuthProvider.local)
                .build()
        );

        //when & then
        assertThatThrownBy(
            () -> findArticleService.findGroupArticle(groupedArticle.getId(), another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
    @Transactional
    @DisplayName("전체 게시글 리스트 찾기")
    void findPublicMapArticles() {
        //when
        List<ArticleSummaryResponse> articles1 = findArticleService
            .findPublicMapArticles(70.0, 5.0, 100.0, 5.0);
        List<ArticleSummaryResponse> articles2 = findArticleService
            .findPublicMapArticles(0.0, 5.0, 0.0, 5.0);

        //then
        assertThat(articles1).hasSize(1);
        assertThat(articles2).hasSize(0);
    }

    @Test
    @Transactional
    @DisplayName("개인 게시글 리스트 찾기")
    void findPrivateMapArticles() {
        //when
        List<ArticleSummaryResponse> articles1 = findArticleService
            .findPrivateMapArticles(70.0, 5.0, 100.0, 5.0, user);
        List<ArticleSummaryResponse> articles2 = findArticleService
            .findPrivateMapArticles(0.0, 5.0, 0.0, 5.0, user);

        //then
        assertThat(articles1).hasSize(1);
        assertThat(articles2).hasSize(0);
    }

    @Test
    @Transactional
    @DisplayName("그룹 게시글 리스트 찾기")
    void findGroupedMapArticles() {
        //when
        List<ArticleSummaryResponse> articles1 = findArticleService
            .findGroupedMapArticles(group.getId(), 70.0, 5.0, 100.0, 5.0, user);
        List<ArticleSummaryResponse> articles2 = findArticleService
            .findGroupedMapArticles(group.getId(), 0.0, 5.0, 0.0, 5.0, user);

        //then
        assertThat(articles1).hasSize(1);
        assertThat(articles2).hasSize(0);
    }
}