package rush.rush.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);

    @Query("select count(commentLike) from CommentLike commentLike "
        + "where commentLike.comment.id = :commentId "
        + "and commentLike.comment.article.publicMap = true "
        + "and commentLike.user.id = :userId ")
    Long countOfPublicComment(
        @Param("commentId") Long commentId, @Param("userId") Long userId);

    @Query("select count(commentLike) from CommentLike commentLike "
        + "where commentLike.comment.id = :commentId "
        + "and commentLike.comment.article.user.id = :userId "
        + "and commentLike.user.id = :userId ")
    Long countOfPrivateComment(
        @Param("commentId") Long commentId, @Param("userId") Long userId);

    @Query("select count(commentLike) from CommentLike commentLike "
        + "inner join commentLike.comment comment "
        + "inner join comment.article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where comment.id = :commentId "
        + "and user.id = :userId "
        + "and commentLike.user.id = :userId ")
    Long countOfGroupedComment(
        @Param("commentId") Long commentId, @Param("userId") Long userId);
}
