package com.example.apple.kindee.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apple.kindee.Controller.adapter.ListSearchAdapter;
import com.example.apple.kindee.Model.Resturant;
import com.example.apple.kindee.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Apple on 11/21/2017 AD.
 */

public class ListSearchActivity extends Activity {
    TextView tv_head;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String Res_name ;
    private int User_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsearch);
        Bundle bundle = getIntent().getExtras();
        tv_head = (TextView) findViewById(R.id.tv_header_listsearch);
        tv_head.setText("ค้นหา : "+bundle.getString("search"));
        Res_name = bundle.getString("search");
        User_id = bundle.getInt("User_id");
        String url="http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Resturant_controller/searchResturant";
        loginCallApi(url);


    }
    public void loginCallApi(String URL){
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
                            Resturant[] result = gson.fromJson(response.toString(),Resturant[].class);

                            //tv.setText(result.result+"");
                            mRecyclerView = (RecyclerView) findViewById(R.id.cv_listResturant_listsearch);

                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            mRecyclerView.setHasFixedSize(true);

                            // use a linear layout manager
                            mLayoutManager = new LinearLayoutManager(getBaseContext());
                            mRecyclerView.setLayoutManager(mLayoutManager);

                            // specify an adapter (see also next example)
                            //String[] myDataset= {"1","2","3"};
                            //Intent i=new Intent(getBaseContext(),DetailActivity.class);
                            mAdapter = new ListSearchAdapter(result,User_id);

                            mRecyclerView.setAdapter(mAdapter);




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
                params.put("Res_name", Res_name);


                return params;
            }
        };
        queue.add(postRequest);
    }

}
