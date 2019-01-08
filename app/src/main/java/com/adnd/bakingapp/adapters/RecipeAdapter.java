package com.adnd.bakingapp.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.databinding.RecipeListItemBinding;
import com.adnd.bakingapp.models.Recipe;

import java.util.List;

public class RecipeAdapter extends BaseAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {

    final private ListItemClickListener<Recipe> mListItemClickListener;

    public RecipeAdapter(List<Recipe> objects, ListItemClickListener<Recipe> listItemClickListener) {
        super(objects);
        this.mListItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecipeListItemBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        Recipe recipe = getObjects().get(position);
        recipeViewHolder.binding.setRecipe(recipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecipeListItemBinding binding;

        public RecipeViewHolder(@NonNull RecipeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(getObjects().get(clickedPosition));
        }
    }

}
