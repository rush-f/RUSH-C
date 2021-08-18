package rush.rush.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rush.rush.domain.User;
import rush.rush.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class DeleteCommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void deleteComment(Long commentId, User user) {
        validateIsOwner(commentId, user);
        commentRepository.deleteById(commentId);
    }

    private void validateIsOwner(Long commentId, User user) {
        if (commentRepository.countByIdAndUserId(commentId, user.getId()).equals(1L)) {
            return;
        }
        throw new IllegalArgumentException("id가 " + commentId + "인 댓글이 없거나, "
            + "id=" + user.getId() + "인 사용자가 해당 댓글의 작성자가 아닙니다.");
    }
}
