package hackaton.academy.timeto.rest;


import com.google.gson.JsonObject;

import org.json.JSONObject;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TimetoApiClient {

    String BASE_URL = "https://9bb2d938.ngrok.io";


        @POST("/orderPlace")
        Call<Boolean> iniviteContacts(@Body ArrayList<Contact> contacts);


}
