package hackaton.academy.timeto.rest;

import hackaton.academy.timeto.model.Place;
import hackaton.academy.timeto.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlacesService {

    /// https://maps.googleapis.com/maps/api/place/nearbysearch/json?
    // location=-33.8670522,151.1957362&radius=1500&type=restaurant&key=AIzaSyAPHRqqY1RjGhhd2DdNFAhibVjaKS0ySOc

    String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";


    /* location request */
    String LOCATION = "location";

    /* radius request */
    String RADIUS = "radius";

    /* type request */
    String TYPE = "type";

    @GET("json")
    Call<Place> getPlaces(@Query(LOCATION) String location, @Query(RADIUS) int radius, @Query(TYPE) String type, @Query("key") String apiKey);
}
