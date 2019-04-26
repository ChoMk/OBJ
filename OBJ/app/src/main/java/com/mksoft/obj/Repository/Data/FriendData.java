package com.mksoft.obj.Repository.Data;

import java.util.ArrayList;

public class FriendData {
    private String id;
    private String name;
    FriendData(){}

    public FriendData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
