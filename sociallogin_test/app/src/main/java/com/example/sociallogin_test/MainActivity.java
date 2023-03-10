package com.example.sociallogin_test;

import static android.os.AsyncTask.*;
import static android.os.AsyncTask.execute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Hash key : ";
    //kakao
    private View loginButton, logoutButton;
    private TextView nickname;
    private ImageView profileImage;

    //naver
    private LinearLayout ll_naver;

    //facebook
    private LoginButton login_facebook;
    private LoginCallBack_FaceBook mLoginCallback;
    private CallbackManager mCallbackManager;

    //google
    GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 123;
    private static final String TAG_g = "MainActivity";
    SignInButton login_google;

    OAuthLogin mOAuthLoginModule;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = getKeyHash(this);

        mContext = getApplicationContext();

        loginButton = findViewById(R.id.login_kakao);
        logoutButton = findViewById(R.id.logout);
        nickname = findViewById(R.id.nickname);
        profileImage = findViewById(R.id.profile);
        ll_naver = findViewById(R.id.ll_naver_login);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new LoginCallBack_FaceBook();

        login_facebook = (LoginButton) findViewById(R.id.login_facebook);
        login_facebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        login_facebook.registerCallback(mCallbackManager, mLoginCallback);

        login_google = findViewById(R.id.sign_in_button);
        login_google.setOnClickListener(this::mOnClick);

        //google 로그인
        // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
        // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // email addresses도 요청함
                .build();

        // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

        // 기존에 로그인 했던 계정을 확인한다.
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

        // 로그인 되있는 경우 (토큰으로 로그인 처리)
        if (gsa != null && gsa.getId() != null) {

        }

        //카카오 로그인 공통 callback 구성
        Function2<OAuthToken,Throwable, Unit> callback = new Function2<OAuthToken,Throwable, Unit>(){
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable error){
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error);
                } else if (oAuthToken != null) {
                    Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                    updateKakaoLoginUi();
                }
                return null;
            }
        };

        //카카오 로그인 버튼 눌렀을 때의 event handler
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카카오톡이 깔려있는지 확인
                //깔려있다면 카카오톡으로 로그인
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,callback);
                } else{
                    //깔려있지 않다면 카카오 계정으로 로그인
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
                }
            }
        });
        //네이버로그인 버튼 눌렀을 때의 event handler
        ll_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.init(
                        mContext
                        ,getString(R.string.naver_client_id)
                        ,getString(R.string.naver_client_secret)
                        ,getString(R.string.naver_client_name)
                        //,OAUTH_CALLBACK_INTENT
                        // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
                );

                @SuppressLint("HandlerLeak")
                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
                        if (success) {
                            String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                            String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                            long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                            String tokenType = mOAuthLoginModule.getTokenType(mContext);

                            Log.i("LoginData","accessToken : "+ accessToken);
                            Log.i("LoginData","refreshToken : "+ refreshToken);
                            Log.i("LoginData","expiresAt : "+ expiresAt);
                            Log.i("LoginData","tokenType : "+ tokenType);

                        } else {
                            String errorCode = mOAuthLoginModule
                                    .getLastErrorCode(mContext).getCode();
                            String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                            Toast.makeText(mContext, "errorCode:" + errorCode
                                    + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                        }
                    };
                };

                mOAuthLoginModule.startOauthLoginActivity(MainActivity.this, mOAuthLoginHandler);
            }
        });

        //로그아웃 버튼 눌렀을 때의 event handler
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //카카오 로그아웃
                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                    } else {
                        Log.e(TAG, "로그아웃 성공, SDK에서 토큰 삭제됨");
                    }
                    updateKakaoLoginUi();
                    return null;
                });
                //네이버 로그아웃
                mOAuthLoginModule.logout(mContext);
                new DeleteTokenTask(mContext, mOAuthLoginModule).execute();
                //메세지 출력
                Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        updateKakaoLoginUi();
    }
    /*//페이스북 로그인 버튼 눌렀을 때 event handler
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
*/
    //네이버로그인 연동해제
    public static class DeleteTokenTask extends AsyncTask<Void, Void, Boolean> {
        private final Context mContext;
        private final OAuthLogin mOAuthLoginModule;
        public DeleteTokenTask(Context mContext, OAuthLogin mOAuthLoginModule) {
            this.mContext = mContext;
            this.mOAuthLoginModule = mOAuthLoginModule;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isSuccessDeleteToken = mOAuthLoginModule.logoutAndDeleteToken(mContext);

            if (!isSuccessDeleteToken) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d(TAG, "errorCode:" + mOAuthLoginModule.getLastErrorCode(mContext));
                Log.d(TAG, "errorDesc:" + mOAuthLoginModule.getLastErrorDesc(mContext));
            }

            return isSuccessDeleteToken;
        }

        protected void onPostExecute(boolean isSuccessDeleteToken) {
        }
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.logout:
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, task -> {
                            Log.d(TAG, "onClick:logout success ");
                            mGoogleSignInClient.revokeAccess()
                                    .addOnCompleteListener(this, task1 -> Log.d(TAG, "onClick:revokeAccess success "));

                        });
                break;

        }
    }

    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                //카카오 로그인이 되어있는 경우
                if (user != null) {

                    Log.i(TAG, "invoke: id=" + user.getId());
                    Log.i(TAG, "invoke: email=" + user.getKakaoAccount().getEmail());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());

                    nickname.setText(user.getKakaoAccount().getProfile().getNickname());
                    //프로필 사진 url로 가져오기 user.getKakaoAccount().getProfile().getThumbnailImageUrl()
                    //Glide 오픈소스를 통해 url 이미지 가져오기 circleCrop()->동그랗게 잘라줌
                    Glide.with(profileImage).load(user.getKakaoAccount().getProfile()
                            .getThumbnailImageUrl()).circleCrop().into(profileImage);

                    //loginButton.setVisibility(View.GONE);
                    //logoutButton.setVisibility(View.VISIBLE);
                } else {
                    nickname.setText(null);
                    profileImage.setImageBitmap(null);
                    //loginButton.setVisibility(View.VISIBLE);
                    //logoutButton.setVisibility(View.GONE);
                }
                return null;
            }
        });
        //mLoginCallback.requestMe(AccessToken.getCurrentAccessToken());
    }

        class NaverHandler extends AsyncTask<Void, Void, String> {

            private final Context mContext;
            private final OAuthLogin mOAuthLoginModule;

            public NaverHandler(Context mContext, OAuthLogin mOAuthLoginModule) {
                this.mContext = mContext;
                this.mOAuthLoginModule = mOAuthLoginModule;
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected String doInBackground(Void... params) {
                String url = "https://openapi.naver.com/v1/nid/me";
                String at = mOAuthLoginModule.getAccessToken(mContext);
                return mOAuthLoginModule.requestApi(mContext, at, url);
            }

            protected void onPostExecute(String content) {
                try {
                    JSONObject loginResult = new JSONObject(content);
                    if (loginResult.getString("resultcode").equals("00")) {
                        JSONObject response = loginResult.getJSONObject("response");
                        String id = response.getString("id");
                        String email = response.getString("email");
                        Toast.makeText(mContext, "id : " + id + " email : " + email, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Log.d(TAG, "handleSignInResult:personName "+personName);
                Log.d(TAG, "handleSignInResult:personGivenName "+personGivenName);
                Log.d(TAG, "handleSignInResult:personEmail "+personEmail);
                Log.d(TAG, "handleSignInResult:personId "+personId);
                Log.d(TAG, "handleSignInResult:personFamilyName "+personFamilyName);
                Log.d(TAG, "handleSignInResult:personPhoto "+personPhoto);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    //앱의 HashKey 얻기
    public
    static
    final
    String getKeyHash(Context context) {
        try {
            PackageInfo info
                    = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md
                        = MessageDigest.getInstance(
                        "SHA");
                md.update(signature.toByteArray());
                String keyHash
                        = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d(TAG
                        +
                        "KeyHash:%s", keyHash);
                return keyHash;
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG
                    +
                    "getKeyHash Error:%s", e.getMessage());
        }
        catch (NoSuchAlgorithmException e) {
            Log.d(TAG
                    +
                    "getKeyHash Error:%s", e.getMessage());
        }
        return
                "";
    }
}