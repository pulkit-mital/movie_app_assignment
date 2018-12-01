package com.pulkit.movieapp.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.pulkit.movieapp.model.movies.Movies;
import com.pulkit.movieapp.model.movies.MoviesResult;
import com.pulkit.movieapp.model.reviews.ReviewResult;
import com.pulkit.movieapp.model.reviews.Reviews;
import com.pulkit.movieapp.model.trailer.TrailerResult;
import com.pulkit.movieapp.model.trailer.Trailers;
import com.pulkit.movieapp.service.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NetworkCallUtil {

    private static MutableLiveData<List<MoviesResult>> movieData = new MutableLiveData<>();
    private static MutableLiveData<List<ReviewResult>> movieReviews = new MutableLiveData<>();
    private static MutableLiveData<List<TrailerResult>> movieTrailers = new MutableLiveData<>();

    private static Observable<Movies> moviesObservable;
    private static Observable<Reviews> reviewsObservable;
    private static Observable<Trailers> trailersObservable;
    public static CompositeDisposable compositeDisposable = new CompositeDisposable();


    /**
     * --------- FETCHING DATA FOR MOVIES, DETAILS -------------------
     */

    public static void fetchMovies(String sort) {

        ApiInterface apiInterface = NetworkUtil.getRetrofitClient().create(ApiInterface.class);

        moviesObservable = apiInterface.getMovies(sort, NetworkUtil.api_key);
        compositeDisposable.add(moviesObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Movies>() {
                    @Override
                    public void onNext(Movies movies) {

                        List<MoviesResult> moviesResults = movies.getResults();
                        movieData.postValue(moviesResults);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }


    /**
     * -------------- FETCHING MOVIE REVIEWS-------------------
     */

    public static void fetchMovieReviews(int id) {

        ApiInterface apiInterface = NetworkUtil.getRetrofitClient().create(ApiInterface.class);

        reviewsObservable = apiInterface.getMovieReviews(id, NetworkUtil.api_key);
        compositeDisposable.add(reviewsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Reviews>() {
                    @Override
                    public void onNext(Reviews reviews) {
                        List<ReviewResult> reviewResults = reviews.getResults();
                        movieReviews.postValue(reviewResults);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


    /**
     * ---------------- FETCHING MOVIE TRAILERS ----------------------
     */

    public static void fetchMovieTrailers(int id) {
        ApiInterface apiInterface = NetworkUtil.getRetrofitClient().create(ApiInterface.class);

        trailersObservable = apiInterface.getMovieTrailers(id, NetworkUtil.api_key);

        compositeDisposable.add(trailersObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Trailers>() {
                    @Override
                    public void onNext(Trailers trailers) {
                        List<TrailerResult> trailerResults = trailers.getResults();
                        movieTrailers.postValue(trailerResults);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


    public static LiveData<List<MoviesResult>> getMovies(){
        return movieData;
    }

    public static LiveData<List<ReviewResult>> getMovieReviews(){
        return movieReviews;
    }

    public static LiveData<List<TrailerResult>> getMovieTrailers(){
        return movieTrailers;
    }


}
