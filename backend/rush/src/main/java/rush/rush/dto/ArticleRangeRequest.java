package rush.rush.dto;

public class ArticleRangeRequest {

    private final Double latitude;
    private final Double latitudeRange;
    private final Double longitude;
    private final Double longitudeRange;

    public ArticleRangeRequest(Double latitude, Double latitudeRange, Double longitude, Double longitudeRange) {
        this.latitude = latitude;
        this.latitudeRange = latitudeRange;
        this.longitude = longitude;
        this.longitudeRange = longitudeRange;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLatitudeRange() {
        return latitudeRange;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLongitudeRange() {
        return longitudeRange;
    }
}
