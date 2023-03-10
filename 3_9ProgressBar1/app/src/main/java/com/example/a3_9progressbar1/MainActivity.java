package com.example.a3_9progressbar1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgStatus = (ProgressBar) findViewById(R.id.progStatus);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnDecrease1:
                mProgStatus.setProgress(mProgStatus.getProgress()-5);
                break;
            case R.id.btnIncrease1:
                mProgStatus.setProgress(mProgStatus.getProgress()+5);
                break;
            case R.id.btnDecrease2:
                mProgStatus.setSecondaryProgress(mProgStatus.getProgress()-5);
                break;
            case R.id.btnIncrease2:
                mProgStatus.setSecondaryProgress(mProgStatus.getProgress()+5);
                break;
        }
    }
}