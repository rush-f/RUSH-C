package rush.rush.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select comment from Comment comment "
        + "where comment.article.id = :articleId "
        + "and comment.article.publicMap = true "
        + "order by comment.createDate desc")
    List<Comment> findAllOfPublicArticle(
        @Param("articleId") Long articleId);

    @Query("select comment from Comment comment "
        + "inner join comment.article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where article.id = :articleId "
        + "and user.id = :userId "
        + "order by comment.createDate desc")
    List<Comment> findAllOfGroupedArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);

    @Query("select comment from Comment comment "
        + "inner join comment.article "
        + "where comment.article.id = :articleId "
        + "and comment.article.user.id = :userId "
        + "order by comment.createDate desc")
    List<Comment> findAllOfPrivateArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);
}
