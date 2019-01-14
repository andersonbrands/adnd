package com.adnd.bakingapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adnd.bakingapp.RecipeStepsDetailsActivity;
import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.adapters.RecipeStepAdapter;
import com.adnd.bakingapp.databinding.FragmentRecipeDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.Step;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;


public class RecipeDetailsFragment extends Fragment implements ListItemClickListener<Step> {

    private FragmentRecipeDetailsBinding binding;
    public RecipeDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeDetailsBinding.inflate(inflater);
        if (getActivity() != null) {
            RecipeDetailsActivityViewModel model = ViewModelProviders.of(getActivity()).get(RecipeDetailsActivityViewModel.class);
            model.getRecipeLiveData().observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        binding.setRecipe(recipe);
                        RecipeStepAdapter adapter = new RecipeStepAdapter(recipe.getSteps(), RecipeDetailsFragment.this);
                        binding.rvRecipeSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
                        binding.rvRecipeSteps.setAdapter(adapter);
                        binding.executePendingBindings();
                    }
                }
            });
        }
        return binding.getRoot();
    }

    @Override
    public void onListItemClick(Step clickedItem, int clickedPosition) {
        Intent intent = new Intent(getActivity(), RecipeStepsDetailsActivity.class);
        intent.putExtra(RecipeStepsDetailsActivity.RECIPE_JSON_EXTRA_KEY, binding.getRecipe().toJSONString());
        // TODO send item position in recipe steps as well
        startActivity(intent);
    }
}
