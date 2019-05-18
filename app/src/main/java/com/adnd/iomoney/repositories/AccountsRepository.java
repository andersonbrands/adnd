package com.adnd.iomoney.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;

import com.adnd.iomoney.AppExecutors;
import com.adnd.iomoney.R;
import com.adnd.iomoney.database.AccountsDao;
import com.adnd.iomoney.database.AppDatabase;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.utils.OperationResult;

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

    public LiveData<Account> loadAccount(int accountId) {
        return accountsDao.getAccount(accountId);
    }


    public LiveData<OperationResult> saveAccount(@NonNull final Account account) {
        final MutableLiveData<OperationResult> operationResultLiveData = new MutableLiveData<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                OperationResult result = doSaveAccount(account);
                operationResultLiveData.postValue(result);
            }
        });
        return operationResultLiveData;
    }

    private OperationResult doSaveAccount(Account account) {
        OperationResult result;
        try {
            if (accountsDao.getAccountBlock(account.getId()) == null) {
                accountsDao.insert(account);
            } else {
                accountsDao.update(account);
            }
            result = new OperationResult();
        } catch (SQLiteConstraintException e) {
            result = new OperationResult(R.string.msg_account_same_name);
        }

        return result;
    }

}
