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
import rush.rush.repository.ArticleGroupRepository;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class FindArticleService {

    private final ArticleRepository articleRepository;
    private final UserGroupRepository userGroupRepository;
    private final ArticleGroupRepository articleGroupRepository;

    @Transactional
    public ArticleResponse findPublicArticle(Long id) {
        ArticleResponse articleResponse = articleRepository.findByPublicMapWithLikes(id)
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 전체지도에 없습니다."));

        return articleResponse;
    }

    @Transactional
    public ArticleResponse findPrivateArticle(Long id, User me) {
        ArticleResponse articleResponse = articleRepository.findByPrivateMapWithLikes(id, me.getId())
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 개인지도에 없습니다."));

        return articleResponse;
    }

    @Transactional
    public ArticleResponse findGroupArticle(Long id, User me) {
        ArticleResponse articleResponse = articleRepository.findAsGroupMapArticleWithLikes(id, me.getId())
            .orElseThrow(() ->
                new IllegalArgumentException("id가 " + id + "인 article이 없거나, 해당 글을 볼 권한이 없습니다."));

        return articleResponse;
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
}
