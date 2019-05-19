package com.adnd.iomoney;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete_transaction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_transaction:
                Intent intent = new Intent(this, AddEditTransactionActivity.class);
                intent.putExtra(AddEditTransactionActivity.TRANSACTION_ID_EXTRA_KEY, binding.getTransaction().getId());
                intent.putExtra(AddEditTransactionActivity.ACCOUNT_ID_EXTRA_KEY, binding.getTransaction().getAccount_id());
                startActivity(intent);
                break;
            case R.id.action_delete_transaction:
                // TODO delete transaction
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
