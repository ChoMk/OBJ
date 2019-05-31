package com.mksoft.obj.ViewModel;


import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.FeedData;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.Repository.Data.UserData;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Philippe on 02/03/2018.
 */

public class UserProfileViewModel extends ViewModel {

    private LiveData<UserData> user;
    private LiveData<List<FriendData>> friendList;
    private LiveData<List<FeedData>> feedList;
    private APIRepo apiRepo;

    @Inject
    public UserProfileViewModel(APIRepo apiRepo) {

        this.apiRepo = apiRepo;
    }

    // ----

    public void init(String userId) {
        if (this.user != null && this.friendList != null) {
            return;
        }
        user = apiRepo.getUserLiveData(userId);
        friendList = apiRepo.getFriendListLiveData(userId);
        feedList = apiRepo.getFeedListLiveData();
    }

    public LiveData<UserData> getUserLiveData() {
        return this.user;
    }
    public LiveData<List<FriendData>> getFriendListLiveData(){return this.friendList;}
    public LiveData<List<FeedData>> getFeedListLiveData(){return this.feedList;}
}
