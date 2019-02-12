package com.adnd.xyzreader;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adnd.xyzreader.databinding.ArticleDetailsActivityBinding;
import com.adnd.xyzreader.models.Article;
import com.adnd.xyzreader.view_models.ArticleDetailsViewModel;

public class ArticleDetailsActivity extends AppCompatActivity {

    public static final String ARTICLE_ID_EXTRA_KEY = "article_id_extra_key";

    ArticleDetailsActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.article_details_activity);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(ARTICLE_ID_EXTRA_KEY)) {
            ArticleDetailsViewModel model =
                    ViewModelProviders.of(this).get(ArticleDetailsViewModel.class);

            int articleId =
                    intentThatStartedThisActivity.getIntExtra(ARTICLE_ID_EXTRA_KEY, 0);
            model.setArticleId(articleId);

            model.getArticleLiveData().observe(this, new Observer<Article>() {
                @Override
                public void onChanged(@Nullable Article article) {
                    if (article != null) {
                        binding.setArticle(article);
                    }
                }
            });
        } else {
            finish();
        }
    }
}
