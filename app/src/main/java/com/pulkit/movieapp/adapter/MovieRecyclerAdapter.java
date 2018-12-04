package com.pulkit.movieapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pulkit.movieapp.R;
import com.pulkit.movieapp.activities.MoviesActivity;
import com.pulkit.movieapp.model.movies.MoviesResult;
import com.pulkit.movieapp.picasso.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to load movie list
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private List<MoviesResult> moviesResultList;
    private Context context;

    public interface ListItemClickListener {

        void onListItemClick(MoviesResult movie);
    }

    public MovieRecyclerAdapter(Context context, List<MoviesResult> moviesResultList) {
        this.context = context;
        this.moviesResultList = moviesResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MoviesResult movie = moviesResultList.get(position);
        holder.itemView.setTag(movie.getId());

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).transform(new RoundedTransformation(14, 0)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MoviesActivity)context).goToMovieDetails(movie);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesResultList == null ? 0 : moviesResultList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }
}
