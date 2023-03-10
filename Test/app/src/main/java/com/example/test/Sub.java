package com.example.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class Sub extends LinearLayout {

    public Sub(Context context, AttributeSet attrs){
        super(context, attrs);

        init(context);
    }

    public Sub(Context context){
        super(context);

        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.insert_food,this,true);
    }
}
