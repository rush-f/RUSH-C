package rush.rush.dto;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ArticleResponse {

    private Long id;
    private String title;
    private String content;
    private Double latitude;
    private Double longitude;
    private AuthorResponse author;
    private Date createDate;
    private Long totalLikes;

    public ArticleResponse(Long id, String title, String content, Double latitude, Double longitude,
        Long userId, String nickName, String imageUrl, Date createDate, Long totalLikes ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.latitude =latitude;
        this.longitude = longitude;
        this.author = new AuthorResponse(userId, nickName, imageUrl);
        this.createDate = createDate;
        this.totalLikes = totalLikes;
    }
}
