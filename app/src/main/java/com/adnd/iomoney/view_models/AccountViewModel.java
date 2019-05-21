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


public class AccountViewModel extends AndroidViewModel {

    private AccountsRepository accountsRepository;
    private MediatorLiveData<List<Account>> accountsLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Account> accountLiveData = new MediatorLiveData<>();

    public AccountViewModel(@NonNull Application application) {
        super(application);

        accountsRepository = new AccountsRepository(application);

        loadAccounts();
    }

    public MutableLiveData<List<Account>> getAccountsLiveData() {
        return accountsLiveData;
    }

    public MediatorLiveData<Account> getAccountLiveData(int account_id) {
        loadAccount(account_id);
        return accountLiveData;
    }

    void loadAccount(int account_id) {
        final LiveData<Account> source = accountsRepository.loadAccount(account_id);

        accountLiveData.addSource(source, new Observer<Account>() {
            @Override
            public void onChanged(@Nullable Account account) {
                accountLiveData.setValue(account);
            }
        });
    }

    private void loadAccounts() {
        final LiveData<List<Account>> source = accountsRepository.loadAccounts();

        accountsLiveData.addSource(source, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {
                accountsLiveData.setValue(accounts);
            }
        });
    }

    public LiveData<OperationResult> saveAccount(Account account) {
        return accountsRepository.saveAccount(account);
    }

    public void deleteAccount(int account_id) {
        accountsRepository.deleteAccount(account_id);
    }
}
