package rush.rush.service.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rush.rush.domain.*;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.NotCommentExistException;
import rush.rush.exception.NotLikeExistsException;
import rush.rush.exception.WrongMapTypeException;
import rush.rush.repository.*;
import rush.rush.service.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentLikeServiceTest extends ServiceTest {

    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

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
    private Comment publicComment;
    private Comment privateComment;
    private Comment groupedComment;

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
        publicComment = commentRepository.save(
            Comment.builder()
                .user(user)
                .content("댓글내용")
                .article(publicArticle)
                .build()
        );
        privateComment = commentRepository.save(
            Comment.builder()
                .user(user)
                .content("댓글내용")
                .article(privateArticle)
                .build()
        );
        groupedComment = commentRepository.save(
            Comment.builder()
                .user(user)
                .content("댓글내용")
                .article(groupedArticle)
                .build()
        );
    }

    @Test
    @DisplayName("좋아요 변경 - 좋아요가 없는 경우")
    void changeMyLike_IfNotHasILiked() {
        //when & then 좋아요가 없는데 좋아요가 있다고 해서 예외발생
        assertThatThrownBy(
            () -> commentLikeService.changeMyLike(publicComment.getId(), MapType.PUBLIC, true,
                user))
            .isInstanceOf(NotLikeExistsException.class);

        // when    public, private, gropued 3가지 경우 확인
        commentLikeService.changeMyLike(publicComment.getId(), MapType.PUBLIC, false, user);
        commentLikeService.changeMyLike(privateComment.getId(), MapType.PRIVATE, false, user);
        commentLikeService.changeMyLike(groupedComment.getId(), MapType.GROUPED, false, user);

        //then
        assertThat(commentLikeRepository.findPublicArticleCommentIdsILiked(publicArticle.getId(), user.getId()))
            .hasSize(1);
        assertThat(
            commentLikeRepository.findPrivateArticleCommentIdsILiked(privateArticle.getId(), user.getId()))
            .hasSize(1);
        assertThat(
            commentLikeRepository.findGroupedArticleCommentIdsILiked(groupedArticle.getId(), user.getId()))
            .hasSize(1);
    }

    @Test
    @DisplayName("좋아요 변경 - 좋아요가 있는 경우")
    void changeMyLike_IfHasILiked() {
        //given
        commentLikeRepository.save(
            CommentLike.builder()
                .user(user)
                .comment(publicComment)
                .build()
        );
        commentLikeRepository.save(
            CommentLike.builder()
                .user(user)
                .comment(privateComment)
                .build()
        );
        commentLikeRepository.save(
            CommentLike.builder()
                .user(user)
                .comment(groupedComment)
                .build()
        );

        // when   public, private, gropued 3가지 경우 확인
        commentLikeService.changeMyLike(publicComment.getId(), MapType.PUBLIC, true, user);
        commentLikeService.changeMyLike(privateComment.getId(), MapType.PRIVATE, true, user);
        commentLikeService.changeMyLike(groupedComment.getId(), MapType.GROUPED, true, user);

        //then
        assertThat(commentLikeRepository.findPublicArticleCommentIdsILiked(publicArticle.getId(), user.getId()))
            .hasSize(0);
        assertThat(
            commentLikeRepository.findPrivateArticleCommentIdsILiked(privateArticle.getId(), user.getId()))
            .hasSize(0);
        assertThat(
            commentLikeRepository.findGroupedArticleCommentIdsILiked(groupedArticle.getId(), user.getId()))
            .hasSize(0);
    }

    @Test
    @DisplayName("좋아요 변경 - 맵타입이 잘못된 경우")
    void changeMyLike_IfWrongMapType() {
        //when & then
        assertThatThrownBy(
            () -> commentLikeService.changeMyLike(publicComment.getId(), MapType.from("test"),
                false, user))
            .isInstanceOf(WrongMapTypeException.class);
    }

    @Test
    @DisplayName("좋아요 변경 - 해당되는 댓글이 존재하지 않는 경우")
    void changeMyLike_IfNotArticleExists() {
        //when & then   public, private, gropued 3가지 경우 확인
        assertThatThrownBy(() -> commentLikeService.changeMyLike(10L, MapType.PUBLIC, false, user))
            .isInstanceOf(NotCommentExistException.class);
        assertThatThrownBy(
            () -> commentLikeService.changeMyLike(10L, MapType.PRIVATE, false, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
        assertThatThrownBy(
            () -> commentLikeService.changeMyLike(10L, MapType.GROUPED, false, user))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
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
            () -> commentLikeService.changeMyLike(privateComment.getId(), MapType.PRIVATE, false,
                another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
        assertThatThrownBy(
            () -> commentLikeService.changeMyLike(groupedComment.getId(), MapType.GROUPED, false,
                another))
            .isInstanceOf(NotAuthorizedOrExistException.class);
    }

    @Test
    @DisplayName("좋아요 확인")
    void hasILiked() {
        //given
        commentLikeRepository.save(
            CommentLike.builder()
                .user(user)
                .comment(publicComment)
                .build()
        );

        //when & then
        assertThat(
            commentLikeService.hasILiked(publicArticle.getId(), MapType.PUBLIC, user.getId()))
            .hasSize(1);
        assertThat(
            commentLikeService.hasILiked(publicArticle.getId(), MapType.PUBLIC, user.getId()).get(0))
            .isEqualTo(publicComment.getId());
        assertThat(
            commentLikeService.hasILiked(privateArticle.getId(), MapType.PRIVATE, user.getId()))
            .hasSize(0);
    }
}
