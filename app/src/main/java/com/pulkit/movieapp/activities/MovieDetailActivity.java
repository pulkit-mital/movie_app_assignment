package com.pulkit.movieapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pulkit.movieapp.R;
import com.pulkit.movieapp.adapter.ReviewRecyclerAdapter;
import com.pulkit.movieapp.adapter.TrailerRecyclerAdapter;
import com.pulkit.movieapp.model.movies.MoviesResult;
import com.pulkit.movieapp.model.reviews.ReviewResult;
import com.pulkit.movieapp.model.trailer.TrailerResult;
import com.pulkit.movieapp.picasso.RoundedTransformation;
import com.pulkit.movieapp.viewmodel.MovieDetailViewModel;
import com.pulkit.movieapp.viewmodel.MovieDetailViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This activity is used to display movie details
 * like movie title, reviews rating trailers and many more
 * even you can mark the movie as favorite
 */

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView releaseTextView;
    private TextView titleTextView;
    private TextView plotTextView;
    private ImageView posterImageView;
    private ImageView appBarImageView;
    private RatingBar ratingBar;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton favoriteFloatingActionButton;
    private MoviesResult moviesResult = new MoviesResult();
    private MovieDetailViewModel movieDetailViewModel;
    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        gettingDataFromIntent();
        initializeViews();
        initializeToolbar();
        settingViewModel();
        initializeFavoriteListener();
        settingData();
        initializeTrailerRecyclerView();
        initializeReviewRecyclerView();

    }

    /**
     * getting movie data from intent from main activity
     */
    private void gettingDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            moviesResult = intent.getParcelableExtra("movie_data");

        }
    }

    /**
     * initializing the views to set the required data
     */

    private void initializeViews() {

        releaseTextView = (TextView) findViewById(R.id.release);
        titleTextView = (TextView) findViewById(R.id.title);
        plotTextView = (TextView) findViewById(R.id.plot);
        posterImageView = (ImageView) findViewById(R.id.image_poster);
        appBarImageView = (ImageView) findViewById(R.id.app_bar_image);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        favoriteFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_detail);
        trailerRecyclerView = (RecyclerView) findViewById(R.id.recycler_trailers);
        reviewRecyclerView = (RecyclerView) findViewById(R.id.recycler_review);

    }

    /**
     * initializing the toolbar
     */

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(moviesResult.getTitle());

    }

    /**
     * initializing the instance of view model for movie detail
     */
    private void settingViewModel() {
        MovieDetailViewModelFactory detailViewModelFactory = new MovieDetailViewModelFactory(moviesResult.getId(), getApplicationContext());
        movieDetailViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(MovieDetailViewModel.class);
    }

    /**
     * initializing click listener for button which help user to mark
     * the movie as favorite
     */

    private void initializeFavoriteListener() {
        favoriteFloatingActionButton.setOnClickListener(this);
    }

    /**
     * setting up the movie detail, title, poster image, rating and many more info.
     */
    private void settingData() {

        //setting value in rating bar
        Float tempRating = Float.valueOf(moviesResult.getVoteCount());
        Float rating = (5 * tempRating) / 10;
        ratingBar.setRating(rating);

        //setting poster and app bar images
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + moviesResult.getBackdropPath()).into(appBarImageView);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + moviesResult.getPosterPath()).transform(new RoundedTransformation(20, 0)).into(posterImageView);

        titleTextView.setText(moviesResult.getTitle());
        plotTextView.setText(moviesResult.getOverview());
        releaseTextView.setText(moviesResult.getReleaseDate());


    }

    /**
     * initializing and loading trailer for the movie
     */

    private void initializeTrailerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);
        trailerRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        trailerRecyclerView.setItemAnimator(new DefaultItemAnimator());

        movieDetailViewModel.getAllTrailers().observe(this, new Observer<List<TrailerResult>>() {
            @Override
            public void onChanged(@Nullable List<TrailerResult> trailerResultList) {
                TrailerRecyclerAdapter trailerRecyclerAdapter = new TrailerRecyclerAdapter(MovieDetailActivity.this, trailerResultList);
                trailerRecyclerView.setAdapter(trailerRecyclerAdapter);
            }
        });
    }

    /**
     * initializing and loading movie reviews
     */
    private void initializeReviewRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        reviewRecyclerView.setLayoutManager(linearLayoutManager);
        reviewRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());

        movieDetailViewModel.getAllReviews().observe(this, new Observer<List<ReviewResult>>() {
            @Override
            public void onChanged(@Nullable List<ReviewResult> reviewResults) {
                ReviewRecyclerAdapter reviewRecyclerAdapter = new ReviewRecyclerAdapter(MovieDetailActivity.this, reviewResults);
                reviewRecyclerView.setAdapter(reviewRecyclerAdapter);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

    /**
     * store the movie marked as favorite in database
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_detail:
                movieDetailViewModel.insert(moviesResult);
                Toast.makeText(this, moviesResult.getOriginalTitle() + " " + "added to favorites!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
