package com.park.smet_k.bauman_gis.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BgisApi {
    // TODO(): URL
//    String BASE_URL = "127.0.0.1:5000";
    String BASE_URL = "http://10.0.2.2:5000";

//    @GET("/get")
//    Call<Void> login(@Body )

    @POST("login")
    Call<Void> userLogin(@Field("login") String login, @Field("password") String password);

}
