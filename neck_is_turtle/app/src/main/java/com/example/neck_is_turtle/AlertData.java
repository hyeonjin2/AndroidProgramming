package com.example.neck_is_turtle;

public class AlertData {
    static final String Am = "오전";
    static final String Pm = "오후";

    int videoId;
    int hour, minute;
    boolean[] days = new boolean[7];
    String ampm;
    String title;
    String details;

    AlertData(){}

    AlertData(int num, int num1, int num2, boolean[] arr){
        this.videoId = num;
        this.hour = num1;
        this.minute = num2;
        this.days = arr;
        this.title = "Schedule Name";
        this.details = "Schedule Detals";

        if(num1 >= 12)
            this.ampm = Pm;
        else
            this.ampm = Am;
    }

    AlertData(int num1, int num2, boolean[] arr, String str1, String str2){
        this.hour = num1;
        this.minute = num2;
        this.days = arr;
        this.title = str1;
        this.details = str2;

        if(num1 >= 12)
            this.ampm = Pm;
        else
            this.ampm = Am;
    }

    AlertData(int num1, int num2, boolean[] arr){
        this.hour = num1;
        this.minute = num2;
        this.days = arr;
        this.title = "Schedule Name";
        this.details = "Schedule Detals";

        if(num1 >= 12)
            this.ampm = Pm;
        else
            this.ampm = Am;
    }
    int getVideoId(){
        return this.videoId;
    }

    int getHour(){
        int result;
        if(this.ampm.equals(Pm)){
            if(this.hour!=12)
                result = this.hour - 12;
            else
                result = this.hour;
        }
        else
            result = this.hour;
        return result;
    }

    int getMinute(){ return this.minute; }

    boolean[] getDays(){ return this.days; }

    String getAmpm(){ return this.ampm; }

    String getTitle(){ return this.title; }

    String getDetails(){ return this.details; }
}
