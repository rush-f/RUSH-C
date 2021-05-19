package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rush.rush.domain.Article;
import rush.rush.domain.User;
import rush.rush.dto.ArticleAuthorResponse;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Long create(CreateArticleRequest createArticleRequest, User user) {
        Article article = new Article(
            createArticleRequest.getTitle(),
            createArticleRequest.getContent(),
            createArticleRequest.getLatitude(),
            createArticleRequest.getLongitude(),
            user
        );
        return articleRepository.save(article)
            .getId();
    }

    public ArticleResponse findOne(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("id가 " + id + "인 article이 없습니다."));

        User user = article.getUser();
        ArticleAuthorResponse articleAuthorResponse = new ArticleAuthorResponse(user.getId(),
            user.getNickName(), user.getImageUrl());

        return new ArticleResponse(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.getLatitude(),
            article.getLongitude(),
            articleAuthorResponse,
            article.getCreateDate()
        );
    }
}
