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
import com.adnd.bakingapp.view_models.ComingFromConfigChangeViewModel;
import com.adnd.bakingapp.view_models.ExoPlayerViewModel;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;
import com.google.android.exoplayer2.Player;

public class RecipeStepsDetailsFragment extends Fragment {

    private boolean comingFromConfigChange = false;
    private ExoPlayerViewModel exoPlayerViewModel;
    private ComingFromConfigChangeViewModel comingFromConfigChangeViewModel;

    private FragmentRecipeStepDetailsBinding binding;

    public RecipeStepsDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeStepDetailsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getActivity() != null) {
            RecipeDetailsActivityViewModel recipeDetailsActivityViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsActivityViewModel.class);
            exoPlayerViewModel = ViewModelProviders.of(getActivity()).get(ExoPlayerViewModel.class);

            comingFromConfigChangeViewModel
                    = ViewModelProviders.of(getActivity()).get(ComingFromConfigChangeViewModel.class);

            comingFromConfigChangeViewModel.getComingFromConfigChange().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean value) {
                    if (value != null) {
                        comingFromConfigChange = value;
                    }
                }
            });

            binding.setModel(recipeDetailsActivityViewModel);

            exoPlayerViewModel.getPlayerLiveData().observe(this, new Observer<Player>() {
                @Override
                public void onChanged(@Nullable Player player) {
                    if (player != null) {
                        binding.setPlayer(player);
                    }
                }
            });
            recipeDetailsActivityViewModel.getRecipeLiveData().observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        binding.setRecipe(recipe);
                    }
                }
            });
            recipeDetailsActivityViewModel.getSelectedStepPosition().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer position) {
                    if (position != null) {
                        binding.setSelectedStepPosition(position);
                        if (!comingFromConfigChange) {
                            String videoUrl = binding.getRecipe().getSteps().get(position).getVideoURL();
                            exoPlayerViewModel.startPlayer(videoUrl);
                        }
                    }
                }
            });
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (getActivity() != null) {
            boolean isChangingConfigurations = getActivity().isChangingConfigurations();
            comingFromConfigChangeViewModel.setComingFromConfigChange(isChangingConfigurations);

            if (!isChangingConfigurations) {
                exoPlayerViewModel.releasePlayer();
            }
        }
    }
}
