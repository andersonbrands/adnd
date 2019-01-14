package com.adnd.bakingapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.databinding.FragmentRecipeDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;

public class RecipeDetailsFragment extends Fragment {

    public RecipeDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentRecipeDetailsBinding binding = FragmentRecipeDetailsBinding.inflate(inflater);
        if (getActivity() != null) {
            RecipeDetailsActivityViewModel model = ViewModelProviders.of(getActivity()).get(RecipeDetailsActivityViewModel.class);
            model.getRecipeLiveData().observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null){
                        binding.setRecipe(recipe);
                        binding.executePendingBindings();
                    }
                }
            });
        }
        return binding.getRoot();
    }
}
