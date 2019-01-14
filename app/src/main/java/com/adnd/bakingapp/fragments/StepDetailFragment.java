package com.adnd.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.bakingapp.databinding.FragmentStepDetailBinding;
import com.adnd.bakingapp.models.Step;


public class StepDetailFragment extends Fragment {

    public static final String STEP_JSON_STRING_EXTRA_KEY = "step_json_string_extra_key";
    private Step step;

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

        return binding.getRoot();
    }

    public static StepDetailFragment newInstance(String stepJSONString) {
        StepDetailFragment instance = new StepDetailFragment();

        Bundle args = new Bundle();
        args.putString(STEP_JSON_STRING_EXTRA_KEY, stepJSONString);
        return instance;
    }
}
