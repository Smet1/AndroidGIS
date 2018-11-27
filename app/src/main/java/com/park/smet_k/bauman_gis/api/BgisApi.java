package com.park.smet_k.bauman_gis.api;

import com.park.smet_k.bauman_gis.Route;
import com.park.smet_k.bauman_gis.model.RouteModel;
import com.park.smet_k.bauman_gis.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BgisApi {
    // TODO(): URL
    String BASE_URL = "http://10.0.2.2:5000";

    @POST("/login")
    Call<User> userLogin(@Body User user);

    @GET("/get/{id}")
    Call<User> getUserInfo(@Path("id") Integer id);

    @POST("/post")
    Call<User> userSignUp(@Body User user);


    @POST("/insert")
    Call<RouteModel> pushRoute(@Body RouteModel routeModel);
}
