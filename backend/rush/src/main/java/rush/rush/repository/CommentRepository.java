package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Comment;
import rush.rush.dto.CommentResponse;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByIdAndUserId(Long id, Long userId);

    @Query("select distinct comment from Comment comment "
        + "where comment.id = :commentId "
        + "and comment.article.publicMap = true ")
    Optional<Comment> findInPublicArticle(@Param("commentId") Long commentId);

    @Query("select distinct comment from Comment comment "
        + "where comment.id = :commentId "
        + "and comment.article.user.id = :userId ")
    Optional<Comment> findInPrivateArticle(@Param("commentId") Long commentId,
        @Param("userId") Long userId);

    @Query("select distinct comment from Comment comment "
        + "inner join comment.article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user groupmember "
        + "where comment.id = :commentId and groupmember.id = :userId")
    Optional<Comment> findInGroupedArticle(@Param("commentId") Long commentId,
        @Param("userId") Long userId);

    @Query("select new rush.rush.dto.CommentResponse(comment.id, comment.content, "
        + "user.id, "
        + "user.nickName, "
        + "user.imageUrl, "
        + "comment.createDate,"
        + "count(commentLikes)) from Comment comment "
        + "left join comment.commentLikes commentLikes "
        + "inner join comment.user user "
        + "where comment.article.id = :articleId "
        + "and comment.article.publicMap = true "
        + "group by comment.id "
        + "order by comment.createDate desc")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<CommentResponse> findAllOfPublicArticle(
        @Param("articleId") Long articleId);

    @Query("select new rush.rush.dto.CommentResponse(comment.id, comment.content, "
        + "user.id, "
        + "user.nickName, "
        + "user.imageUrl, "
        + "comment.createDate,"
        + "count(commentLikes)) from Comment comment "
        + "left join comment.commentLikes commentLikes "
        + "inner join comment.user user "
        + "inner join comment.article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "where article.id = :articleId "
        + "and usergroup.user.id = :userId "
        + "group by comment.id "
        + "order by comment.createDate desc")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<CommentResponse> findAllOfGroupedArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);

    @Query("select new rush.rush.dto.CommentResponse(comment.id, comment.content, "
        + "user.id, "
        + "user.nickName, "
        + "user.imageUrl, "
        + "comment.createDate,"
        + "count(commentLikes)) from Comment comment "
        + "left join comment.commentLikes commentLikes "
        + "inner join comment.user user "
        + "inner join comment.article "
        + "where comment.article.id = :articleId "
        + "and comment.article.user.id = :userId "
        + "group by comment.id "
        + "order by comment.createDate desc")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<CommentResponse> findAllOfPrivateArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);
}
