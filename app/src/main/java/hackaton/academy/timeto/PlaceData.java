package hackaton.academy.timeto;

public class PlaceData {

    private String name;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    private String imageURL;
    private String overview;
    private float distance;

    public PlaceData(int imageResourceId, String name,  String overview, float distance) {

    public PlaceData(String imageURL, String name,  String overview) {
        this.name = name;
        this.imageURL = imageURL;
        this.overview = overview;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
