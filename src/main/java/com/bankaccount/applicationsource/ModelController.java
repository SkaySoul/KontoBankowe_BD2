package com.bankaccount.applicationsource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ModelController {

    DatabaseConnector dbConnector;


    public ModelController() {
        this.dbConnector = new DatabaseConnector();
    }

    public Boolean findAccount(String login, String password) throws SQLException, ClassNotFoundException {
        try {
            //watching is login exist in database
            String checkLogin = "select exists (select login from users where login = '" + login + "');";
            PreparedStatement preparedStatementLogin = dbConnector.getDbConnection().prepareStatement(checkLogin);
            ResultSet rsLogin = preparedStatementLogin.executeQuery();
            if (rsLogin.next()) {
                if (rsLogin.getInt(1) == 0) {
                    return false;
                    // watching password is correct
                } else {
                    String checkPassword = "select passwords from users where login = '" + login + "';";
                    PreparedStatement preparedStatementPassword = dbConnector.getDbConnection().prepareStatement(checkPassword);
                    ResultSet rsPassword = preparedStatementPassword.executeQuery();
                    if (rsPassword.next()) {
                        if (!rsPassword.getString(1).equals(password)) {
                            return false;
                        } else return true;
                    }
                    rsPassword.close();
                }
            }
            rsLogin.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addNewUser(String userName, String userSurname, String login, String password) throws SQLException, ClassNotFoundException {

        try {
            //checking is login is unique
            String checkLogin = "select exists (select login from users where login = '" + login + "');";
            PreparedStatement preparedStatement = dbConnector.getDbConnection().prepareStatement(checkLogin);
            ResultSet rsLogin = preparedStatement.executeQuery();
            if (rsLogin.next()) {
                if (rsLogin.getInt(1) == 1) {
                    return false;
                } else {

                    //inserting to table users
                    String insertUsers = "insert into users (login, passwords, accountnumber, username, usersurname) values (?,?,?,?,?);";
                    try {
                        int number = (int) (Math.random() * 89999999) + 10000000;
                        PreparedStatement preparedStatementUsers = dbConnector.getDbConnection().prepareStatement(insertUsers);
                        preparedStatementUsers.setString(1, login);
                        preparedStatementUsers.setString(2, password);
                        preparedStatementUsers.setString(3, "" + number);
                        preparedStatementUsers.setString(4, userName);
                        preparedStatementUsers.setString(5, userSurname);
                        preparedStatementUsers.executeUpdate();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String getUserID = "select id from users where login = '" + login + "';";
                    ResultSet resultSet = null;
                    try {
                        PreparedStatement preparedStatementUserID = dbConnector.getDbConnection().prepareStatement(getUserID);
                        resultSet = preparedStatementUserID.executeQuery();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    int id = 0;

                    if (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }
                    //inserting to table credit status
                    String insertCreditStatus = "insert into creditstatus(userid, creditbalance, creditstatus) values (?,?,?);";

                    try {
                        PreparedStatement preparedStatementCreditStatus = dbConnector.getDbConnection().prepareStatement(insertCreditStatus);

                        preparedStatementCreditStatus.setInt(1, id);
                        preparedStatementCreditStatus.setFloat(2, 0);
                        preparedStatementCreditStatus.setBoolean(3, true);
                        preparedStatementCreditStatus.executeUpdate();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    //inserting to table account status
                    String insertAccountStatus = "insert into accountstatus(userid, currentbalance) values (?,?);";
                    try {
                        PreparedStatement preparedStatementAccountStatus = dbConnector.getDbConnection().prepareStatement(insertAccountStatus);
                        preparedStatementAccountStatus.setInt(1, id);
                        preparedStatementAccountStatus.setFloat(2, 0);
                        preparedStatementAccountStatus.executeUpdate();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    resultSet.close();
                    return true;

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Account getAccountInfo(String login) {
        try {
            String password = null;
            String accountNumber = null;
            float currentBalance = 0;
            String ownerName = null;
            String ownerSurname = null;
            List<OperationHistoryUnit> operationList = new ArrayList<>();
            float creditBalance = 0;
            boolean creditStatus = false;

            String getInfoFromUsers = "select accountnumber, username, usersurname, passwords from users where login = '" + login + "';";
            PreparedStatement preparedStatementInfoUsers = dbConnector.getDbConnection().prepareStatement(getInfoFromUsers);
            ResultSet rsInfoUsers = preparedStatementInfoUsers.executeQuery();

            if (rsInfoUsers.next()) {
                accountNumber = rsInfoUsers.getString(1);
                ownerName = rsInfoUsers.getString(2);
                ownerSurname = rsInfoUsers.getString(3);
                password = rsInfoUsers.getString(4);
            }

            String getInfoFromAccountStatus = "select currentbalance from accountstatus where userid = (select id from users where login = '" + login + "');";
            PreparedStatement preparedStatementInfoAccountStatus = dbConnector.getDbConnection().prepareStatement(getInfoFromAccountStatus);
            ResultSet rsInfoAccountStatus = preparedStatementInfoAccountStatus.executeQuery();
            if (rsInfoAccountStatus.next()) {
                currentBalance = rsInfoAccountStatus.getFloat(1);
            }
            rsInfoAccountStatus.close();

            String getInfoFromCreditStatus = "select creditbalance, creditstatus from creditstatus where userid = (select id from users where login = '" + login + "');";
            PreparedStatement preparedStatementInfoCreditStatus = dbConnector.getDbConnection().prepareStatement(getInfoFromCreditStatus);
            ResultSet rsInfoCreditStatus = preparedStatementInfoCreditStatus.executeQuery();

            if (rsInfoCreditStatus.next()) {
                creditBalance = rsInfoCreditStatus.getFloat(1);
                creditStatus = rsInfoCreditStatus.getBoolean(2);
            }

            String getInfoFromOperationList = "select operations.operationtype, operationlist.operationvalue from operationlist, operations where userid = (select id from users where login = '" + login + "') and operationlist.operationid = operations.id;";
            PreparedStatement preparedStatementInfoOperationList = dbConnector.getDbConnection().prepareStatement(getInfoFromOperationList);
            ResultSet rsInfoOperationList = preparedStatementInfoOperationList.executeQuery();

            if (rsInfoOperationList.next()) {
                while (rsInfoOperationList.next()) {
                    OperationHistoryUnit unit = new OperationHistoryUnit(rsInfoOperationList.getString("operationtype"), rsInfoOperationList.getFloat("operationvalue"));
                    operationList.add(unit);
                }
            }
            rsInfoAccountStatus.close();
            rsInfoCreditStatus.close();
            rsInfoOperationList.close();
            rsInfoUsers.close();

            return new Account(login, password, accountNumber, currentBalance, ownerName, ownerSurname, creditBalance, operationList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean makePaymentIn(String value, String login) {

        try {
            String payIn = "update accountstatus set currentbalance = currentbalance + '" + value + "' where userid = (select id from users where login = '" + login + "');";
            PreparedStatement preparedStatementPaymentIn = dbConnector.getDbConnection().prepareStatement(payIn);
            preparedStatementPaymentIn.executeUpdate();
            return writeToHistory(login, value, 1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean makePaymentOut(String value, String login) {
        try {
            String payOut = "update accountstatus set currentbalance = currentbalance - " + value + " where userid = (select id from users where login = '" + login + "');";
            PreparedStatement preparedStatementPaymentOut = dbConnector.getDbConnection().prepareStatement(payOut);
            preparedStatementPaymentOut.executeUpdate();
            return writeToHistory(login, value, 2);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean makeTransfer(String accountNumber, String login, String value) {
        try {
            String transfer = "update accountstatus set currentbalance = currentbalance - " + value + " where userid = (select id from users where login = '" + login + "');";
            String transferReceiver = "update accountstatus set currentbalance = currentbalance + " + value + " where userid = (select id from users where accountnumber = '" + accountNumber + "');";
            PreparedStatement preparedStatementTransfer = dbConnector.getDbConnection().prepareStatement(transfer);
            preparedStatementTransfer.executeUpdate();
            PreparedStatement preparedStatementTransferReceiver = dbConnector.getDbConnection().prepareStatement(transferReceiver);
            preparedStatementTransferReceiver.executeUpdate();
            return writeToHistory(login, value, 4);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean makeCredit(String value, String login, boolean newCreditValue) {
        try {
            String creditingCreditStatus = "update creditstatus set creditbalance = creditbalance + " + value + ", creditstatus = " + newCreditValue + " where userid = (select id from users where login = '" + login + "');";
            String creditingAccountStatus = "update accountstatus set currentbalance = currentbalance + " + value + " where userid = (select id from users where login = '" + login + "');";
            PreparedStatement preparedStatementCreditStatus = dbConnector.getDbConnection().prepareStatement(creditingCreditStatus);
            preparedStatementCreditStatus.executeUpdate();
            PreparedStatement preparedStatementAccountStatus = dbConnector.getDbConnection().prepareStatement(creditingAccountStatus);
            preparedStatementAccountStatus.executeUpdate();
            return writeToHistory(login, value, 3);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }


    }
    public boolean deleteAccount(String login) {
        try {
            String deleteAccount = "delete from users where login = '"  + login + "';";
            PreparedStatement preparedStatementDeleteAccount = dbConnector.getDbConnection().prepareStatement(deleteAccount);
            preparedStatementDeleteAccount.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean writeToHistory(String login, String value, int operationType){
        try {

            String insertOperationInfo = "insert into operationlist (userid , operationid , operationvalue) values ((select id from users where login = '"+login+"'), "+operationType + ", "+value+");";
            PreparedStatement preparedStatementDeleteAccount = dbConnector.getDbConnection().prepareStatement(insertOperationInfo);
            preparedStatementDeleteAccount.executeUpdate();

            return true;
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
