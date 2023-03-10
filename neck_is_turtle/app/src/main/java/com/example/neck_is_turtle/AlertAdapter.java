package com.example.neck_is_turtle;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlertAdapter extends BaseAdapter {
    Context context;
    private int showCount = 2;
    ArrayList<AlertData> alerts = new ArrayList<AlertData>();

    public void setShowCount(int num){
        showCount = num;
    }

    public int getSize(){
        return alerts.size();
    }

    @Override
    public int getCount() {
        return showCount;
    }

    @Override
    public Object getItem(int position) {
        return alerts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        AlertData alertsData = alerts.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.box_schedule, parent, false);
        }

        // box_schedule 아이템들 가져오기
        TextView tv_title = convertView.findViewById(R.id.tv_title);
        TextView tv_details = convertView.findViewById(R.id.tv_details);

        // AlertData에 있는 정보 넣기
        String title = alerts.get(position).getAmpm()+" "+String.valueOf(alerts.get(position).getHour()
                +":"+String.valueOf(alerts.get(position).getMinute())+"   "+alerts.get(position).getTitle());
        tv_title.setText(title);
        tv_details.setText(alerts.get(position).getDetails());

        return convertView;
    }

    // 아이템 add함수
    public void addItem(AlertData alertData) {
        alerts.add(alertData);
    }
}
