package com.adnd.bakingapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.adnd.bakingapp.adapters.RecipeStepAdapter;
import com.adnd.bakingapp.adapters.RecipeStepsPagerAdapter;
import com.adnd.bakingapp.databinding.FragmentRecipeStepDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;

public class RecipeStepsDetailsFragment extends Fragment {

    private RecipeStepsPagerAdapter adapter;

    public RecipeStepsDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentRecipeStepDetailsBinding binding = FragmentRecipeStepDetailsBinding.inflate(inflater);
        if (getActivity() != null) {
            RecipeDetailsActivityViewModel model = ViewModelProviders.of(getActivity()).get(RecipeDetailsActivityViewModel.class);
            model.getRecipeLiveData().observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        binding.setRecipe(recipe);
                        adapter = new RecipeStepsPagerAdapter(getFragmentManager(), recipe.getSteps(), getResources());
                        binding.vpRecipeSteps.setAdapter(adapter);
                    }
                }
            });
            model.getSelectedStepPosition().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer position) {
                    if (adapter != null && position != null) {
                        binding.vpRecipeSteps.setCurrentItem(position);
                    }
                }
            });
        }
        return binding.getRoot();
    }

}
