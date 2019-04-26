package com.mksoft.obj.Repository;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.LoginActivity.Fragment.JoinPageFragment;
import com.mksoft.obj.Component.Activity.LoginActivity.LoginRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Repository.Data.UserData;
import com.mksoft.obj.Repository.WebService.APIService;

import org.mozilla.javascript.tools.jsc.Main;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class APIRepo {
    private final APIService webservice;

    @Inject
    public APIRepo(APIService webservice) {
        Log.d("testResultRepo", "make it!!!");
        this.webservice = webservice;
    }
    public void checkUser(String userID, final JoinPageFragment joinPageFragment, final TextView stateTextview){
        Call<UserData>call =webservice.checkUser(userID);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                if(response.isSuccessful() && response.body() != null){

                    joinPageFragment.setIdState(false);
                    stateTextview.setText("이미 가입되어 있습니다.");
                    stateTextview.setTextColor(Color.parseColor("#FFF35757"));

                }else{
                    joinPageFragment.setIdState(true);
                    stateTextview.setText("사용가능 아이디입니다.");
                    stateTextview.setTextColor(Color.parseColor("#83f162"));
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d("Login fail log", t.toString());
            }
        });
    }
    public void postUser(String userID, UserData userData, final JoinPageFragment joinPageFragment){
        Call<Object>call = webservice.postUser(userID, userData);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Toast.makeText(LoginRootActivity.loginRootActivity, "가입에 성공하였습니다. 로그인하세요."
                        ,Toast.LENGTH_SHORT).show();
                joinPageFragment.onBackKey();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(LoginRootActivity.loginRootActivity, "가입에 실패하였습니다. 문의주세요."
                        ,Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getUserInfo(final String userID, final LoginRootActivity loginRootActivity){
        Call<UserData>call =webservice.getUser(userID);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), response.body().getUserName()+"님이" +
                            " 로그인 하셨습니다.", Toast.LENGTH_LONG).show();
                    SharedPreferences pref = MainActivity.mainActivity.getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("access_ID", response.body().getId());
                    editor.commit();
                    Intent intent = new Intent(loginRootActivity, FeedRootActivity.class);
                    loginRootActivity.startActivity(intent);


                }else{
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "로그인 실패.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d("Login fail log", t.toString());
            }
        });
    }




}
