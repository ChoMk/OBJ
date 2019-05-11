package com.mksoft.obj.Component.Activity.FeeedActivity.fragment.RequestFriendPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

public class RequestFriendPageFragment extends Fragment implements FeedRootActivity.onKeyBackPressedListener{
    FeedRootActivity feedRootActivity;
    TextView IDtextView;
    Button requestFriendButton;

    @Inject
    APIRepo apiRepo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((FeedRootActivity) context).setOnKeyBackPressedListener(this);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.feed_page_friend_request_fragment, container, false);
        this.configureDagger();

        init(rootView);


        return rootView;
    }
    public void init(ViewGroup view){

        IDtextView = view.findViewById(R.id.friendRequestPageIdEditText);
        requestFriendButton = view.findViewById(R.id.friendRequestButton);

        requestFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IDtextView.getText().toString().length()==0){
                    Toast.makeText(getContext(), "요청할 친구를 입력하세요.", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });

    }

    @Override
    public void onBackKey() {
        feedRootActivity = (FeedRootActivity) getActivity();
        feedRootActivity.setOnKeyBackPressedListener(null);
        feedRootActivity.onBackPressed();
    }
}
