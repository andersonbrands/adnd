package com.adnd.iomoney.adapters;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;

import com.adnd.iomoney.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingAdapters {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
            String valueText =textView.getText().toString();
            return Float.parseFloat(valueText.replace("$ ", ""));
        } catch (NumberFormatException e) {
            return 0.0f;
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
