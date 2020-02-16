package com.example.skincancerdetection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button btLogin, btChecker;
    public String loginCheckPostUrl = "https://flask-skin-api.herokuapp.com/api/login";
    public String logoutCheckPostUrl = "https://flask-skin-api.herokuapp.com/api/logout";
    public String logoutCheckResponseBody;
    public String loginCheckResponseBody;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public String loginCheckPostBody;
    public String logoutCheckPostBody;
    public String token;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Home");
        btLogin = (Button) findViewById(R.id.buttonLogin);
        btChecker = (Button) findViewById(R.id.buttonChecker);
        buttonClickListener();
    }

    public void buttonClickListener() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//---------------

//        Thread welcomeThread = new Thread(){
//            @Override
//            public void run(){
//                try{
//                    super.run();
//                    sleep(3000);
//                }
//                catch (Exception e){
//                    Log.d("Sleeper Exception",e.toString());
//                }
//                finally{
//                    Intent i = new Intent(SplashActivity)
//                }
//
//            }
//        }

//---------------

                SharedPreferences loginCredentials = getApplicationContext().getSharedPreferences("LoginCredentials", 0);
                token = loginCredentials.getString("tokenId", null);
                uid = loginCredentials.getString("userId", null);
                String pass = loginCredentials.getString("pass", null);

                if (token == null) {
                    Log.d("Token null", "Going to Login Page");

                    //  Launching Login Activity
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intentLogin);

                } else {
                    JSONObject loginCheckerObject = new JSONObject();
                    try {
                        loginCheckerObject.put("user-id", uid);
                        loginCheckerObject.put("password", pass);
                        loginCheckPostBody = loginCheckerObject.toString();
                        inPostRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        Log.d("JSON Exception", e.toString());
                    }
                }
            }
        });

        btChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginCredentials", 0);
                Toast.makeText(getApplicationContext(), pref.getString("tokenId", null), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), pref.getString("userId", null), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), pref.getString("pass", null), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void inPostRequest() throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(loginCheckPostBody, JSON);

        Request request = new Request.Builder()
                .url(loginCheckPostUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                loginCheckResponseBody = response.body().string();
                Log.d("Inside Login Checker", loginCheckResponseBody);
                try {
                    Log.d("Not null", loginCheckResponseBody);
                    JSONObject loginCheckerResponseObject = new JSONObject(loginCheckResponseBody);
                    if (loginCheckerResponseObject.get("status").toString().equals("Success")) {
                        if (loginCheckerResponseObject.get("tokenId").toString().equals(token)) {
                            Log.d("Already Logged In", "moving to dashboard");
                            Intent intentLogin = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intentLogin);

                        }
                        else {
                            Log.d("Have to Log in", "moving to Login");
                            Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intentLogin);

                            JSONObject logoutCheckerObject = new JSONObject();
                            try {
                                logoutCheckerObject.put("user-id", uid);
                                logoutCheckerObject.put("tokenId", loginCheckerResponseObject.get("tokenId").toString());
                                logoutCheckPostBody = logoutCheckerObject.toString();
                                outPostRequest();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                Log.d("JSON Exception", e.toString());
                            }
                        }
                    }
                    else {
                        Log.d("Have to Log in", "moving to Login");
                        Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentLogin);

                        JSONObject logoutCheckerObject = new JSONObject();
                        try {
                            logoutCheckerObject.put("user-id", uid);
                            logoutCheckerObject.put("tokenId", loginCheckerResponseObject.get("tokenId").toString());
                            logoutCheckPostBody = logoutCheckerObject.toString();
                            outPostRequest();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            Log.d("JSON Exception", e.toString());
                        }
                    }

                } catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                }
            }
        });
    }

    public void outPostRequest() throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(logoutCheckPostBody,JSON);
        Request request = new Request.Builder()
                .url(logoutCheckPostUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logoutCheckResponseBody = response.body().string();
                Log.d("TAG",logoutCheckResponseBody);
                try{
                    JSONObject logoutCheckerResponseObject = new JSONObject(logoutCheckResponseBody);
                    if(logoutCheckerResponseObject.get("status").toString().equals("Success")){
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginCredentials", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("tokenId","-");
                        editor.putString("userId","-");
                        editor.putString("pass","-");
                        editor.commit();
                    }
                }
                catch(JSONException e){
                    Log.d("TAG","JSON Exception");
                }

            }
        });
    }
}