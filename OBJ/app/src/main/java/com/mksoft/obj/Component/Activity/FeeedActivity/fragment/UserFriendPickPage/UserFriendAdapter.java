package com.mksoft.obj.Component.Activity.FeeedActivity.fragment.UserFriendPickPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksoft.obj.R;
import com.mksoft.obj.Repository.Data.FriendData;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendName;

        MyViewHolder(View view){
            super(view);
            friendName = view.findViewById(R.id.pickFriend);
        }
    }
    List<FriendData> items =  Collections.emptyList();
    Context context;
    private int lastSelectedPosition = -1;
    UserFriendAdapter.MyViewHolder myViewHolder;
    View preSelectedView = null;
    public UserFriendAdapter(Context context){
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_page_user_friend_pick_paget_item, parent, false);

        return new UserFriendAdapter.MyViewHolder(v);
    }

    //Bundle bundle;
    //FeedItemViewFragment FeedItemViewFragment;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        myViewHolder = (UserFriendAdapter.MyViewHolder) holder;
        myViewHolder.friendName.setText(items.get(position).getName());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preSelectedView != null){
                    preSelectedView.setBackgroundColor(0x00000000);
                }
                view.setBackgroundColor(0xFFA1A7FF);
                preSelectedView = view;
                lastSelectedPosition = position;
                notifyDataSetChanged();
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


    public void refreshItem(List<FriendData> items){
        this.items = items;
        notifyDataSetChanged();
    }
    public FriendData getItem(int idx){
        return items.get(idx);
    }
    public List<FriendData> getAllItem(){return items;}
    public int getLastSelectedPosition(){return lastSelectedPosition;}


}
