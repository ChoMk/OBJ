package com.mksoft.obj.Component.Activity.FeeedActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.mksoft.obj.Component.Activity.BackPressCloseHandler;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.OtherMethod.HideKeyboard;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.FriendData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class FeedRootActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    public static FeedRootActivity feedRootActivity;
    BackPressCloseHandler backPressCloseHandler;

    AllViewFeedPageFragment allViewFeedPageFragment;

    HideKeyboard hideKeyboard;


    String access_ID;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    APIRepo apiRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_page_feed_root_activity);
        feedRootActivity = this;
        this.configureDagger();
        init();
    }
    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
    }


    private void init(){
        hideKeyboard = new HideKeyboard(this);
        allViewFeedPageFragment = new AllViewFeedPageFragment();
        backPressCloseHandler = new BackPressCloseHandler(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.feedRootMainContainer, allViewFeedPageFragment).commit();
        Bundle bundle = getIntent().getBundleExtra("BUNDLE");
        access_ID = bundle.getString("access_ID");
        //testMakeFriend();
    }


    //hide keyboard
    public HideKeyboard getHideKeyboard(){
        return hideKeyboard;
    }

    // back key

    public interface onKeyBackPressedListener{
        void onBackKey();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener){
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        }else{
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                backPressCloseHandler.onBackPressed();
            }
            else{
                super.onBackPressed();
            }
        }
    }

    public String getAccess_ID() {
        return access_ID;
    }
    public void testMakeFriend(){
        List<FriendData> tempList = new ArrayList<FriendData>();
        FriendData friendData1 = new FriendData();
        FriendData friendData2 = new FriendData();
        friendData1.setName("동민");
        friendData1.setId("ehdals");
        friendData2.setName("규택");
        friendData2.setId("rbxor");
        tempList.add(friendData1);
        tempList.add(friendData2);
        apiRepo.postFriend(access_ID, tempList);
    }
}
