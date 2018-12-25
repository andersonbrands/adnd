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

import java.util.List;

public class MovieVideoAdapter extends BaseAdapter<MovieVideo, MovieVideoAdapter.MovieVideoViewHolder> {

    final private ListItemClickListener<MovieVideo> mListItemClickListener;

    public MovieVideoAdapter(List<MovieVideo> movieVideos, ListItemClickListener<MovieVideo> listItemClickListener) {
        super(movieVideos);
        mListItemClickListener = listItemClickListener;
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
    public void onBindViewHolder(@NonNull MovieVideoAdapter.MovieVideoViewHolder movieVideoViewHolder, int position) {
        final MovieVideo movieVideo = getObjects().get(position);
        movieVideoViewHolder.bind(movieVideo);
    }

    class MovieVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MovieVideoListItemBinding binding;

        MovieVideoViewHolder(MovieVideoListItemBinding binding) {
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
            mListItemClickListener.onListItemClick(getObjects().get(clickedPosition));
        }
    }
}
