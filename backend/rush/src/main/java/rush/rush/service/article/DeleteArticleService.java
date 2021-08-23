package rush.rush.service.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class DeleteArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void deleteArticle(Long articleId, User user) {
        validate(articleId, user);
        articleRepository.deleteById(articleId);
    }

    private void validate(Long articleId, User user) {
        Long authorId = articleRepository.findArticleAuthorId(articleId)
            .orElseThrow(() -> new IllegalArgumentException("ID가 " + articleId + "인 글이 없습니다."));
        Long userId = user.getId();

        if (!authorId.equals(userId)) {
            throw new IllegalArgumentException("글 작성자가 아니어서 글을 삭제할 권한이 없습니다.\n"
                + "글 작성자 ID : " + authorId
                + "삭제 시도한 사용자 ID : " + userId);
        }
    }
}
