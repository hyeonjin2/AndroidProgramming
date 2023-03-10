package com.example.lab054_4activityrecreate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            //savedInstanceState 객체에 대해 get~() 메서드를
            //호출하여 데이터를 읽는다.
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        // outState 객체에 대해 put~() 메서드를
        // 호출하여 데이터를 저장한다.
    }
}