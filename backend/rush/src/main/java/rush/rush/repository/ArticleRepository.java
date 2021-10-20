package rush.rush.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import rush.rush.domain.Article;
import rush.rush.dto.ArticleResponse;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUserId(Long userId);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Article> findAllByPublicMapTrueAndLatitudeBetweenAndLongitudeBetween(
        Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Article> findAllByPrivateMapTrueAndUserIdAndLatitudeBetweenAndLongitudeBetween(Long userId,
        Double lowerLatitude, Double upperLatitude, Double lowerLongitude, Double upperLongitude);

    @Query("select distinct article from Article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "where g.id = :groupId and usergroup.user.id = :userId "
        + "and article.latitude between :lowerLatitude and :upperLatitude "
        + "and article.longitude between :lowerLongitude and :upperLongitude")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
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
        + "where article.publicMap = true and article.id = :articleId "
        + "group by article.id")
    Optional<ArticleResponse> findPublicArticle(@Param("articleId") Long articleId);

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
        + "where article.privateMap = true and article.id = :articleId and user.id = :userId "
        + "group by article.id")
    Optional<ArticleResponse> findPrivateArticle(@Param("articleId") Long articleId, @Param("userId") Long userId);

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
        + "where article.id = :articleId and groupmember.id = :userId "
        + "group by article.id")
    Optional<ArticleResponse> findGroupedArticle(@Param("articleId") Long articleId,
                                                 @Param("userId") Long userId);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Article> findByPublicMapTrueAndId(Long articleId);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Article> findByPrivateMapTrueAndIdAndUserId(Long articleId, Long userId);

    @Query("select distinct article from Article article "
        + "inner join article.articleGroups articlegroup "
        + "inner join articlegroup.group g "
        + "inner join g.userGroups usergroup "
        + "inner join usergroup.user groupmember "
        + "where article.id = :articleId and groupmember.id = :userId")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Article> findAsGroupMapArticle(@Param("articleId") Long articleId,
        @Param("userId") Long userId);

    @Query("select distinct article from Article article "
        + "left join fetch article.articleLikes articleLikes "
        + "where article.user.id = :userId "
        + "order by article.createDate desc")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Article> findArticlesWithComments(@Param("userId") Long userId);

    @Query("select distinct article.user.id from Article article where article.id = :articleId")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Long> findArticleAuthorId(@Param("articleId") Long articleId);

    @Modifying
    @Query("update Article a set a.content = :newContent where a.id = :articleId")
    void editArticleContent(@Param("articleId") Long articleId, @Param("newContent") String newContent);
}
