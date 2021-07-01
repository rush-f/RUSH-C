package rush.rush.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
public class FindMapArticlesRequest {

    private Double latitude;
    private Double latitudeRange;
    private Double longitude;
    private Double longitudeRange;
}
