package com.mksoft.obj.Repository;

public class OfferedImageData {
    private int id = 0;
    private String imageName;
    private String GIFPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getGIFPath() {
        return GIFPath;
    }

    public void setGIFPath(String GIFPath) {
        this.GIFPath = GIFPath;
    }
}
