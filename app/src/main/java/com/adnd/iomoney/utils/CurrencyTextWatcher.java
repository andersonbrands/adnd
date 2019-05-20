package com.adnd.iomoney.utils;

import android.databinding.InverseBindingListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyTextWatcher implements TextWatcher {

    private static DecimalFormat df = new DecimalFormat("0.00");

    private boolean ignore = false;

    private EditText editText;
    private InverseBindingListener listener;

    private Pattern pattern = Pattern.compile("\\d+");


    public CurrencyTextWatcher(EditText textView, InverseBindingListener listener) {
        this.editText = textView;
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.setSelection(count);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (ignore) {
            return;
        }
        ignore = true;
        editText.setText(parseText(s.toString()));
        listener.onChange();
        ignore = false;
    }

    private String parseText(String s) {
        StringBuilder sb = new StringBuilder();

        if (s.contains("-")) {
            sb.append("-");
        }

        Matcher m = pattern.matcher(s);
        while (m.find()) {
            sb.append(m.group());
        }
        if (sb.toString().equals("-")) {
            sb.deleteCharAt(0);
        }
        if (sb.length() == 0) {
            sb.append("0");
        }
        float f = Float.parseFloat(sb.toString()) / 100.0f;

        return "$ " + df.format(f);
    }
}
