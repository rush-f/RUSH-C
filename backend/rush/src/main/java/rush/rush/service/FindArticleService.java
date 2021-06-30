package rush.rush.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.LocationRange;
import rush.rush.domain.User;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.ArticleSummaryResponse;
import rush.rush.dto.AuthorResponse;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class FindArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse findPublicArticle(Long id) {
        Article article = articleRepository.findByPublicMapTrueAndId(id)
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 전체지도에 없습니다."));

        User user = article.getUser();
        AuthorResponse authorResponse = new AuthorResponse(user.getId(),
            user.getNickName(), user.getImageUrl());

        return toResponse(article);
    }

    @Transactional
    public ArticleResponse findPrivateArticle(Long id, User me) {
        Article article = articleRepository.findByPrivateMapTrueAndIdAndUserId(id, me.getId())
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 개인지도에 없습니다."));

        return toResponse(article);
    }

    private ArticleResponse toResponse(Article article) {
        User author = article.getUser();
        AuthorResponse authorResponse = new AuthorResponse(author.getId(),
            author.getNickName(), author.getImageUrl());

        return new ArticleResponse(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.getLatitude(),
            article.getLongitude(),
            authorResponse,
            article.getCreateDate()
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
}
