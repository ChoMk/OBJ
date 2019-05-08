package com.mksoft.obj.Component.Activity.LoginActivity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.LoginActivity.LoginRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.R;
import com.mksoft.obj.Repository.APIRepo;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.support.AndroidSupportInjection;

public class LoginPageFragment extends Fragment {

    RelativeLayout loginProjectLoginPageRelativeLayout;
    EditText loginProjectLoginPageIdEditText;
    Button loginProjectLoginPageLoginButton;
    Button loginProjectLoginPageJoinButton;

    FragmentTransaction fragmentTransaction;

    @Inject
    APIRepo apiRepo;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();

    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((LoginRootActivity) context).setOnKeyBackPressedListener(null);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.login_page_login_fragment, container, false);
        initUI(rootView);
        hideKeyboard();
        clickHideKeyboard();
        clickLoginButton();
        clickJoinButton();
        return rootView;
    }
    private void initUI(ViewGroup rootView){
        loginProjectLoginPageRelativeLayout = rootView.findViewById(R.id.loginProjectLoginPageRelativeLayout);
        loginProjectLoginPageIdEditText = rootView.findViewById(R.id.loginProjectLoginPageIdEditText);
        loginProjectLoginPageLoginButton = rootView.findViewById(R.id.loginProjectLoginPageLoginButton);
        loginProjectLoginPageJoinButton = rootView.findViewById(R.id.loginProjectLoginPageJoinButton);
    }
    private void hideKeyboard(){
        LoginRootActivity.loginRootActivity.getHideKeyboard().hideKeyboard();
    }
    private void clickHideKeyboard(){
        loginProjectLoginPageRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

            }
        });
    }
    private void clickLoginButton(){
        loginProjectLoginPageLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginProjectLoginPageIdEditText.getText().toString() != null &&
                loginProjectLoginPageIdEditText.getText().toString().length()!=0
                ) {
                    apiRepo.getUserInfo(loginProjectLoginPageIdEditText.getText().toString(), LoginRootActivity.loginRootActivity);

                }else{
                    Toast.makeText(LoginRootActivity.loginRootActivity.getApplicationContext(), "입력창을 확인하세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void clickJoinButton(){
        loginProjectLoginPageJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                LoginRootActivity.loginRootActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.loginRootmainContainer, new JoinPageFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}
