package rush.rush.dto;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CommentResponse {

    private Long id;
    private String content;
    private AuthorResponse author;
    private Date createDate;
    private Long totalLikes;

    public CommentResponse(Long id, String content, AuthorResponse author, Date createDate){
        this.id = id;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
    }

    public CommentResponse(Long id, String content, Long userId, String nickName, String imageUrl,
        Date createDate, Long totalLikes){
        this.id = id;
        this.content = content;
        this.author = new AuthorResponse(userId, nickName, imageUrl);
        this.createDate = createDate;
        this.totalLikes = totalLikes;
    }
}
