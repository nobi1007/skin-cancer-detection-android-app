package com.example.skincancerdetection;

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

import static com.example.skincancerdetection.LoginActivity.JSON;

public class RegisterActivity extends AppCompatActivity {

    AutoCompleteTextView registerUserId, registerFirstName, registerLastName;
    EditText registerEmailId, registerPassword, registerMobile, registerAge;
    Button bt_register;

    public String registerPostUrl= "https://flask-skin-api.herokuapp.com/api/register";
    public String registerPostBody;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Registration");
        registerUserId = (AutoCompleteTextView)findViewById(R.id.registerUserId);
        registerFirstName = (AutoCompleteTextView)findViewById(R.id.registerFirstName);
        registerLastName = (AutoCompleteTextView)findViewById(R.id.registerLastName);
        registerEmailId = (EditText)findViewById(R.id.registerEmailId);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        registerMobile = (EditText)findViewById(R.id.registerMobile);
        registerAge = (EditText)findViewById(R.id.registerAge);
        bt_register = (Button)findViewById(R.id.buttonRegisterSubmit);

        registerButtonClickListener();
    }

    public void registerButtonClickListener(){
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = registerUserId.getText().toString();
                String password = registerPassword.getText().toString();
                String firstName = registerFirstName.getText().toString();
                String lastName = registerLastName.getText().toString();
                String email = registerEmailId.getText().toString();
                String mobile = registerMobile.getText().toString();
                String age = registerAge.getText().toString();


                JSONObject registerRequestObject = new JSONObject();
                try{
                    JSONObject temp = new JSONObject();
                    temp.put("user-id",userId);
                    temp.put("first-name",firstName);
                    temp.put("last-name",lastName);
                    temp.put("email-id",email);
                    temp.put("password",password);
                    temp.put("age",age);
                    temp.put("mobile-number",mobile);
                    registerRequestObject.put("user",temp);
                }
                catch(JSONException e){
                    Log.d("JSON Exception",e.toString());
                }

                registerPostBody = registerRequestObject.toString();

                try {
                    postRequest(registerPostUrl,registerPostBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void postRequest(String postUrl,String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG",response.body().string());
            }
        });
    }

}
