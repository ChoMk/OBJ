package com.mksoft.obj.Repository;

import java.io.Serializable;

public class FeedData implements Serializable {

    private int id = 0;
    private String userName;
    private String feedContents;
    private String GIFPath;


    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFeedContents() {
        return feedContents;
    }

    public String getGIFPath() {
        return GIFPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFeedContents(String feedContents) {
        this.feedContents = feedContents;
    }

    public void setGifPath(String gifPath) {
        this.GIFPath = gifPath;
    }
}
