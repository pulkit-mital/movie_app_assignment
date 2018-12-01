package com.pulkit.movieapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pulkit.movieapp.R;
import com.pulkit.movieapp.model.movies.MoviesResult;
import com.pulkit.movieapp.picasso.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private List<MoviesResult> moviesResultList;
    final private ListItemClickListener mOnClickListener;
    private Context context;

    public interface ListItemClickListener {

        void onListItemClick(MoviesResult movie);
    }

    public MovieRecyclerAdapter(Context context, List<MoviesResult> moviesResultList, ListItemClickListener listItemClickListener) {
        this.context = context;
        this.mOnClickListener = listItemClickListener;
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

        MoviesResult movie = moviesResultList.get(position);
        holder.itemView.setTag(movie.getId());

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).transform(new RoundedTransformation(14, 0)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return moviesResultList == null ? 0 : moviesResultList.size();
    }

    public void clearList(){
        if(moviesResultList == null){
            moviesResultList = new ArrayList<>();
        }else {
            int itemCount = moviesResultList.size();
            moviesResultList.clear();
            notifyItemRangeRemoved(0,itemCount);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            MoviesResult result = moviesResultList.get(adapterPosition);
            mOnClickListener.onListItemClick(result);
        }
    }
}
