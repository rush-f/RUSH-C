package rush.rush.service;

import org.junit.jupiter.api.BeforeEach;
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

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CommentServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .email("test@test.com")
            .password("test password")
//            .invitationCode("test invitation Code")
            .nickName("test")
            .provider(AuthProvider.local)
            .build();
        savedUser = userRepository.save(user);
    }

    @Test
    void findCommentsByArticleId() {
        // given
        Article article = Article.builder()
                .title("title")
                .content("content")
                .latitude(0.0)
                .longitude(0.0)
                .user(savedUser)
                .doesBelongToPrivate(true)
                .doesBelongToPublic(true)
                .build();
        articleRepository.save(article);

        Comment comment = new Comment("댓글내용", savedUser, article);
        comment = commentRepository.save(comment);

        // when & then
        List<CommentResponse> comments = commentService.findCommentsByArticleId(article.getId());

        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getId()).isEqualTo(comment.getId());
        assertThat(comments.get(0).getContent()).isEqualTo(comment.getContent());
    }
}
