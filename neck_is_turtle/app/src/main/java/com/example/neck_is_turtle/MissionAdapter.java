package com.example.neck_is_turtle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class MissionAdapter extends BaseAdapter {
    Context context;
    ArrayList<MissionData> missions = new ArrayList<MissionData>();

    @Override
    public int getCount() {
        return missions.size();
    }

    @Override
    public Object getItem(int position) {
        return missions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        MissionData missionData = missions.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.box_mission, parent, false);
        }

        // box_mission 아이템들 가져오기
        CheckBox mission_title = convertView.findViewById(R.id.mission_title);
        TextView mission_details = convertView.findViewById(R.id.mission_details);

        // MissionData에 있는 정보 넣기
        mission_title.setText(missions.get(position).getTitle());
        mission_details.setText(missions.get(position).getDetails());

        return convertView;
    }
    // 아이템 add함수
    public void addItem(MissionData missionData) {
        missions.add(missionData);
    }
}
