package rush.rush.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Article;
import rush.rush.domain.Comment;
import rush.rush.domain.MapType;
import rush.rush.domain.User;
import rush.rush.dto.AuthorResponse;
import rush.rush.dto.CommentResponse;
import rush.rush.dto.CreateCommentRequest;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.WrongMapTypeException;
import rush.rush.repository.ArticleRepository;
import rush.rush.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CreateCommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public CommentResponse create(Long articleId, MapType mapType,
        CreateCommentRequest createCommentRequest, User user) {
        if (mapType == MapType.PUBLIC) {
            return createOnPublicArticle(articleId, user, createCommentRequest);
        }
        if (mapType == MapType.PRIVATE) {
            return createOnPrivateArticle(articleId, user, createCommentRequest);
        }
        if (mapType == MapType.GROUPED) {
            return createOnGroupedArticle(articleId, user, createCommentRequest);
        }
        throw new WrongMapTypeException("MapType 오류 - " + mapType.name());
    }

    private CommentResponse createOnPublicArticle(Long articleId, User user,
        CreateCommentRequest createCommentRequest) {
        Article article = articleRepository.findByPublicMapTrueAndId(articleId)
            .orElseThrow(() -> new NotArticleExistsException("존재하지 않는 article ID 입니다."));

        Comment savedComment = commentRepository.save(
            Comment.builder()
                .content(createCommentRequest.getContent())
                .user(user)
                .article(article).build());

        return toCommentResponse(savedComment);
    }

    private CommentResponse createOnPrivateArticle(Long articleId, User user,
        CreateCommentRequest createCommentRequest) {
        Article article = articleRepository
            .findByPrivateMapTrueAndIdAndUserId(articleId, user.getId())
            .orElseThrow(() -> new NotAuthorizedOrExistException(
                "해당 article 조회 권한이 없거나, 존재하지 않는 article ID 입니다."));

        Comment savedComment = commentRepository.save(
            Comment.builder()
                .content(createCommentRequest.getContent())
                .user(user)
                .article(article).build());

        return toCommentResponse(savedComment);
    }

    private CommentResponse createOnGroupedArticle(Long articleId, User user,
        CreateCommentRequest createCommentRequest) {
        Article article = articleRepository.findAsGroupMapArticle(articleId, user.getId())
            .orElseThrow(() -> new NotAuthorizedOrExistException(
                "해당 article 조회 권한이 없거나, 존재하지 않는 article ID 입니다."));

        Comment savedComment = commentRepository.save(
            Comment.builder()
                .content(createCommentRequest.getContent())
                .user(user)
                .article(article).build());

        return toCommentResponse(savedComment);
    }

    private CommentResponse toCommentResponse(Comment comment) {
        AuthorResponse authorResponse = toAuthResponse(comment.getUser());

        return new CommentResponse(
            comment.getId(),
            comment.getContent(),
            authorResponse,
            comment.getCreateDate()
        );
    }

    private AuthorResponse toAuthResponse(User author) {
        return new AuthorResponse(
            author.getId(),
            author.getNickName(),
            author.getImageUrl()
        );
    }
}
