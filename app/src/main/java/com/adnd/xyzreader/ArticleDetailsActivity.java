package com.adnd.xyzreader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adnd.xyzreader.view_models.ArticleDetailsViewModel;

public class ArticleDetailsActivity extends AppCompatActivity {

    public static final String ARTICLE_ID_EXTRA_KEY = "article_id_extra_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_details_activity);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(ARTICLE_ID_EXTRA_KEY)) {
            ArticleDetailsViewModel model =
                    ViewModelProviders.of(this).get(ArticleDetailsViewModel.class);

            int articleId =
                    intentThatStartedThisActivity.getIntExtra(ARTICLE_ID_EXTRA_KEY, 0);
            model.setArticleId(articleId);
        } else {
            finish();
        }
    }
}
