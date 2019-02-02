package com.adnd.builditbigger.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.builditbigger.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater);
        return binding.getRoot();
    }

}
