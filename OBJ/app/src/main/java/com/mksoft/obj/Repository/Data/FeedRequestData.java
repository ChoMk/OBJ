package com.mksoft.obj.Repository.Data;

public class FeedRequestData {
    String user1;
    String user2;
    String pickImage;

    public FeedRequestData(){

    }

    public FeedRequestData(String user1, String user2, String pickImage) {
        this.user1 = user1;
        this.user2 = user2;
        this.pickImage = pickImage;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getPickImage() {
        return pickImage;
    }

    public void setPickImage(String pickImage) {
        this.pickImage = pickImage;
    }
}
