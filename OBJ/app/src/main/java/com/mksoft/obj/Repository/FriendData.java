package com.mksoft.obj.Repository;

public class FriendData {
    private int id = 0;
    private String FriendName;
    //룸을 이용하여 내부 저장.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFriendName() {
        return FriendName;
    }

    public void setFriendName(String friendName) {
        FriendName = friendName;
    }
}
