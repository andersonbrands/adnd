package com.adnd.iomoney;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
                promptDeleteTransaction();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void promptDeleteTransaction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.msg_delete_transaction)
                .setTitle(R.string.title_delete_transaction)
                .setPositiveButton(R.string.action_delete_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.deleteTransaction(binding.getTransaction());
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
