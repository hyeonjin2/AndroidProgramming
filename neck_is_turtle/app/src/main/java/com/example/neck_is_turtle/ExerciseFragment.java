package com.example.neck_is_turtle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment implements TimePicker.OnTimeChangedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<VideoData> videos = new ArrayList<VideoData>();
    private ListView listView;

    private Dialog mDialog,sDialog;
    private TimePicker timePicker;
    private Button day_mon,day_tue,day_wed,day_thu,day_fri,day_sat,day_sun,btn_set,btn_mclose;
    private Button btn_see,btn_sclose;
    private TextView mtv_video,mtv_creator;
    Calendar calendar;

    int shour,smin;

    private Button btn1,btn2,btn3,btn4;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_exercise.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseFragment newInstance(String param1, String param2) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        // 서버에서 비디오 정보 가져오기// 리스트뷰에 넣기
        listView = view.findViewById(R.id.listview_video2);
        // adapter 가져오기
        VideoAdapter adapter = new VideoAdapter();

        // item정보 리스트뷰에 올리기
        Button btn_alert = listView.findViewById(R.id.btn_alert);
        Button btn_like = listView.findViewById(R.id.btn_like);
        adapter.addItem(new VideoData(1,0,0,"Video Name1","Creator Name1"
                ,1));
        adapter.addItem(new VideoData(2,0,0,"Video Name2","Creator Name2"
                ,2));
        adapter.addItem(new VideoData(3,0,0,"Video Name3","Creator Name3"
                ,3));
        adapter.addItem(new VideoData(4,0,0,"Video Name4","Creator Name4"
                ,4));
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        btn1 = view.findViewById(R.id.btn_1);
        btn2 = view.findViewById(R.id.btn_2);
        btn3 = view.findViewById(R.id.btn_3);
        btn4 = view.findViewById(R.id.btn_4);

