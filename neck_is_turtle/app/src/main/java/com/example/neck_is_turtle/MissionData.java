package com.example.neck_is_turtle;

public class MissionData {
    private String title;
    private String details;

    MissionData(){}

    MissionData(String str1,String str2){
        title = str1;
        details = str2;
    }
    String getTitle(){return this.title;}

    String getDetails(){return this.details;}
}
