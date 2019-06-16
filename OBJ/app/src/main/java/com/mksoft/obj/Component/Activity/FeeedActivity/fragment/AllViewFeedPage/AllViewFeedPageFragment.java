package com.mksoft.obj.Component.Activity.FeeedActivity.fragment.AllViewFeedPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.OfferedImagePage.OfferedImagePageFragment;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.FeedData;
import com.mksoft.obj.Repository.Data.UserData;
import com.mksoft.obj.ViewModel.UserProfileViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dagger.android.support.AndroidSupportInjection;

public class AllViewFeedPageFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FeedAdapter feedAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private UserProfileViewModel viewModel;

    FloatingActionButton fab;

    FragmentTransaction fragmentTransaction;

    ImageView userImag;
    TextView userName;
    String userNameS;
    @Inject
    APIRepo apiRepo;

    int feedSize = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((FeedRootActivity) context).setOnKeyBackPressedListener(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureViewModel();
    }
    private void configureViewModel(){

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel.class);
        viewModel.init(FeedRootActivity.feedRootActivity.getAccess_ID());
        viewModel.getUserLiveData().observe(this, userData -> updateUI(userData));
        viewModel.getFeedListLiveData().observe(this, feedData -> refreshItem(feedData));

    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureDagger();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.feed_page_all_view_feed_page_fragment, container, false);
        init(rootView);

        clickAddButton();

        hideKeyboard();
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    private void init(ViewGroup rootView){
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiRepo.getFeedListLiveData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        fab = rootView.findViewById(R.id.fab_main);
        userImag = (ImageView)rootView.findViewById(R.id.userIMG);
        userName = (TextView)rootView.findViewById(R.id.userInfoTextView);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.feedRecyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext());

        initListView();
    }

    private void initListView(){

        recyclerView.setHasFixedSize(true);
        feedAdapter = new FeedAdapter(getContext());
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void clickAddButton(){
        if(fab == null)
            return;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userName", userNameS);
                OfferedImagePageFragment offeredImagePageFragment = new OfferedImagePageFragment();
                offeredImagePageFragment.setArguments(bundle);
                fragmentTransaction = getFragmentManager().beginTransaction();
                FeedRootActivity.feedRootActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.feedRootMainContainer, offeredImagePageFragment, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private void hideKeyboard(){
        FeedRootActivity.feedRootActivity.getHideKeyboard().hideKeyboard();
    }
    private void updateUI(@Nullable UserData user){
        if (user != null){

            if(user.getUserImgUrl() == null||user.getUserImgUrl().length() == 0){
                Glide.with(this).load(R.drawable.userbaseimg).apply(RequestOptions.circleCropTransform()).into(userImag);
            }else{

                Glide.with(this).load(user.getUserImgUrl()).apply(RequestOptions.circleCropTransform()).into(userImag);
            }
            this.userName.setText(user.getName());
            userNameS = user.getName();

        }
    }
    private void refreshItem(List<FeedData> feedData){
        Collections.reverse(feedData);
        feedAdapter.refreshItem(feedData);

        mSwipeRefreshLayout.setRefreshing(false);
    }

}
