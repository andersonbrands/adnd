package com.adnd.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adnd.popularmovies.R;
import com.adnd.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private List<Movie> movies;

    final private ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Movie clickedItem);
    }

    public PosterAdapter(List<Movie> movies, ListItemClickListener listItemClickListener) {
        if (movies == null) {
            this.movies = new ArrayList<>();
        } else {
            this.movies = movies;
        }
        mListItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.poster_list_item, viewGroup, false);
        PosterViewHolder posterViewHolder = new PosterViewHolder(view);

        return posterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder posterViewHolder, int position) {
        Movie movie = movies.get(position);
        posterViewHolder.bind(movie.getPosterUrl());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView posterImageView;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster_image);
            posterImageView.setOnClickListener(this);
        }

        void bind(String posterUrl) {
            Picasso.get()
                    .load(posterUrl)
                    .fit()
                    .into(posterImageView);
        }

        @Override
        public void onClick(View v) {
            final int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(movies.get(clickedPosition));
        }
    }
}
