package com.mksoft.obj.Component.Activity.FeeedActivity.fragment.AllViewFeedPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.Data.FeedData;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView;
        ImageView feedImageView;
        ImageView regestUserImg;
        TextView feedContentsTextView;
        TextView feedLike;
        MyViewHolder(View view){
            super(view);
            userNameTextView = view.findViewById(R.id.userNameTextView);
            feedImageView = view.findViewById(R.id.feedImageView);
            feedContentsTextView = view.findViewById(R.id.feedContentsTextView);
            feedLike = view.findViewById(R.id.feedLikeTextView);
            regestUserImg = view.findViewById(R.id.regestUserImg);

        }
    }
    List<FeedData> items =  Collections.emptyList();
    Context context;

    MyViewHolder myViewHolder;

    public FeedAdapter(Context context){
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_page_all_view_feed_page_item, parent, false);

        return new MyViewHolder(v);
    }

    //Bundle bundle;
    //FeedItemViewFragment FeedItemViewFragment;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        myViewHolder = (MyViewHolder) holder;
        myViewHolder.userNameTextView.setText(items.get(position).getName());
        Glide.with(context).load(items.get(position).getFeedImgUrl()).into(myViewHolder.feedImageView);
        myViewHolder.feedContentsTextView.setText(items.get(position).getFeedContents());
        myViewHolder.feedLike.setText("좋아요 "+items.get(position).getFeedLike()+"개");
        if(items.get(position).getUserImgUrl() == null||items.get(position).getUserImgUrl().length() == 0){
            Glide.with(context).load(R.drawable.userbaseimg).apply(RequestOptions.circleCropTransform()).into(myViewHolder.regestUserImg);
        }else{

            Glide.with(context).load(items.get(position).getUserImgUrl()).apply(RequestOptions.circleCropTransform()).into(myViewHolder.regestUserImg);
        }




        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }//밀어서 삭제 (스와이브 삭제 용)


    public void refreshItem(List<FeedData> items){
        this.items = items;
        notifyDataSetChanged();
    }
    public FeedData getItem(int idx){
        return items.get(idx);
    }
    public List<FeedData> getAllItem(){return items;}
}
