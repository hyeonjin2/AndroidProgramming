package com.example.test_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button, button2,button3;
    TimerTask timerTask;
    Timer timer = new Timer();
    String str_num;
    int count = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startTimerTask();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitTimerTask();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopTimerTask();
            }
        });
    }
    @Override
    protected void onDestroy()
    {
        timer.cancel();
        super.onDestroy();
    }

    public void startTimerTask(){
        stopTimerTask();

        timerTask = new TimerTask()
        {
            int num;

            @Override
            public void run()
            {
                count--;
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        num = 59 - count;
                        textView.setText(String.valueOf(num));
                    }
                });
            }
        };
        timer.schedule(timerTask,0 ,1000);
    }

    public void waitTimerTask(){
        if(timerTask != null){
            str_num = (String) textView.getText();
            timerTask.cancel();
            timerTask = null;
            count = -Integer.parseInt(str_num) + 60;
        }
    }

    public void stopTimerTask(){
        if(timerTask != null)
        {
            textView.setText("0");
            timerTask.cancel();
            timerTask = null;
            count = 60;
        }
    }
}