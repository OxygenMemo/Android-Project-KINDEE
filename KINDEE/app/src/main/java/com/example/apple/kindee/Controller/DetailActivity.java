package com.example.apple.kindee.Controller;

//import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apple.kindee.Controller.adapter.ListCommentAdapter;
import com.example.apple.kindee.Controller.adapter.ListSearchAdapter;
import com.example.apple.kindee.Model.Comment;
import com.example.apple.kindee.Model.ResRate;
import com.example.apple.kindee.Model.Result;
import com.example.apple.kindee.Model.ResultLogin;
import com.example.apple.kindee.Model.Type;
import com.example.apple.kindee.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Apple on 11/22/2017 AD.
 */

public class DetailActivity extends AppCompatActivity{
    private TextView tv_head_detail;
    private TextView tv_Type_name_detail;
    private ImageView img_img_detail;
    private Bundle bundle;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private ListCommentAdapter listCommentAdapter;
    private Handler handler = new Handler();
    private String url_getResRate = "http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Rate_controller/getResRate/";
    private String url_getcommand = "http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Comment_controller/getAllCommentWithResId/";
    private String url_addComment = "http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Comment_controller/addComment";
    private String comment_text;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);
        bundle = getIntent().getExtras();

        int Res_id = bundle.getInt("Res_id");
        String Res_name = bundle.getString("Res_name");
        String Res_detail = bundle.getString("Res_detail");
        String Res_img_path = bundle.getString("Res_img_path");
        Double Res_latitude = bundle.getDouble("Res_latitude");
        Double Res_longitude = bundle.getDouble("Res_longitude");
        int Type_id = bundle.getInt("Type_id");
        String Type_name = bundle.getString("Type_name");

        Toast.makeText(this,bundle.getInt("User_id")+"",Toast.LENGTH_SHORT).show();
        tv_Type_name_detail = (TextView) findViewById(R.id.tv_Type_name_detail);
        tv_head_detail = (TextView) findViewById(R.id.tv_head_detail);
        img_img_detail = (ImageView) findViewById(R.id.img_img_detail);

        tv_head_detail.setText("ชื่อ : "+Res_name);
        tv_Type_name_detail.setText("ประเภท : "+Type_name);
        new DetailActivity.DownLoadImageTask(img_img_detail).execute(Res_img_path);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_rate_detail);

        ArrayList<String> arr_Rate = new ArrayList<>();
        arr_Rate.add(" ");
        arr_Rate.add("1");
        arr_Rate.add("2");
        arr_Rate.add("3");
        arr_Rate.add("4");
        arr_Rate.add("5");
        List<String> list = arr_Rate;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        //spinner.setSelection(2);

        getResRate(url_getResRate+Res_id);
        getCommentWithResId(url_getcommand+Res_id);


    }

    public void callMapDetailSearch(View v){
        Intent i = new Intent(this,MapDetailActivity.class);
        i.putExtra("Res_id",bundle.getInt("Res_id"));
        i.putExtra("Res_id",bundle.getString("Res_name"));
        i.putExtra("Res_detail",bundle.getString("Res_detail"));
        i.putExtra("Res_img_path",bundle.getString("Res_img_path"));
        i.putExtra("Res_latitude",bundle.getDouble("Res_latitude"));
        i.putExtra("Res_longitude",bundle.getDouble("Res_longitude"));
        i.putExtra("Type_id",bundle.getInt("Type_id"));
        i.putExtra("Type_name",bundle.getString("Type_name"));

        startActivity(i);
    }
    public void addComment(View v){
        EditText editText =findViewById(R.id.edt_comment_detail);
        this.comment_text =editText.getText().toString();
        AddCommentCallApi(url_addComment);
    }
    public void AddCommentCallApi(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        final TextView tv= (TextView) findViewById(R.id.tv_Register_register);

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            getCommentWithResId(url_getcommand+bundle.getInt("Res_id"));
                            EditText commenttext = findViewById(R.id.edt_comment_detail);
                            commenttext.setText("");

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
                params.put("User_id", bundle.getInt("User_id")+"");
                params.put("Res_id", bundle.getInt("Res_id")+"");
                params.put("Com_text",comment_text);

                return params;
            }
        };
        queue.add(postRequest);
    }
    private void getCommentWithResId(final String url){
        //final TextView tv= (TextView) findViewById(R.id.tv_Register_register);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(response.toString());
                        try {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.setPrettyPrinting();

                            Gson gson = gsonBuilder.create();
                            Comment[] comments = gson.fromJson(response.toString(),Comment[].class);
                            //Log.d("gggg",response.toString());
                            //Log.d("gggg",bundle.getString("Res_id"));
                            //Log.d("gggg",url);
                            //tv.setText(result.result+"");
                            recyclerView = (RecyclerView) findViewById(R.id.cv_listcomment_detail);

                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(getBaseContext());
                            recyclerView.setLayoutManager(layoutManager);

                            listCommentAdapter = new ListCommentAdapter(comments);

                            recyclerView.setAdapter(listCommentAdapter);

                        }catch (Exception e){
                            Toast.makeText(getBaseContext(),"fail other case",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Internet not connect",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    private void getResRate(final String url){
        //final TextView tv= (TextView) findViewById(R.id.tv_Register_register);
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(response.toString());
                        try {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.setPrettyPrinting();

                            Gson gson = gsonBuilder.create();
                            ResRate resRate = gson.fromJson(response.toString(),ResRate.class);
                            //Log.d("gggg",response.toString());
                            //Log.d("gggg",bundle.getString("Res_id"));
                            //Log.d("gggg",url);
                            //tv.setText(result.result+"");
                            TextView tvResRate = findViewById(R.id.tv_Ras_rate_detail);
                            //คะแนน
                            tvResRate.setText("คะแนน "+resRate.Rate_number);
                        }catch (Exception e){
                            Toast.makeText(getBaseContext(),"fail other case",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Internet not connect",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.layout.menutest, menu);
        //return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    */
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                         /*
                             decodeStream(InputStream is)
                                 Decode an input stream into a bitmap.
                          */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }


    }


}
