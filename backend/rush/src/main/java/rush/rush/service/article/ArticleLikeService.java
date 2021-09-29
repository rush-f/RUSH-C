package rush.rush.service.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleLike;
import rush.rush.domain.MapType;
import rush.rush.domain.User;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.NotExistsException;
import rush.rush.exception.WrongMapTypeException;
import rush.rush.repository.ArticleLikeRepository;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void changeMyLike(Long articleId, MapType mapType, Boolean hasILiked, User user) {
        if (mapType == MapType.PUBLIC) {
            changeMyLikeOnPublicArticle(articleId, hasILiked, user);
        } else if (mapType == MapType.PRIVATE) {
            changeMyLikeOnPrivateArticle(articleId, hasILiked, user);
        } else if (mapType == MapType.GROUPED) {
            changeMyLikeOnGroupedArticle(articleId, hasILiked, user);
        }
    }

    @Transactional(readOnly = true)
    public boolean hasILiked(Long articleId, MapType mapType, Long userId) {
        if (mapType == MapType.PUBLIC) {
            return articleLikeRepository.countOfPublicArticle(articleId, userId) >= 1;
        }
        if (mapType == MapType.PRIVATE) {
            return articleLikeRepository.countOfPrivateArticle(articleId, userId) >= 1;
        }
        if (mapType == MapType.GROUPED) {
            return articleLikeRepository.countOfGroupedArticle(articleId, userId) >= 1;
        }
        throw new WrongMapTypeException("MapType 오류 - " + mapType.name());
    }

    private void changeMyLikeOnPublicArticle(Long articleId, Boolean hasILiked, User user) {
        Article article = articleRepository.findByPublicMapTrueAndId(articleId)
            .orElseThrow(() -> new NotExistsException("존재하지 않는 article ID 입니다."));

        changeLike(article, user, hasILiked);
    }

    private void changeMyLikeOnPrivateArticle(Long articleId, Boolean hasILiked, User user) {
        Article article = articleRepository.findByPrivateMapTrueAndIdAndUserId(articleId,
                user.getId())
            .orElseThrow(() -> new NotAuthorizedOrExistException(
                "해당 article 조회 권한이 없거나, 존재하지 않는 article ID 입니다."));

        changeLike(article, user, hasILiked);
    }

    private void changeMyLikeOnGroupedArticle(Long articleId, Boolean hasILiked, User user) {
        Article article = articleRepository.findAsGroupMapArticle(articleId, user.getId())
            .orElseThrow(() -> new NotAuthorizedOrExistException(
                "해당 article 조회 권한이 없거나, 존재하지 않는 article ID 입니다."));

        changeLike(article, user, hasILiked);
    }

    private void changeLike(Article article, User user, Boolean hasILiked) {
        if (hasILiked) {
            articleLikeRepository.delete(findLike(article.getId(), user.getId()));
        } else {
            articleLikeRepository.save(ArticleLike.builder()
                .user(user)
                .article(article)
                .build());
        }
    }

    private ArticleLike findLike(Long articleId, Long userId) {
        return articleLikeRepository.findByUserIdAndArticleId(userId, articleId)
            .orElseThrow(() -> new NotExistsException("해당하는 좋아요는 없습니다"));
    }
}
