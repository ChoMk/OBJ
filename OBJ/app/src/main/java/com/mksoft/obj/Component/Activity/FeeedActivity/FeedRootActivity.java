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
    SharedPreferences pref;



    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    APIRepo apiRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
