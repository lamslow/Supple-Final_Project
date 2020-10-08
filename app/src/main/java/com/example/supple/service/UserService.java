package com.example.supple.service;

import com.example.supple.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("/loginApp")
    Call<String> login(@Query("Username") String username,
                       @Query("Password") String password);

    @GET("getUser")
    Call<List<User>> getAllUsers(@Query("Username") String username);

    @FormUrlEncoded
    @POST("/signUpUser")
    Call<String> signUp(@Field("Username") String username,
                        @Field("Password") String password,
                        @Field("Fullname") String fullname,
                        @Field("Phone") String phone,
                        @Field("Email") String email,
                        @Field("Address") String address);

    @FormUrlEncoded
    @POST("/resetPass")
    Call<String> resetPass(@Field("_id") String id,
                           @Field("Username") String username,
                           @Field("Password") String pass,
                           @Field("Phone") String phone,
                           @Field("Email") String email);

    @FormUrlEncoded
    @POST("/changePass")
    Call<String> changePass(@Field("_id") String id,
                           @Field("Username") String username,
                           @Field("Password") String pass);

    @GET("GetInforUser")
    Call<List<User>> getInforUser();


}
