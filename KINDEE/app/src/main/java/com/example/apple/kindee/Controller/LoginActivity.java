package com.example.apple.kindee.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apple.kindee.Model.ResultLogin;
import com.example.apple.kindee.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

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

    public void login(View view){
        EditText username = (EditText) findViewById(R.id.edt_Username_login);
        EditText password = (EditText) findViewById(R.id.edt_Password_login);
        String url ="http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Login_controller/Login";
        if(checkRequiredInfo(username,password)){
            loginCallApi(url,username.getText().toString(),password.getText().toString());
        }

    }
    public void loginCallApi(String URL, final String username, final String password){
        RequestQueue queue = Volley.newRequestQueue(this);
        final TextView tv= (TextView) findViewById(R.id.tv_Register_register);

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.setPrettyPrinting();

                            Gson gson = gsonBuilder.create();
                            ResultLogin result = gson.fromJson(response.toString(),ResultLogin.class);

                            //tv.setText(result.result+"");

                            if (result.status) {
                                callHome(result.user.User_id,result.user.User_fullname);

                            } else {
                                Toast.makeText(getBaseContext(), "Login fail", Toast.LENGTH_SHORT).show();
                            }


                        }catch (Exception e){
                            Toast.makeText(getBaseContext(),"fail other case",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getBaseContext(),"internet not connect",Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);
    }
    private boolean checkRequiredInfo(EditText edt_username,EditText edt_password){
        boolean check=true;
        if( edt_username.getText().toString().length() == 0 ) {
            edt_username.setError("required!");
            check=false;
        }
        if( edt_password.getText().toString().length() == 0 ) {
            edt_password.setError("required!");
            check=false;
        }

        return check;
    }
    private void callHome(int User_id,String User_fullname){
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra("User_id",User_id);
        i.putExtra("User_fullname",User_fullname);
        startActivity(i);
    }



}
