package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByIsPublicTrueAndLatitudeBetweenAndLongitudeBetween(
        Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);

    Optional<Article> findByIsPublicTrueAndId(Long articleId);
}
