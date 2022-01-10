//package com.bankaccount.applicationsource;
//
//public class Payment extends Operation{
//
//    private final String operationName = "Payment";
//
//    @Override
//    public void writeToHistory(Account account,float value){
//        String name;
//        if (value>0){
//            name = operationName + "In";
//
//        }
//        else {
//            name = operationName + "Out";
//        }
//        OperationHistoryUnit unit = new OperationHistoryUnit(name, value);
//        account.addToOperationList(unit);
//
//    }
//
//
//    public boolean checkConditions(Account account, float value){
//        return !(checkBalance(account) - value < 0);
//    }
//
//}
