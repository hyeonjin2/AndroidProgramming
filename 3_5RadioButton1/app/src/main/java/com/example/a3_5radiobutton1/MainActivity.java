package com.example.a3_5radiobutton1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutMain = (LinearLayout) findViewById(R.id.layoutMain);

        int id = R.id.radioGreen; // 초록을 기본값으로 선택한다.
        RadioGroup ColorGroup = (RadioGroup) findViewById(R.id.groupColor);
        ColorGroup.check(id);   // 라디오 그룹에서 초록을 선택한다.
        changeColor(id);        // 초록을 클릭한 것처럼 처리한다.
    }
    public void mOnClick(View v){
        changeColor(v.getId());
    }

    private void changeColor(int id) {
        switch(id){
            case R.id.radioRed:
                mLayoutMain.setBackgroundColor(0xffff0000);
                break;
            case R.id.radioGreen:
                mLayoutMain.setBackgroundColor(0xff00ff00);
                break;
            case R.id.radioBlue:
                mLayoutMain.setBackgroundColor(0xff0000ff);
                break;
        }
    }
}