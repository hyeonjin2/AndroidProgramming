package com.example.neck_is_turtle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class LikeActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener{

    ImageButton btn_back,btn_like,btn_alert;
    LinearLayout linearLayout;
    Button day_mon,day_tue,day_wed,day_thu,day_fri,day_sat,day_sun,btn_set,btn_aclose;
    Button btn_see,btn_sclose;
    Dialog lDialog,sDialog;
    TimePicker timePicker;
    Calendar calendar;
    int shour,smin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        btn_back = findViewById(R.id.btn_back);
        linearLayout = findViewById(R.id.lay_like);

        lDialog = new Dialog(LikeActivity.this);
        lDialog.setContentView(R.layout.dialog_alert);

        sDialog = new Dialog(LikeActivity.this);
        sDialog.setContentView(R.layout.dialog_alert_set_completion);

        timePicker = lDialog.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(this);

        day_mon = lDialog.findViewById(R.id.day_mon);
        day_tue = lDialog.findViewById(R.id.day_tue);
        day_wed = lDialog.findViewById(R.id.day_wed);
        day_thu = lDialog.findViewById(R.id.day_thu);
        day_fri = lDialog.findViewById(R.id.day_fri);
        day_sat = lDialog.findViewById(R.id.day_sat);
        day_sun = lDialog.findViewById(R.id.day_sun);

        btn_set = lDialog.findViewById(R.id.set);
        btn_aclose = lDialog.findViewById(R.id.btn_close);

        btn_see = sDialog.findViewById(R.id.see);
        btn_sclose = sDialog.findViewById(R.id.btn_close);

        // 타임피커 초기값 현재시간으로 설정하기
        calendar = Calendar.getInstance();
        shour = calendar.get(calendar.HOUR_OF_DAY);
        smin = calendar.get(calendar.MINUTE);

        // 뒤로가기 버튼 클릭했을 때
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        // 레이아웃 인플레이터 객체
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 메인에 새로 생성한 레이아웃 추가
        layoutInflater.inflate(R.layout.box_video, linearLayout, true);
        btn_like = linearLayout.findViewById(R.id.btn_like);
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup parentViewGroup = (ViewGroup) linearLayout.getParent();
                if (null != parentViewGroup) {
                    parentViewGroup.removeView(linearLayout);
                }
            }
        });
        // 알림설정 띄우기
        btn_alert = linearLayout.findViewById(R.id.btn_alert);
        btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                lDialog.show();
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
                Toast.makeText(LikeActivity.this, shour+"시 : "+smin+"분 "+days+"마다 알림",
                        Toast.LENGTH_SHORT).show();
                // 서버로 정보 넘겨주기 (영상번호, 시간, 분, 반복 날짜)


                // 대화상자 닫기
                lDialog.dismiss();
                // 설정완료 창 띄우기
                sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sDialog.show();
            }
        });
        btn_aclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lDialog.dismiss();
            }
        });
        btn_sclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        btn_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeActivity.this,AlertActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
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