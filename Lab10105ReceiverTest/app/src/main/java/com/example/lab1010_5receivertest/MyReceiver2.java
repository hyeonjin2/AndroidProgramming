package com.example.lab1010_5receivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int data = intent.getIntExtra("mydata",-1);
        Toast.makeText(context,"MyReceiver2: mydata = "+data,
                Toast.LENGTH_SHORT).show();
    }
}
