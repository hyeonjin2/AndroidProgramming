package com.example.lap1113_2echoclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private ClientThread mClientThread;
    private EditText mEditIP, mEditData;
    private Button mBtnConnect, mBtnSend;
    private TextView mTextOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditIP = (EditText) findViewById(R.id.editIP);
        mEditData = (EditText) findViewById(R.id.editData);
        mBtnConnect = (Button) findViewById(R.id.btnConnect);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        mTextOutput = (TextView) findViewById(R.id.textOutput);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void mOnClick(View v){
        switch (v.getId()) {
            case R.id.btnConnect:
                if (mClientThread == null) {
                    String str = mEditIP.getText().toString();
                    if (str.length() != 0) {
                        mClientThread = new ClientThread(str, mMainHandler);
                        mClientThread.start();
                        mBtnConnect.setEnabled(false);
                        mBtnSend.setEnabled(true);
                    }
                }
                break;
            case R.id.btnQuit:
                finish();
                break;
            case R.id.btnSend:
                if(SendThread.mHandler != null){
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = mEditData.getText().toString();
                    SendThread.mHandler.sendMessage(msg);
                    mEditData.selectAll();
                }
                break;
        }
    }

    private Handler mMainHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    mTextOutput.append((String) msg.obj);
                    break;
            }
        }
    };
}

class ClientThread extends Thread{

    private String mServAddr;
    private Handler mMainHandler;

    public ClientThread(String ServAddr, Handler mainHandler){
        mServAddr = ServAddr;
        mMainHandler = mainHandler;
    }

    @Override
    public void run() {
        Socket sock = null;
        try{
            sock = new Socket(mServAddr, 9000);
            doPrintln(">> ????????? ?????? ??????!");
            SendThread sendThread = new SendThread(this, sock.getOutputStream());
            RecvThread recvThread = new RecvThread(this, sock.getInputStream());
            sendThread.start();
            recvThread.start();
            sendThread.join();
            recvThread.join();
        }catch (Exception e){
            doPrintln(e.getMessage());
        }finally{
            try{
                if(sock != null) {
                    sock.close();
                    doPrintln(">> ????????? ?????? ??????!");
                }
            }catch (IOException e){
                doPrintln(e.getMessage());
            }
        }
    }

    public void doPrintln(String str){
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = str + "\n";
        mMainHandler.sendMessage(msg);
    }
}

class SendThread extends Thread{

    private ClientThread mClientThread;
    private OutputStream mOutStream;
    public static Handler mHandler;

    public SendThread(ClientThread clientThread, OutputStream outputStream){
        mClientThread = clientThread;
        mOutStream = outputStream;
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        try{
                            String s = (String) msg.obj;
                            mOutStream.write(s.getBytes());
                            mClientThread.doPrintln("[?????? ?????????] "+s);
                        }catch(IOException e){
                            mClientThread.doPrintln(e.getMessage());
                        }
                        break;
                }
            }
        };
        Looper.loop();
    }
}

class RecvThread extends Thread{

    private ClientThread mClientThread;
    private InputStream mInStream;

    public RecvThread(ClientThread clientTread, InputStream inStream){
        mClientThread = clientTread;
        mInStream = inStream;
    }

    @Override
    public void run() {
        byte[] buf = new byte[1024];
        while(true){
            try{
                int nbytes = mInStream.read(buf);
                if(nbytes>0){
                    String s =  new String(buf, 0, nbytes);
                    mClientThread.doPrintln("[?????? ?????????] "+s);
                }
                else{
                    mClientThread.doPrintln(">> ????????? ?????? ??????!");
                    if(SendThread.mHandler != null){
                        Message msg = Message.obtain();
                        msg.what = 2;
                        SendThread.mHandler.sendMessage(msg);
                    }
                    break;
                }
            }catch (IOException e){
                mClientThread.doPrintln(e.getMessage());
            }
        }
    }
}