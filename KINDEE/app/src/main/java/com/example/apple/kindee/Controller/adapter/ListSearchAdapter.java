package com.example.apple.kindee.Controller.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.kindee.Controller.DetailActivity;
import com.example.apple.kindee.Model.Resturant;
import com.example.apple.kindee.Model.User;
import com.example.apple.kindee.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Apple on 11/22/2017 AD.
 */

public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.ViewHolder> {
    private Resturant[] mDataset;
    private TextView tv;
    private int User_id;

    private View.OnClickListener onclick;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imv;
        public TextView Res_name;
        public TextView Type_name;
        public CardView cv;
        public ViewHolder(View v) {
            super(v);
            imv = (ImageView) v.findViewById(R.id.Res_img);
            Res_name = (TextView) v.findViewById(R.id.Res_name);
            Type_name = (TextView) v.findViewById(R.id.Type_name);
            cv = v.findViewById(R.id.card_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListSearchAdapter(Resturant[] myDataset,int user_id) {
        mDataset = myDataset;
        this.User_id= user_id;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cardview_listsearch, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //Bitmap bmp = BitmapFactory.decodeFile(new java.net.URL(url).openStream());
            new DownLoadImageTask(holder.imv).execute(mDataset[position].Res_img_path);

        holder.Res_name.setText("ชื่อร้าน "+mDataset[position].Res_name);
        holder.Type_name.setText("ประเภท "+mDataset[position].Type_name);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),mDataset[position].Res_name,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(),DetailActivity.class);
                i.putExtra("Res_id",mDataset[position].Res_id);
                i.putExtra("Res_name",mDataset[position].Res_name);
                i.putExtra("Res_detail",mDataset[position].Res_detail);
                i.putExtra("Res_latitude",mDataset[position].Res_latitude);
                i.putExtra("Res_longitude",mDataset[position].Res_longitude);
                i.putExtra("Type_id",mDataset[position].Type_id);
                i.putExtra("Type_name",mDataset[position].Type_name);
                i.putExtra("Res_img_path",mDataset[position].Res_img_path);
                i.putExtra("User_id", User_id);
                v.getContext().startActivity(i);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }


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


