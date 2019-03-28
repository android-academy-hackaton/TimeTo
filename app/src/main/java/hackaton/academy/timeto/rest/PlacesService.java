package hackaton.academy.timeto.rest;

import hackaton.academy.timeto.model.Place;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesService {

    /// https://maps.googleapis.com/maps/api/place/nearbysearch/json?
    // location=-33.8670522,151.1957362&radius=1500&type=restaurant&key=AIzaSyAPHRqqY1RjGhhd2DdNFAhibVjaKS0ySOc

    String BASE_URL         = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    String BASE_URL_PHOTO   = "ֿhttps://maps.googleapis.com/maps/api/place/";

    //?key=AIzaSyAYQCLhhFzj-FWnHnKvvtQ-YJh0QRBolV4&maxwidth=400&photoreference=CmRaAAAARyD7dyMTbiFBvKgJsH79lASULY3gR7mLF9shC3xtBLF9M_8GjGQFO9I0MoirALKVbH4fupMayI9T63mS6zcShNTX0avoCYChWvwcXZCCwR82x4ENrB4le14Mbjt8dYhPEhDyzTrwmhYjQkK2CVMYrBQdGhRn8nvwJyu2NvohpM5fHf3HYW-XEQ


    String MAXWIDTH = "maxwidth";
    String REFERENCE = "photoreference";
    /* location request */
    String LOCATION = "location";

    /* radius request */
    String RADIUS = "radius";

    /* type request */
    String TYPE = "type";

    @GET("json")
    Call<Place> getPlaces(@Query(LOCATION) String location, @Query(RADIUS) int radius, @Query(TYPE) String type, @Query("key") String apiKey);

    @GET("photo")
    Call<Place> getPhotos(@Query("key") String apiKey,@Query(MAXWIDTH) int maxWidth, @Query(REFERENCE) String photoReference);



}
