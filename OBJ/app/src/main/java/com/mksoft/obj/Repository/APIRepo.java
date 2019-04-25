package com.mksoft.obj.Repository;


import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Repository.Data.UserData;
import com.mksoft.obj.Repository.WebService.APIService;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    public void postUser(String userID, UserData userData){
        Call<Object>call = webservice.postUser(userID, userData);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }
    public void getUserInfo(final String userID){
        Call<UserData>call =webservice.getUser(userID);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), response.body().getUserName()+"님이" +
                            " 로그인 하셨습니다.", Toast.LENGTH_LONG).show();

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
