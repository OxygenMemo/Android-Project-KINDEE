package com.example.apple.kindee.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.apple.kindee.R;

/**
 * Created by Apple on 11/19/2017 AD.
 */

public class HomeActivity extends Activity {
    private int User_id;
    private String User_fullname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        User_id = bundle.getInt("User_id");
        User_fullname = bundle.getString("User_fullname");

        //TextView tv = (TextView) findViewById(R.id.test);
        //tv.setText(User_id+" "+User_fullname);
    }
    public void callAddresturant(View view){
        Intent i = new Intent(this,AddresturantActivity.class);
        startActivity(i);
    }
    public void callSearchActivity(View view){
        Intent i = new Intent(this,SearchActivity.class);
        startActivity(i);
    }

}
