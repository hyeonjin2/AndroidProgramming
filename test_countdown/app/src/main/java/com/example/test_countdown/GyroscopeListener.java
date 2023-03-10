package com.example.test_countdown;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

public class GyroscopeListener implements SensorEventListener {

    //단위시간을 구하기 위한 변수
    private double timestamp;
    private double dt;

    // 회전각을 구하기 위한 변수
    private double RAD2DGR = 180 / Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;

    //Roll and Pitch
    private double roll;    //x
    private double pitch;   //y
    private double yaw;     //z

    private double gyroX;
    private double gyroY;
    private double gyroZ;

    TextView dataX;
    TextView dataY;
    TextView dataZ;

    boolean inChallenge;

    GyroscopeListener(TextView x, TextView y, TextView z){
        dataX = x;
        dataY = y;
        dataZ = z;
        inChallenge = false;
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
        if(roll*RAD2DGR>=70&&roll*RAD2DGR<=90){
            inChallenge = true;
            Log.d("Log","inChallenge true");
        }else {
            inChallenge = false;
            Log.d("Log","inChallenge false");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public boolean isInChallenge(){
        return inChallenge;
    }
}
