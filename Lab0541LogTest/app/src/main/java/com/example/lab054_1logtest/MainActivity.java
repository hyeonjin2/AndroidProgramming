package com.example.lab054_1logtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LogTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnAssert:
                Log.wtf(TAG, "Assert message");
                break;
            case R.id.btnError:
                Log.wtf(TAG, "Error message");
                break;
            case R.id.btnWarn:
                Log.wtf(TAG, "Warn message");
                break;
            case R.id.btnInfo:
                Log.wtf(TAG, "Info message");
                break;
            case R.id.btnDebug:
                Log.wtf(TAG, "Debug message");
                break;
            case R.id.btnVerbose:
                Log.wtf(TAG, "Verbose message");
                break;
        }
    }
}