package com.adnd.iomoney.dialogs;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.adnd.iomoney.R;
import com.adnd.iomoney.databinding.CreateRenameAccountDialogBinding;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.utils.OperationResult;
import com.adnd.iomoney.view_models.AccountViewModel;


public class CreateRenameAccountDialog extends DialogFragment {

    public static final String KEY_ACCOUNT_ID = "account_id";

    private CreateRenameAccountDialogBinding binding;
    private int accountId = -1;
    private Account account;
    private AccountViewModel model;

    public CreateRenameAccountDialog() {
    }

    public static CreateRenameAccountDialog newInstance(int accountId) {
        Bundle args = new Bundle();
        args.putInt(KEY_ACCOUNT_ID, accountId);
        CreateRenameAccountDialog fragment = new CreateRenameAccountDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        accountId = args.getInt(KEY_ACCOUNT_ID, accountId);
        if (getActivity() != null) {
            model = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateRenameAccountDialogBinding.inflate(inflater);

        int titleResId = accountId == -1 ? R.string.add_new_account : R.string.rename_account;
        binding.setTitle(getString(titleResId));

        if (accountId != -1) {
            final MutableLiveData<Account> accountLiveData = model.getAccountLiveData(accountId);
            accountLiveData.observe(this, new Observer<Account>() {
                @Override
                public void onChanged(@Nullable Account account) {
                    if (account == null) {
                        account = new Account();
                    }
                    CreateRenameAccountDialog.this.account = account;
                    binding.etAccountName.setText(account.getName());

                    accountLiveData.removeObserver(this);
                }
            });
        } else {
            account = new Account();
        }

        binding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        binding.btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });

        setCancelable(false);
        return binding.getRoot();
    }

    private void applyChanges() {
        String accountName = binding.etAccountName.getText().toString().trim();
        if (TextUtils.isEmpty(accountName)) {
            binding.etAccountName.setError(getString(R.string.must_not_be_empty));
        } else {
            account.setName(accountName);
            model.saveAccount(account).observe(this, new Observer<OperationResult>() {
                @Override
                public void onChanged(@Nullable OperationResult result) {
                    if (result.isSuccess()) {
                        getDialog().dismiss();
                    } else {
                        binding.etAccountName.setError(result.getErrorMessage(getResources()));
                    }
                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.etAccountName.requestFocus();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
            );
        }

    }

}
