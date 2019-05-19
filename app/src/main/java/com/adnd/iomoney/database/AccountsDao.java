package com.adnd.iomoney.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.adnd.iomoney.models.Account;

import java.util.List;

@Dao
public interface AccountsDao {

    @Query("SELECT * FROM accounts")
    LiveData<List<Account>> getAccounts();

    @Query("SELECT * FROM accounts")
    List<Account> getAccountsBlock();

    @Query("SELECT * FROM accounts WHERE accounts.id = :id")
    LiveData<Account> getAccount(int id);

    @Query("SELECT * FROM accounts WHERE accounts.id = :id")
    Account getAccountBlock(int id);

    @Insert
    void insert(Account account);

    @Insert
    void insert(List<Account> accounts);

    @Update
    void update(Account account);

    @Update
    void update(List<Account> accounts);

    @Delete
    void delete(Account account);

    @Query("DELETE FROM accounts WHERE accounts.id = :id")
    void deleteAccountById(int id);
}
