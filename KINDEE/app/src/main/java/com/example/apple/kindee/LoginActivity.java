package com.example.apple.kindee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Apple on 11/18/2017 AD.
 */

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }
    public void callRegister(View v){
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
    }



}
