package com.mksoft.obj.Repository.Data;

public class OfferedImageData {
    private int id = 0;
    private String imageName;
    private int GIFPath;

    public OfferedImageData(int id, String imageName, int GIFPath) {
        this.id = id;
        this.imageName = imageName;
        this.GIFPath = GIFPath;
    }

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

    public int getGIFPath() {
        return GIFPath;
    }

    public void setGIFPath(int GIFPath) {
        this.GIFPath = GIFPath;
    }
}
