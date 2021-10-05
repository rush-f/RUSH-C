package rush.rush.domain;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import rush.rush.exception.WrongInputException;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "article_id")
    private Article article;

    @CreationTimestamp
    private Timestamp createDate;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Builder
    public Comment(Long id, String content, User user, Article article, Timestamp createDate, List<CommentLike> commentLikes) {
        validate(content, user, article);
        this.id = id;
        this.content = content;
        this.user = user;
        this.article = article;
        article.addComment(this);
        this.createDate = createDate;
        if (Objects.nonNull(commentLikes)) {
            this.commentLikes = commentLikes;
        }
    }

    private void validate(String content, User user, Article article) {
        if (Objects.isNull(content) || content.trim().isEmpty()) {
            throw new WrongInputException("내용이 비어있습니다.");
        }
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new WrongInputException("작성자가 올바르게 지정되지 않았습니다.");
        }
        if (Objects.isNull(article) || Objects.isNull(article.getId())) {
            throw new WrongInputException("글이 올바르게 지정되지 않았습니다.");
        }
    }
}
