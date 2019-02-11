package com.adnd.xyzreader.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.xyzreader.R;
import com.adnd.xyzreader.view_models.ArticleDetailsViewModel;

public class ArticleDetailsFragment extends Fragment {

    private ArticleDetailsViewModel mViewModel;

    public static ArticleDetailsFragment newInstance() {
        return new ArticleDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ArticleDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}
