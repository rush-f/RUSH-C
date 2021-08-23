package rush.rush.dto;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class MyPageArticleResponse {

    private Long id;
    private String title;
    private boolean publicMap;
    private boolean privateMap;
    private Date createDate;
    private Long totalComments;
}
