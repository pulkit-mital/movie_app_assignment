package com.pulkit.movieapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pulkit.movieapp.model.movies.MoviesResult;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private LiveData<List<MoviesResult>> movies;
    private LiveData<List<MoviesResult>> favouriteMovies;
    private Repository repository;

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("xxx","data cleared");
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);


        repository = new Repository(application);

    }

    public LiveData<List<MoviesResult>> mLiveData() {
        movies = repository.mLiveData();
        return movies;
    }

    public LiveData<List<MoviesResult>> mLiveDataFavourite() {
        if (favouriteMovies == null) {
            favouriteMovies = new MutableLiveData<>();
        }
        favouriteMovies = repository.mLiveDataFav();
        return favouriteMovies;
    }


    public void getTopRated() {
        repository.getTopRated();
    }

    public void getPopular() {
        repository.getPopular();
    }

    public void getFavouriteMovies() {
        repository.getFavouriteMovies();


    }

    public void deleteData(int id) {

        repository.deleteData(id);
    }
}

