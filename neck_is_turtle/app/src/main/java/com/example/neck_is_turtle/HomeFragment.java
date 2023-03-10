package com.example.neck_is_turtle;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements TimePicker.OnTimeChangedListener{

    private TextView tv_today,tv_challengeTime,tv_time;
    private Calendar calendar;
    private Button btn_add,btn_show,btn_c_close,set_challenge_time,
            btn_challengeStart,btn_challengeStop;
    private LinearLayout lay_empty,lay_fill;
    private ImageButton btn_set_challenge;
    private ListView listView_alerts,listView_missions;
    private TimePicker set_time;
    private Dialog cDialog;
    private TimePicker timePicker;
    private SeekBar seekBar;
    private int c_hour = 1, c_min = 0, count = 60;
    private double challengeCount = 0;
    private double challengeDiscount = 0;
    private double challengeAvg = 0;

    //Roll and Pitch
    private double roll;    //x
    private double pitch;   //y
    private double yaw;     //z

    private SensorManager mSensorManager = null;
    private SensorEventListener gyroListener;
    private Sensor sensor = null;

    TimerTask timerTask;
    Timer timer = new Timer();

    private static final String TAG = "HomeFragment";

    String[] days = new String[]{"?????????","?????????","?????????","?????????","?????????","?????????","?????????"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tv_today = view.findViewById(R.id.tv_today);
        btn_add = view.findViewById(R.id.btn_add);
        lay_empty = view.findViewById(R.id.layout_empty);
        btn_set_challenge = view.findViewById(R.id.btn_set_challenge);
        tv_challengeTime = view.findViewById(R.id.tv_challengeTime);
        tv_time = view.findViewById(R.id.tv_time);
        btn_challengeStart = view.findViewById(R.id.btn_challengeStart);
        btn_challengeStop = view.findViewById(R.id.btn_challengeStop);
        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setMax(60);

        cDialog = new Dialog(getActivity());
        cDialog.setContentView(R.layout.dialog_challenge);

        timePicker = cDialog.findViewById(R.id.set_time);
        timePicker.setOnTimeChangedListener(this);

        btn_c_close = cDialog.findViewById(R.id.btn_c_close);
        set_challenge_time = cDialog.findViewById(R.id.set_challenge_time);

        // calendar?????? ????????? int??? ????????? ?????? ???????????? -> 1:????????? ~ 7:?????????
        calendar = Calendar.getInstance();
        tv_today.setText(days[calendar.get(calendar.DAY_OF_WEEK)-1]);

        // ???????????? ????????? ????????? ????????? ??????
        /*
         * ???????????? ???????????? ?????? ???????????? ?????? ????????? ????????????
         * */
        // ????????? ????????? ????????? ???????????? ?????? ?????? ??????
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("clicked",true);
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // ????????? ????????? ????????? ???????????? ?????? invisible?????? ??? ??????
        lay_empty.setVisibility(View.VISIBLE);
        // ????????????????????? ?????? ?????????
        listView_alerts = view.findViewById(R.id.listview_schedule);
        lay_fill = view.findViewById(R.id.lay_fill);
        lay_fill.setVisibility(View.GONE);
        btn_show = view.findViewById(R.id.btn_show);

        set_time = cDialog.findViewById(R.id.set_time);
        set_time.setHour(1);
        set_time.setMinute(0);
        set_time.setIs24HourView(true);

        AlertAdapter adapter = new AlertAdapter();

        // ???????????? ????????????????????? ?????????
        boolean[] list_days = new boolean[]{true,false,false,false,true,false,false};
        adapter.addItem(new AlertData(21,30,list_days));
        adapter.addItem(new AlertData(23,45,list_days));
        adapter.addItem(new AlertData(15,20,list_days));
        listView_alerts.setAdapter(adapter);

        btn_show.setVisibility(View.VISIBLE);
        // ?????????????????? ?????? ??????
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 217, view.getResources().getDisplayMetrics());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT,height);
        listView_alerts.setLayoutParams(params);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_show.setSelected(!btn_show.isSelected());
                if(!btn_show.isSelected()) {
                    adapter.setShowCount(adapter.getSize());
                    int numSize = 100*adapter.getSize() + 15*(adapter.getSize() - 1)+2;
                    final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, numSize, view.getResources().getDisplayMetrics());
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT, height);
                    listView_alerts.setLayoutParams(params);
                    btn_show.setText("?????? ??????");
                }
                else{
                    adapter.setShowCount(2);
                    final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 217, view.getResources().getDisplayMetrics());
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT,height);
                    listView_alerts.setLayoutParams(params);
                    btn_show.setText("?????? ?????????");
                }
            }
        });
        // ??????????????????
        listView_missions = view.findViewById(R.id.listview_mission);
        MissionAdapter adapter1 = new MissionAdapter();

        adapter1.addItem(new MissionData("???????????? ??????","??? ??????????????? 1?????? ?????? ?????????!"));
        adapter1.addItem(new MissionData("???????????? ?????? ?????????","?????? ???????????? ????????? ????????? ???????????????!"));
        listView_missions.setAdapter(adapter1);

        int numSize1 = 100*adapter1.getCount() + 15*(adapter1.getCount() - 1)+2;
        final int height1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, numSize1, view.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT,height1);
        listView_missions.setLayoutParams(params1);

        // challenge ?????? ?????? ?????? ?????????
        btn_set_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ??????????????? ?????????
                cDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cDialog.show();
            }
        });
        btn_c_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog.dismiss();
            }
        });
        set_challenge_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog.dismiss();
                // ???????????? ????????? ???????????? ????????????
                count = c_hour * 60 + c_min;
                tv_challengeTime.setText(String.valueOf(count));
                Log.e(TAG,String.valueOf(count));
            }
        });
        // ??????????????? ??????
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        // ?????????????????? ?????? ??????
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // ????????? ??????
        btn_challengeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerTask();
                gyroListener = new GyroscopeListener();
                mSensorManager.registerListener(gyroListener, sensor, 1000000000);
            }
        });
        // ????????? ??????
        btn_challengeStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimerTask();
                mSensorManager.unregisterListener(gyroListener);
            }
        });
        return view;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        c_hour = hourOfDay;
        c_min = minute;
    }

    public void startTimerTask(){
        stopTimerTask();
        seekBar.setMax(count);

        btn_challengeStart.setVisibility(View.GONE);
        btn_challengeStop.setVisibility(View.VISIBLE);

        timerTask = new TimerTask()
        {
            int num = 0;
            int temp = count - 1;

            @Override
            public void run()
            {
                count--;
                tv_time.post(new Runnable() {
                    @Override
                    public void run() {
                        num = temp - count;
                        seekBar.setProgress(num);
                        tv_time.setText(String.valueOf(num));
                        if(count==-1){
                            timerTask.cancel();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);

        if(challengeCount!=0&&challengeDiscount!=0) {
            challengeAvg = challengeCount/(challengeCount+challengeDiscount)*100;
            Toast.makeText(getActivity(), "????????? " + String.valueOf(challengeAvg) + "% ??????????????????!", Toast.LENGTH_SHORT).show();
        }
        roll = 0;
        pitch = 0;
        yaw = 0;
        challengeCount = 0;
        challengeDiscount = 0;
    }

    public void stopTimerTask() {
        if (timerTask != null) {
            tv_time.setText("0");
            timerTask.cancel();
            timerTask = null;
            seekBar.setProgress(0);

            if (challengeCount != 0 && challengeDiscount != 0) {
                double sum = challengeCount+challengeDiscount;
                challengeAvg = challengeCount/sum*100;
                Toast.makeText(getActivity(), "????????? " + String.format("%.2f",challengeAvg) + "% ??????????????????!", Toast.LENGTH_SHORT).show();
                challengeCount = 0;
                challengeDiscount = 0;
            }
            roll = 0;
            pitch = 0;
            yaw = 0;
            btn_challengeStop.setVisibility(View.GONE);
            btn_challengeStart.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(gyroListener);
        roll = 0;
        pitch = 0;
        yaw = 0;
        challengeCount = 0;
        challengeDiscount = 0;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(gyroListener);
    }
    public class GyroscopeListener implements SensorEventListener {
        //??????????????? ????????? ?????? ??????
        private double timestamp;
        private double dt;

        // ???????????? ????????? ?????? ??????
        private double RAD2DGR = 180 / Math.PI;
        private static final float NS2S = 1.0f/1000000000.0f;

        @Override
        public void onSensorChanged(SensorEvent event) {

            /* ??? ?????? ????????? ????????? ?????????. */
            double gyroX = event.values[0];
            double gyroY = event.values[1];
            double gyroZ = event.values[2];

            /* ???????????? ???????????? ???????????? ???????????? ?????? ?????? ??????(dt)??? ?????????.
             * dt : ????????? ?????? ????????? ???????????? ?????? ??????
             * NS2S : nano second -> second */
            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            /* ??? ?????? ????????? ????????? ?????? ?????? timestamp??? 0????????? dt?????? ???????????? ???????????? ????????????. */
            if (dt - timestamp*NS2S != 0) {

                /* ????????? ????????? ?????? -> ?????????(pitch, roll)?????? ??????.
                 * ??????????????? pitch, roll??? ????????? '?????????'??????.
                 * SO ?????? ?????? ?????????????????? ???????????? 'RAD2DGR'??? ???????????? degree??? ????????????.  */
                pitch = pitch + gyroY*dt;
                roll = roll + gyroX*dt;
                yaw = yaw + gyroZ*dt;

                boolean inChallenge = roll*RAD2DGR>=70&&roll*RAD2DGR<=90;

                Log.d("LOG","[X] : "+ String.format("%.4f", gyroX)+"[roll] : "+String.format("%.1f",roll*RAD2DGR));
                Log.d("LOG","[Y] : "+ String.format("%.4f", gyroY)+"[pitch] : "+String.format("%.1f",pitch*RAD2DGR));
                Log.d("LOG","[Z] : "+ String.format("%.4f", gyroZ)+"[yaw] : "+String.format("%.1f",yaw*RAD2DGR));

                if(inChallenge){
                    challengeCount++;
                }
                else{
                    challengeDiscount++;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}