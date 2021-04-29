package rush.rush.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rush.rush.domain.Article;
import rush.rush.dto.ArticleResponse;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Long create(CreateArticleRequest createArticleRequest) {
        Article article = new Article(
            createArticleRequest.getTitle(),
            createArticleRequest.getContent(),
            createArticleRequest.getLatitude(),
            createArticleRequest.getLongitude()
        );
        return articleRepository.save(article)
            .getId();
    }

    public ArticleResponse findOne(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("id가 " + id + "인 article이 없습니다."));

        return new ArticleResponse(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.getLatitude(),
            article.getLongitude(),
            article.getCreateDate()
        );
    }
}
