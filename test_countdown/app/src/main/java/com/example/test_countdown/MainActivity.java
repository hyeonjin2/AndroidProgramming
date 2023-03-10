package com.example.test_countdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity{

    TextView textView;
    Button button, button2,button3;
    TimerTask timerTask;
    Timer timer = new Timer();
    String str_num;
    int timerCount = 30;
    int num;

    int count = 0;
    int discount = 0;

    public static final int SEND_START = 0;
    public static final int SEND_STOP = 1;

    private SensorManager mSensorManager = null;
    private SensorEventListener gyroListener;
    private Sensor sensor = null;

    Thread thread;

    //Roll and Pitch
    private double roll;    //x
    private double pitch;   //y
    private double yaw;     //z

    //단위시간을 구하기 위한 변수
    private double timestamp;
    private double dt;

    // 회전각을 구하기 위한 변수
    private double RAD2DGR = 180 / Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;

    TextView dataX,dataY,dataZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataX = findViewById(R.id.data_x);
        dataY = findViewById(R.id.data_y);
        dataZ = findViewById(R.id.data_z);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        // 센서매니저 생성
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 자이로스코프 센서 등록
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerTask();
                gyroListener = new GyroscopeListener(dataX,dataY,dataZ);
                mSensorManager.registerListener(gyroListener, sensor, 1000000000);
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
                mSensorManager.unregisterListener(gyroListener);
                roll = 0;
                pitch = 0;
                yaw = 0;
                count = 0;
                discount = 0;
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

            @Override
            public void run()
            {
                timerCount--;
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        num = 29 - timerCount;
                        textView.setText(String.valueOf(num));
                    }
                });
            }
        };
        timer.schedule(timerTask,0 ,1000);
        mSensorManager.unregisterListener(gyroListener);
    }

    public void waitTimerTask(){
        if(timerTask != null){
            str_num = (String) textView.getText();
            timerTask.cancel();
            timerTask = null;
            timerCount = -Integer.parseInt(str_num) + 30;
        }
    }

    public void stopTimerTask(){
        if(timerTask != null)
        {
            textView.setText("0");
            timerTask.cancel();
            timerTask = null;
            timerCount = 30;
            Toast.makeText(this, "count : "+String.valueOf(count)+"\t"+"discount : "+String.valueOf(discount), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mSensorManager.registerListener(gyroListener,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(gyroListener);
        roll = 0;
        pitch = 0;
        yaw = 0;
        count = 0;
        discount = 0;
    }

    private class GyroscopeListener implements SensorEventListener {

        TextView dataX,dataY,dataZ;

        double gyroX;
        double gyroY;
        double gyroZ;

        boolean inChallenge;

        GyroscopeListener(TextView x, TextView y, TextView z){
            dataX = x;
            dataY = y;
            dataZ = z;
        }
        @Override
        public void onSensorChanged(SensorEvent event) {

            /* 각 축의 각속도 성분을 받는다. */
            gyroX = event.values[0];
            gyroY = event.values[1];
            gyroZ = event.values[2];

            /* 각속도를 적분하여 회전각을 추출하기 위해 적분 간격(dt)을 구한다.
             * dt : 센서가 현재 상태를 감지하는 시간 간격
             * NS2S : nano second -> second */
            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            /* 맨 센서 인식을 활성화 하여 처음 timestamp가 0일때는 dt값이 올바르지 않으므로 넘어간다. */
            if (dt - timestamp*NS2S != 0) {

                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
                pitch = pitch + gyroY*dt;
                roll = roll + gyroX*dt;
                yaw = yaw + gyroZ*dt;

                dataX.setText("[X] : "+ String.format("%.4f", gyroX)+"[roll] : "+String.format("%.1f",roll*RAD2DGR));
                dataY.setText("[Y] : "+ String.format("%.4f", gyroY)+"[pitch] : "+String.format("%.1f",pitch*RAD2DGR));
                dataZ.setText("[Z] : "+ String.format("%.4f", gyroZ)+"[yaw] : "+String.format("%.1f",yaw*RAD2DGR));

            }

            inChallenge = roll*RAD2DGR>=70&&roll*RAD2DGR<=90;

            if(inChallenge){
                count++;
                Log.d("Log","inChallenge true");
            }
            else{
                discount++;
                Log.d("Log","inChallenge false");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
