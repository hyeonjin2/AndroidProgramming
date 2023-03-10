package com.example.lab1010_5receivertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mReceiver;
    private IntentFilter mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiver = new MyReceiver2();
        mFilter =  new IntentFilter("andbook.example.TESTEVENT2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mReceiver,mFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnIntent1:
                Intent intent1 = new Intent(this,MyReceiver1.class);
                intent1.putExtra("mydata",100);
                sendBroadcast(intent1);
                break;
            case R.id.btnIntent2:
                Intent intent2 = new Intent();
                intent2.setAction("andbook.example.TESTEVENT2");
                intent2.putExtra("mydata",200);
                sendBroadcast(intent2);
                break;
        }
    }
}