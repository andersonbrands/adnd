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

import java.util.List;

public class AccountsListViewModel extends AndroidViewModel {

    private AccountsRepository accountsRepository;
    private MediatorLiveData<List<Account>> accountsLiveData = new MediatorLiveData<>();

    public AccountsListViewModel(@NonNull Application application) {
        super(application);

        accountsRepository = new AccountsRepository(application);

        loadAccounts();
    }

    public MutableLiveData<List<Account>> getAccountsLiveData() {
        return accountsLiveData;
    }

    private void loadAccounts() {
        final LiveData<List<Account>> source = accountsRepository.loadAccounts();
        accountsLiveData.addSource(source, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {
                accountsLiveData.setValue(accounts);
                accountsLiveData.removeSource(source);
            }
        });
    }

}
