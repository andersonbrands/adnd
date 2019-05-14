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
import com.adnd.iomoney.utils.OperationResult;

public class AddEditTransactionViewModel extends AndroidViewModel {

    private MediatorLiveData<Transaction> transactionLiveData = new MediatorLiveData<>();
    private TransactionsRepository transactionsRepository;

    public AddEditTransactionViewModel(@NonNull Application application) {
        super(application);

        transactionsRepository = new TransactionsRepository(application);
    }

    public void setup(int transaction_id, final int account_id) {
        final LiveData<Transaction> source = transactionsRepository.loadTransactionById(transaction_id);

        transactionLiveData.addSource(source, new Observer<Transaction>() {
            @Override
            public void onChanged(@Nullable Transaction transaction) {
                // TODO if transaction is null create a default one
                if (transaction == null) {
                    transaction = new Transaction();
                    transaction.setDescription("Dinner");
                    transaction.setValue(23.56f);
                    transaction.setTags("Dinner, expensive, healthy");
                    transaction.setAccount_id(account_id);
                }
                transactionLiveData.setValue(transaction);
                transactionLiveData.removeSource(source);
            }
        });
    }

    public LiveData<OperationResult> saveTransaction() {
        return transactionsRepository.addTransaction(transactionLiveData.getValue());
    }

    public MediatorLiveData<Transaction> getTransactionLiveData() {
        return transactionLiveData;
    }

}
