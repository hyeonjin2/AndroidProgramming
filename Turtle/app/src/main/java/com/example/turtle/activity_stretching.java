package com.example.turtle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class activity_stretching extends AppCompatActivity implements TimePicker.OnTimeChangedListener {

    ImageButton btn_home;
    Button btn_exe,btn1,btn2,btn3,btn4;
    Button day_mon,day_tue,day_wed,day_thu,day_fri,day_sat,day_sun,btn_set,btn_mclose;
    Button btn_see,btn_sclose;
    ImageButton alert,btn_alert,btn_like;
    Dialog mDialog,sDialog;
    TimePicker timePicker;
    Calendar calendar;
    int shour,smin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stretching);

        btn_home = findViewById(R.id.btn_home);
        btn_exe = findViewById(R.id.btn_exe);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);

        alert = findViewById(R.id.btn_videoAlert1);

        mDialog = new Dialog(activity_stretching.this);
        mDialog.setContentView(R.layout.dialog_alert);

        sDialog = new Dialog(activity_stretching.this);
        sDialog.setContentView(R.layout.dialog_alert_set_completion);

        timePicker = mDialog.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(this);

        day_mon = mDialog.findViewById(R.id.day_mon);
        day_tue = mDialog.findViewById(R.id.day_tue);
        day_wed = mDialog.findViewById(R.id.day_wed);
        day_thu = mDialog.findViewById(R.id.day_thu);
        day_fri = mDialog.findViewById(R.id.day_fri);
        day_sat = mDialog.findViewById(R.id.day_sat);
        day_sun = mDialog.findViewById(R.id.day_sun);

        btn_set = mDialog.findViewById(R.id.set);
        btn_mclose = mDialog.findViewById(R.id.btn_close);

        btn_see = sDialog.findViewById(R.id.see);
        btn_sclose = sDialog.findViewById(R.id.btn_close);

        btn_alert = findViewById(R.id.btn_alert);
        btn_like = findViewById(R.id.btn_like);

        // 타임피커 초기값 현재시간으로 설정하기
        calendar = Calendar.getInstance();
        shour = calendar.get(calendar.HOUR_OF_DAY);
        smin = calendar.get(calendar.MINUTE);

        // 하단 홈버튼 눌렀을 때
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_home = new Intent(activity_stretching.this,MainActivity.class);
                startActivity(intent_home);
                overridePendingTransition(0,0);
            }
        });
        // 상단 알람버튼 눌렀을 때
        btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 상단 하트 버튼 눌렀을 때
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // 상단 운동 버튼 눌렀을 때
        btn_exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_str = new Intent(activity_stretching.this,activity_exercise.class);
                startActivity(intent_str);
                overridePendingTransition(0,0);
            }
        });
        // 알람설정 버튼 눌렀을 때
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 대화상자 배경 투명
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });
        // 대화상자 확인버튼 눌렀을 때
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택된 요일 days 리스트에 넣기
                Button[] buttons = {day_mon,day_tue,day_wed,day_thu,day_fri,day_sat,day_sun};
                String[] str_day = {"월","화","수","목","금","토","일"};
                boolean[] selected_day = new boolean[7];
                Arrays.fill(selected_day,false);
                ArrayList<String> days = new ArrayList<String>();

                for(int i=0; i<7;i++){
                    if(buttons[i].isSelected()){
                        days.add(str_day[i]);
                    }
                }
                // 선택된 요일 boolean list에 넣기
                for(int i=0; i<7;i++){
                    selected_day[i] = buttons[i].isSelected();
                }
                // 설정한 시간, 요일 Toast메세지 출력
                Toast.makeText(activity_stretching.this, shour+"시 : "+smin+"분 "+days+"마다 알림",
                        Toast.LENGTH_SHORT).show();
                // 서버로 정보 넘겨주기 (영상번호, 시간, 분, 반복 날짜)


                // 대화상자 닫기
                mDialog.dismiss();
                // 설정완료 창 띄우기
                sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sDialog.show();
            }
        });
        btn_mclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        btn_sclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
    }
    // 타임피커에서 설정된 시간 가져오기
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        shour = hourOfDay;
        smin = minute;
    };

    // 카테고리 버튼들 selected 설정
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btn_1:
                btn1.setSelected(!btn1.isSelected());
                break;
            case R.id.btn_2:
                btn2.setSelected(!btn2.isSelected());
                break;
            case R.id.btn_3:
                btn3.setSelected(!btn3.isSelected());
                break;
            case R.id.btn_4:
                btn4.setSelected(!btn4.isSelected());
                break;
        }
    }

    // 대화상자 요일 버튼들 selected 설정
    public void dOnClick(View v){
        switch (v.getId()){
            case R.id.day_mon:
                day_mon.setSelected(!day_mon.isSelected());
                break;
            case R.id.day_tue:
                day_tue.setSelected(!day_tue.isSelected());
                break;
            case R.id.day_wed:
                day_wed.setSelected(!day_wed.isSelected());
                break;
            case R.id.day_thu:
                day_thu.setSelected(!day_thu.isSelected());
                break;
            case R.id.day_fri:
                day_fri.setSelected(!day_fri.isSelected());
                break;
            case R.id.day_sat:
                day_sat.setSelected(!day_sat.isSelected());
                break;
            case R.id.day_sun:
                day_sun.setSelected(!day_sun.isSelected());
                break;
        }
    }
}