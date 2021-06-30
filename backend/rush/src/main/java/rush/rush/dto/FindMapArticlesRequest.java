package rush.rush.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindMapArticlesRequest {

    private final Double latitude;
    private final Double latitudeRange;
    private final Double longitude;
    private final Double longitudeRange;

    private FindMapArticlesRequest() {
        this.latitude = 37.63;
        this.latitudeRange = 0.0095;
        this.longitude = 127.07;
        this.longitudeRange = 0.0250;
    }
}
