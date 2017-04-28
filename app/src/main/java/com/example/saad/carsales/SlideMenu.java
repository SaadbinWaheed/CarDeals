package com.example.saad.carsales;

/**
 * Created by Fahaid on 4/28/2017.
 */

public class SlideMenu {
    private  int imgid;
    private String title;

    public SlideMenu(int imgid, String title) {
        this.imgid = imgid;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
