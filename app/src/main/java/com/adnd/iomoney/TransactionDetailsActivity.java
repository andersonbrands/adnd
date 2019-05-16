package com.adnd.iomoney;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adnd.iomoney.databinding.ActivityTransactionDetailsBinding;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.view_models.TransactionViewModel;

public class TransactionDetailsActivity extends AppCompatActivity {

    public static final String TRANSACTION_ID_KEY = "transaction_id_key";
    private ActivityTransactionDetailsBinding binding;
    private TransactionViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_details);

        model = ViewModelProviders.of(this).get(TransactionViewModel.class);

        setSupportActionBar(binding.toolbar);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(TRANSACTION_ID_KEY)) {
            int transaction_id =
                    intentThatStartedThisActivity.getIntExtra(TRANSACTION_ID_KEY, -1);

            model.setTransactionId(transaction_id);

            model.getTransactionLiveData().observe(this, new Observer<Transaction>() {
                @Override
                public void onChanged(@Nullable Transaction transaction) {
                    binding.setTransaction(transaction);
                }
            });
        } else {
            finish();
        }
    }

}
