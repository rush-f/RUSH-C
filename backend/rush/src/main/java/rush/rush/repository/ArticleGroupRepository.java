package rush.rush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.ArticleGroup;

public interface ArticleGroupRepository extends JpaRepository<ArticleGroup, Long> {
}