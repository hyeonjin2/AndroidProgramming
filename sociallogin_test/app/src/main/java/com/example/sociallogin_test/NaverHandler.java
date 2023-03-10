package com.example.sociallogin_test;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.kakao.sdk.auth.model.OAuthToken;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginData;
import com.nhn.android.naverlogin.ui.OAuthLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NaverHandler extends AsyncTask<Void, Void, String> {

    private final Context mContext;
    private final OAuthLogin mOAuthLoginModule;

    public NaverHandler(Context mContext, OAuthLogin mOAuthLoginModule) {
        this.mContext = mContext;
        this.mOAuthLoginModule = mOAuthLoginModule;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(Void... params) {
        String url = "https://openapi.naver.com/v1/nid/me";
        String at = mOAuthLoginModule.getAccessToken(mContext);
        return mOAuthLoginModule.requestApi(mContext, at, url);
    }

    protected void onPostExecute(String content) {
        try {
            JSONObject loginResult = new JSONObject(content);
            if (loginResult.getString("resultcode").equals("00")){
                JSONObject response = loginResult.getJSONObject("response");
                String id = response.getString("id");
                String email = response.getString("email");
                Toast.makeText(mContext, "id : "+id +" email : "+email, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
