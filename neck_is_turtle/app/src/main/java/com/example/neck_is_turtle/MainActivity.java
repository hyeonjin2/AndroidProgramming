package com.example.neck_is_turtle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    GraphFragment fragment1;
    HomeFragment fragment2;
    VideoFragment fragment3;
    private View v1,v2,v3;
    private TabLayout tabs;
    private Boolean clickChange = false;
    private Dialog hDialog;
    private Button btn_check;

    private final static String TAG_MAIN = "Main : ";

    ImageButton show_alerts,show_like,btn_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hDialog = new Dialog(MainActivity.this);
        hDialog.setContentView(R.layout.dialog_help);

        btn_check = hDialog.findViewById(R.id.btn_help_check);

        v1 = findViewById(R.id.v_graph);
        v2 = findViewById(R.id.v_home);
        v3 = findViewById(R.id.v_stretching);

        fragment1 = new GraphFragment();
        fragment2 = new HomeFragment();
        fragment3 = new VideoFragment();

        show_alerts = findViewById(R.id.show_alerts);
        show_like = findViewById(R.id.show_like);
        btn_help = findViewById(R.id.btn_help);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment2).commit();
        tabs = findViewById(R.id.tabs);

        // 두번째 tab인 홈화면이 먼저 보이도록 설정
        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();

        // tab에 이미지 넣기
        ImageView img1 = new ImageView(this);
        img1.setImageResource(R.drawable.bar_chart);
        img1.setPadding(5,0,0,0);
        img1.setPadding(50,50,50,50);

        ImageView img2 = new ImageView(this);
        img2.setImageResource(R.drawable.home);
        img2.setPadding(50,50,50,50);

        ImageView img3 = new ImageView(this);
        img3.setImageResource(R.drawable.stretching_exercises);
        img3.setPadding(0,0,5,0);
        img3.setPadding(50,50,50,50);

        tabs.getTabAt(0).setCustomView(img1);
        tabs.getTabAt(1).setCustomView(img2);
        tabs.getTabAt(2).setCustomView(img3);

        // tab 사용설정
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0){
                    selected = fragment1;
                    v1.setVisibility(View.VISIBLE);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                }
                else if(position == 1){
                    selected = fragment2;
                    v1.setVisibility(View.INVISIBLE);
                    v2.setVisibility(View.VISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                }
                else if(position == 2){
                    selected = fragment3;
                    v1.setVisibility(View.INVISIBLE);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.VISIBLE);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 알람보기 버튼 클릭 시
        show_alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_alerts = new Intent(MainActivity.this, AlertActivity.class);
                startActivity(intent_alerts);
                overridePendingTransition(0,0);
            }
        });
        // 좋아요보기 버튼 클릭 시
        show_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_like = new Intent(MainActivity.this, LikeActivity.class);
                startActivity(intent_like);
                overridePendingTransition(0,0);
            }
        });
        // 추가하러가기 버튼 클릭 리스너 인텐트 받기
        try {
            Bundle bundle = getIntent().getExtras();
            clickChange = bundle.getBoolean("clicked");
        }catch (Exception ex){
            clickChange = false;
            Log.e(TAG_MAIN,"ClickListener Error");
        }
        if(clickChange){
            tab = tabs.getTabAt(2);
            tab.select();
            overridePendingTransition(0,0);
        }
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                hDialog.show();
            }
        });
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hDialog.dismiss();
            }
        });
    }
}