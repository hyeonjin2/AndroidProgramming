package com.example.lab065_5listenerhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "버튼1 클릭!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class MyButton extends androidx.appcompat.widget.AppCompatButton {

    public MyButton(Context context) {
        super(context);
        init();
    }

    public MyButton(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        // 초기화 코드를 넣는다.
        setBackgroundColor(Color.YELLOW);
        setTextColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Toast.makeText(getContext(), "버튼2 클릭!", Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }
}




