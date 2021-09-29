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
import rush.rush.exception.WrongInputException;

@Entity
@Getter
@NoArgsConstructor
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Builder
    public ArticleLike(Long id, User user, Article article) {
        validate(user);
        validate(article);
        this.id = id;
        this.user = user;
        this.article = article;
        article.addArticleLike(this);
    }

    private void validate(User user) {
        if (Objects.isNull(user)) {
            throw new WrongInputException("user가 null일 수 없습니다.");
        }
        if (Objects.isNull(user.getId())) {
            throw new WrongInputException("user에 id가 없습니다.");
        }
    }

    private void validate(Article article) {
        if (Objects.isNull(article)) {
            throw new WrongInputException("article이 null일 수 없습니다.");
        }
        if (Objects.isNull(article.getId())) {
            throw new WrongInputException("article에 id가 없습니다.");
        }
    }
}
