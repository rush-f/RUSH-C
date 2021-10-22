package rush.rush.service.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class EditArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void editArticleContent(Long articleId, String newContent, User user) {
        validate(articleId, user);
        articleRepository.editArticleContent(articleId, newContent);
    }

    private void validate(Long articleId, User user) {
        Long authorId = articleRepository.findArticleAuthorId(articleId)
            .orElseThrow(() -> new NotArticleExistsException("ID가 " + articleId + "인 글이 없습니다."));
        Long userId = user.getId();

        if (!authorId.equals(userId)) {
            throw new NotAuthorizedOrExistException("글 작성자가 아니어서 글을 수정할 권한이 없습니다.\n"
                + "글 작성자 ID : " + authorId
                + "수정 시도한 사용자 ID : " + userId);
        }
    }
}
