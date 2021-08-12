package rush.rush.domain;

import java.sql.Timestamp;
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
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class ArticleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private Group group;

    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public ArticleGroup(Long id, Article article, Group group, Timestamp createDate) {
        validate(article);
        validate(group);
        this.id = id;
        this.article = article;
        this.group = group;
        this.createDate = createDate;
    }
    
    private void validate(Article article) {
        if (Objects.isNull(article)) {
            throw new IllegalArgumentException("article이 null일 수 없습니다.");
        }
        if (Objects.isNull(article.getId())) {
            throw new IllegalArgumentException("article에 id가 없습니다.");
        }
    }
    
    private void validate(Group group) {
        if (Objects.isNull(group)) {
            throw new IllegalArgumentException("group이 null일 수 없습니다.");
        }
        if (Objects.isNull(group.getId())) {
            throw new IllegalArgumentException("group에 id가 없습니다.");
        }
    }
}
