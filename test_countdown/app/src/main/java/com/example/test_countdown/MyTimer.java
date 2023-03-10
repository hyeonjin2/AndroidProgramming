package com.example.test_countdown;

import android.os.CountDownTimer;
import android.widget.TextView;

    class MyTimer extends CountDownTimer {
        TextView textView;

        public MyTimer(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText(millisUntilFinished/100 + "초");
        }

        @Override
        public void onFinish() {
            textView.setText("0초");
        }
    }
