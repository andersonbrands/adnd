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

import java.util.List;

public class TransactionsRepository {

    private TransactionsDao transactionsDao;

    public TransactionsRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);

        transactionsDao = db.transactionsDao();
    }

    public LiveData<List<Transaction>> loadTransactionsByAccountId(int account_id) {
        return transactionsDao.getTransactionsByAccountId(account_id);
    }

    public LiveData<Transaction> loadTransactionById(int transaction_id) {
        return transactionsDao.getTransactionById(transaction_id);
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
                    result = new OperationResult(R.string.msg_account_same_name);
                }
                operationResultLiveData.postValue(result);
            }
        });
        return operationResultLiveData;
    }

}
