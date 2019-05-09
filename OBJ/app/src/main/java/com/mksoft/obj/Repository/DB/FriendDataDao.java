package com.mksoft.obj.Repository.DB;


import com.mksoft.obj.Repository.Data.FriendData;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FriendDataDao {
    @Insert(onConflict = REPLACE)
    void save(FriendData friendData);

    @Insert(onConflict = REPLACE)
    void saveList(List<FriendData> friendData);

    @Query("SELECT * FROM frienddata")
    LiveData<List<FriendData>> getFriendListLiveData();//라이브 데이터 형식으로 불러오기

    @Query("SELECT * FROM frienddata WHERE lastRefresh > :lastRefreshMax LIMIT 1")
    FriendData getFriendListData(Date lastRefreshMax);//일반적인  List<FriendData>불러오기
    //초기화 기준 시간을 확인하여 불러오기 1개
    @Query("DELETE FROM frienddata")
    void deleteAll();
}
