package com.example.termproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class InsertFood extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    private String result;
    private boolean selected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_food);
    }

    public void mOnClick_layout(View v){
        Intent intentFood = new Intent();
        switch (v.getId()){
            case R.id.btn_food_back:
                intentFood.setClass(InsertFood.this,Daily.class);
                break;
            case R.id.btn_plus_image:
                intentFood.setType("image/*");
                intentFood.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFood, REQUEST_CODE);
                break;
        }
        startActivity(intentFood);
    }

    public void mOnClick_button(View v){
        switch (v.getId()){
            case R.id.btn_f1:
                Button btn_f1 = (Button)findViewById(R.id.btn_f1);
                result = btn_f1.getText().toString();
                break;
            case R.id.btn_f2:
                Button btn_f2 = (Button)findViewById(R.id.btn_f2);
                result = btn_f2.getText().toString();
                break;
            case R.id.btn_f3:
                Button btn_f3 = (Button)findViewById(R.id.btn_f3);
                result = btn_f3.getText().toString();
                break;
            case R.id.btn_f4:
                Button btn_f4 = (Button)findViewById(R.id.btn_f4);
                result = btn_f4.getText().toString();
                break;
            case R.id.btn_f5:
                Button btn_f5 = (Button)findViewById(R.id.btn_f5);
                result = btn_f5.getText().toString();
                break;
        }
    }

    public void mOnClick_record(View v){
        Intent intent = new Intent(InsertFood.this, Daily.class);

        EditText hour = (EditText)findViewById(R.id.edit_hour);
        EditText minute = (EditText)findViewById(R.id.edit_minute);
        if(hour.getText()!=null) {
            int m_hour = Integer.parseInt(hour.getText().toString());
            intent.putExtra("hour", m_hour);
        }

        if(minute.getText()!=null) {
            int m_minute = Integer.parseInt(minute.getText().toString());
            intent.putExtra("minute", m_minute);
        }

        EditText explain = (EditText)findViewById(R.id.edit_explain);
        if(explain.getText()!=null) {
            intent.putExtra("explain", explain.getText().toString());
        }

        RatingBar star = (RatingBar)findViewById(R.id.daily_rating);
        intent.putExtra("rate",star.getRating());

        intent.putExtra("meal",result);

        Bitmap sendBitmap = BitmapFactory.decodeResource(getResources(), imageView.getId());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        if(selected) {
            intent.putExtra("image", byteArray);
        }
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    imageView = (ImageView)findViewById(R.id.btn_plus_image);
                    imageView.setImageBitmap(img);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setBackgroundColor(Color.WHITE);
                    selected = true;
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
