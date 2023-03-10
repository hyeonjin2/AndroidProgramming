package com.example.turtle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class activity_exercise extends AppCompatActivity {

    ImageButton btn_home;
    Button btn_str,btn1,btn2,btn3,btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        btn_home = findViewById(R.id.btn_home);
        btn_str = findViewById(R.id.btn_str);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);

        // 하단 홈버튼 눌렀을 때
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_home = new Intent(activity_exercise.this,MainActivity.class);
                startActivity(intent_home);
                overridePendingTransition(0,0);
                //overridePendingTransition(R.anim.in_left,R.anim.out_right);
            }
        });
        // 상단 스트레칭 버튼 눌렀을 때
        btn_str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_exe = new Intent(activity_exercise.this,activity_stretching.class);
                startActivity(intent_exe);
                overridePendingTransition(0,0);
            }
        });
    }

    // 버튼 색 바꾸기
    public void setButton(Button button, boolean click){

        if(click) { // 클릭 된 버튼 바꾸기
        }
        else { // 클릭 안된 버튼들 바꾸기
        }
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btn_1:
                setButton(btn1,true);
                setButton(btn2,false);
                setButton(btn3,false);
                setButton(btn4,false);
                break;
            case R.id.btn_2:
                setButton(btn1,false);
                setButton(btn2,true);
                setButton(btn3,false);
                setButton(btn4,false);
                break;
            case R.id.btn_3:
                setButton(btn1,false);
                setButton(btn2,false);
                setButton(btn3,true);
                setButton(btn4,false);
                break;
            case R.id.btn_4:
                setButton(btn1,false);
                setButton(btn2,false);
                setButton(btn3,false);
                setButton(btn4,true);
                break;
        }
    }
}