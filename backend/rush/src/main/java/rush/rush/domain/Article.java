package rush.rush.domain;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @NotNull
    private String content;

    @CreationTimestamp
    private Timestamp createDate;

    public Article(String title, String content) {
        this(null, title, content, null);
    }

    public Article(Long id, String title, String content, Timestamp createDate) {
        validate(title, content);
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    private void validate(String title, String content) {
        if (Objects.isNull(title) || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목이 비어있습니다.");
        }
        if (Objects.isNull(content) || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용이 비어있습니다.");
        }
    }
}
