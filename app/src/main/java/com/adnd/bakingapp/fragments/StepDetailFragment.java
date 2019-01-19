package com.adnd.bakingapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.databinding.FragmentStepDetailBinding;
import com.adnd.bakingapp.exoplayer.ExoPlayerController;
import com.adnd.bakingapp.models.Step;
import com.adnd.bakingapp.view_models.RecipeDetailsActivityViewModel;


public class StepDetailFragment extends Fragment implements RecipeStepsDetailsFragment.iPauseResume {

    public static final String STEP_JSON_STRING_EXTRA_KEY = "step_json_string_extra_key";
    private Step step;
    private FragmentStepDetailBinding binding;
    private RecipeDetailsActivityViewModel model;

    public StepDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = Step.fromJSONString(getArguments().getString(STEP_JSON_STRING_EXTRA_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStepDetailBinding.inflate(inflater);

        model = ViewModelProviders.of(getActivity()).get(RecipeDetailsActivityViewModel.class);

        binding.setStep(step);

        return binding.getRoot();
    }

    public static StepDetailFragment newInstance(String stepJSONString) {
        StepDetailFragment instance = new StepDetailFragment();

        Bundle args = new Bundle();
        args.putString(STEP_JSON_STRING_EXTRA_KEY, stepJSONString);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onPauseFragment() {
        if (binding != null) {
            binding.playerView.getPlayer().stop();
            binding.playerView.setPlayer(null);
        }
    }

    @Override
    public void onResumeFragment() {
        if (binding != null && model != null) {
            model.setSourceAndPrepare(binding.getStep().getVideoURL(), binding.playerView);
        }
    }
}
