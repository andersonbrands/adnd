package com.adnd.iomoney.adapters;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;

import com.adnd.iomoney.R;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.utils.CurrencyTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingAdapters {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @BindingAdapter("raw_currency")
    public static void setRawCurrency(TextInputEditText textInputEditText, float value) {
        if (Float.isNaN(value)) {
            textInputEditText.setText("");
        } else {
            Resources res = textInputEditText.getResources();
            textInputEditText.setText(res.getString(R.string.currency_format, value));
        }
    }

    @InverseBindingAdapter(attribute = "raw_currency", event = "")
    public static float getRawCurrency(TextInputEditText textInputEditText) {
        try {
            String valueText = textInputEditText.getText().toString();
            return Float.parseFloat(valueText.replace("$ ", ""));
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    @BindingAdapter(value = "raw_currencyAttrChanged")
    public static void setListener(final TextInputEditText textInputEditText, final InverseBindingListener listener) {
        if (listener != null) {
            textInputEditText.addTextChangedListener(new CurrencyTextWatcher(textInputEditText, listener));
        }
    }

    @BindingAdapter("android:text")
    public static void setFloat(TextView textView, float value) {
        if (Float.isNaN(value)) {
            textView.setText("");
        } else {
            Resources res = textView.getResources();
            textView.setText(res.getString(R.string.currency_format, value));
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static float getFloat(TextView textView) {
        try {
            String valueText = textView.getText().toString();
            return Float.parseFloat(valueText.replace("$ ", ""));
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    @BindingAdapter("android:text")
    public static void setAccount(TextView textView, Account account) {
        if (account != null) {
            textView.setText(account.getName());
        }
    }

    @BindingAdapter("android:text")
    public static void setDate(TextView textView, Date date) {
        if (date == null) {
            date = new Date();
        }
        textView.setText(sdf.format(date));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static Date getDate(TextView textView) {
        String dateText = textView.getText().toString();

        return getDateFromText(dateText);
    }

    public static Date getDateFromText(String dateText) {
        try {
            return sdf.parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

}
