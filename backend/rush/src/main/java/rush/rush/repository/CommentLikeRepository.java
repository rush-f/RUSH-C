package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);

    @Query("select commentLike.comment.id from CommentLike commentLike "
        + "inner join commentLike.comment.article article "
        + "where article.id = :articleId "
        + "and article.publicMap = true "
        + "and commentLike.user.id = :userId")
    Long[] findHasILikedInPublic(
        @Param("articleId") Long articleId, @Param("userId") Long userId);


    @Query("select commentLike.comment.id from CommentLike commentLike "
        + "inner join commentLike.comment.article article "
        + "where article.id = :articleId "
        + "and article.user.id = :userId "
        + "and commentLike.user.id = :userId")
    Long[] findHasILikedInPravete(
        @Param("articleId") Long articleId, @Param("userId") Long userId);

    @Query("select commentLike.comment.id from CommentLike commentLike "
        + "inner join commentLike.comment.article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where article.id = :articleId "
        + "and user.id = :userId "
        + "and commentLike.user.id = :userId ")
    Long[] findHasILikedInGroup(
        @Param("articleId") Long articleId, @Param("userId") Long userId);
}
