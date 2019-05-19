package com.adnd.iomoney;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.adnd.iomoney.databinding.ActivityAddEditTransactionBinding;
import com.adnd.iomoney.utils.OperationResult;
import com.adnd.iomoney.view_models.AddEditTransactionViewModel;

public class AddEditTransactionActivity extends AppCompatActivity {

    public static final String TRANSACTION_ID_EXTRA_KEY = "transaction_id_extra_key";
    public static final String ACCOUNT_ID_EXTRA_KEY = "account_id_extra_key";
    private ActivityAddEditTransactionBinding binding;
    private AddEditTransactionViewModel model;
    private int transaction_id = -1;

    private Observer<OperationResult> operationObserver = new Observer<OperationResult>() {
        @Override
        public void onChanged(@Nullable OperationResult operationResult) {
            if (operationResult.isSuccess()) {
                Snackbar.make(binding.getRoot(), "Transaction saved", Snackbar.LENGTH_LONG).show();
                finish();
            } else {
                Snackbar.make(binding.getRoot(), operationResult.getErrorMessage(getResources()), Snackbar.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_transaction);

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intentThatStartedActivity = getIntent();

        if (intentThatStartedActivity.hasExtra(TRANSACTION_ID_EXTRA_KEY)) {
            transaction_id =
                    intentThatStartedActivity.getIntExtra(TRANSACTION_ID_EXTRA_KEY, transaction_id);
        }
        int account_id = -1;
        if (intentThatStartedActivity.hasExtra(ACCOUNT_ID_EXTRA_KEY)) {
            account_id =
                    intentThatStartedActivity.getIntExtra(ACCOUNT_ID_EXTRA_KEY, account_id);
        }

        setSupportActionBar(binding.toolbar);

        binding.setTitle(getString(R.string.add_new_transaction));

        model = ViewModelProviders.of(this).get(AddEditTransactionViewModel.class);

        model.setup(transaction_id, account_id);

        binding.fabSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (transaction_id != -1) {
                    model.editTransaction().observe(AddEditTransactionActivity.this, operationObserver);
                } else {
                    model.saveTransaction().observe(AddEditTransactionActivity.this, operationObserver);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
