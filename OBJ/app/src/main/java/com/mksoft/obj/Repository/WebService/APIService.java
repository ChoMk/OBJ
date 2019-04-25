package com.mksoft.obj.Repository.WebService;

import com.mksoft.obj.Repository.Data.FriedListData;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.Repository.Data.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @GET("/user/{userID}.json")
    Call<UserData> getUser(
            @Path("userID") String userID);

    @GET("/friend/{userID}.json")
    Call<FriedListData> getUserFriends(
            @Path("userID") String userID);

    @PUT("/user/{userID}.json")
    Call<Object> postUser(
            @Path("userID") String userID,
            @Body UserData userData
    );


}
