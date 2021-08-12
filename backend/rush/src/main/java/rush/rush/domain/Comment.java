package rush.rush.domain;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    public Comment(String content, User user, Article article) {
        this(null, content, user, article, null);
    }

    public Comment(Long id, String content, User user, Article article, Timestamp createDate) {
        validate(content, user, article);
        this.id = id;
        this.content = content;
        this.user = user;
        this.article = article;
        article.addComment(this);
        this.createDate = createDate;
    }

    private void validate(String content, User user, Article article) {
        if (Objects.isNull(content) || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용이 비어있습니다.");
        }
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new IllegalArgumentException("작성자가 올바르게 지정되지 않았습니다.");
        }
        if (Objects.isNull(article) || Objects.isNull(article.getId())) {
            throw new IllegalArgumentException("글이 올바르게 지정되지 않았습니다.");
        }
    }
}
