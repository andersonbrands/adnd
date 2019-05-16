package com.adnd.iomoney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.adnd.iomoney.databinding.ActivityTransactionDetailsBinding;

public class TransactionDetailsActivity extends AppCompatActivity {

    private ActivityTransactionDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_details);

        setSupportActionBar(binding.toolbar);
    }

}
