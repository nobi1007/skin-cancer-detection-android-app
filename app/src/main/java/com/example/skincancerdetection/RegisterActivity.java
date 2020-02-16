package com.example.skincancerdetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    TextView incorrectUserId, incorrectPass, incorrectFname,incorrectLname,incorrectEmail, incorrectMob, incorrectAge;

    public String registerResponse;
    public String responseMessage, responseStatus;
    public String registerPostUrl= "https://flask-skin-api.herokuapp.com/api/register";
    public String registerPostBody;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        this.setTitle("Registration");
        this.getSupportActionBar().hide();

        incorrectUserId = findViewById(R.id.tv_incorrectUserId);
        incorrectFname = findViewById(R.id.tv_incorrectFname);
        incorrectLname = findViewById(R.id.tv_incorrectLname);
        incorrectEmail = findViewById(R.id.tv_incorrectEmail);
        incorrectPass = findViewById(R.id.tv_incorrectPass);
        incorrectMob = findViewById(R.id.tv_incorrectMobile);
        incorrectAge = findViewById(R.id.tv_incorrectAge);

        registerUserId = (AutoCompleteTextView)findViewById(R.id.registerUserId);
        registerFirstName = (AutoCompleteTextView)findViewById(R.id.registerFirstName);
        registerLastName = (AutoCompleteTextView)findViewById(R.id.registerLastName);
        registerEmailId = (EditText)findViewById(R.id.registerEmailId);
        registerPassword = (EditText)findViewById(R.id.registerPassword);
        registerMobile = (EditText)findViewById(R.id.registerMobile);
        registerAge = (EditText)findViewById(R.id.registerAge);
        bt_register = (Button)findViewById(R.id.buttonRegisterSubmit);

        textFilledListener();
        registerButtonClickListener();
    }

    public void textFilledListener(){

        registerUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        registerFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        registerLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        registerEmailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        registerMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        registerAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

    }

    public void enableSubmitIfReady(){
        boolean isIdFilled = registerUserId.getText().toString().length() > 0;
        boolean isPassFilled = registerPassword.getText().toString().length() > 0;
        boolean isFnameFilled = registerFirstName.getText().toString().length() > 0;
        boolean isLnameFilled = registerLastName.getText().toString().length() > 0;
        boolean isEmailFilled = registerEmailId.getText().toString().length() > 0;
        boolean isMobileFilled = registerMobile.getText().toString().length() == 10;
        boolean isAgeFilled = registerAge.getText().toString().length() > 1;

        if (!isIdFilled) {
            incorrectUserId.setText("This field can't be empty.");
            bt_register.setEnabled(false);
        } else {
            incorrectUserId.setText("");
        }


        if (!isPassFilled) {
            incorrectPass.setText("This field can't be empty.");
            bt_register.setEnabled(false);
        } else {
            incorrectPass.setText("");
        }

        if (!isFnameFilled) {
            incorrectFname.setText("This field can't be empty.");
            bt_register.setEnabled(false);
        } else {
            incorrectFname.setText("");
        }

        if (!isLnameFilled) {
            incorrectLname.setText("This field can't be empty.");
            bt_register.setEnabled(false);
        } else {
            incorrectLname.setText("");
        }

        if (!isEmailFilled) {
            incorrectEmail.setText("This field can't be empty.");
            bt_register.setEnabled(false);
        } else {
            incorrectEmail.setText("");
        }

        if (!isMobileFilled) {
            incorrectMob.setText("This field can't be empty.");
            bt_register.setEnabled(false);
        } else {
            incorrectMob.setText("");
        }

        if (!isAgeFilled) {
            incorrectAge.setText("This field can't be empty.");
            bt_register.setEnabled(false);
        } else {
            incorrectAge.setText("");
        }

        if (isIdFilled && isPassFilled && isFnameFilled && isLnameFilled && isEmailFilled && isMobileFilled && isAgeFilled) {
            bt_register.setEnabled(true);
        }
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
                registerResponse = response.body().string();
                try{
                    JSONObject responseObject = new JSONObject(registerResponse);
                    responseStatus = responseObject.get("status").toString();
                    responseMessage = responseObject.get("message").toString();
                }
                catch (JSONException e){
                    Log.d("JSONException",e.toString());
                }

                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseStatus.equals("Success")){
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                        else{
                            if(responseMessage.equals("Invalid Request - email already registered")){
                                incorrectEmail.setText("An account with this email id is already registered");
                            }
                            else if(responseMessage.equals("Invalid Request - User Id is already registered")){
                                incorrectUserId.setText("An account with this user id already registered");
                            }
                        }
                    }
                });
            }
        });
    }

}
