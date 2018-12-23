package com.adnd.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.popularmovies.R;
import com.adnd.popularmovies.databinding.MovieVideoListItemBinding;
import com.adnd.popularmovies.models.MovieVideo;

import java.util.ArrayList;
import java.util.List;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.MovieVideoViewHolder> {

    private List<MovieVideo> movieVideos;

    final private ListItemClickListener<MovieVideo> mListItemClickListener;

    public MovieVideoAdapter(List<MovieVideo> movieVideos, ListItemClickListener<MovieVideo> listItemClickListener) {
        if (movieVideos == null) {
            this.movieVideos = new ArrayList<>();
        } else {
            this.movieVideos = movieVideos;
        }
        mListItemClickListener = listItemClickListener;
    }

    @Override
    public int getItemCount() {
        return movieVideos.size();
    }

    @NonNull
    @Override
    public MovieVideoAdapter.MovieVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieVideoListItemBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.movie_video_list_item, parent, false);
        return new MovieVideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideoAdapter.MovieVideoViewHolder movieVideViewHolder, int position) {
        final MovieVideo movieVideo = movieVideos.get(position);
        movieVideViewHolder.bind(movieVideo);
    }

    public class MovieVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MovieVideoListItemBinding binding;

        public MovieVideoViewHolder(MovieVideoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        void bind(MovieVideo movieVideo) {
            binding.setMovieVideo(movieVideo);
        }

        @Override
        public void onClick(View v) {
            final int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(movieVideos.get(clickedPosition));
        }
    }
}
