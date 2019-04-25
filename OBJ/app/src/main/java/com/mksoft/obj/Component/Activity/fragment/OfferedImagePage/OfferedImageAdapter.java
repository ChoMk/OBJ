package com.mksoft.obj.Component.Activity.fragment.OfferedImagePage;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Component.Activity.fragment.UserFriendPickPage.UserFriendPickPageFragment;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.Data.OfferedImageData;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class OfferedImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView imagenameTextView;
        ImageView offeredImageView;
        MyViewHolder(View view){
            super(view);
            imagenameTextView = view.findViewById(R.id.imageNameTextView);
            offeredImageView = view.findViewById(R.id.offeredImageView);
        }
    }
    List<OfferedImageData> items =  Collections.emptyList();
    Context context;

    OfferedImageAdapter.MyViewHolder myViewHolder;

    public OfferedImageAdapter(Context context){
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offered_image_page_item, parent, false);

        return new OfferedImageAdapter.MyViewHolder(v);
    }

    FragmentTransaction fragmentTransaction;
    UserFriendPickPageFragment userFriendPickPageFragment;
    Bundle bundle;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        myViewHolder = (OfferedImageAdapter.MyViewHolder) holder;
        myViewHolder.imagenameTextView.setText(items.get(position).getImageName());
        Glide.with(context).load(items.get(position).getGIFPath()).into(myViewHolder.offeredImageView);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putString("imageName", items.get(position).getImageName());

                userFriendPickPageFragment = new UserFriendPickPageFragment();
                userFriendPickPageFragment.setArguments(bundle);
                Toast toast = Toast.makeText(MainActivity.mainActivity.getApplicationContext(), items.get((position)).getImageName()+
                        "을 선택하셨습니다.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, Gravity.CENTER, 0);
                toast.show();
                fragmentTransaction = MainActivity.mainActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, userFriendPickPageFragment,null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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


    public void refreshItem(List<OfferedImageData> items){
        this.items = items;
        notifyDataSetChanged();
    }
    public OfferedImageData getItem(int idx){
        return items.get(idx);
    }
    public List<OfferedImageData> getAllItem(){return items;}
}
