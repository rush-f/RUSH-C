package rush.rush.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CreateArticleRequest {

    private String title;
    private String content;
    private double latitude;    // 위도
    private double longitude;   // 경도
    private boolean publicMap;
    private boolean privateMap;
    private List<Long> groupIdsToBeIncluded;
}
