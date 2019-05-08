package com.mksoft.obj.Repository.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class FriendData {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id ="";

    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("friend_img_url")
    @Expose
    private String friendImgUrl="";


    private Date lastRefresh = null;

    public FriendData(@NonNull String id, String name, String friendImgUrl, Date lastRefresh) {
        this.id = id;
        this.name = name;
        this.friendImgUrl = friendImgUrl;
        this.lastRefresh = lastRefresh;
    }

    public FriendData(){

    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFriendImgUrl() {
        return friendImgUrl;
    }
    public void setFriendImgUrl(String friendImgUrl) {
        this.friendImgUrl = friendImgUrl;
    }


    public Date getLastRefresh() {
        return lastRefresh;
    }
    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }
}
