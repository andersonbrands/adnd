package com.adnd.bakingapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.adapters.RecipeStepAdapter;
import com.adnd.bakingapp.databinding.FragmentRecipeDetailsBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.models.Step;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;


public class RecipeDetailsFragment extends Fragment {

    private ListItemClickListener<Step> stepListItemClickListener;
    private FragmentRecipeDetailsBinding binding;

    public RecipeDetailsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepListItemClickListener = (ListItemClickListener<Step>) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ListItemClickListener<Step>");
        }
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
                        RecipeStepAdapter adapter = new RecipeStepAdapter(recipe.getSteps(), stepListItemClickListener);
                        binding.rvRecipeSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
                        binding.rvRecipeSteps.setAdapter(adapter);
                        binding.executePendingBindings();
                    }
                }
            });
        }
        return binding.getRoot();
    }


}
