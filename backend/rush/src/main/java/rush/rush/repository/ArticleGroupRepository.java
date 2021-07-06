package rush.rush.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.ArticleGroup;

public interface ArticleGroupRepository extends JpaRepository<ArticleGroup, Long> {

    List<ArticleGroup> findAllByGroupId(Long groupId);

    List<ArticleGroup> findAllByArticleId(Long ArticleId);
}