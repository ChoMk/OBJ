package com.mksoft.obj.Component.Activity.LoginActivity;

import android.os.Bundle;
import android.widget.FrameLayout;


import com.mksoft.obj.Component.Activity.BackPressCloseHandler;
import com.mksoft.obj.Component.Activity.LoginActivity.Fragment.LoginPageFragment;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.OtherMethod.HideKeyboard;
import com.mksoft.obj.R;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class LoginRootActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    BackPressCloseHandler backPressCloseHandler;
    public static LoginRootActivity loginRootActivity;
    FrameLayout mainContainer;
    LoginPageFragment loginFragment;
    HideKeyboard hideKeyboard;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_login_root_activity);
        loginRootActivity = this;
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
        mainContainer = findViewById(R.id.loginRootmainContainer);
        backPressCloseHandler = new BackPressCloseHandler(this);
        loginFragment = new LoginPageFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.loginRootmainContainer, loginFragment).commit();//로그인 화면

    }





    //hide keyboard
    public HideKeyboard getHideKeyboard(){
        return hideKeyboard;
    }

    ////////////////////// back key

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
