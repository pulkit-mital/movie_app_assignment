package com.pulkit.movieapp.service;

import com.pulkit.movieapp.model.movies.Movies;
import com.pulkit.movieapp.model.reviews.Reviews;
import com.pulkit.movieapp.model.trailer.Trailers;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    /**
     * query for getting list of movies
     * @param filter
     * @param apiKey
     * @return
     */
    @GET("movie/{filter}")
    Observable<Movies> getMovies(@Path("filter") String filter, @Query("api_key") String apiKey);


    /**
     * Getting reviews of particular movie
     * @param id
     * @param apiKey
     * @return
     */
    @GET("movie/{id}/reviews")
    Observable<Reviews> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);


    /**
     * getting trailers for that particular movie
     * @param id
     * @param apiKey
     * @return
     */

    @GET("movie/{id}/videos")
    Observable<Trailers> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);
}
