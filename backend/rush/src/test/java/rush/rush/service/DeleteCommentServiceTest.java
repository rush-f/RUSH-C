package rush.rush.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Comment;
import rush.rush.domain.User;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.CommentRepository;
import rush.rush.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class DeleteCommentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DeleteCommentService deleteCommentService;

    @Test
    @Transactional
    @DisplayName("댓글 삭제")
    void deleteComment() {
        // given : 글에 댓글이 한개 저장되어있다.
        User savedUser = userRepository.save(
            User.builder()
                .email("test@test.com")
                .password("test password")
                .nickName("test")
                .provider(AuthProvider.local)
                .build()
        );

        Article savedArticle = articleRepository.save(
            Article.builder()
                .title("title")
                .content("content")
                .latitude(0.0)
                .longitude(0.0)
                .user(savedUser)
                .privateMap(true)
                .publicMap(true)
                .build()
        );

        Comment comment = commentRepository.save(
            Comment.builder()
                .user(savedUser)
                .content("댓글내용")
                .article(savedArticle)
                .build()
        );

        // when : 댓글을 삭제한다.
        deleteCommentService.deleteComment(comment.getId(), savedUser);

        // then : 댓글이 삭제되었다.
        assertThat(commentRepository.findAllOfPublicArticle(savedArticle.getId()))
            .hasSize(0);
    }

    @Test
    @Transactional
    @DisplayName("댓글 삭제 - 본인 댓글이 아닌데 삭제 시도시 예외처리")
    void deleteComment_IfNotAuthor_ThrowException() {
        // given : 글에 댓글이 한개 저장되어있다.
        User articleAuthor = userRepository.save(
            User.builder()
                .email("articleAuthor@test.com")
                .password("test password")
                .nickName("articleAuthor")
                .provider(AuthProvider.local)
                .build()
        );
        Article savedArticle = articleRepository.save(
            Article.builder()
                .title("title")
                .content("content")
                .latitude(0.0)
                .longitude(0.0)
                .user(articleAuthor)
                .privateMap(true)
                .publicMap(true)
                .build()
        );
        User commentAuthor = userRepository.save(
            User.builder()
                .email("commentAuthor@test.com")
                .password("test password")
                .nickName("commentAuthor")
                .provider(AuthProvider.local)
                .build()
        );
        Comment comment = Comment.builder()
            .user(commentAuthor)
            .content("댓글내용")
            .article(savedArticle)
            .build();
        commentRepository.save(comment);

        // when & then : 댓글 작성자가 아닌 사람이 댓글 삭제 시도 -> 댓글 삭제 실패
        assertThatThrownBy(() -> deleteCommentService.deleteComment(comment.getId(), articleAuthor))
            .isInstanceOf(IllegalArgumentException.class);

        // then : 댓글이 삭제되지 않았다.
        assertThat(commentRepository.findAllOfPublicArticle(savedArticle.getId()))
            .hasSize(1);
    }
}