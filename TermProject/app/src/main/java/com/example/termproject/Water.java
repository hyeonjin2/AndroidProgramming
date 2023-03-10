package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Water extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water);
    }

    public void mOnClick_water(View v){
        Intent intentWater = new Intent(Water.this, Daily.class);
        switch (v.getId()){
            case R.id.btn_record_water:
                EditText goal = (EditText)findViewById(R.id.edit_goal_water);
                EditText water = (EditText)findViewById(R.id.edit_water);
                if(water.getText()!=null && goal.getText()!=null) {
                    intentWater.putExtra("goal", Float.parseFloat(goal.getText().toString()));
                    intentWater.putExtra("water", Float.parseFloat(water.getText().toString()));
                }
                break;
            case R.id.btn_water_back:
                break;
        }
        startActivity(intentWater);
    }
}
