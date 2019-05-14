package com.adnd.iomoney.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.databinding.FragmentAddEditTransactionBinding;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.view_models.AddEditTransactionViewModel;

public class AddEditTransactionFragment extends Fragment {

    private AddEditTransactionViewModel model;
    private FragmentAddEditTransactionBinding binding;

    public static AddEditTransactionFragment newInstance() {
        return new AddEditTransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditTransactionBinding.inflate(inflater);

        if (getActivity() != null) {
            model = ViewModelProviders.of(getActivity()).get(AddEditTransactionViewModel.class);

            model.getTransactionLiveData().observe(getActivity(), new Observer<Transaction>() {
                @Override
                public void onChanged(@Nullable Transaction transaction) {
                    binding.setTransaction(transaction);
                }
            });
        }

        return binding.getRoot();
    }

}
