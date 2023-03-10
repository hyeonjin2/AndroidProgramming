package com.example.p03_viewattr2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImagePic1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImagePic1 = (ImageView) findViewById(R.id.imagePic1);
    }

    public void mOnClick1(View v){
        mImagePic1.setVisibility(View.INVISIBLE);
    }

    public void mOnClick2(View v){
        mImagePic1.setVisibility(View.GONE);
    }

}