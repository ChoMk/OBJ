package com.mksoft.obj.Repository.DB;


import com.mksoft.obj.Repository.Data.FeedData;
import com.mksoft.obj.Repository.Data.FriendData;
import com.mksoft.obj.Repository.Data.UserData;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UserData.class, FriendData.class, FeedData.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDB extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile AppDB INSTANCE;

    // --- DAO ---
    public abstract UserDataDao userDao();
    public abstract FriendDataDao friendDao();
    public abstract FeedDataDao feedDataDao();

}
