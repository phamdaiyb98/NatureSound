package com.haui.phamdai.mediaappmusickpt;

import android.widget.ArrayAdapter;

public class Song {
    private String title;
    private int link;

    public Song(String title, int link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

//    Nếu dùng ArrayAdapter thì dùng cái này để hiển thị luôn Object ra listView
//    @Override
//    public String toString() {
//        return title;
//    }
}
