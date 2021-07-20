package rush.rush.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.ArticleLiking;

public interface ArticleLikingRepository extends JpaRepository<ArticleLiking,Long> {

    Optional<ArticleLiking> findByUserIdAndArticleId(Long userId, Long articleId);

    Integer countByArticleId(Long articleId);

}
