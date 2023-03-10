package com.example.termproject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;

public class Daily extends AppCompatActivity {

    private int month;
    private int day;
    private TextView date;
    private float water;
    private float goal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_food);

        Intent intentDaily = getIntent();
        month = intentDaily.getIntExtra("month",0);
        day = intentDaily.getIntExtra("day",0);
        date = (TextView)findViewById(R.id.text_date);
        date.setText(String.valueOf(month)+"월 "+String.valueOf(day)+"일");
        float water = intentDaily.getFloatExtra("water",-1);
        float goal = intentDaily.getFloatExtra("goal",-1);
        if(water != -1) {
            setWater(goal, water);
        }
        if(intentDaily.hasExtra("hour")){
            int hour = intentDaily.getIntExtra("hour",-1);
            Toast.makeText(this, String.valueOf(hour),Toast.LENGTH_SHORT).show();
            //insertFood();
        }
    }

    public void mOnClick_daily(View v){
        Intent intentBtn = new Intent();
        switch (v.getId()){
            case R.id.btn_daily_back:
                intentBtn.setClass(Daily.this,MainActivity.class);
                break;
            case R.id.btn_food:
                intentBtn.setClass(Daily.this,InsertFood.class);
                break;
            case R.id.btn_alert:
                intentBtn.setClass(Daily.this,SetAlert.class);
                break;
            case R.id.btn_water:
                intentBtn.setClass(Daily.this,Water.class);
                break;
        }
        startActivity(intentBtn);
    }

    public void setWater(float goal, float water){
        LinearLayout lay_water = (LinearLayout)findViewById(R.id.l_water);

        float g_number = goal;
        float w_number = water;
        float r_number = g_number - w_number;
        if(w_number<0) {
            return;
        }
        float r = (w_number*10)%10;       // 컵의 물 H,E
        int l = (int) w_number;      // 꽉찬 컵의 개수

        for(int i = 0; i < l; i++){
            ImageView img_waterF = new ImageView(this);
            img_waterF.setLayoutParams(new ViewGroup.LayoutParams(
                    100, ViewGroup.LayoutParams.WRAP_CONTENT));
            img_waterF.setImageResource(R.drawable.full_cup);
            img_waterF.setScaleType(ImageView.ScaleType.FIT_CENTER);
            lay_water.addView(img_waterF);
        }
        if(r >= 5){
            ImageView img_waterH = new ImageView(this);
            img_waterH.setLayoutParams(new ViewGroup.LayoutParams(
                    95, ViewGroup.LayoutParams.WRAP_CONTENT));
            img_waterH.setImageResource(R.drawable.half_cup);
            img_waterH.setScaleType(ImageView.ScaleType.FIT_CENTER);
            lay_water.addView(img_waterH);
        }

        if(r_number >= 1) {
            int e = (int) r_number;    // empty 컵의 개수
            for(int i = 0; i < e; i++){
                ImageView img_waterE = new ImageView(this);
                img_waterE.setLayoutParams(new ViewGroup.LayoutParams(
                        100, ViewGroup.LayoutParams.WRAP_CONTENT));
                img_waterE.setImageResource(R.drawable.empty_cup);
                img_waterE.setScaleType(ImageView.ScaleType.FIT_CENTER);
                lay_water.addView(img_waterE);
            }
        }
    }
    public void insertFood(){
        LinearLayout lay_food =(LinearLayout)findViewById(R.id.insert_list);

        // 레이아웃 설정
        LinearLayout lay_record = new LinearLayout(this);
        LinearLayout.LayoutParams lay_recordParams = (LinearLayout.LayoutParams) lay_record.getLayoutParams();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(12*dm.density);
        lay_record.setBackgroundResource(R.drawable.gray_background);
        lay_recordParams.topMargin = size;
        lay_recordParams.width=ViewGroup.LayoutParams.MATCH_PARENT;
        lay_recordParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        lay_record.setOrientation(LinearLayout.HORIZONTAL);

        // vertical 레이아웃 설정
        LinearLayout lay_word = new LinearLayout(this);
        lay_word.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lay_wordParams = (LinearLayout.LayoutParams) lay_word.getLayoutParams();
        lay_wordParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lay_wordParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lay_wordParams.gravity = Gravity.CENTER;

        TextView time = new TextView(this);
        TextView explain = new TextView(this);
        TextView meal = new TextView(this);
        ImageView img_food = new ImageView(this);
        RatingBar star = new RatingBar(this,null,R.attr.ratingBarStyleSmall);

        // meal 레이아웃 설정
        LinearLayout.LayoutParams meal_Params=(LinearLayout.LayoutParams) meal.getLayoutParams();
        meal.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        size = Math.round(2*dm.density);
        meal_Params.setMargins(size,size,size,size);
        meal_Params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        meal_Params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // time 레이아웃 설정
        LinearLayout.LayoutParams time_Params=(LinearLayout.LayoutParams) meal.getLayoutParams();
        time.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
        time_Params.setMargins(size,size,size,size);
        time_Params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        time_Params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // explain 레이아웃 설정
        LinearLayout.LayoutParams explain_Params=(LinearLayout.LayoutParams) meal.getLayoutParams();
        explain.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
        explain_Params.setMargins(size,size,size,size);
        explain_Params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        explain_Params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // star 레이아웃 설정
        LinearLayout.LayoutParams star_Params=(LinearLayout.LayoutParams) meal.getLayoutParams();
        star_Params.setMargins(size,size,size,size);
        size = Math.round(10*dm.density);
        star_Params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        star_Params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        star_Params.setMargins(0,0,0,size);
        star_Params.gravity = Gravity.RIGHT;
        star.setNumStars(5);
        star.setStepSize(1);

        // intent로 time, explain, meal, star 값 가져오기
        Intent intentFood = getIntent();
        if(intentFood.hasExtra("hour")&&intentFood.hasExtra("minute")) {
            time.setText(String.valueOf(intentFood.getIntExtra("hour", -1))
                    + " : " + String.valueOf(intentFood.getIntExtra("minute", -1)));
        }
        if(intentFood.hasExtra("explain")) {
            explain.setText(intentFood.getStringExtra("explain"));
        }
        if(intentFood.hasExtra("meal")) {
            meal.setText(intentFood.getStringExtra("meal"));
        }
        if(intentFood.hasExtra("rate")) {
            star.setRating(intentFood.getFloatExtra("rate", 0));
        }

        // intent로 이미지 가져오기
        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        if(intentFood.hasExtra("image")){
            img_food.setImageBitmap(image);
        }

        // 이미지 레이아웃 설정
        LinearLayout.LayoutParams img_LayoutParams = (LinearLayout.LayoutParams) img_food.getLayoutParams();
        size = Math.round(150*dm.density);
        img_food.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        size = Math.round(4*dm.density);
        img_LayoutParams.setMargins(size,size,size,size);
        img_food.setScaleType(ImageView.ScaleType.FIT_CENTER);


        lay_record.addView(img_food);
        lay_word.addView(meal);
        lay_word.addView(time);
        lay_word.addView(explain);
        lay_word.addView(star);
        lay_record.addView(lay_word);
        lay_food.addView(lay_record);
    }

/*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    }*/
}
