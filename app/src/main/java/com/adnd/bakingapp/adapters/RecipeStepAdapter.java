package com.adnd.bakingapp.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.databinding.RecipeStepsListItemBinding;
import com.adnd.bakingapp.models.Step;

import java.util.List;

public class RecipeStepAdapter extends BaseAdapter<Step, RecipeStepAdapter.RecipeStepViewHolder> {

    final private ListItemClickListener<Step> mListItemClickListener;

    public RecipeStepAdapter(List<Step> objects, ListItemClickListener<Step> listItemClickListener) {
        super(objects);
        this.mListItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecipeStepsListItemBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.recipe_steps_list_item, parent, false);
        return new RecipeStepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder recipeViewHolder, int position) {
        Step recipe = getObjects().get(position);
        recipeViewHolder.binding.setStep(recipe);
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecipeStepsListItemBinding binding;

        public RecipeStepViewHolder(@NonNull RecipeStepsListItemBinding binding) {
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
