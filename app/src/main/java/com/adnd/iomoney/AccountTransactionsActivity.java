package com.adnd.iomoney;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.adnd.iomoney.adapters.ListItemClickListener;
import com.adnd.iomoney.databinding.ActivityAccountTransactionsBinding;
import com.adnd.iomoney.dialogs.CreateRenameAccountDialog;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.view_models.AccountTransactionsListViewModel;

public class AccountTransactionsActivity extends AppCompatActivity implements ListItemClickListener<Transaction> {

    public static final String ACCOUNT_ID_EXTRA_KEY = "account_id_extra_key";
    private ActivityAccountTransactionsBinding binding;
    private AccountTransactionsListViewModel model;
    private int account_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_transactions);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(ACCOUNT_ID_EXTRA_KEY)) {
            account_id =
                    intentThatStartedThisActivity.getIntExtra(ACCOUNT_ID_EXTRA_KEY, -1);
            setSupportActionBar(binding.toolbar);

            model = ViewModelProviders.of(this).get(AccountTransactionsListViewModel.class);

            model.getAccountLiveData(account_id).observe(this, new Observer<Account>() {
                @Override
                public void onChanged(@Nullable Account account) {
                    if (account != null) {
                        binding.setAccountName(account.getName());
                    }
                }
            });

            model.loadAccountAndTransactions(account_id);

            binding.fabAddTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AccountTransactionsActivity.this, AddEditTransactionActivity.class);
                    intent.putExtra(AddEditTransactionActivity.ACCOUNT_ID_EXTRA_KEY, account_id);
                    startActivity(intent);
                }
            });

        } else {
            Snackbar.make(binding.toolbar, "No account id provided!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    @Override
    public void onListItemClick(Transaction clickedItem, int position) {
        Intent intent = new Intent(this, TransactionDetailsActivity.class);
        intent.putExtra(TransactionDetailsActivity.TRANSACTION_ID_KEY, clickedItem.getId());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rename_delete_account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rename_account:
                promptRenameAccount();
                break;
            case R.id.action_delete_account:
                // TODO prompt delete account
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void promptRenameAccount() {
        Account account = model.getAccountLiveData(account_id).getValue();
        int accountId = -1;
        if (account != null) {
            accountId = account.getId();
        }
        FragmentManager fm = getSupportFragmentManager();
        CreateRenameAccountDialog dialog = CreateRenameAccountDialog.newInstance(accountId);
        dialog.show(fm, "rename_account_fragment_dialog");
    }

    private void promptDeleteAccount() {

    }

}
