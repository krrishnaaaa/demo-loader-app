package com.pcsalt.example.demomphrx.net;

import com.pcsalt.example.demomphrx.model.WebUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkRepo {
    @GET("HjRRSIl")
    Call<List<WebUrl>> getFromUrl1();

    @GET("VJ7L0YDVl")
    Call<List<WebUrl>> getFromUrl2();
}
