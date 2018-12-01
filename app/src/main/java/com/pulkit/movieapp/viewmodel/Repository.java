package com.pulkit.movieapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.pulkit.movieapp.model.movies.MoviesResult;
import com.pulkit.movieapp.model.reviews.ReviewResult;
import com.pulkit.movieapp.model.trailer.TrailerResult;
import com.pulkit.movieapp.network.NetworkCallUtil;
import com.pulkit.movieapp.network.NetworkUtil;
import com.pulkit.movieapp.roomdatabase.AppDatabase;
import com.pulkit.movieapp.roomdatabase.MovieDao;

import java.util.List;

public class Repository {

    private MovieDao mMovieDao;

    private LiveData<List<MoviesResult>> movies;
    private LiveData<List<MoviesResult>> movieFav;
    private int movieID;
    private LiveData<List<ReviewResult>> mReviewResult;
    private LiveData<List<TrailerResult>> mTrailerResult;


    // constructor for movie
    public Repository(Application application) {

        AppDatabase db = AppDatabase.getDatabase(application);
        mMovieDao = db.movieDao();

        NetworkCallUtil.fetchMovies("popular");


    }

    // constructor for review and trailer

    public Repository(int movie1ID, Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        mMovieDao = db.movieDao();
        this.movieID = movie1ID;
        NetworkCallUtil.fetchMovieReviews(movieID);
        NetworkCallUtil.fetchMovieTrailers(movieID);

    }


    // Methods for MainActivity

    public LiveData<List<MoviesResult>> mLiveData() {
        movies = NetworkCallUtil.getMovies();

        return movies;
    }


    public void getFavData() {
        movieFav = mMovieDao.getAllMovies();

    }

    public LiveData<List<MoviesResult>> mLiveDataFav() {

        return movieFav;
    }

    public void getTopRated() {
        NetworkCallUtil.fetchMovies("top_rated");
    }

    public void getPopular() {
        NetworkCallUtil.fetchMovies("popular");
    }


    public void deleteData(int id) {

        new deleteAsyncTask(mMovieDao).execute(id);

    }


    //----------------------------------------------------------------------------------


    public void insert(MoviesResult result) {

        new insertAsyncTask(mMovieDao).execute(result);
    }


    class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private MovieDao mMovieDao;


        public deleteAsyncTask(MovieDao movieDao) {
            mMovieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mMovieDao.delete(integers[0]);

            return null;
        }
    }


    class insertAsyncTask extends AsyncTask<MoviesResult, Void, Void> {

        private MovieDao mMovieDao;

        public insertAsyncTask(MovieDao movieDao) {

            mMovieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MoviesResult... results) {

            mMovieDao.insert(results[0]);

            return null;
        }
    }

    //Methods for Detail Activity


    public LiveData<List<ReviewResult>> mReviewLiveData() {
        mReviewResult = NetworkCallUtil.getMovieReviews();

        return mReviewResult;
    }

    public LiveData<List<TrailerResult>> mTrailerLiveData() {
        mTrailerResult = NetworkCallUtil.getMovieTrailers();

        return mTrailerResult;
    }




}
