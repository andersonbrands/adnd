package com.adnd.iomoney.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;

import java.util.List;

@Dao
public interface TransactionsDao {

    @Query("SELECT * FROM transactions WHERE transactions.account_id = :account_id ORDER BY transactions.date DESC")
    LiveData<List<Transaction>> getTransactionsByAccountId(int account_id);

    @Query("SELECT * FROM transactions WHERE transactions.id = :transaction_id")
    LiveData<Transaction> getTransactionById(int transaction_id);

    @Insert
    void insert(Transaction transaction);

    @Insert
    void insert(List<Transaction> transactions);

}
