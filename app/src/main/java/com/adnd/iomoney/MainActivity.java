package com.adnd.iomoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.adnd.iomoney.adapters.ListItemClickListener;
import com.adnd.iomoney.dialogs.CreateRenameAccountDialog;
import com.adnd.iomoney.models.Account;

public class MainActivity extends AppCompatActivity implements ListItemClickListener<Account> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.app_name);

        FloatingActionButton fab = findViewById(R.id.fab_add_account);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptAddAccount();
            }
        });
    }

    private void promptAddAccount() {
        FragmentManager fm = getSupportFragmentManager();
        CreateRenameAccountDialog dialog = CreateRenameAccountDialog.newInstance(-1);
        dialog.show(fm, "add_account_fragment_dialog");
    }

    @Override
    public void onListItemClick(Account clickedItem, int position) {
        Intent intent = new Intent(this, AccountTransactionsActivity.class);
        intent.putExtra(AccountTransactionsActivity.ACCOUNT_ID_EXTRA_KEY, clickedItem.getId());
        startActivity(intent);
    }
}
