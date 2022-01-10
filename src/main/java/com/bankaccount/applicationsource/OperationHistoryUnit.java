package com.bankaccount.applicationsource;

public class OperationHistoryUnit {
    private String operationType;
    private float value;

    public OperationHistoryUnit(String operationType, float value) {
        this.operationType = operationType;
        this.value = value;
    }

}
