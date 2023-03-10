package com.example.neck_is_turtle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class VideoAdapter extends BaseAdapter implements TimePicker.OnTimeChangedListener {
    ArrayList<VideoData> videos = new ArrayList<VideoData>();
    Context context;

    ImageButton btn_alert,btn_like;
    TimePicker timePicker;
    Button day_mon,day_tue,day_wed,day_thu,day_fri,day_sat,day_sun,btn_set,btn_mclose;
    Button btn_see,btn_sclose;
    TextView mtv_video,mtv_creator;
    Dialog mDialog,sDialog;
    Calendar calendar;
    int shour,smin;

    private StretchingFragment.AlertItemClickListener alertItemClickListener;
    private StretchingFragment.LikeItemClickListener likeItemClickListener;

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mViewHolder viewHolder;
        context = parent.getContext();
        VideoData videoData = videos.get(position);

        // 현재 화면에 보여지는 뷰만 만들도록 설정
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.box_video, parent, false);
            // box_video 아이템들 가져오기
            viewHolder = new mViewHolder();
            viewHolder.tv_videoName = convertView.findViewById(R.id.tv_video);
            viewHolder.tv_creatorName = convertView.findViewById(R.id.tv_creator);
            viewHolder.thumbnail = convertView.findViewById(R.id.img_video);
            viewHolder.profile = convertView.findViewById(R.id.img_creator);
            viewHolder.btn_alert = convertView.findViewById(R.id.btn_alert);
            viewHolder.btn_like = convertView.findViewById(R.id.btn_like);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (mViewHolder) convertView.getTag();
        }

        viewHolder.bind(videos.get(position).getVideoName(),videos.get(position).getCreatorName());

        viewHolder.btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alertItemClickListener!=null){
                    alertItemClickListener.onAlertItemClick(position);
                }
            }
        });
        viewHolder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likeItemClickListener!=null){
                    likeItemClickListener.onLikeItemClick(position);
                }
            }
        });

        return convertView;
    }

    public void setAlertClickListener(StretchingFragment.AlertItemClickListener listener){
        alertItemClickListener = listener;
    }

    public void setLikeClickListener(StretchingFragment.LikeItemClickListener listener){
        likeItemClickListener = listener;
    }

    // 아이템 add함수
    public void addItem(VideoData videoData) {
        videos.add(videoData);
    }

    // 타임피커에서 설정된 시간 가져오기
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        shour = hourOfDay;
        smin = minute;
    }
    public static class mViewHolder{
        TextView tv_videoName,tv_creatorName;
        ImageView thumbnail,profile;
        ImageButton btn_alert,btn_like;

        public void bind(String videoName,String creatorName){
            tv_videoName.setText(videoName);
            tv_creatorName.setText(creatorName);
            thumbnail.setImageResource(R.drawable.video_example);
            profile.setImageResource(R.drawable.creator_example);
        }
    }
    public void clear(){
        videos.clear();
    }
}

