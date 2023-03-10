package com.example.lab1010_2boundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyService mService;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((MyService.LocalBinder) service).getService();
            mBound =true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConn);
            mBound = false;
        }
    }

    public void mOnClick(View v){
        switch(v.getId()) {
            case R.id.btnCalc:
                if(mBound){
                    EditText editNum1 = (EditText) findViewById(R.id.editNum1);
                    EditText editNum2 = (EditText) findViewById(R.id.editNum2);
                    if(editNum1.length()>0 && editNum2.length()>0){
                        int num1 = Integer.parseInt(editNum1.getText().toString());
                        int num2 = Integer.parseInt(editNum2.getText().toString());

                        int result = mService.CalcNum(num1, num2);
                        Toast.makeText(this,"계산 결과 = "+result,
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}