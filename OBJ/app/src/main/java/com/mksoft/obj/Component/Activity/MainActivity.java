package com.mksoft.obj.Component.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.Component.Activity.LoginActivity.LoginRootActivity;
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

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    public static MainActivity mainActivity;
    SharedPreferences pref;
    String access_ID;



    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureDagger();
        mainActivity = this;
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
        IDInit();
        startPageSelect();
    }




    public void IDInit(){
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        access_ID = pref.getString("access_ID", "");
        //ID가 있으면 토큰을 받고 없으면 default 값인 ""을 받자.
        //로그인 했는지 확인
    }
    private void IDRemove(){
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        pref.edit().remove("access_ID");

    }//로그아웃
    private void startPageSelect(){
        Intent intent;
        if(access_ID != null && access_ID != ""){
            Bundle bundle = new Bundle();
            bundle.putString("access_ID", access_ID);
            intent = new Intent(this, FeedRootActivity.class);
            intent.putExtra("BUNDLE", bundle);

        }else{
            intent = new Intent(this, LoginRootActivity.class);

        }
        startActivity(intent);

    }//이미 로그인 했으면 피드창으로


}
