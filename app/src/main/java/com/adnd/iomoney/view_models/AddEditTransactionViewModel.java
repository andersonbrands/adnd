package com.adnd.iomoney.view_models;

import android.app.Application;
import android.app.DatePickerDialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.DatePicker;

import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.repositories.AccountsRepository;
import com.adnd.iomoney.repositories.TransactionsRepository;
import com.adnd.iomoney.utils.OperationResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddEditTransactionViewModel extends AndroidViewModel implements DatePickerDialog.OnDateSetListener {

    private MediatorLiveData<Transaction> transactionLiveData = new MediatorLiveData<>();
    private MediatorLiveData<List<Account>> accountsLiveData = new MediatorLiveData<>();
    private AccountsRepository accountsRepository;
    private TransactionsRepository transactionsRepository;
    private MutableLiveData<Account> selectedAccountLiveData = new MutableLiveData<>();

    public AddEditTransactionViewModel(@NonNull Application application) {
        super(application);

        transactionsRepository = new TransactionsRepository(application);
        accountsRepository = new AccountsRepository(application);
    }

    public MutableLiveData<Account> getSelectedAccountLiveData() {
        return selectedAccountLiveData;
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
                    transaction.setDate(new Date());
                    transaction.setAccount_id(account_id);
                }
                transactionLiveData.setValue(transaction);
                transactionLiveData.removeSource(source);
            }
        });
        loadAccounts(account_id);
    }

    public LiveData<OperationResult> saveTransaction() {
        return transactionsRepository.addTransaction(transactionLiveData.getValue());
    }

    public void loadAccounts(final int selectedAccountId) {
        final LiveData<List<Account>> source = accountsRepository.loadAccounts();
        accountsLiveData.addSource(source, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {
                accountsLiveData.setValue(accounts);
                for (Account account : accounts) {
                    if (account.getId() == selectedAccountId) {
                        setSelectedAccount(account);
                        break;
                    }
                }
            }
        });
    }

    public void setSelectedAccount(Account account) {
        selectedAccountLiveData.setValue(account);
        setAccountIdForTransaction(account.getId());
    }

    private void setAccountIdForTransaction(int account_id) {
        Transaction transaction = transactionLiveData.getValue();
        if (transaction != null) {
            transaction.setAccount_id(account_id);
        }
    }

    public LiveData<List<Account>> getAccountsLiveData() {
        return accountsLiveData;
    }

    public LiveData<Transaction> getTransactionLiveData() {
        return transactionLiveData;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        transactionLiveData.getValue().setDate(c.getTime());
    }

}
