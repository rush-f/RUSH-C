package rush.rush.dto;

import java.util.Date;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleResponse that = (ArticleResponse) o;

        return Objects.equals(id, that.id)
            && Objects.equals(title, that.title)
            && Objects.equals(content, that.content)
            && Objects.equals(latitude, that.latitude)
            && Objects.equals(longitude, that.longitude)
            && Objects.equals(author, that.author)
            && Objects.equals(createDate, that.createDate)
            && Objects.equals(totalLikes, that.totalLikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id, title, content, latitude, longitude, author, createDate, totalLikes);
    }
}
