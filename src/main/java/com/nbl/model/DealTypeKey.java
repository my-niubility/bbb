package com.nbl.model;

public class DealTypeKey {
    private String optCode;

    private String stepKey;

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode == null ? null : optCode.trim();
    }

    public String getStepKey() {
        return stepKey;
    }

    public void setStepKey(String stepKey) {
        this.stepKey = stepKey == null ? null : stepKey.trim();
    }
}