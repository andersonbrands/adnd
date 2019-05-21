package com.adnd.iomoney.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
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

            model.getAccountsLiveData().observe(getActivity(), new Observer<List<Account>>() {
                @Override
                public void onChanged(@Nullable List<Account> accountList) {
                    TransactionDetailsFragment.this.accountList = accountList;
                }
            });

            model.getTransactionLiveData().observe(getActivity(), new Observer<Transaction>() {
                @Override
                public void onChanged(@Nullable Transaction transaction) {
                    binding.setTransaction(transaction);

                    if (accountList != null) {
                        for (Account account : accountList) {
                            if (account.getId() == transaction.getAccount_id()) {
                                binding.setAcountName(account.getName());
                                break;
                            }
                        }
                    }
                }
            });


            binding.btShowInMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Transaction t = binding.getTransaction();
                    Uri uri = Uri.parse(String.format("geo:<%f>,<%f>", t.getLat(), t.getLon()));
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }

        return binding.getRoot();
    }
}
