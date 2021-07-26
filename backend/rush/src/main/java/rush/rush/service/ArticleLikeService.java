package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.ArticleLike;
import rush.rush.domain.MapType;
import rush.rush.domain.User;
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
        }
        else if (mapType == MapType.PRIVATE) {
            changeMyLikeOnPrivateArticle(articleId, hasILiked, user);
        }
        else if (mapType == MapType.GROUPED) {
            changeMyLikeOnGroupedArticle(articleId, hasILiked, user);
        }
    }

    @Transactional
    public boolean hasILiked(Long articleId, MapType mapType, Long userId) {
        int count = articleLikeRepository.countByUserIdAndArticleId(userId,articleId);

        return count >= 1;
    }

    private void changeMyLikeOnPublicArticle(Long articleId, Boolean hasILiked, User user){
        Article article = articleRepository.findByPublicMapTrueAndId(articleId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 article ID 입니다."));

        changeLike(article, user, hasILiked);
    }

    private void changeMyLikeOnPrivateArticle(Long articleId, Boolean hasILiked, User user){
        Article article = articleRepository.findByPrivateMapTrueAndIdAndUserId(articleId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 article 조회 권한이 없거나, 존재하지 않는 article ID 입니다."));

        changeLike(article, user, hasILiked);
    }

    private void changeMyLikeOnGroupedArticle(Long articleId, Boolean hasILiked, User user){
        Article article = articleRepository.findAsGroupMapArticle(articleId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 article 조회 권한이 없거나, 존재하지 않는 article ID 입니다."));

        changeLike(article, user, hasILiked);
    }

    private void changeLike(Article article, User user, Boolean hasILiked){
        if(hasILiked){
            articleLikeRepository.delete(findLike(article.getId(), user.getId()));
        }
        else{
            articleLikeRepository.save(ArticleLike.builder()
                .user(user)
                .article(article)
                .build());
        }

    }

    private ArticleLike findLike(Long articleId, Long userId){
        return  articleLikeRepository.findByUserIdAndArticleId(userId, articleId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 좋아요는 없습니다"));
    }
}
