package com.bankaccount.applicationsource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize
public class Account implements Config {


    private String login;

    private String password;

    private String accountNumber;

    public float currentBalance;

    private String ownerName;

    private String ownerSurname;
    private List<OperationHistoryUnit> operationList;

    private float creditBalance;
    private boolean creditStatus;


    public Account(String login, String password, String accountNumber,
                   float currentBalance, String ownerName, String ownerSurname, float creditBalance, List<OperationHistoryUnit> operationList) {

        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.operationList = operationList;
        this.creditBalance = creditBalance;
        this.creditStatus = creditBalance < maxCreditValue;
        this.login = login;
        this.password = password;

    }

    public float getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(float creditBalance) {
        this.creditBalance = creditBalance;
    }

    public boolean isCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(boolean creditStatus) {
        this.creditStatus = creditStatus;
    }

    public List<OperationHistoryUnit> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<OperationHistoryUnit> operationList) {
        this.operationList = operationList;
    }

    public void addToOperationList(OperationHistoryUnit unit) {
        this.operationList.add(unit);
    }


    public float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

}

