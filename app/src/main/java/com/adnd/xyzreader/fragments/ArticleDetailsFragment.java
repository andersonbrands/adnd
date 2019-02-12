package com.adnd.xyzreader.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.xyzreader.databinding.ArticleDetailsFragmentBinding;
import com.adnd.xyzreader.models.Article;
import com.adnd.xyzreader.view_models.ArticleDetailsViewModel;

public class ArticleDetailsFragment extends Fragment {

    private ArticleDetailsFragmentBinding binding;

    public static ArticleDetailsFragment newInstance() {
        return new ArticleDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ArticleDetailsFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            ArticleDetailsViewModel model = ViewModelProviders.of(getActivity()).get(ArticleDetailsViewModel.class);

            model.getArticleLiveData().observe(this, new Observer<Article>() {
                @Override
                public void onChanged(@Nullable Article article) {
                    if (article != null) {
                        binding.setArticle(article);
                    }
                }
            });
        }
    }

}
