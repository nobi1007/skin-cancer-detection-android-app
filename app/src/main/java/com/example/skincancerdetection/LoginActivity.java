package com.example.skincancerdetection;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginActivity extends AppCompatActivity {

    Button bt_login;
    AutoCompleteTextView loginUserId;
    EditText loginPassword;

    public String loginPostUrl= "https://flask-skin-api.herokuapp.com/api/login";
    public String loginResponseBody;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public String loginPostBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");
        bt_login = (Button)findViewById(R.id.buttonLoginSubmit);
        loginUserId = (AutoCompleteTextView)findViewById(R.id.loginUserId);
        loginPassword = (EditText)findViewById(R.id.loginPassword);

        loginButtonClickListener();
    }

    public void loginButtonClickListener(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userid = loginUserId.getText().toString();
                String password = loginPassword.getText().toString();


                JSONObject loginRequestObject = new JSONObject();
                try{
                    loginRequestObject.put("user-id",userid);
                    loginRequestObject.put("password",password);
                }
                catch(JSONException e){
                    Log.d("JSON Exception",e.toString());
                }

                loginPostBody = loginRequestObject.toString();

                try {
                    postRequest(loginPostUrl,loginPostBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postRequest(String postUrl,String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(postBody,JSON);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

//        String

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("TAG",response.body().string());
                loginResponseBody = response.body().string();
                Log.d("TAG",loginResponseBody);
                storeTokenId();
            }
        });
    }

    public void storeTokenId(){
        try {
            JSONObject responseObject = new JSONObject(loginResponseBody);
            String status = responseObject.get("status").toString();
            String message = responseObject.get("message").toString();
            String tokenId = responseObject.get("tokenId").toString();
            Log.d("response","Status = "+status+" Message = "+message+" Token ID = "+tokenId);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("tokenId",tokenId);
            editor.commit();
            Log.d("hello","Saved Successfully");

        }
        catch(JSONException e){
            Log.d("JSON Exception",e.toString());
        }
    }
}
