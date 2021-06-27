package rush.rush.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.article.id = :articleId")
    List<Comment> findAllByArticleId(@Param("articleId") Long articleId);
}
