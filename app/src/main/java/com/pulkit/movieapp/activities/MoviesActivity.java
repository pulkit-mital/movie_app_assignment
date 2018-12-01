package com.pulkit.movieapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pulkit.movieapp.R;
import com.pulkit.movieapp.adapter.MovieRecyclerAdapter;
import com.pulkit.movieapp.model.movies.MoviesResult;
import com.pulkit.movieapp.viewmodel.MovieViewModel;

import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int selected = 0;
    private MovieRecyclerAdapter movieRecyclerAdapter;
    MovieViewModel movieViewModel;
    private final static String MENU_SELECTED = "selected";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initializeViews();
        initializeToolbar();
        intializeRecyclerView();
        initializeViewModel();

        if (savedInstanceState == null) {
            populateUI(selected);
        } else {
            selected = savedInstanceState.getInt(MENU_SELECTED);
            populateUI(selected);
        }
    }


    private void initializeViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_main);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle("Movies");
    }

    private void intializeRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initializeViewModel() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
    }

    private void populateUI(int selected) {

        movieViewModel.mLiveData().removeObservers(this);

        switch (selected) {
            case 0:
                movieViewModel.mLiveData().observe(this, new Observer<List<MoviesResult>>() {
                    @Override
                    public void onChanged(@Nullable List<MoviesResult> moviesResultList) {
                        setupRecyclerView(moviesResultList);
                    }
                });
                break;


            case 1:
                movieViewModel.mLiveDataFavourite().observe(this, new Observer<List<MoviesResult>>() {
                    @Override
                    public void onChanged(@Nullable List<MoviesResult> moviesResultList) {
                        setupRecyclerView(moviesResultList);
                    }
                });
                break;
        }
    }

    private void setupRecyclerView(List<MoviesResult> results) {
        if (results != null) {

            movieRecyclerAdapter = new MovieRecyclerAdapter(this, results, new MovieRecyclerAdapter.ListItemClickListener() {
                @Override
                public void onListItemClick(MoviesResult movie) {
                    Toast.makeText(MoviesActivity.this, "" + movie.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

            recyclerView.setAdapter(movieRecyclerAdapter);
            movieRecyclerAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No Movies Found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_SELECTED, selected);
        super.onSaveInstanceState(outState);
    }

    // For menu settings

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.highest_Rated:

                movieViewModel.getTopRated();
                selected = 0;
                populateUI(selected);

                break;

            case R.id.most_popular:

                movieViewModel.getPopular();
                selected = 0;
                populateUI(selected);
                break;

            case R.id.fav:


                movieViewModel.getFavouriteMovies();
                selected = 1;
                populateUI(selected);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

}


