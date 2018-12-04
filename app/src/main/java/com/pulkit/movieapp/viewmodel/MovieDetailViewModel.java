package com.pulkit.movieapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pulkit.movieapp.model.movies.MoviesResult;
import com.pulkit.movieapp.model.reviews.ReviewResult;
import com.pulkit.movieapp.model.trailer.TrailerResult;

import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    LiveData<List<MoviesResult>> movieData;
    LiveData<List<ReviewResult>> reviewData;
    LiveData<List<TrailerResult>> trailerData;

    private Repository mRespository;

    public MovieDetailViewModel(int id, Context context) {
        mRespository = new Repository(id, context);

    }

    public void insert(MoviesResult result) {
        mRespository.insert(result);
    }

    public LiveData<List<MoviesResult>> getAllFavourite() {
        return movieData;
    }

    public LiveData<List<ReviewResult>> getAllReviews() {
        reviewData = mRespository.mReviewLiveData();
        return reviewData;
    }

    public LiveData<List<TrailerResult>> getAllTrailers() {
        trailerData = mRespository.mTrailerLiveData();
        return trailerData;
    }


}