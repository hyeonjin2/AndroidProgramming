package com.example.lab054_5savestate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mNumber;
    private TextView mTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mNumber = savedInstanceState.getInt("number",0);
        }
        mTextNumber = (TextView) findViewById(R.id.textNumber);
        mTextNumber.setText(mNumber + "");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("number",mNumber);
    }

    public void mOnClick(View v){
        mNumber++;
        mTextNumber.setText(mNumber + "");
    }
}