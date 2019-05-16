package com.adnd.iomoney.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.R;
import com.adnd.iomoney.databinding.FragmentTransactionDetailsBinding;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.view_models.TransactionViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class TransactionDetailsFragment extends Fragment {

    private TransactionViewModel model;
    private FragmentTransactionDetailsBinding binding;

    public TransactionDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionDetailsBinding.inflate(inflater);

        if (getActivity() != null) {
            model = ViewModelProviders.of(getActivity()).get(TransactionViewModel.class);

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
