package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Article;
import rush.rush.dto.ArticleResponse;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUserId(Long userId);

    List<Article> findAllByPublicMapTrueAndLatitudeBetweenAndLongitudeBetween(
        Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);

    List<Article> findAllByPrivateMapTrueAndUserIdAndLatitudeBetweenAndLongitudeBetween(Long userId,
        Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);

    @Query("select distinct article from Article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "where g.id = :groupId and usergroup.user.id = :userId "
        + "and article.latitude between :lowerLatitude and :upperLatitude "
        + "and article.longitude between :lowerLongitude and :upperLongitude")
    List<Article> findAllOfGroupedMap(@Param("userId") Long userId, @Param("groupId") Long groupId,
        @Param("lowerLatitude") Double lowerLatitude, @Param("upperLatitude") Double upperLatitude,
        @Param("lowerLongitude") Double lowerLongitude, @Param("upperLongitude") Double upperLongitude);

    @Query("select distinct new rush.rush.dto.ArticleResponse(article.id, article.title, "
        + "article.content, "
        + "article.latitude, "
        + "article.longitude, "
        + "user.id, "
        + "user.nickName, "
        + "user.imageUrl, "
        + "article.createDate, "
        + "count(articleLikes)) from Article article "
        + "left join article.articleLikes articleLikes "
        + "inner join article.user user "
        + "where article.publicMap = true and article.id = :articleId")
    Optional<ArticleResponse> findByPublicMapWithLikes(@Param("articleId") Long articleId);

    @Query("select distinct new rush.rush.dto.ArticleResponse(article.id, article.title, "
        + "article.content, "
        + "article.latitude, "
        + "article.longitude, "
        + "user.id, "
        + "user.nickName, "
        + "user.imageUrl, "
        + "article.createDate, "
        + "count(articleLikes)) from Article article "
        + "left join article.articleLikes articleLikes "
        + "inner join article.user user "
        + "where article.privateMap = true and article.id = :articleId and user.id = :userId ")
    Optional<ArticleResponse> findByPrivateMapWithLikes(@Param("articleId") Long articleId,
        @Param("userId") Long userId);

    @Query("select distinct new rush.rush.dto.ArticleResponse(article.id, article.title, "
        + "article.content, "
        + "article.latitude, "
        + "article.longitude, "
        + "user.id, "
        + "user.nickName, "
        + "user.imageUrl, "
        + "article.createDate, "
        + "count(articleLikes)) from Article article "
        + "left join article.articleLikes articleLikes "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user groupmember "
        + "inner join article.user user "
        + "where article.id = :articleId and groupmember.id = :userId ")
    Optional<ArticleResponse> findAsGroupMapArticleWithLikes(@Param("articleId") Long articleId,
        @Param("userId") Long userId);

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
        + "left join fetch article.articleGroups articlegroups "
        + "left join fetch articlegroups.group "
        + "where article.user.id = :userId "
        + "order by article.createDate desc")
    List<Article> findArticlesWithGroupsByUserId(@Param("userId") Long userId);

    @Query("select distinct article.user.id from Article article where article.id = :articleId")
    Optional<Long> findArticleAuthorId(@Param("articleId") Long articleId);
}
