package com.adnd.iomoney.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.repositories.AccountsRepository;
import com.adnd.iomoney.repositories.TransactionsRepository;
import com.adnd.iomoney.utils.OperationResult;

import java.util.List;

public class AccountTransactionsListViewModel extends AndroidViewModel {

    private AccountsRepository accountsRepository;
    private TransactionsRepository transactionsRepository;
    private MediatorLiveData<Account> accountLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<Transaction>> transactionsListLiveData = new MediatorLiveData<>();

    public AccountTransactionsListViewModel(@NonNull Application application) {
        super(application);

        accountsRepository = new AccountsRepository(application);
        transactionsRepository = new TransactionsRepository(application);
    }

    public MediatorLiveData<List<Transaction>> getTransactionsListLiveData() {
        return transactionsListLiveData;
    }

    public MediatorLiveData<Account> getAccountLiveData() {
        return accountLiveData;
    }

    public void loadAccount(int account_id) {
        final LiveData<Account> source = accountsRepository.loadAccount(account_id);

        accountLiveData.addSource(source, new Observer<Account>() {
            @Override
            public void onChanged(@Nullable Account account) {
                accountLiveData.setValue(account);
                accountLiveData.removeSource(source);
            }
        });

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
