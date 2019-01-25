package com.adnd.bakingapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.adapters.ListItemClickListener;
import com.adnd.bakingapp.adapters.RecipeAdapter;
import com.adnd.bakingapp.databinding.FragmentRecipesListBinding;
import com.adnd.bakingapp.models.Recipe;
import com.adnd.bakingapp.view_models.RecipesListViewModel;

import java.util.List;

public class RecipesListFragment extends Fragment {

    private ListItemClickListener<Recipe> recipeListItemClickListener;
    private FragmentRecipesListBinding binding;

    public RecipesListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            recipeListItemClickListener = (ListItemClickListener<Recipe>) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ListItemClickListener<Recipe>");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipesListBinding.inflate(inflater);

        if (getActivity() != null) {
            RecipesListViewModel model = ViewModelProviders.of(getActivity()).get(RecipesListViewModel.class);

            binding.setModel(model);

            model.getRecipeListLiveData().observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(@Nullable List<Recipe> recipes) {
                    setRecyclerView(recipes);
                }
            });

        }

        return binding.getRoot();
    }

    private void setRecyclerView(List<Recipe> recipes) {
        binding.rvRecipes.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes, recipeListItemClickListener);
        binding.rvRecipes.setAdapter(recipeAdapter);
        binding.setAdapter(recipeAdapter);
    }
}
