package com.adnd.builditbigger.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.builditbigger.databinding.FragmentMainBinding;
import com.adnd.builditbigger.view_models.MainActivityViewModel;


public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            MainActivityViewModel model = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
            binding.setModel(model);
        }

    }
}
