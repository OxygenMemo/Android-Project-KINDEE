package com.example.apple.kindee.Controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.kindee.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Apple on 11/22/2017 AD.
 */

public class DetailActivity extends AppCompatActivity{
    private TextView tv_head_detail;
    private TextView tv_Type_name_detail;
    private ImageView img_img_detail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);
        Bundle bundle = getIntent().getExtras();

        int Res_id = bundle.getInt("Res_id");
        String Res_name = bundle.getString("Res_name");
        String Res_detail = bundle.getString("Res_detail");
        String Res_img_path = bundle.getString("Res_img_path");
        Double Res_latitude = bundle.getDouble("Res_latitude");
        Double Res_longitude = bundle.getDouble("Res_longitude");
        int Type_id = bundle.getInt("Type_id");
        String Type_name = bundle.getString("Type_name");

        tv_Type_name_detail = (TextView) findViewById(R.id.tv_Type_name_detail);
        tv_head_detail = (TextView) findViewById(R.id.tv_head_detail);
        img_img_detail = (ImageView) findViewById(R.id.img_img_detail);

        tv_head_detail.setText("ชื่อ : "+Res_name);
        tv_Type_name_detail.setText("ประเภท : "+Type_name);
        new DetailActivity.DownLoadImageTask(img_img_detail).execute(Res_img_path);


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
