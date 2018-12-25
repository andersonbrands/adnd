package com.adnd.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.adnd.popularmovies.R;
import com.adnd.popularmovies.databinding.MovieReviewListItemBinding;
import com.adnd.popularmovies.models.MovieReview;

import java.util.List;

public class MovieReviewAdapter extends BaseAdapter<MovieReview, MovieReviewAdapter.MovieReviewViewHolder> {

    public MovieReviewAdapter(List<MovieReview> movieReviews) {
        super(movieReviews);
    }

    @NonNull
    @Override
    public MovieReviewAdapter.MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieReviewListItemBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.movie_review_list_item, parent, false);
        return new MovieReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapter.MovieReviewViewHolder movieReviewViewHolder, int position) {
        final MovieReview MovieReview = getObjects().get(position);
        movieReviewViewHolder.bind(MovieReview);
    }

    class MovieReviewViewHolder extends RecyclerView.ViewHolder {
        MovieReviewListItemBinding binding;

        MovieReviewViewHolder(MovieReviewListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MovieReview MovieReview) {
            binding.setMovieReview(MovieReview);
        }

    }
}
