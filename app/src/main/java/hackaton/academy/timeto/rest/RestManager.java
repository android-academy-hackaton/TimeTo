package hackaton.academy.timeto.rest;

import android.text.format.Time;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private static PlacesService placesService;
    private static TimetoApiClient timetoApiClient;

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

    public static TimetoApiClient getTimeToApiClientInstance() {
        if (timetoApiClient == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(TimetoApiClient.BASE_URL) // TODO Check if it is the right one
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            timetoApiClient = retrofit.create(TimetoApiClient.class);
        }

        return timetoApiClient;
    }
}
