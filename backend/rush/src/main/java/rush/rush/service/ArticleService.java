package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rush.rush.domain.Article;
import rush.rush.dto.CreateArticleRequest;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Long create(CreateArticleRequest createArticleRequest) {
        Article article = new Article(createArticleRequest.getTitle(),
            createArticleRequest.getContent());

        return articleRepository.save(article)
            .getId();
    }
}
