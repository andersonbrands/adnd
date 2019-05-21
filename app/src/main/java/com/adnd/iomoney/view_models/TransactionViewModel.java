package com.adnd.iomoney.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.repositories.TransactionsRepository;

public class TransactionViewModel extends AccountViewModel {

    private MediatorLiveData<Transaction> transactionLiveData = new MediatorLiveData<>();
    private TransactionsRepository transactionsRepository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);

        transactionsRepository = new TransactionsRepository(application);
    }

    public LiveData<Transaction> getTransactionLiveData() {
        return transactionLiveData;
    }

    public void setTransactionId(int transactionId) {
        final LiveData<Transaction> source = transactionsRepository.loadTransactionById(transactionId);

        transactionLiveData.addSource(source, new Observer<Transaction>() {
            @Override
            public void onChanged(@Nullable Transaction transaction) {
                transactionLiveData.setValue(transaction);
            }
        });
    }

    public void deleteTransaction(Transaction transaction) {
        transactionsRepository.deleteTransaction(transaction);
    }

}
