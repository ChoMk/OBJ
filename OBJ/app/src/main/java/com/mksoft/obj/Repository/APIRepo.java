package com.mksoft.obj.Repository;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.mksoft.obj.Component.Activity.FeeedActivity.FeedRootActivity;
import com.mksoft.obj.Component.Activity.FeeedActivity.fragment.UserFriendPickPage.UserFriendPickPageFragment;
import com.mksoft.obj.Component.Activity.LoginActivity.Fragment.JoinPageFragment;
import com.mksoft.obj.Component.Activity.LoginActivity.LoginRootActivity;
import com.mksoft.obj.Component.Activity.MainActivity;
import com.mksoft.obj.Repository.DB.FeedDataDao;
import com.mksoft.obj.Repository.DB.FriendDataDao;
import com.mksoft.obj.Repository.DB.UserDataDao;
import com.mksoft.obj.Repository.Data.FeedData;
import com.mksoft.obj.Repository.Data.FeedRequestData;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.Repository.Data.UserData;
import com.mksoft.obj.Repository.WebService.APIService;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class APIRepo {
    private static int FRESH_TIMEOUT_IN_MINUTES = 8;

    private final APIService webservice;
    private final UserDataDao userDao;
    private final Executor executor;
    private final FriendDataDao friendDataDao;
    private final FeedDataDao feedDataDao;

    @Inject
    public APIRepo(APIService webservice, UserDataDao userDao,
                   FriendDataDao friendDataDao,
                   FeedDataDao feedDataDao,
                   Executor executor) {
        Log.d("testResultRepo", "make it!!!");
        this.friendDataDao = friendDataDao;
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
        this.feedDataDao = feedDataDao;
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
    public void postMakeFeedRequest(FeedRequestData feedRequestData, final UserFriendPickPageFragment userFriendPickPageFragment){
        Call<Object>call = webservice.postMakeFeedRequest(feedRequestData.getPickImage()
                +feedRequestData.getUser1()+feedRequestData.getUser2(), feedRequestData);
        Log.d("dkdkdkdkefek", feedRequestData.getPickImage()+feedRequestData.getUser1()+feedRequestData.getUser2());
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                userFriendPickPageFragment.replaceFromPickPageToFeedPage();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("requestfeederro", t.toString());
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
    public void postFriend(String userID, List<FriendData> friendData){
        Call<Object>call = webservice.postFriend(userID, friendData);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });

    }
    public void getUserInfo(final String userID, final LoginRootActivity loginRootActivity){
        Call<UserData>call =webservice.getUser(userID);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), response.body().getName()+"님이" +
                            " 로그인 하셨습니다.", Toast.LENGTH_LONG).show();
                    SharedPreferences pref = MainActivity.mainActivity.getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("access_ID", response.body().getId());
                    editor.commit();

                    Intent intent = new Intent(loginRootActivity, FeedRootActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("access_ID", response.body().getId());
                    intent.putExtra("BUNDLE", bundle);
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
    public LiveData<List<FeedData>> getFeedListLiveData(){
        refreshFeedList();
        return feedDataDao.getFeedDataList();
    }
    private void refreshFeedList(){
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean feedListDataExists = (feedDataDao.getFeedData(getMaxRefreshTime(new Date())) != null);
            //최대 1분전에 룸에 유절르 저장했으면 그거 그냥 리턴 그게 아니면 서버에서 받아와서 룸을 초기화 시켜주고

            // If user have to be updated
            if (!feedListDataExists) {//확인
                Log.d("testtarace","request");
                webservice.getFeedData().enqueue(new Callback<List<FeedData>>() {
                    @Override
                    public void onResponse(Call<List<FeedData>> call, Response<List<FeedData>> response) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        //Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();

                        executor.execute(() -> {
                            List<FeedData> feedDataList = response.body();
                            for(int i =0; i<feedDataList.size(); i++){
                                Log.d("test190509", feedDataList.get(i).getName());
                                feedDataList.get(i).setLastRefresh(new Date());

                            }
                            feedDataDao.deleteAll();
                            feedDataDao.saveList(feedDataList);
                        });
                    }

                    @Override
                    public void onFailure(Call<List<FeedData>> call, Throwable t) {
                        Log.d("test190508",t.toString());
                    }
                });
            }else{
                Log.d("testtarace","no request");

            }
        });
    }
    public LiveData<List<FriendData>> getFriendListLiveData(String userLogin){
        refreshFriendList(userLogin);
        return friendDataDao.getFriendListLiveData();
    }

    private void refreshFriendList(final String userLogin){
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean friendListDataExists = (friendDataDao.getFriendListData(getMaxRefreshTime(new Date())) != null);
            //최대 1분전에 룸에 유절르 저장했으면 그거 그냥 리턴 그게 아니면 서버에서 받아와서 룸을 초기화 시켜주고

            // If user have to be updated
            if (!friendListDataExists) {//확인
                Log.d("testtarace","request");
                webservice.getUserFriends(userLogin).enqueue(new Callback<List<FriendData>>() {
                    @Override
                    public void onResponse(Call<List<FriendData>> call, Response<List<FriendData>> response) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        //Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();

                        executor.execute(() -> {
                            List<FriendData> friendDataList = response.body();
                            for(int i =0; i<friendDataList.size(); i++){
                                Log.d("test190508", friendDataList.get(i).getName());
                                friendDataList.get(i).setLastRefresh(new Date());

                            }
                            friendDataDao.deleteAll();
                            friendDataDao.saveList(friendDataList);
                        });
                    }

                    @Override
                    public void onFailure(Call<List<FriendData>> call, Throwable t) {
                        Log.d("test190508",t.toString());
                    }
                });
            }else{
                Log.d("testtarace","no request");

            }
        });
    }
    public LiveData<UserData> getUserLiveData(String userLogin) {
        refreshUser(userLogin); // try to refresh data if possible from Github Api
        return userDao.getUserLiveData(); // return a LiveData directly from the database.
        //라이브데이터를 갖고오는 과정은 쓰래드가 필요 없으니 쓰래드를 사용하지 않는다.
    }

    private void refreshUser(final String userLogin) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean userExists = (userDao.getUser(getMaxRefreshTime(new Date())) != null);
            //최대 1분전에 룸에 유절르 저장했으면 그거 그냥 리턴 그게 아니면 서버에서 받아와서 룸을 초기화 시켜주고

            // If user have to be updated
            if (!userExists) {
                webservice.getUser(userLogin).enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        //Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            UserData user = response.body();
                            user.setLastRefresh(new Date());
                            userDao.save(user);
                        });
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) { }
                });
            }
            webservice.getUser(userLogin).enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                    //Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                    executor.execute(() -> {
                        UserData user = response.body();
                        user.setLastRefresh(new Date());
                        userDao.save(user);
                    });
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) { }
            });
        });
    }//유저 값을 넘겨주는 것이 아니라 데이터베이스에 유저를 저장하고 디비(room)에 저장하기 위하여 쓰래드 사용....

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.SECOND, -FRESH_TIMEOUT_IN_MINUTES);//현제 시간에서 FRESH_TIMEOUT_IN_MINUTES() 전 시간을 불러온다.
        return cal.getTime();
    }

}
