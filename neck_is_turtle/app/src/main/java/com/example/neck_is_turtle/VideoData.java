package com.example.neck_is_turtle;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class VideoData {
    private int id, category;
    private int thumbnailImage;
    private int profileImage;
    private String videoName;
    private String creatorName;

    public VideoData(int num, int img1, int img2, String name1, String name2, int num2){
        this.id = num;
        this.category = num2;
        this.thumbnailImage = img1;
        this.profileImage = img2;
        this.videoName = name1;
        this.creatorName = name2;
    }

    public VideoData() {}

    public int getId(){ return this.id; }

    public int getCategory(){
        return this.category;
    }

    public int getThumbnailImage(){
        return this.thumbnailImage;
    }

    public int getProfileImage(){
        return this.profileImage;
    }

    public String getVideoName(){
        return this.videoName;
    }

    public String getCreatorName(){
        return this.creatorName;
    }
}
