package rush.rush.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLike(Long id, User user, Comment comment) {
        validate(user);
        validate(comment);
        this.id = id;
        this.user = user;
        this.comment = comment;
    }

    private void validate(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("user가 null일 수 없습니다.");
        }
        if (Objects.isNull(user.getId())) {
            throw new IllegalArgumentException("user에 id가 없습니다.");
        }
    }

    private void validate(Comment comment) {
        if (Objects.isNull(comment)) {
            throw new IllegalArgumentException("article이 null일 수 없습니다.");
        }
        if (Objects.isNull(comment.getId())) {
            throw new IllegalArgumentException("article에 id가 없습니다.");
        }
    }
}
