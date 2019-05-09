package com.adnd.iomoney.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.databinding.FragmentAccountTransactionsListBinding;

public class AccountTransactionsListFragment extends Fragment {

    private FragmentAccountTransactionsListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountTransactionsListBinding.inflate(inflater);

        return binding.getRoot();
    }


}
