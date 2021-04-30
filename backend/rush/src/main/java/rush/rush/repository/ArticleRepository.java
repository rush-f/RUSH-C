package rush.rush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
