package com.example.botton01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBotton; // Button에 커서를 올리고 alt+enter해서 import한다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBotton = (Button) findViewById(R.id.button);   // 레이아웃에서 버튼 객체를 찾아낸다.
        mBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "버튼 클릭!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}