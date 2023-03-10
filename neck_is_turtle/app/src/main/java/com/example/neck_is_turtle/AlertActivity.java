package com.example.neck_is_turtle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AlertActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener{

    ImageButton btn_back;
    TextView tv_hour,tv_minute,tv_ampm,
            tv_mon,tv_tue,tv_wed,tv_thu,tv_fri,tv_sat,tv_sun;
    Button day_mon,day_tue,day_wed,day_thu,day_fri,day_sat,day_sun,btn_set,btn_aclose;
    Button btn_comp;
    TextView[] tv_days;
    int hour, minute;
    boolean[] selected_days = new boolean[7];
    ImageButton btn_menu;
    Dialog aDialog,cDialog;
    TimePicker timePicker;
    Calendar calendar;
    int shour,smin;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        btn_back = findViewById(R.id.btn_back);

        aDialog = new Dialog(AlertActivity.this);
        aDialog.setContentView(R.layout.dialog_alert);

        cDialog = new Dialog(AlertActivity.this);
        cDialog.setContentView(R.layout.dialog_change);

        timePicker = aDialog.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(this);

        day_mon = aDialog.findViewById(R.id.day_mon);
        day_tue = aDialog.findViewById(R.id.day_tue);
        day_wed = aDialog.findViewById(R.id.day_wed);
        day_thu = aDialog.findViewById(R.id.day_thu);
        day_fri = aDialog.findViewById(R.id.day_fri);
        day_sat = aDialog.findViewById(R.id.day_sat);
        day_sun = aDialog.findViewById(R.id.day_sun);

        btn_set = aDialog.findViewById(R.id.set);
        btn_aclose = aDialog.findViewById(R.id.btn_close);

        btn_comp = cDialog.findViewById(R.id.btn_comp);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lay_alerts);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        // 레이아웃 인플레이터 객체
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 메인에 새로 생성한 레이아웃 추가
        layoutInflater.inflate(R.layout.box_alert, linearLayout, true);

        // alert_box에 있는 item 가져오기
        tv_hour = linearLayout.findViewById(R.id.tv_hour);
        tv_minute = linearLayout.findViewById(R.id.tv_minute);
        tv_ampm = linearLayout.findViewById(R.id.tv_ampm);

        tv_mon = linearLayout.findViewById(R.id.tv_mon);
        tv_tue = linearLayout.findViewById(R.id.tv_tue);
        tv_wed = linearLayout.findViewById(R.id.tv_wed);
        tv_thu = linearLayout.findViewById(R.id.tv_thu);
        tv_fri = linearLayout.findViewById(R.id.tv_fri);
        tv_sat = linearLayout.findViewById(R.id.tv_sat);
        tv_sun = linearLayout.findViewById(R.id.tv_sun);

        aSwitch = linearLayout.findViewById(R.id.switch_alert);
        aSwitch.setOnCheckedChangeListener(new mSwitchListener());

        // 타임피커 초기값 현재시간으로 설정하기
        calendar = Calendar.getInstance();
        shour = calendar.get(calendar.HOUR_OF_DAY);
        smin = calendar.get(calendar.MINUTE);

        btn_menu = linearLayout.findViewById(R.id.btn_menu);

        tv_days = new TextView[]{tv_mon, tv_tue, tv_wed, tv_thu, tv_fri, tv_sat, tv_sun};

        // 임의로 알람 설정하기
        AlertData a1 = new AlertData(13, 45, null);
        selected_days = new boolean[]{false, false, true, true, true, false, false};

        // 알람내용 item에 적용하기
        tv_hour.setText(String.valueOf(a1.getHour()));
        tv_minute.setText(String.valueOf(a1.getMinute()));
        tv_ampm.setText(a1.getAmpm());

        for (int i = 0; i < 7; i++) {
            tv_days[i].setSelected(selected_days[i]);
        }
        // 팝업메뉴 띄우기
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.menu_alert,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.menu_edit){
                            //메뉴에서 수정하기 버튼 클릭
                            aDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            aDialog.show();
                        }else{  // 메뉴에서 삭제하기 버튼 클릭
                            ViewGroup parentViewGroup = (ViewGroup) linearLayout.getParent();
                            if (null != parentViewGroup) {
                                parentViewGroup.removeView(linearLayout);
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        // 대화상자 확인버튼 눌렀을 때
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택된 요일 days 리스트에 넣기
                Button[] buttons = {day_mon,day_tue,day_wed,day_thu,day_fri,day_sat,day_sun};
                String[] str_day = {"월","화","수","목","금","토","일"};
                boolean[] selected_day = new boolean[7];
                Arrays.fill(selected_day,false);
                ArrayList<String> days = new ArrayList<String>();

                for(int i=0; i<7;i++){
                    if(buttons[i].isSelected()){
                        days.add(str_day[i]);
                    }
                }
                // 선택된 요일 boolean list에 넣기
                for(int i=0; i<7;i++){
                    selected_day[i] = buttons[i].isSelected();
                }
                // 설정한 시간, 요일 Toast메세지 출력
                Toast.makeText(AlertActivity.this, shour+"시 : "+smin+"분 "+days+"마다 알림",
                        Toast.LENGTH_SHORT).show();

                AlertData a1 = new AlertData(shour, smin, null);

                // 알람내용 item에 적용하기
                tv_hour.setText(String.valueOf(a1.getHour()));
                tv_minute.setText(String.valueOf(a1.getMinute()));
                tv_ampm.setText(a1.getAmpm());

                for (int i = 0; i < 7; i++) {
                    tv_days[i].setSelected(selected_day[i]);
                }

                // 대화상자 닫기
                aDialog.dismiss();
                // 설정완료 창 띄우기
                cDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cDialog.show();
            }
        });
        btn_aclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aDialog.dismiss();
            }
        });
        btn_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog.dismiss();
            }
        });
    }
    // 타임피커에서 설정된 시간 가져오기
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        shour = hourOfDay;
        smin = minute;
    };
    // 대화상자 요일 버튼들 selected 설정
    public void dOnClick(View v){
        switch (v.getId()){
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
    class mSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
                Toast.makeText(AlertActivity.this, "알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(AlertActivity.this, "알람이 해제되었습니다!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}