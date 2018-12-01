package com.pulkit.movieapp.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtil {

    final static String MOVIE_DB_URL = "http://api.themoviedb.org/3/";
    final static String API_KEY = "api_key";
    final static String api_key = "064488f18191e4cbe225a94f92aa8b27";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(MOVIE_DB_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }

        return retrofit;
    }

}
