package com.pulkit.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pulkit.movieapp.R;
import com.pulkit.movieapp.model.trailer.TrailerResult;
import com.pulkit.movieapp.picasso.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.ViewHolder> {


    private Context context;
    private List<TrailerResult> trailerResultList;

    public TrailerRecyclerAdapter(Context context, List<TrailerResult> trailerResultList) {
        this.context = context;
        this.trailerResultList = trailerResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_trailer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TrailerResult trailerResult = trailerResultList.get(position);
        Picasso.get()
                .load("http://img.youtube.com/vi/" + trailerResult.getKey() + "/0.jpg")
                .transform(new RoundedTransformation(14, 0))
                .into(holder.trailerImageView);

        holder.trailerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + trailerResult.getKey()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerResultList == null ? 0 : trailerResultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView trailerImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            trailerImageView = (ImageView) itemView.findViewById(R.id.image_View_trailer);
        }
    }
}
