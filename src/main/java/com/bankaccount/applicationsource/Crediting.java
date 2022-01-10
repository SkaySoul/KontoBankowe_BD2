//package com.bankaccount.applicationsource;
//
//public class Crediting extends Operation implements Config{
//
//    private final String operationName = "Crediting";
//
//
//    public boolean checkValue(Account account, float value){
//        return !(account.getCreditBalance() + value > maxCreditValue);
//    }
//
//    public void getCredit(Account account, float value){
//            account.setCreditBalance(account.getCreditBalance()+value);
//            if(account.getCreditBalance()+value > maxCreditValue){
//                account.setCreditStatus(false);
//            }
//    }
//
//
//    @Override
//    public void writeToHistory(Account account,float value){
//        OperationHistoryUnit unit = new OperationHistoryUnit(operationName, value);
//        account.addToOperationList(unit);
//    }
//
//
//
//    public boolean checkConditions(Account account, float value){
//        if (account.isCreditStatus()){
//            return checkValue(account, value);
//        }
//        else return false;
//    }
//}
