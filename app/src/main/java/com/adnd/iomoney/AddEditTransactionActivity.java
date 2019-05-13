package com.adnd.iomoney;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.adnd.iomoney.databinding.ActivityAddEditTransactionBinding;
import com.adnd.iomoney.view_models.AddEditTransactionViewModel;

public class AddEditTransactionActivity extends AppCompatActivity {

    public static final String TRANSACTION_ID_EXTRA_KEY = "transaction_id_extra_key";
    private ActivityAddEditTransactionBinding binding;
    private AddEditTransactionViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_transaction);

        Intent intentThatStartedActivity = getIntent();

        if (intentThatStartedActivity.hasExtra(TRANSACTION_ID_EXTRA_KEY)) {
            final int transaction_id =
                    intentThatStartedActivity.getIntExtra(TRANSACTION_ID_EXTRA_KEY, -1);
            Snackbar.make(binding.toolbar, "Transaction id: " + transaction_id, Snackbar.LENGTH_LONG).show();
        }

        setSupportActionBar(binding.toolbar);

        binding.setTitle(getString(R.string.add_new_transaction));

        model = ViewModelProviders.of(this).get(AddEditTransactionViewModel.class);

        // TODO use view model

        binding.fabSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Saving transaction...", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
