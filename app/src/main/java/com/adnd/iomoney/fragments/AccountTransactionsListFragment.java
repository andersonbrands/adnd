package com.adnd.iomoney.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.adapters.ListItemClickListener;
import com.adnd.iomoney.adapters.TransactionAdapter;
import com.adnd.iomoney.databinding.FragmentAccountTransactionsListBinding;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.view_models.AccountTransactionsListViewModel;

import java.util.List;

public class AccountTransactionsListFragment extends Fragment {

    private ListItemClickListener<Transaction> transactionListItemClickListener;
    private FragmentAccountTransactionsListBinding binding;

    public AccountTransactionsListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            transactionListItemClickListener = (ListItemClickListener<Transaction>) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ListItemClickListener<Transaction>");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountTransactionsListBinding.inflate(inflater);

        if (getActivity() != null) {
            AccountTransactionsListViewModel model = ViewModelProviders.of(getActivity()).get(AccountTransactionsListViewModel.class);

            model.getTransactionsListLiveData().observe(getActivity(), new Observer<List<Transaction>>() {
                @Override
                public void onChanged(@Nullable List<Transaction> transactions) {
                    setRecyclerView(transactions);
                    float accountBalance = 0.0f;
                    for (Transaction t : transactions) {
                        accountBalance += t.getValue();
                    }
                    binding.setAccountbalance(accountBalance);
                }
            });

        }

        return binding.getRoot();
    }

    private void setRecyclerView(List<Transaction> transactions) {
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
        TransactionAdapter adapter = new TransactionAdapter(transactions, transactionListItemClickListener);
        binding.rvTransactions.setAdapter(adapter);
    }

}
