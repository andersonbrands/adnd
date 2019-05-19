package com.adnd.iomoney.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.database.sqlite.SQLiteConstraintException;

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

public class TransactionsRepository {

    private TransactionsDao transactionsDao;
    private AccountsDao accountsDao;

    public TransactionsRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        transactionsDao = db.transactionsDao();
        accountsDao = db.accountsDao();
    }

    public LiveData<List<Transaction>> loadTransactionsByAccountId(int account_id) {
        return transactionsDao.getTransactionsByAccountId(account_id);
    }

    public LiveData<Transaction> loadTransactionById(int transaction_id) {
        return transactionsDao.getTransactionById(transaction_id);
    }

    public LiveData<OperationResult> editTransaction(final Transaction transaction) {
        final MutableLiveData<OperationResult> operationResultLiveData = new MutableLiveData<>();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                OperationResult result;
                try {
                    transactionsDao.update(transaction);
                    result = new OperationResult();
                } catch (Exception e) {
                    result = new OperationResult(R.string.msg_could_not_edit_transaction);
                }
                operationResultLiveData.postValue(result);
            }
        });

        return operationResultLiveData;
    }

    public LiveData<OperationResult> addTransaction(final Transaction transaction) {
        final MutableLiveData<OperationResult> operationResultLiveData = new MutableLiveData<>();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                OperationResult result;
                try {
                    transactionsDao.insert(transaction);
                    result = new OperationResult();
                } catch (Exception e) {
                    result = new OperationResult(R.string.msg_could_not_add_transaction);
                }
                updateAccountBalance(transaction.getAccount_id());
                operationResultLiveData.postValue(result);
            }
        });
        return operationResultLiveData;
    }

    private void updateAccountBalance(final int account_id) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Account account = accountsDao.getAccountBlock(account_id);
                if (account != null) {
                    List<Transaction> transactions = transactionsDao.getTransactionsByAccountIdBlock(account_id);
                    account.setBalance(getBalanceFromTransactions(transactions));
                    accountsDao.update(account);
                }
            }
        });
    }

    private float getBalanceFromTransactions(List<Transaction> transactions) {
        float balance = 0.0f;
        for (Transaction t : transactions) {
            balance += t.getValue();
        }
        return balance;
    }
}
