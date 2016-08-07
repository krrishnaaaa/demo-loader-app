package com.pcsalt.example.demomphrx.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is used for getting Retrofit client. This client uses GSON to parse the response
 * received from the API and place them in proper model objects.
 */
public class NetworkClient {
    private static final String BASE_URL = "http://beta.json-generator.com/api/json/get/";
    private static Retrofit retrofit = null;

    /**
     * To create the client of the Retrofit
     *
     * @return instance of Retrofit
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
