package com.adnd.iomoney.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.repositories.AccountsRepository;
import com.adnd.iomoney.utils.OperationResult;

import java.util.List;

public class AccountTransactionsListViewModel extends AndroidViewModel {

    private AccountsRepository accountsRepository;
    private MediatorLiveData<Account> accountLiveData = new MediatorLiveData<>();

    public AccountTransactionsListViewModel(@NonNull Application application) {
        super(application);

        accountsRepository = new AccountsRepository(application);

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
    }

}
