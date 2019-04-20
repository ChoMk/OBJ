package com.mksoft.obj.Component.Activity;

import android.os.Bundle;

import com.mksoft.obj.Component.Activity.fragment.AllViewFeedPage.AllViewFeedPageFragment;
import com.mksoft.obj.OtherMethod.HideKeyboard;
import com.mksoft.obj.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    public static MainActivity mainActivity;
    HideKeyboard hideKeyboard;
    BackPressCloseHandler backPressCloseHandler;

    AllViewFeedPageFragment allViewFeedPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        init();
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
