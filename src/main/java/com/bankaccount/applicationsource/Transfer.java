package com.bankaccount.applicationsource;

//import java.util.List;
//import java.util.Objects;
//
//public class Transfer extends Operation{
//
//    private final String operationName = "Transfer";
//
//
//    public Account searchReceiver(String receiverAccNum, List<Account> accountList){
//        for (Account account : accountList) {
//            if (Objects.equals(account.getAccountNumber(), receiverAccNum)) {
//                return account;
//            }
//        }
//        return null;
//    }
//
//    public boolean checkConditions(Account account, float value){ return checkBalance(account) >= value;
//    }
//
//    @Override
//    public void writeToHistory(Account account,float value){
//        OperationHistoryUnit unit = new OperationHistoryUnit(operationName, value);
//        account.addToOperationList(unit);
//    }
//
//}
