package com.pulkit.movieapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final int movieId;
    Context mContext;


    public MovieDetailViewModelFactory(int movieId, Context context) {
        this.movieId = movieId;
        mContext = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieDetailViewModel(movieId, mContext);
    }

}