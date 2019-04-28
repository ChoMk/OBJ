package com.mksoft.obj.DI;


import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.DB.AppDB;
import com.mksoft.obj.Repository.DB.UserDataDao;
import com.mksoft.obj.Repository.WebService.APIService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {


    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    AppDB provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                AppDB.class, "AppDBOBJ.db")
                .build();
    }

    @Provides
    @Singleton
    UserDataDao provideUserDao(AppDB database) { return database.userDao(); }




// --- NETWORK INJECTION ---

    private static String BASE_URL = "https://objproject-cd7af.firebaseio.com";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    APIService provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(APIService.class);
    }


    // --- REPOSITORY INJECTION ---
    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }


    @Provides
    @Singleton
    APIRepo provideAPIRepository(APIService webservice, UserDataDao userDataDao, Executor executor) {
        return new APIRepo(webservice, userDataDao, executor);
    }


}
