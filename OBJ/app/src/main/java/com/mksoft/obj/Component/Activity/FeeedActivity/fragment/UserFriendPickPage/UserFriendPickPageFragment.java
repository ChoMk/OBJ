package com.mksoft.obj.Component.Activity.FeeedActivity.fragment.UserFriendPickPage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.FeedRequestData;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.ViewModel.UserProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

public class UserFriendPickPageFragment extends Fragment implements FeedRootActivity.onKeyBackPressedListener{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    UserFriendAdapter userFriendAdapter;
    FragmentTransaction fragmentTransaction;
    String selectedOfferedImageName;
    private UserProfileViewModel viewModel;
    UserFriendPickPageFragment userFriendPickPageFragment;

    Button submitButton;
    @Inject
    APIRepo apiRepo;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    AlertDialog dialog;
    String userName;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((FeedRootActivity) context).setOnKeyBackPressedListener(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }
    private void configureViewModel(){

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel.class);
        viewModel.init(FeedRootActivity.feedRootActivity.getAccess_ID());
        viewModel.getFriendListLiveData().observe(this, friendData -> refreshFriendList(friendData));
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.feed_page_user_friend_pick_page_fragment, container, false);

        init(rootView);

        clickAddButton();

        hideKeyboard();

        return rootView;
    }

    private void init(ViewGroup rootView){
        userFriendPickPageFragment = this;
        selectedOfferedImageName = getArguments().getString("imageName");
        userName =  getArguments().getString("userName");
        Log.d("argu", selectedOfferedImageName);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.userFriendRecyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        submitButton = rootView.findViewById(R.id.submitButton);
        initListView();
    }

    private void initListView(){

        recyclerView.setHasFixedSize(true);
        userFriendAdapter = new UserFriendAdapter(getContext());
        recyclerView.setAdapter(userFriendAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void clickAddButton(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userFriendAdapter.getLastSelectedPosition() != -1){
                    dialogShow();
                }else{
                    Toast.makeText(getContext(), "친구를 선택하세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void hideKeyboard(){
        FeedRootActivity.feedRootActivity.getHideKeyboard().hideKeyboard();
    }

    private void dialogShow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(userFriendAdapter.getItem(userFriendAdapter.getLastSelectedPosition()).getName()+"과 "
        +selectedOfferedImageName+"을 추시겠습니까?");
        builder.setMessage("한번 더 확인하세요 ! ");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FeedRequestData feedRequestData = new FeedRequestData();
                        feedRequestData.setAttr2(selectedOfferedImageName);

                        feedRequestData.setAttr1(userFriendAdapter.getItem(userFriendAdapter.getLastSelectedPosition()).getName());
                        apiRepo.postMakeFeedRequest(userName, feedRequestData, userFriendPickPageFragment);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    @Override
    public void onBackKey() {
        FeedRootActivity.feedRootActivity.setOnKeyBackPressedListener(null);
        FeedRootActivity.feedRootActivity.onBackPressed();
    }
    public void refreshFriendList(List<FriendData> friendData){
        Log.d("hi", "hi");
        userFriendAdapter.refreshItem(friendData);
    }
    public void replaceFromPickPageToFeedPage(){
        fragmentTransaction = FeedRootActivity.feedRootActivity.getSupportFragmentManager().beginTransaction();
        FeedRootActivity.feedRootActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.feedRootMainContainer, new AllViewFeedPageFragment(), null);
        fragmentTransaction.commit();
    }
}
