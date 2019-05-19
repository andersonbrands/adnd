package com.adnd.iomoney.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.iomoney.AppExecutors;
import com.adnd.iomoney.R;
import com.adnd.iomoney.database.AccountsDao;
import com.adnd.iomoney.database.AppDatabase;
import com.adnd.iomoney.database.TransactionsDao;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.utils.OperationResult;

import java.util.ArrayList;
import java.util.List;

public class AccountsRepository {

    private AccountsDao accountsDao;
    private TransactionsDao transactionsDao;

    public AccountsRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        accountsDao = db.accountsDao();
        transactionsDao = db.transactionsDao();
    }

    public LiveData<List<Account>> loadAccounts() {
        final MutableLiveData<List<Account>> accountsListLiveData = new MutableLiveData<>();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Account> accounts = accountsDao.getAccountsBlock();

                for (Account account: accounts) {
                    List<Transaction> transactions = transactionsDao.getTransactionsByAccountIdBlock(account.getId());
                    account.setBalance(getBalanceFromTransactions(transactions));
                }
                accountsListLiveData.postValue(accounts);
            }
        });

        return accountsListLiveData;
    }

    public LiveData<Account> loadAccount(final int accountId) {
        final MediatorLiveData<Account> accountMediatorLiveData = new MediatorLiveData<>();
        final LiveData<Account> accountSource = accountsDao.getAccount(accountId);
        final LiveData<List<Transaction>> transactionsSource = transactionsDao.getTransactionsByAccountId(accountId);

        accountMediatorLiveData.addSource(accountSource, new Observer<Account>() {
            @Override
            public void onChanged(@Nullable final Account account) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Transaction> transactions =
                                transactionsDao.getTransactionsByAccountIdBlock(accountId);
                        account.setBalance(getBalanceFromTransactions(transactions));
                        accountMediatorLiveData.postValue(account);
                    }
                });
            }
        });

        accountMediatorLiveData.addSource(transactionsSource, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable List<Transaction> transactions) {
                Account account = accountMediatorLiveData.getValue();
                if (account != null && transactions != null) {
                    account.setBalance(getBalanceFromTransactions(transactions));
                }
            }
        });

        return accountMediatorLiveData;
    }

    private float getBalanceFromTransactions(List<Transaction> transactions) {
        float balance = 0.0f;
        for (Transaction t : transactions) {
            balance += t.getValue();
        }
        return balance;
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
