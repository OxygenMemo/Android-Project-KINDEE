package com.example.apple.kindee.Controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.kindee.Model.Comment;
import com.example.apple.kindee.R;

/**
 * Created by Apple on 11/25/2017 AD.
 */

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ViewHolder> {
    public Comment[] comments;
    public TextView tv ;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_item_comment;
        public ViewHolder(View v){
            super(v);
            tv_item_comment = v.findViewById(R.id.tv_item_comment);

        }
    }
    public ListCommentAdapter(Comment[] comments){
        this.comments = comments;

    }

    @Override
    public ListCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cardview_listcomment, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ListCommentAdapter.ViewHolder vh = new ListCommentAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ListCommentAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //Bitmap bmp = BitmapFactory.decodeFile(new java.net.URL(url).openStream());
        //new ListSearchAdapter.DownLoadImageTask(holder.imv).execute(mDataset[position].Res_img_path);

        holder.tv_item_comment.setText(comments[position].User_fullname+" : "+comments[position].Com_text);


    }

    @Override
    public int getItemCount() {
        return comments.length;
    }

}
