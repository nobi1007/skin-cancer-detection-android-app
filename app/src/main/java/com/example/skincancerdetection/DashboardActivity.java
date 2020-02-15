package com.example.skincancerdetection;

//import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
//import android.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.skincancerdetection.MainActivity.JSON;

public class DashboardActivity extends AppCompatActivity {
    TextView count;
    public String logoutPostUrl = "https://flask-skin-api.herokuapp.com/api/logout";
    public String logoutResponseBody;
    public String logoutPostBody, uid, token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Dashboard");
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.about:
            Toast.makeText(this, "Page is under Construction.", Toast.LENGTH_LONG).show();
            return(true);
        case R.id.exit:
            JSONObject logoutObject = new JSONObject();
            SharedPreferences forLogout = getApplicationContext().getSharedPreferences("LoginCredentials", 0);
            uid = forLogout.getString("userId",null);
            token = forLogout.getString("tokenId",null);
            try{
                logoutObject.put("user-id",uid);
                logoutObject.put("tokenId",token);
                logoutPostBody = logoutObject.toString();
                logoutPostRequest();
            }
            catch(JSONException e){
                Log.d("JSON Exception",e.toString());
            }

            finish();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }


    public void logoutPostRequest(){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(logoutPostBody, JSON);

        Request request = new Request.Builder()
                .url(logoutPostUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logoutResponseBody = response.body().string();

                SharedPreferences afterLogout = getApplicationContext().getSharedPreferences("LoginCredentials", 0);
                SharedPreferences.Editor editor = afterLogout.edit();
                editor.putString("tokenId","-");
                editor.putString("userId","-");
                editor.putString("pass","-");
                editor.commit();
                finish();
            }
        });
    }
}
