package com.example.turtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_stretching,btn_alert,btn_like;
    Button btn_add;
    TextView tv_today;
    Calendar calendar;
    LinearLayout lay_schedule, lay_empty;

    String[] days = new String[]{"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_stretching=findViewById(R.id.btn_stretching);
        btn_alert = findViewById(R.id.btn_alert);
        btn_like = findViewById(R.id.btn_like);
        tv_today = findViewById(R.id.tv_today);
        btn_add = findViewById(R.id.btn_add);

        lay_schedule = findViewById(R.id.lay_schedule);
        lay_empty = findViewById(R.id.layout_empty);

        // 사용자가 설정한 알람이 있는지 확인
        /*
         * 서버에서 사용자에 대해 알람설정 정보 가져와 확인하기
         * */
        // 설정한 알람이 없다면 추가하기 버튼 기능 구현
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // 설정한 알람이 있다면 추가하기 박스 invisible설정 및 알람
        lay_empty.setVisibility(View.GONE);
        // 리스트 레이아웃에 추가하기
        // 레이아웃 인플레이터 객체
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 메인에 새로 생성한 레이아웃 추가
        layoutInflater.inflate(R.layout.box_schedule, lay_schedule, true);

        // calendar에서 가져온 int값 요일로 바꿔 설정하기 -> 1:일요일 ~ 7:토요일
        calendar = Calendar.getInstance();
        tv_today.setText(days[calendar.get(calendar.DAY_OF_WEEK)-1]);

        // 하단 메뉴바 스트레칭 클릭 시
        btn_stretching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // 상단 알람 버튼 클릭 시
        btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // 상단 하트 버튼 클릭 시
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}