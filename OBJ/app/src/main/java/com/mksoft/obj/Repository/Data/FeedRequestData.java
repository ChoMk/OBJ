package com.mksoft.obj.Repository.Data;

public class FeedRequestData {
    String Attr1;
    String Attr2;

    public FeedRequestData(){

    }

    public FeedRequestData(String Attr1, String Attr2) {
        this.Attr1 = Attr1;
        this.Attr2 = Attr2;
    }

    public String getAttr1() {
        return Attr1;
    }

    public void setAttr1(String attr1) {
        Attr1 = attr1;
    }

    public String getAttr2() {
        return Attr2;
    }

    public void setAttr2(String attr2) {
        Attr2 = attr2;
    }
}