//        // onClick 사용설정
//        btn1.setOnClickListener(mOnClickListener);
//        btn2.setOnClickListener(mOnClickListener);
//        btn3.setOnClickListener(mOnClickListener);
//        btn4.setOnClickListener(mOnClickListener);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setSelected(!btn1.isSelected());
                if(btn1.isSelected()){
                    adapter.clear();
                    adapter.addItem(new VideoData(1,0,0,"Video Name1","Creator Name1"
                            ,1));
                    adapter.notifyDataSetChanged();
                    Log.d("Log","리스트1으로 갱신되었습니다!");
                }
                else{
                    adapter.clear();
                    adapter.addItem(new VideoData(1,0,0,"Video Name1","Creator Name1"
                            ,1));
                    adapter.addItem(new VideoData(2,0,0,"Video Name2","Creator Name2"
                            ,2));
                    adapter.addItem(new VideoData(3,0,0,"Video Name3","Creator Name3"
                            ,3));
                    adapter.addItem(new VideoData(4,0,0,"Video Name4","Creator Name4"
                            ,4));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setSelected(!btn2.isSelected());
                if(btn2.isSelected()){
                    adapter.clear();
                    adapter.addItem(new VideoData(2,0,0,"Video Name2","Creator Name2"
                            ,2));
                    adapter.notifyDataSetChanged();
                    Log.d("Log","리스트2으로 갱신되었습니다!");
                }
                else{
                    adapter.clear();
                    adapter.addItem(new VideoData(1,0,0,"Video Name1","Creator Name1"
                            ,1));
                    adapter.addItem(new VideoData(2,0,0,"Video Name2","Creator Name2"
                            ,2));
                    adapter.addItem(new VideoData(3,0,0,"Video Name3","Creator Name3"
                            ,3));
                    adapter.addItem(new VideoData(4,0,0,"Video Name4","Creator Name4"
                            ,4));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setSelected(!btn3.isSelected());
                if(btn3.isSelected()){
                    adapter.clear();
                    adapter.addItem(new VideoData(3,0,0,"Video Name3","Creator Name3"
                            ,3));
                    adapter.notifyDataSetChanged();
                    Log.d("Log","리스트3으로 갱신되었습니다!");
                }
                else{
                    adapter.clear();
                    adapter.addItem(new VideoData(1,0,0,"Video Name1","Creator Name1"
                            ,1));
                    adapter.addItem(new VideoData(2,0,0,"Video Name2","Creator Name2"
                            ,2));
                    adapter.addItem(new VideoData(3,0,0,"Video Name3","Creator Name3"
                            ,3));
                    adapter.addItem(new VideoData(4,0,0,"Video Name4","Creator Name4"
                            ,4));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4.setSelected(!btn4.isSelected());
                if(btn4.isSelected()){
                    adapter.clear();
                    adapter.addItem(new VideoData(4,0,0,"Video Name4","Creator Name4"
                            ,4));
                    adapter.notifyDataSetChanged();
                    Log.d("Log","리스트4으로 갱신되었습니다!");
                }
                else{
                    adapter.clear();
                    adapter.addItem(new VideoData(1,0,0,"Video Name1","Creator Name1"
                            ,1));
                    adapter.addItem(new VideoData(2,0,0,"Video Name2","Creator Name2"
                            ,2));
                    adapter.addItem(new VideoData(3,0,0,"Video Name3","Creator Name3"
                            ,3));
                    adapter.addItem(new VideoData(4,0,0,"Video Name4","Creator Name4"
                            ,4));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // 알람설정 버튼 클릭시
        adapter.setAlertClickListener(new StretchingFragment.AlertItemClickListener() {
            @Override
            public void onAlertItemClick(int position) {
                VideoData data = (VideoData) adapter.getItem(position);

                mDialog = new Dialog(getContext());
                mDialog.setContentView(R.layout.dialog_alert);
                mtv_video = mDialog.findViewById(R.id.tv_video);
                mtv_creator = mDialog.findViewById(R.id.tv_creator);

                // 대화상자 띄우기
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mtv_video = mDialog.findViewById(R.id.tv_video);
                mtv_creator = mDialog.findViewById(R.id.tv_creator);
                mtv_video.setText(data.getVideoName());
                mtv_creator.setText(data.getCreatorName());
                mDialog.show();

                sDialog = new Dialog(getContext());
                sDialog.setContentView(R.layout.dialog_alert_set_completion);

                timePicker = mDialog.findViewById(R.id.timePicker);

                day_mon = mDialog.findViewById(R.id.day_mon);
                day_tue = mDialog.findViewById(R.id.day_tue);
                day_wed = mDialog.findViewById(R.id.day_wed);
                day_thu = mDialog.findViewById(R.id.day_thu);
                day_fri = mDialog.findViewById(R.id.day_fri);
                day_sat = mDialog.findViewById(R.id.day_sat);
                day_sun = mDialog.findViewById(R.id.day_sun);

                btn_set = mDialog.findViewById(R.id.set);
                btn_mclose = mDialog.findViewById(R.id.btn_close);

                btn_see = sDialog.findViewById(R.id.see);
                btn_sclose = sDialog.findViewById(R.id.btn_close);

                // 대화상자 요일버튼 클릭리스너 지정
                day_mon.setOnClickListener(mOnClickListener);
                day_tue.setOnClickListener(mOnClickListener);
                day_wed.setOnClickListener(mOnClickListener);
                day_thu.setOnClickListener(mOnClickListener);
                day_fri.setOnClickListener(mOnClickListener);
                day_sat.setOnClickListener(mOnClickListener);
                day_sun.setOnClickListener(mOnClickListener);

                // 타임피커 초기값 현재시간으로 설정
                calendar = Calendar.getInstance();
                shour = calendar.get(calendar.HOUR_OF_DAY);
                smin = calendar.get(calendar.MINUTE);

                btn_mclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });

                btn_set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 대화상자 닫기
                        mDialog.dismiss();
                        // 알람이미지 selected 설정하기
                        adapter.btn_alert = view.findViewById(R.id.btn_alert);
                        adapter.btn_alert.setSelected(true);
                        // 설정완료 창 띄우기
                        sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        sDialog.show();
                    }
                });

                btn_sclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sDialog.dismiss();
                    }
                });

                btn_see.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),AlertActivity.class);
                        startActivity(intent);
                        sDialog.dismiss();
                    }
                });
            }
        });

        adapter.setLikeClickListener(new StretchingFragment.LikeItemClickListener() {
            @Override
            public void onLikeItemClick(int position) {
                VideoData data = (VideoData) adapter.getItem(position);
                adapter.btn_like = view.findViewById(R.id.btn_like);
                adapter.btn_like.setSelected(!adapter.btn_like.isSelected());
            }
        });

        return view;
    }
//    View.OnClickListener mOnClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                // 카테고리 버튼들 selected 설정
//                case R.id.btn_1:
//                    btn1.setSelected(!btn1.isSelected());
//
//                    break;
//                case R.id.btn_2:
//                    btn2.setSelected(!btn2.isSelected());
//                    break;
//                case R.id.btn_3:
//                    btn3.setSelected(!btn3.isSelected());
//                    break;
//                case R.id.btn_4:
//                    btn4.setSelected(!btn4.isSelected());
//                    break;
//            }
//        }
//    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 대화상자 요일 버튼들 selected 설정
                case R.id.day_mon:
                    day_mon.setSelected(!day_mon.isSelected());
                    break;
                case R.id.day_tue:
                    day_tue.setSelected(!day_tue.isSelected());
                    break;
                case R.id.day_wed:
                    day_wed.setSelected(!day_wed.isSelected());
                    break;
                case R.id.day_thu:
                    day_thu.setSelected(!day_thu.isSelected());
                    break;
                case R.id.day_fri:
                    day_fri.setSelected(!day_fri.isSelected());
                    break;
                case R.id.day_sat:
                    day_sat.setSelected(!day_sat.isSelected());
                    break;
                case R.id.day_sun:
                    day_sun.setSelected(!day_sun.isSelected());
                    break;
            }
        }
    };

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        shour = hourOfDay;
        smin = minute;
    }
}