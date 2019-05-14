package com.adnd.iomoney.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;

public class BindingAdapters {

    @BindingAdapter("android:text")
    public static void setFloat(TextView textView, float value) {
        if (Float.isNaN(value)) {
            textView.setText("");
        } else {
            // TODO format???
            textView.setText(String.valueOf(value));
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static float getFloat(TextView textView) {
        try {
            return Float.parseFloat(textView.getText().toString());
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

}
