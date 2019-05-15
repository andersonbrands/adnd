package com.adnd.iomoney.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.adapters.BindingAdapters;
import com.adnd.iomoney.databinding.FragmentAddEditTransactionBinding;
import com.adnd.iomoney.dialogs.AccountPickerDialog;
import com.adnd.iomoney.dialogs.DatePickerFragment;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.models.Transaction;
import com.adnd.iomoney.view_models.AddEditTransactionViewModel;

import java.util.Date;
import java.util.List;

public class AddEditTransactionFragment extends Fragment {

    private AddEditTransactionViewModel model;
    private FragmentAddEditTransactionBinding binding;

    public static AddEditTransactionFragment newInstance() {
        return new AddEditTransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditTransactionBinding.inflate(inflater);

        if (getActivity() != null) {
            model = ViewModelProviders.of(getActivity()).get(AddEditTransactionViewModel.class);

            model.getTransactionLiveData().observe(getActivity(), new Observer<Transaction>() {
                @Override
                public void onChanged(@Nullable Transaction transaction) {
                    binding.setTransaction(transaction);
                }
            });

            binding.etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = BindingAdapters.getDateFromText(binding.etDate.getText().toString());
                    DialogFragment datePickerFragment = DatePickerFragment.newInstance(date.getTime());
                    datePickerFragment.show(getActivity().getSupportFragmentManager()
                            , "date_picker_fragment");
                }
            });

            binding.etAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountPickerDialog accountPickerDialog = AccountPickerDialog.newInstance();
                    accountPickerDialog.show(getActivity().getSupportFragmentManager(), "account_picker_dialog");
                }
            });

        }

        return binding.getRoot();
    }

}
