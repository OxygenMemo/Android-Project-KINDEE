package com.example.apple.kindee.Controller;

import android.app.Activity;
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
import com.example.apple.kindee.Model.Result;
import com.example.apple.kindee.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Apple on 11/18/2017 AD.
 */

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }
    public void register(View v) {

        EditText edt_username = findViewById(R.id.edt_Username_register);
        EditText edt_password = findViewById(R.id.edt_password_register);
        EditText edt_repassword = findViewById(R.id.edt_repassword_register);
        EditText edt_fname = findViewById(R.id.edt_fname_register);
        EditText edt_lname = findViewById(R.id.edt_lname_register);

        if(checkRequiredInfo(edt_username,edt_password,edt_repassword,edt_fname,edt_lname)) {
            String url = "http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Register_controller/Register";

            registerCallApi(url,edt_username.getText().toString(),edt_password.getText().toString(),edt_fname.getText().toString(),edt_lname.getText().toString());
            //getJsonFromUrl(url);

            //Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            //startActivity(i);
        }
    }

    private void getJsonFromUrl(String url){
        final TextView tv= (TextView) findViewById(R.id.tv_Register_register);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        tv.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText(error.toString());
            }
        });
        queue.add(stringRequest);
    }


    private void registerCallApi(String URL, final String username, final String password, final String fname, final String lname) {
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
                            Result result = gson.fromJson(response.toString(),Result.class);

                            //tv.setText(result.result+"");

                                if (result.result) {
                                    Toast.makeText(getBaseContext(), "Register complete", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getBaseContext(), "Register fail username repect", Toast.LENGTH_SHORT).show();
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
                params.put("fname",fname);
                params.put("lname",lname);

                return params;
            }
        };
        queue.add(postRequest);

    }
    


    private boolean checkRequiredInfo(EditText edt_username,EditText edt_password,EditText edt_repassword,EditText edt_fname,EditText edt_lname){
        boolean check=true;
        if( edt_username.getText().toString().length() == 0 ) {
            edt_username.setError("required!");
            check=false;
        }
        if( edt_password.getText().toString().length() == 0 ) {
            edt_password.setError("required!");
            check=false;
        }
        if( edt_repassword.getText().toString().length() == 0 ) {
            edt_repassword.setError("required!");
            check=false;
        }
        if( edt_fname.getText().toString().length() == 0 ) {
            edt_fname.setError("required!");
            check=false;
        }
        if( edt_lname.getText().toString().length() == 0 ) {
            edt_lname.setError("required!");
            check=false;
        }
        return check;
    }

}
