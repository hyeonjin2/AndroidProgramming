package com.example.lab076_7dialogcustom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String mName = "???";
    private String mPassword = "???";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateResult();
    }

    private void updateResult() {
        TextView textResult = (TextView) findViewById(R.id.textResult);
        textResult.setText("이름: "+mName+"\n"+"암호: " +mPassword);
    }

    public void mOnClick(View v){
        // 1. XML로 대화상자 레이아웃 정의; dialog_login.xml 참조
        // 2. AlertDialog.Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 3. setView() 메서드로 레이아웃 지정
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_login,null);
        builder.setView(layout);
        // 4. 제목, 아이콘, 메시지, 버튼 추가 (선택사항)
        final EditText mEditName = (EditText) layout.findViewById(R.id.editName);
        final EditText mEditPassword = (EditText) layout.findViewById(R.id.editPassword);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mName = mEditName.getText().toString();
                        mPassword = mEditPassword.getText().toString();
                        updateResult();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel,null);
        // 5. 대화상자 객체 생성 & 화면 보이기
        builder.create().show();
    }
}