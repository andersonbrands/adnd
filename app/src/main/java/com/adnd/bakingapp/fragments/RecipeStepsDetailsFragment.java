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

import com.adnd.bakingapp.databinding.FragmentRecipeStepDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.ExoPlayerViewModel;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;
import com.google.android.exoplayer2.Player;

public class RecipeStepsDetailsFragment extends Fragment {

    public RecipeStepsDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentRecipeStepDetailsBinding binding = FragmentRecipeStepDetailsBinding.inflate(inflater);
        if (getActivity() != null) {
            final RecipeDetailsActivityViewModel model = ViewModelProviders.of(getActivity()).get(RecipeDetailsActivityViewModel.class);
            final ExoPlayerViewModel exoPlayerViewModel = ViewModelProviders.of(getActivity()).get(ExoPlayerViewModel.class);

            binding.setModel(model);

            exoPlayerViewModel.getPlayerLiveData().observe(this, new Observer<Player>() {
                @Override
                public void onChanged(@Nullable Player player) {
                    if (player != null) {
                        binding.setPlayer(player);
                    }
                }
            });
            model.getRecipeLiveData().observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        binding.setRecipe(recipe);
                    }
                }
            });
            model.getSelectedStepPosition().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer position) {
                    if (position != null) {
                        binding.setSelectedStepPosition(position);

                        String videoUrl = binding.getRecipe().getSteps().get(position).getVideoURL();
                        exoPlayerViewModel.setSourceAndPrepare(videoUrl);
                    }
                }
            });
        }
        return binding.getRoot();
    }

}
