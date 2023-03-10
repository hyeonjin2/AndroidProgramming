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

    String[] days = new String[]{"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};

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

        // calendar에서 가져온 int값 요일로 바꿔 설정하기 -> 1:일요일 ~ 7:토요일
        calendar = Calendar.getInstance();
        tv_today.setText(days[calendar.get(calendar.DAY_OF_WEEK)-1]);

        // 사용자가 설정한 알람이 있는지 확인
        /*
         * 서버에서 사용자에 대해 알람설정 정보 가져와 확인하기
         * */
        // 설정한 알람이 없다면 추가하기 버튼 기능 구현
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

        // 설정한 알람이 있다면 추가하기 박스 invisible설정 및 알람
        lay_empty.setVisibility(View.VISIBLE);
        // 알람리스트뷰에 알람 올리기
        listView_alerts = view.findViewById(R.id.listview_schedule);
        lay_fill = view.findViewById(R.id.lay_fill);
        lay_fill.setVisibility(View.GONE);
        btn_show = view.findViewById(R.id.btn_show);

        set_time = cDialog.findViewById(R.id.set_time);
        set_time.setHour(1);
        set_time.setMinute(0);
        set_time.setIs24HourView(true);

        AlertAdapter adapter = new AlertAdapter();

        // 알람정보 알람리스트뷰에 올리기
        boolean[] list_days = new boolean[]{true,false,false,false,true,false,false};
        adapter.addItem(new AlertData(21,30,list_days));
        adapter.addItem(new AlertData(23,45,list_days));
        adapter.addItem(new AlertData(15,20,list_days));
        listView_alerts.setAdapter(adapter);

        btn_show.setVisibility(View.VISIBLE);
        // 알람리스트뷰 크기 설정
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
                    btn_show.setText("알람 접기");
                }
                else{
                    adapter.setShowCount(2);
                    final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 217, view.getResources().getDisplayMetrics());
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT,height);
                    listView_alerts.setLayoutParams(params);
                    btn_show.setText("알람 더보기");
                }
            }
        });
        // 미션리스트뷰
        listView_missions = view.findViewById(R.id.listview_mission);
        MissionAdapter adapter1 = new MissionAdapter();

        adapter1.addItem(new MissionData("스트레칭 하기","목 스트레칭을 1분간 함께 해봐요!"));
        adapter1.addItem(new MissionData("측면사진 기록 남기기","아직 측면사진 기록을 남기지 않으셨네요!"));
        listView_missions.setAdapter(adapter1);

        int numSize1 = 100*adapter1.getCount() + 15*(adapter1.getCount() - 1)+2;
        final int height1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, numSize1, view.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT,height1);
        listView_missions.setLayoutParams(params1);

        // challenge 설정 버튼 클릭 이벤트
        btn_set_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 띄우기
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
                // 텍스트뷰 설정된 시간으로 변경하기
                count = c_hour * 60 + c_min;
                tv_challengeTime.setText(String.valueOf(count));
                Log.e(TAG,String.valueOf(count));
            }
        });
        // 센서매니저 생성
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        // 자이로스코프 센서 등록
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // 챌린지 시작
        btn_challengeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerTask();
                gyroListener = new GyroscopeListener();
                mSensorManager.registerListener(gyroListener, sensor, 1000000000);
            }
        });
        // 챌린지 스탑
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
            Toast.makeText(getActivity(), "챌린지 " + String.valueOf(challengeAvg) + "% 달성했습니다!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "챌린지 " + String.format("%.2f",challengeAvg) + "% 달성했습니다!", Toast.LENGTH_SHORT).show();
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
        //단위시간을 구하기 위한 변수
        private double timestamp;
        private double dt;

        // 회전각을 구하기 위한 변수
        private double RAD2DGR = 180 / Math.PI;
        private static final float NS2S = 1.0f/1000000000.0f;

        @Override
        public void onSensorChanged(SensorEvent event) {

            /* 각 축의 각속도 성분을 받는다. */
            double gyroX = event.values[0];
            double gyroY = event.values[1];
            double gyroZ = event.values[2];

            /* 각속도를 적분하여 회전각을 추출하기 위해 적분 간격(dt)을 구한다.
             * dt : 센서가 현재 상태를 감지하는 시간 간격
             * NS2S : nano second -> second */
            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            /* 맨 센서 인식을 활성화 하여 처음 timestamp가 0일때는 dt값이 올바르지 않으므로 넘어간다. */
            if (dt - timestamp*NS2S != 0) {

                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
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