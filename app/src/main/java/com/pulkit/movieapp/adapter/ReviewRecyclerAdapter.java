package com.pulkit.movieapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pulkit.movieapp.R;
import com.pulkit.movieapp.model.reviews.ReviewResult;

import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder> {

    private List<ReviewResult> movieReviewList;
    private Context context;

    public ReviewRecyclerAdapter(Context context, List<ReviewResult> movieReviewList){
        this.context = context;
        this.movieReviewList = movieReviewList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_review,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ReviewResult movieReview = movieReviewList.get(position);

        holder.authorTextView.setText(movieReview.getAuthor());
        holder.reviewTextView.setText(movieReview.getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviewList == null ? 0 : movieReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView authorTextView;
        TextView reviewTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            authorTextView = (TextView) itemView.findViewById(R.id.txt_author);
            reviewTextView = (TextView) itemView.findViewById(R.id.txt_review);
        }
    }
}
