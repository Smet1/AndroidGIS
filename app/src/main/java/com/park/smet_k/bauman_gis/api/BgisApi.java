package com.park.smet_k.bauman_gis.api;

import com.park.smet_k.bauman_gis.model.Message;
import com.park.smet_k.bauman_gis.model.RouteModel;
import com.park.smet_k.bauman_gis.model.Stairs;
import com.park.smet_k.bauman_gis.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BgisApi {
    // TODO(): URL
    String BASE_URL = "http://10.0.2.2:5000";


    // user login block
    @POST("/login")
    Call<User> userLogin(@Body User user);

    @GET("/get/{id}")
    Call<User> getUserInfo(@Path("id") Integer id);

    @POST("/post")
    Call<User> userSignUp(@Body User user);


    // user history routes
    @POST("/insert")
    Call<RouteModel> pushRoute(@Body RouteModel routeModel);

    @DELETE("/clear_routes/{id}")
    Call<Message> deleteHistory(@Path("id") Integer id);

    @GET("/get_routes/{id}")
    Call<List<RouteModel>> pullRoutes(@Path("id") Integer id);


    // stairs points block
    @GET("/map/stairs/getall")
    Call<List<Stairs>> getStairs();
}
