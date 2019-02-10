package com.adnd.xyzreader.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.xyzreader.R;
import com.adnd.xyzreader.databinding.ArticleListItemBinding;
import com.adnd.xyzreader.models.Article;

import java.util.List;

public class ArticleAdapter extends BaseAdapter<Article, ArticleAdapter.ArticleViewHolder> {

    final private ListItemClickListener<Article> mListItemClickListener;

    public ArticleAdapter(List<Article> objects, ListItemClickListener<Article> mListItemClickListener) {
        super(objects);
        this.mListItemClickListener = mListItemClickListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ArticleListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int position) {
        Article article = getObjects().get(position);
        articleViewHolder.binding.setArticle(article);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ArticleListItemBinding binding;

        public ArticleViewHolder(@NonNull ArticleListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(getObjects().get(clickedPosition), clickedPosition);
        }
    }
}
