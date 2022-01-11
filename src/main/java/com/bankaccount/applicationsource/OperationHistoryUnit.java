package com.bankaccount.applicationsource;

public class OperationHistoryUnit {
    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    private String operationType;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    private float value;

    public OperationHistoryUnit(String operationType, float value) {
        this.operationType = operationType;
        this.value = value;
    }

}
