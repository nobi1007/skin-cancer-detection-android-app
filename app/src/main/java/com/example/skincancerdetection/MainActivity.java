package com.example.skincancerdetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btLogin, btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Home");
        btLogin = (Button)findViewById(R.id.buttonLogin);
        btRegister = (Button)findViewById(R.id.buttonRegister);
        buttonClickListener();
    }

    public void buttonClickListener(){
        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intentLogin = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentLogin);
            }
        });
    }

}
