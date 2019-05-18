package com.adnd.iomoney.view_models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.repositories.TransactionsRepository;

import java.util.List;

public class AccountTransactionsListViewModel extends AccountViewModel {

    private TransactionsRepository transactionsRepository;
    private MediatorLiveData<List<Transaction>> transactionsListLiveData = new MediatorLiveData<>();

    public AccountTransactionsListViewModel(@NonNull Application application) {
        super(application);

        transactionsRepository = new TransactionsRepository(application);
    }

    public MediatorLiveData<List<Transaction>> getTransactionsListLiveData() {
        return transactionsListLiveData;
    }

    public void loadAccountAndTransactions(int account_id) {
        loadAccount(account_id);
        loadTransactionsByAccountId(account_id);
    }

    private void loadTransactionsByAccountId(int account_id) {
        final LiveData<List<Transaction>> source
                = transactionsRepository.loadTransactionsByAccountId(account_id);
        transactionsListLiveData.addSource(source, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable List<Transaction> transactions) {
                transactionsListLiveData.setValue(transactions);
            }
        });
    }

}
