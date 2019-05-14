package com.adnd.iomoney.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.adnd.iomoney.view_models.AddEditTransactionViewModel;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    public static final String TIME_KEY = "time_key";

    public static DatePickerFragment newInstance(long time) {

        Bundle args = new Bundle();

        args.putLong(TIME_KEY, time);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        Bundle args = getArguments();

        if (args != null && args.containsKey(TIME_KEY)) {
            c.setTime(new Date(args.getLong(TIME_KEY)));
        } else {
            c.setTime(new Date());
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        AddEditTransactionViewModel model =
                ViewModelProviders.of(getActivity()).get(AddEditTransactionViewModel.class);

        return new DatePickerDialog(getActivity(), model, year, month, day);
    }

}
