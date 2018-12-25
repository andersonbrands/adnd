package com.adnd.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adnd.popularmovies.R;
import com.adnd.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterAdapter extends BaseAdapter<Movie, PosterAdapter.PosterViewHolder> {

    final private ListItemClickListener<Movie> mListItemClickListener;

    public PosterAdapter(List<Movie> movies, ListItemClickListener<Movie> listItemClickListener) {
        super(movies);
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
        Movie movie = getObjects().get(position);
        posterViewHolder.bind(movie.getPosterUrl());
    }

    class PosterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView posterImageView;

        PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster_image);
            posterImageView.setOnClickListener(this);
        }

        void bind(String posterUrl) {
            Picasso.get()
                    .load(posterUrl)
                    .placeholder(R.drawable.ic_image_white_24dp)
                    .error(R.drawable.ic_error_outline_white_24dp)
                    .into(posterImageView);
        }

        @Override
        public void onClick(View v) {
            final int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(getObjects().get(clickedPosition));
        }
    }
}
