package rush.rush.service.article;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rush.rush.domain.Article;
import rush.rush.domain.AuthProvider;
import rush.rush.domain.Comment;
import rush.rush.domain.User;
import rush.rush.dto.CommentResponse;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.CommentRepository;
import rush.rush.repository.UserRepository;
import rush.rush.service.comment.FindCommentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FindCommentServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FindCommentService findCommentService;

    @Test
    @Transactional
    void findCommentsByArticleId() {
        // given
        User user = User.builder()
            .email("test@test.com")
            .password("test password")
            .nickName("test")
            .provider(AuthProvider.local)
            .build();
        User savedUser = userRepository.save(user);

        Article article = Article.builder()
            .title("title")
            .content("content")
            .latitude(0.0)
            .longitude(0.0)
            .user(savedUser)
            .publicMap(true)
            .privateMap(true)
            .build();
        articleRepository.save(article);

        Comment comment = new Comment(null, "댓글내용", savedUser, article, null, null);
        comment = commentRepository.save(comment);

        // when
        List<CommentResponse> comments = findCommentService.findCommentsOfPublicArticle(article.getId());

        // then
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getId()).isEqualTo(comment.getId());
        assertThat(comments.get(0).getContent()).isEqualTo(comment.getContent());
    }
}