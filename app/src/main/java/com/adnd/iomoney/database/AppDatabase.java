package com.adnd.iomoney.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;

@Database(entities = {Account.class, Transaction.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance = null;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "io-money-app-db";

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract AccountsDao accountsDao();

    public abstract TransactionsDao transactionsDao();

}