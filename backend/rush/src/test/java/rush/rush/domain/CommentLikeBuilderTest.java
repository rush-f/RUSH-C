package rush.rush.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class CommentLikeBuilderTest {

    @Mock
    private Comment comment;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        comment = Mockito.mock(Comment.class);
        when(comment.getId()).thenReturn(1L);

        user = Mockito.mock(User.class);
        when(user.getId()).thenReturn(1L);
    }

    @Test
    @DisplayName("Builder")
    void builder() {
        CommentLike commentLike = CommentLike.builder()
            .id(1L)
            .comment(comment)
            .user(user)
            .build();
        assertThat(commentLike.getId()).isEqualTo(1L);
        assertThat(commentLike.getComment().getId()).isEqualTo(comment.getId());
        assertThat(commentLike.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Builder - user가 null일 때 예외처리")
    void builder_IfUserIsNull_ThrowException() {
        assertThatThrownBy(() -> CommentLike.builder()
            .id(1L)
            .comment(comment)
            .build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void builder_IfUserIdIsNull_ThrowException() {
        // given : user id가 null이다.
        when(user.getId()).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> CommentLike.builder()
            .id(1L)
            .comment(comment)
            .user(user)
            .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void builder_IfCommentIsNull_ThrowException() {
        assertThatThrownBy(() -> CommentLike.builder()
            .id(1L)
            .user(user)
            .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    void builder_IfCommentIdIsNull_ThrowException() {
        // given : comment id가 null이다.
        when(comment.getId()).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> CommentLike.builder()
            .id(1L)
            .comment(comment)
            .user(user)
            .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
