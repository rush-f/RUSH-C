package rush.rush.service.article;

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
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class FindArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public ArticleResponse findPublicArticle(Long id) {
        ArticleResponse articleResponse = articleRepository.findByPublicMapWithLikes(id)
            .orElseThrow(() ->
                new NotArticleExistsException("id가 " + id + "인 article이 전체지도에 없습니다."));

        return articleResponse;
    }

    @Transactional(readOnly = true)
    public ArticleResponse findPrivateArticle(Long id, User me) {
        ArticleResponse articleResponse = articleRepository
            .findByPrivateMapWithLikes(id, me.getId())
            .orElseThrow(() ->
                new NotArticleExistsException("id가 " + id + "인 article이 개인지도에 없습니다."));

        return articleResponse;
    }

    @Transactional(readOnly = true)
    public ArticleResponse findGroupArticle(Long id, User me) {
        ArticleResponse articleResponse = articleRepository
            .findAsGroupMapArticleWithLikes(id, me.getId())
            .orElseThrow(() ->
                new NotAuthorizedOrExistException(
                    "id가 " + id + "인 article이 없거나, 해당 글을 볼 권한이 없습니다."));

        return articleResponse;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> findGroupedMapArticles(Long groupId,
        Double latitude, Double latitudeRange, Double longitude, Double longitudeRange, User user) {
        LocationRange locationRange = new LocationRange(latitude, latitudeRange, longitude,
            longitudeRange);

        List<Article> articles = articleRepository
            .findAllOfGroupedMap(user.getId(), groupId,
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
