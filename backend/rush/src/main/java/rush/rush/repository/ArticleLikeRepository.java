package rush.rush.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike,Long> {

    Optional<ArticleLike> findByUserIdAndArticleId(Long userId, Long articleId);

    Integer countByArticleId(Long articleId);

    Integer countByUserIdAndArticleId(Long userId, Long ArticleId);

}
