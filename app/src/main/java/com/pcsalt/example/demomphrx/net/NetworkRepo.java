package com.pcsalt.example.demomphrx.net;

import com.pcsalt.example.demomphrx.model.WebUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * This class is the repository class for getting data from API.
 */
public interface NetworkRepo {

    /**
     * This method appends the value with the URL in the Retrofit instance. Creates a GET query.
     *
     * @return Call instance
     */
    @GET("HjRRSIl")
    Call<List<WebUrl>> getFromUrl1();

    /**
     * This method appends the value with the URL in the Retrofit instance. Creates a GET query.
     *
     * @return Call instance
     */
    @GET("VJ7L0YDVl")
    Call<List<WebUrl>> getFromUrl2();
}
