package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class CommentTest {

    @Mock
    private User user;

    @Mock
    private Article article;

    @Mock
    private CommentLike commentLike;

    @BeforeEach
    void setUp() {
        user = Mockito.mock(User.class);
        when(user.getId()).thenReturn(1L);

        article = Mockito.mock(Article.class);
        when(article.getId()).thenReturn(1L);

        commentLike = Mockito.mock(CommentLike.class);
        when(commentLike.getId()).thenReturn(1L);
    }

    @Test
    @DisplayName("Builder - Collection 필드를 주입하지 않는 경우")
    void builder_IfNotInjectCollectionFields_EmptyCollection() {
        Comment comment = Comment.builder()
            .id(1L)
            .article(article)
            .user(user)
            .content("댓글내용임")
            .build();

        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getUser().getId()).isEqualTo(user.getId());
        assertThat(comment.getArticle().getId()).isEqualTo(article.getId());

        assertThat(comment.getCommentLikes()).isNotNull();
        assertThat(comment.getCommentLikes()).hasSize(0);
    }

    @Test
    @DisplayName("Builder - Collection 필드를 주입할 경우")
    void builder_withCollectionFieldInjection() {
        List<CommentLike> commentLikeList = new ArrayList<>();
        commentLikeList.add(commentLike);

        Comment comment = Comment.builder()
            .id(1L)
            .article(article)
            .user(user)
            .content("댓글내용임")
            .createDate(Timestamp.valueOf(LocalDateTime.now()))
            .commentLikes(commentLikeList)
            .build();

        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getCommentLikes()).hasSize(1);
    }
}