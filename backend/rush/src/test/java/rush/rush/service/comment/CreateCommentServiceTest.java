package rush.rush.service.comment;

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
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Group;
import rush.rush.domain.MapType;
import rush.rush.domain.User;
import rush.rush.domain.UserGroup;
import rush.rush.dto.CommentResponse;
import rush.rush.dto.CreateCommentRequest;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.WrongMapTypeException;
import rush.rush.repository.ArticleGroupRepository;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.CommentRepository;
import rush.rush.repository.GroupRepository;
import rush.rush.repository.UserGroupRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CreateCommentServiceTest {

    @Autowired
    CreateCommentService createCommentService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

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
    private CreateCommentRequest createCommentRequest;

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
        createCommentRequest = new CreateCommentRequest("댓글 내용");
    }

    @Test
    @Transactional
    @DisplayName("댓글 생성")
    void create() {
        //when
        CommentResponse publicCommentResponse = createCommentService.create(publicArticle.getId(),
            MapType.PUBLIC, createCommentRequest, user);
        CommentResponse privateCommentResponse = createCommentService.create(privateArticle.getId(),
            MapType.PRIVATE, createCommentRequest, user);
        CommentResponse groupedCommentResponse = createCommentService.create(groupedArticle.getId(),
            MapType.GROUPED, createCommentRequest, user);

        //then
        assertThat(commentRepository.findAllOfPublicArticle(publicArticle.getId()).get(0)
            .getId()).isEqualTo(publicCommentResponse.getId());
        assertThat(commentRepository.findAllOfPublicArticle(publicArticle.getId())).hasSize(1);

        assertThat(
            commentRepository.findAllOfPrivateArticle(privateArticle.getId(), user.getId()).get(0)
                .getId())
            .isEqualTo(privateCommentResponse.getId());
        assertThat(commentRepository.findAllOfPrivateArticle(privateArticle.getId(),
            user.getId())).hasSize(1);

        assertThat(
            commentRepository.findAllOfGroupedArticle(groupedArticle.getId(), user.getId()).get(0)
                .getId())
            .isEqualTo(groupedCommentResponse.getId());
        assertThat(commentRepository.findAllOfGroupedArticle(groupedArticle.getId(),
            user.getId())).hasSize(1);
    }

    @Test
    @Transactional
    @DisplayName("댓글 생성 - 맵타입이 잘못된 경우")
    void create_IfWrongMapType() {
        //when & then
        assertThatThrownBy(
            () -> createCommentService.create(publicArticle.getId(), MapType.from("test"),
                createCommentRequest, user))
            .isInstanceOf(WrongMapTypeException.class);
    }

    @Test
    @Transactional
    @DisplayName("댓글 생성 - 존재하지 않는 게시글에 작성을 시도할 경우")
    void create_IfNotArticleExists() {
        //when & then
        assertThatThrownBy(
            () -> createCommentService.create(100L, MapType.PUBLIC,
                createCommentRequest, user))
            .isInstanceOf(NotArticleExistsException.class);
        assertThatThrownBy(
            () -> createCommentService.create(100L, MapType.PRIVATE,
                createCommentRequest, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
        assertThatThrownBy(
            () -> createCommentService.create(100L, MapType.GROUPED,
                createCommentRequest, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
    @Transactional
    @DisplayName("댓글 생성 - 해당 게시글에 권한이 없는 유저가 작성을 시도할 경우")
    void create_IfNotAuthorized() {
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
            () -> createCommentService.create(privateArticle.getId(), MapType.PRIVATE,
                createCommentRequest, another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
        assertThatThrownBy(
            () -> createCommentService.create(groupedArticle.getId(), MapType.GROUPED,
                createCommentRequest, another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }
}