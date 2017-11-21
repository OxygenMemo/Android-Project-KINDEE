package com.example.apple.kindee.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.apple.kindee.R;

/**
 * Created by Apple on 11/21/2017 AD.
 */

public class ListSearchActivity extends Activity {
    TextView tv_head;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsearch);
        Bundle bundle = getIntent().getExtras();
        tv_head = (TextView) findViewById(R.id.tv_header_listsearch);
        tv_head.setText("ค้นหา : "+bundle.getString("search"));


    }
}
