package com.mksoft.obj.Component.Activity;

import android.os.Bundle;
import android.util.Log;

import com.mksoft.obj.Component.Activity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.OtherMethod.HideKeyboard;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.Repository.Data.UserData;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {


    public static MainActivity mainActivity;
    HideKeyboard hideKeyboard;
    BackPressCloseHandler backPressCloseHandler;

    AllViewFeedPageFragment allViewFeedPageFragment;
    FriendData temp = null;


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    APIRepo apiRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, allViewFeedPageFragment).commit();
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
