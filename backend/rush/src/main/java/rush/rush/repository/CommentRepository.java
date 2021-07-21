package rush.rush.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticleIdOrderByCreateDateDesc(Long articleId);

    @Query("select comment from Comment comment "
        + "inner join comment.article "
        + "where comment.article.id = :articleId "
        + "and comment.article.user.id = :userId "
        + "order by comment.createDate desc")
    List<Comment> findAllOfPrivateArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);
}
