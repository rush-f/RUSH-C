package rush.rush.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    Optional<ArticleLike> findByUserIdAndArticleId(Long userId, Long articleId);

    @Query("select count(articlelike) from ArticleLike articlelike "
        + "where articlelike.article.id = :articleId "
        + "and articlelike.article.publicMap = true "
        + "and articlelike.user.id = :userId ")
    Long countOfPublicArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);

    @Query("select count(articlelike) from ArticleLike articlelike "
        + "where articlelike.article.id = :articleId "
        + "and articlelike.article.user.id = :userId "
        + "and articlelike.user.id = :userId ")
    Long countOfPrivateArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);

    @Query("select count(articlelike) from ArticleLike articlelike "
        + "inner join articlelike.article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user user "
        + "where article.id = :articleId "
        + "and user.id = :userId "
        + "and articlelike.user.id = :userId ")
    Long countOfGroupedArticle(
        @Param("articleId") Long articleId, @Param("userId") Long userId);
}
