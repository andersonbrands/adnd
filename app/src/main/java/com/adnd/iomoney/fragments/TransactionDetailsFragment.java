package com.adnd.iomoney.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.databinding.FragmentTransactionDetailsBinding;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.view_models.TransactionViewModel;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TransactionDetailsFragment extends Fragment {

    private TransactionViewModel model;
    private FragmentTransactionDetailsBinding binding;
    private List<Account> accountList;

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

                    for (Account account : accountList) {
                        if (account.getId() == transaction.getAccount_id()) {
                            binding.setAcountName(account.getName());
                            break;
                        }
                    }
                }
            });

            model.getAccountsLiveData().observe(getActivity(), new Observer<List<Account>>() {
                @Override
                public void onChanged(@Nullable List<Account> accountList) {
                    TransactionDetailsFragment.this.accountList = accountList;
                }
            });
        }

        return binding.getRoot();
    }
}
