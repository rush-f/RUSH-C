package rush.rush.service.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.ArticleLike;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.MapType;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.NotLikeExistsException;
import rush.rush.exception.WrongMapTypeException;
import rush.rush.repository.ArticleGroupRepository;
import rush.rush.repository.ArticleLikeRepository;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ArticleLikeServiceTest {

    @Autowired
    ArticleLikeService articleLikeService;

    @Autowired
    ArticleLikeRepository articleLikeRepository;

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
                .latitude(0.0)
                .longitude(0.0)
                .publicMap(true)
                .privateMap(false)
                .build()
        );
        privateArticle = articleRepository.save(
            Article.builder()
                .user(user)
                .title("글제목")
                .content("내용내용")
                .latitude(0.0)
                .longitude(0.0)
                .publicMap(false)
                .privateMap(true)
                .build()
        );
        groupedArticle = articleRepository.save(
            Article.builder()
                .user(user)
                .title("글제목")
                .content("내용내용")
                .latitude(0.0)
                .longitude(0.0)
                .publicMap(false)
                .privateMap(false)
                .build()
        );
        Group group = groupRepository.save(
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
    @DisplayName("좋아요 변경 - 좋아요가 없는 경우")
    void changeMyLike_IfNotHasILiked() {
        //when & then 좋아요가 없는데 좋아요가 있다고 해서 예외발생
        assertThatThrownBy(
            () -> articleLikeService.changeMyLike(publicArticle.getId(), MapType.PUBLIC, true,
                user))
            .isInstanceOf(NotLikeExistsException.class);

        // when    public, private, gropued 3가지 경우 확인
        articleLikeService.changeMyLike(publicArticle.getId(), MapType.PUBLIC, false, user);
        articleLikeService.changeMyLike(privateArticle.getId(), MapType.PRIVATE, false, user);
        articleLikeService.changeMyLike(groupedArticle.getId(), MapType.GROUPED, false, user);

        //then
        assertThat(articleLikeRepository.countOfPublicArticle(publicArticle.getId(), user.getId()))
            .isEqualTo(1L);
        assertThat(
            articleLikeRepository.countOfPrivateArticle(privateArticle.getId(), user.getId()))
            .isEqualTo(1L);
        assertThat(
            articleLikeRepository.countOfGroupedArticle(groupedArticle.getId(), user.getId()))
            .isEqualTo(1L);
    }

    @Test
    @Transactional
    @DisplayName("좋아요 변경 - 좋아요가 있는 경우")
    void changeMyLike_IfHasILiked() {
        //given
        articleLikeRepository.save(
            ArticleLike.builder()
                .user(user)
                .article(publicArticle)
                .build()
        );
        articleLikeRepository.save(
            ArticleLike.builder()
                .user(user)
                .article(privateArticle)
                .build()
        );
        articleLikeRepository.save(
            ArticleLike.builder()
                .user(user)
                .article(groupedArticle)
                .build()
        );
        // when   public, private, gropued 3가지 경우 확인
        articleLikeService.changeMyLike(publicArticle.getId(), MapType.PUBLIC, true, user);
        articleLikeService.changeMyLike(privateArticle.getId(), MapType.PRIVATE, true, user);
        articleLikeService.changeMyLike(groupedArticle.getId(), MapType.GROUPED, true, user);

        //then
        assertThat(articleLikeRepository.countOfPublicArticle(publicArticle.getId(), user.getId()))
            .isEqualTo(0L);
        assertThat(
            articleLikeRepository.countOfPrivateArticle(privateArticle.getId(), user.getId()))
            .isEqualTo(0L);
        assertThat(
            articleLikeRepository.countOfGroupedArticle(groupedArticle.getId(), user.getId()))
            .isEqualTo(0L);
    }

    @Test
    @Transactional
    @DisplayName("좋아요 변경 - 맵타입이 잘못된 경우")
    void changeMyLike_IfWrongMapType() {
        //when & then
        assertThatThrownBy(
            () -> articleLikeService.changeMyLike(publicArticle.getId(), MapType.from("test"),
                false, user))
            .isInstanceOf(WrongMapTypeException.class);
    }

    @Test
    @Transactional
    @DisplayName("좋아요 변경 - 해당되는 게시글이 존재하지 않는 경우")
    void changeMyLike_IfNotArticleExists() {
        //when & then   public, private, gropued 3가지 경우 확인
        assertThatThrownBy(() -> articleLikeService.changeMyLike(100L, MapType.PUBLIC, false, user))
            .isInstanceOf(NotArticleExistsException.class);
        assertThatThrownBy(
            () -> articleLikeService.changeMyLike(100L, MapType.PRIVATE, false, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
        assertThatThrownBy(
            () -> articleLikeService.changeMyLike(100L, MapType.GROUPED, false, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
    @Transactional
    @DisplayName("좋아요 변경 - 권한이 없는 유저가 좋아요 변경을 시도하는 경우")
    void changeMyLike_IfNotAuthorized() {
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
            () -> articleLikeService.changeMyLike(privateArticle.getId(), MapType.PRIVATE, false,
                another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
        assertThatThrownBy(
            () -> articleLikeService.changeMyLike(groupedArticle.getId(), MapType.GROUPED, false,
                another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
    @Transactional
    @DisplayName("좋아요 확인")
    void hasILiked() {
        //given
        articleLikeRepository.save(
            ArticleLike.builder()
                .user(user)
                .article(publicArticle)
                .build()
        );

        //when & then
        assertThat(
            articleLikeService.hasILiked(publicArticle.getId(), MapType.PUBLIC, user.getId()))
            .isEqualTo(true);
        assertThat(
            articleLikeService.hasILiked(privateArticle.getId(), MapType.PRIVATE, user.getId()))
            .isEqualTo(false);
    }
}