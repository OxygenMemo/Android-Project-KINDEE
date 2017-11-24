package com.example.apple.kindee.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.apple.kindee.R;

/**
 * Created by Apple on 11/21/2017 AD.
 */

public class SearchActivity extends Activity {
    EditText edt_search;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edt_search =  (EditText) findViewById(R.id.edt_search_search);
    }
    public void callListSearchActivity(View v){
        Intent i = new Intent(this,ListSearchActivity.class);
        i.putExtra("search",edt_search.getText().toString());
        startActivity(i);
    }
}
