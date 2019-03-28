package hackaton.academy.timeto.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private static PlacesService placesService;

    public static PlacesService getPlaceServiceInstance() {
        if (placesService == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(PlacesService.BASE_URL) // TODO Check if it is the right one
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            placesService = retrofit.create(PlacesService.class);
        }

        return placesService;
    }
}
