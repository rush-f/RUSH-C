package rush.rush.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle(Article article);
}
