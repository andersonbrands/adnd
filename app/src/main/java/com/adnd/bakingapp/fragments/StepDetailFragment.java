package com.adnd.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.databinding.FragmentStepDetailBinding;
import com.adnd.bakingapp.exoplayer.ExoPlayerController;
import com.adnd.bakingapp.models.Step;


public class StepDetailFragment extends Fragment {

    public static final String STEP_JSON_STRING_EXTRA_KEY = "step_json_string_extra_key";
    private Step step;

    ExoPlayerController playerController;

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
        FragmentStepDetailBinding binding = FragmentStepDetailBinding.inflate(inflater);

        binding.setStep(step);

        if (playerController == null) {
            playerController = new ExoPlayerController();
            playerController.create(getActivity());
            playerController.setToPlayerView(binding.playerView);
            playerController.setSourceAndPrepare(step.getVideoURL());
        }

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playerController != null) {
            playerController.pause();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && playerController != null) {
            playerController.pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (playerController != null) {
            playerController.release();
            playerController = null;
        }
    }

    public static StepDetailFragment newInstance(String stepJSONString) {
        StepDetailFragment instance = new StepDetailFragment();

        Bundle args = new Bundle();
        args.putString(STEP_JSON_STRING_EXTRA_KEY, stepJSONString);
        instance.setArguments(args);
        return instance;
    }
}
