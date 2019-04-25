package com.mksoft.obj.DI;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mksoft.obj.Repository.APIRepo;
import com.mksoft.obj.Repository.WebService.APIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module//(includes = ViewModelModule.class)
public class AppModule {


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
    @Singleton
    APIRepo provideAPIRepository(APIService webservice) {
        return new APIRepo(webservice);
    }


}
