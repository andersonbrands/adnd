package com.adnd.iomoney.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;

import com.adnd.iomoney.AppExecutors;
import com.adnd.iomoney.R;
import com.adnd.iomoney.database.AccountsDao;
import com.adnd.iomoney.database.AppDatabase;
import com.adnd.iomoney.database.TransactionsDao;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.utils.OperationResult;

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

    private OperationResult validateTransaction(Transaction transaction) {
        if (TextUtils.isEmpty(transaction.getDescription())) {
            return new OperationResult(R.string.msg_description_cannot_be_empty);
        }
        if (transaction.isHasLocation() && transaction.hasNoCoordinates()) {
            return new OperationResult(R.string.msg_pick_location_from_map);
        }

        return new OperationResult();
    }

    public LiveData<OperationResult> editTransaction(final Transaction transaction) {
        return addEditTransaction(transaction, true);
    }

    public void deleteTransaction(final Transaction transaction) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                transactionsDao.delete(transaction);
            }
        });
    }

    private LiveData<OperationResult> addEditTransaction(final Transaction transaction, final boolean edit) {
        final MutableLiveData<OperationResult> operationResultLiveData = new MutableLiveData<>();

        OperationResult r = validateTransaction(transaction);

        if (r.isSuccess()) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    OperationResult result;
                    int msg = edit ? R.string.msg_could_not_edit_transaction : R.string.msg_could_not_add_transaction;
                    try {
                        if (edit)
                            transactionsDao.update(transaction.clean());
                        else
                            transactionsDao.insert(transaction.clean());

                        result = new OperationResult();
                    } catch (Exception e) {
                        result = new OperationResult(msg);
                    }
                    updateAccountBalance(transaction.getAccount_id());
                    operationResultLiveData.postValue(result);
                }
            });
        } else {
            operationResultLiveData.setValue(r);
        }

        return operationResultLiveData;
    }

    public LiveData<OperationResult> addTransaction(final Transaction transaction) {
        return addEditTransaction(transaction, false);
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
