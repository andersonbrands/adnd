package com.adnd.iomoney.dialogs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.adapters.ListItemClickListener;
import com.adnd.iomoney.adapters.SimpleAccountAdapter;
import com.adnd.iomoney.databinding.PickAccountDialogBinding;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.view_models.AddEditTransactionViewModel;

import java.util.List;

public class AccountPickerDialog extends DialogFragment implements ListItemClickListener<Account> {

    private AddEditTransactionViewModel model;
    private PickAccountDialogBinding binding;

    public static AccountPickerDialog newInstance() {

        Bundle args = new Bundle();

        AccountPickerDialog fragment = new AccountPickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PickAccountDialogBinding.inflate(inflater);

        if (getActivity() != null) {
            model = ViewModelProviders.of(getActivity()).get(AddEditTransactionViewModel.class);

            model.getAccountsLiveData().observe(getActivity(), new Observer<List<Account>>() {
                @Override
                public void onChanged(@Nullable List<Account> accounts) {
                    SimpleAccountAdapter adapter = new SimpleAccountAdapter(accounts, AccountPickerDialog.this);
                    binding.rvSelectAccount.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.rvSelectAccount.setAdapter(adapter);
                }
            });
        }

        binding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onListItemClick(Account clickedItem, int position) {
        model.setSelectedAccount(clickedItem);
        getDialog().dismiss();
    }
}
