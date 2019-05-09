package com.adnd.iomoney.utils;

import android.content.res.Resources;

public class OperationResult {

    private boolean success;
    private String errorMessage;
    private int errorMessageResId;

    public OperationResult() {
        this.success = true;
        this.errorMessage = null;
        this.errorMessageResId = -1;
    }

    public OperationResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
        this.errorMessageResId = -1;
    }

    public OperationResult(int errorMessageResId) {
        this.success = false;
        this.errorMessageResId = errorMessageResId;
        this.errorMessage = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage(Resources res) {
        if (errorMessageResId != -1) {
            return res.getString(errorMessageResId);
        } else {
            return errorMessage;
        }
    }

}
