package com.adnd.iomoney.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.adnd.iomoney.database.AccountsDao;
import com.adnd.iomoney.database.AppDatabase;
import com.adnd.iomoney.models.Account;

import java.util.List;

public class AccountsRepository {

    private AccountsDao accountsDao;

    public AccountsRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        accountsDao = db.accountsDao();
    }

    public LiveData<List<Account>> loadAccounts() {
        return accountsDao.getAccounts();
    }

    public void addAccount(final Account account) {
        accountsDao.insert(account);
    }

}
