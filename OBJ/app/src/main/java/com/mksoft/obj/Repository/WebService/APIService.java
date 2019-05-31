package com.mksoft.obj.Repository.WebService;

import com.mksoft.obj.Repository.Data.FeedData;
import com.mksoft.obj.Repository.Data.FeedRequestData;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.Repository.Data.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @GET("/user/{userID}.json")
    Call<UserData> getUser(
            @Path("userID") String userID);

    @GET("/friend/{userID}.json")
    Call<List<FriendData>> getUserFriends(
            @Path("userID") String userID);
    @GET("/feed.json")
    Call<List<FeedData>> getFeedData();


    @GET("/user/{userID}.json")
    Call<UserData> checkUser(
            @Path("userID") String userID
    );


    @PUT("/user/{userID}.json")
    Call<Object> postUser(
            @Path("userID") String userID,
            @Body UserData userData
    );
    @PUT("/make_feed_request/{feedID}.json")
    Call<Object> postMakeFeedRequest(
            @Path("feedID") String feedID,
            @Body FeedRequestData feedRequestData
            );

    @PUT("/friend/{userID}.json")
    Call<Object> postFriend(
            @Path("userID") String userID,
            @Body List<FriendData> friendData
    );
}
