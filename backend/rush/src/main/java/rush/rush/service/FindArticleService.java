package rush.rush.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleGroup;
import rush.rush.domain.LocationRange;
import rush.rush.domain.User;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.ArticleSummaryResponse;
import rush.rush.dto.AuthorResponse;
import rush.rush.repository.ArticleGroupRepository;
import rush.rush.repository.ArticleLikeRepository;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class FindArticleService {

    private final ArticleRepository articleRepository;
    private final UserGroupRepository userGroupRepository;
    private final ArticleGroupRepository articleGroupRepository;
    private final ArticleLikeRepository articleLikeRepository;

    @Transactional
    public ArticleResponse findPublicArticle(Long id) {
        Article article = articleRepository.findByPublicMapTrueAndId(id)
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 전체지도에 없습니다."));

        User user = article.getUser();

        return toResponse(article);
    }

    @Transactional
    public ArticleResponse findPrivateArticle(Long id, User me) {
        Article article = articleRepository.findByPrivateMapTrueAndIdAndUserId(id, me.getId())
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 개인지도에 없습니다."));

        return toResponse(article);
    }

    @Transactional
    public ArticleResponse findGroupArticle(Long id, User me) {
        Article article = articleRepository.findAsGroupMapArticle(id, me.getId())
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 없거나, 해당 글을 볼 권한이 없습니다."));

        return toResponse(article);
    }

    private ArticleResponse toResponse(Article article) {
        User author = article.getUser();
        AuthorResponse authorResponse = new AuthorResponse(author.getId(),
            author.getNickName(), author.getImageUrl());
        Long totalLikes = articleLikeRepository.countByArticleId(article.getId());

        return new ArticleResponse(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.getLatitude(),
            article.getLongitude(),
            authorResponse,
            article.getCreateDate(),
            totalLikes
        );
    }

    @Transactional
    public List<ArticleSummaryResponse> findPublicMapArticles(
        Double latitude, Double latitudeRange, Double longitude, Double longitudeRange) {
        LocationRange locationRange = new LocationRange(latitude, latitudeRange, longitude,
            longitudeRange);

        List<Article> articles = articleRepository
            .findAllByPublicMapTrueAndLatitudeBetweenAndLongitudeBetween(
                locationRange.getLowerLatitude(), locationRange.getUpperLatitude(),
                locationRange.getLowerLongitude(), locationRange.getUpperLongitude());

        return toResponses(articles);
    }

    @Transactional
    public List<ArticleSummaryResponse> findPrivateMapArticles(Double latitude,
        Double latitudeRange, Double longitude, Double longitudeRange, User me) {
        LocationRange locationRange = new LocationRange(latitude, latitudeRange, longitude,
            longitudeRange);

        List<Article> articles = articleRepository
            .findAllByPrivateMapTrueAndUserIdAndLatitudeBetweenAndLongitudeBetween(me.getId(),
                locationRange.getLowerLatitude(), locationRange.getUpperLatitude(),
                locationRange.getLowerLongitude(), locationRange.getUpperLongitude());

        return toResponses(articles);
    }

    @Transactional
    public List<ArticleSummaryResponse> findGroupedMapArticles(Long groupId,
            Double latitude, Double latitudeRange, Double longitude, Double longitudeRange, User user) {
        userGroupRepository.findByUserIdAndGroupId(user.getId(), groupId)
            .orElseThrow(() -> new IllegalArgumentException(
                "ID=" + user.getId() + "인 사용자가 ID=" + groupId + "인 그룹에 속해있지 않습니다."));

        // Todo: 일부분만 가져오도록 구현
        List<ArticleGroup> articleGroups = articleGroupRepository.findAllByGroupId(groupId);

        List<Article> articles = articleGroups.stream()
            .map(ArticleGroup::getArticle)
            .collect(Collectors.toUnmodifiableList());
        return toResponses(articles);
    }

    private List<ArticleSummaryResponse> toResponses(List<Article> articles) {
        if (Objects.isNull(articles)) {
            return Collections.emptyList();
        }
        return articles.stream()
            .map(article -> new ArticleSummaryResponse(
                article.getId(),
                article.getLatitude(),
                article.getLongitude(),
                article.getTitle()))
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public Boolean isMyArticle(Long articleId, User user) {
        Long articleAuthorId = articleRepository.findArticleAuthorId(articleId)
            .orElseThrow(() -> new IllegalArgumentException("ID가 " + articleId + "인 글이 없습니다."));
        return articleAuthorId.equals(user.getId());
    }
}
