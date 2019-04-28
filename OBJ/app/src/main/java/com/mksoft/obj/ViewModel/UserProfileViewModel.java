package com.mksoft.obj.ViewModel;


import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.Data.UserData;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Philippe on 02/03/2018.
 */

public class UserProfileViewModel extends ViewModel {

    private LiveData<UserData> user;
    private APIRepo apiRepo;

    @Inject
    public UserProfileViewModel(APIRepo apiRepo) {

        this.apiRepo = apiRepo;
    }

    // ----

    public void init(String userId) {
        if (this.user != null) {
            return;
        }
        user = apiRepo.getUserLiveData(userId);
    }

    public LiveData<UserData> getUserLiveData() {
        return this.user;
    }


}
