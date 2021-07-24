package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUserId(Long userId);

    List<Article> findAllByPublicMapTrueAndLatitudeBetweenAndLongitudeBetween(
        Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);

    List<Article> findAllByPrivateMapTrueAndUserIdAndLatitudeBetweenAndLongitudeBetween(Long userId,
        Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);

    Optional<Article> findByPublicMapTrueAndId(Long articleId);

    Optional<Article> findByPrivateMapTrueAndIdAndUserId(Long articleId, Long userId);

    @Query("select distinct article from Article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user groupmember "
        + "where article.id = :articleId and groupmember.id = :userId")
    Optional<Article> findAsGroupMapArticle(@Param("articleId") Long articleId,
        @Param("userId") Long userId);

    @Query("select distinct article from Article article "
        + "join fetch article.articleGroups articlegroups "
        + "join fetch articlegroups.group "
        + "where article.user.id = :userId "
        + "order by article.createDate desc")
    List<Article> findArticlesWithGroupsByUserId(@Param("userId") Long userId);
}
