package com.adnd.iomoney.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.adnd.iomoney.models.Account;

import java.util.List;

@Dao
public interface AccountsDao {

    @Query("SELECT * FROM accounts")
    LiveData<List<Account>> getAccounts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Account> accounts);

}
