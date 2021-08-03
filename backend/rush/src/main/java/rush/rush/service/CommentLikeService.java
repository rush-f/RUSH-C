package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.Comment;
import rush.rush.domain.CommentLike;
import rush.rush.domain.MapType;
import rush.rush.domain.User;
import rush.rush.repository.CommentLikeRepository;
import rush.rush.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void changeMyLike(Long commentId, MapType mapType, Boolean hasILiked, User user) {
        if (mapType == MapType.PUBLIC) {
            changeMyLikeOnPublicComment(commentId, hasILiked, user);
        }
        else if (mapType == MapType.PRIVATE) {
            changeMyLikeOnPrivateComment(commentId, hasILiked, user);
        }
        else if (mapType == MapType.GROUPED) {
            changeMyLikeOnGroupedComment(commentId, hasILiked, user);
        }
    }

    private void changeMyLikeOnPublicComment(Long commentId, Boolean hasILiked, User user){
        Comment comment = commentRepository.findAsPublicArticle(commentId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 comment ID 입니다."));

        changeLike(comment, user, hasILiked);
    }

    private void changeMyLikeOnPrivateComment(Long commentId, Boolean hasILiked, User user){
        Comment comment = commentRepository.findAsPrivateArticle(commentId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 comment 조회 권한이 없거나, 존재하지 않는 comment ID 입니다."));

        changeLike(comment, user, hasILiked);
    }

    private void changeMyLikeOnGroupedComment(Long commentId, Boolean hasILiked, User user){
        Comment comment = commentRepository.findAsGroupedArticle(commentId, user.getId())
            .orElseThrow(() -> new IllegalArgumentException("해당 comment 조회 권한이 없거나, 존재하지 않는 comment ID 입니다."));

        changeLike(comment, user, hasILiked);
    }

    private void changeLike(Comment comment, User user, Boolean hasILiked){
        if(hasILiked){
            commentLikeRepository.delete(findLike(comment.getId(), user.getId()));
        }
        else{
            commentLikeRepository.save(CommentLike.builder()
                .user(user)
                .comment(comment)
                .build());
        }
    }

    private CommentLike findLike(Long commentId, Long userId){
        return commentLikeRepository.findByUserIdAndCommentId(userId, commentId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 좋아요는 없습니다"));
    }
}
