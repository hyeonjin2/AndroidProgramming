package com.example.a3_6togglebutton1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v){

        ToggleButton btn = (ToggleButton) v;
        if(btn.isChecked())
            btn.setTextColor(0xffff0000);
        else
            btn.setTextColor(0xff0000ff);
    }
}