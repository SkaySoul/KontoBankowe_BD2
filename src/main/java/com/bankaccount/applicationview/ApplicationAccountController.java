package com.bankaccount.applicationview;

import com.bankaccount.applicationsource.Account;
import com.bankaccount.applicationsource.Config;
import com.bankaccount.applicationsource.OperationHistoryUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class ApplicationAccountController extends ApplicationRootController {
    @FXML
    private Button deleteAccountButton;

    @FXML
    private Text operationText;

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField creditBalanceField;

    @FXML
    private Button creditButton;

    @FXML
    private TextField creditStatusField;

    @FXML
    private TextField currentBalanceField;

    @FXML
    private TextField nameField;

    @FXML
    private Button operationListButton;

    @FXML
    private Button payinButton;

    @FXML
    private Button payoutButton;

    @FXML
    private TextField surnameField;

    @FXML
    private Button transferButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Button logoutButton;

    public ApplicationAccountController() throws SQLException, ClassNotFoundException {
    }

    private String login;

    @FXML
    void initialize() {
        logoutButton.setOnAction(event -> {
            openNewScene(event, this, "application-login-screen.fxml");
        });

        payinButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
            dialog.setTitle("Payment transaction");
            dialog.setHeaderText("Please enter value of transaction");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (NumberUtils.isCreatable(result.get())) {
                    if (Integer.parseInt(result.get()) <= 0) {
                        createAlertBox("error", "You cant insert values lower than 0");
                    } else {
                        if (initializeController().makePaymentIn(result.get(), login)) {
                            createAlertBox("Information", "Transaction was successful!");
                            accountInitialize(login);
                        } else createAlertBox("Error", "Something went wrong, please try again...");
                    }
                } else createAlertBox("error", "You cant insert not-numbers value");
            } else createAlertBox("error", "You need to insert some value");
        });


        payoutButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
            dialog.setTitle("Payment transaction");
            dialog.setHeaderText("Please enter value of transaction");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (NumberUtils.isCreatable(result.get())) {
                    if (Integer.parseInt(result.get()) <= 0) {
                        createAlertBox("error", "You cant insert values lower than 0");
                    } else {
                        if (Float.parseFloat(result.get()) - Float.parseFloat(currentBalanceField.getText().replaceAll("[^0-9?!\\\\.]", "")) < 0) {
                            if (initializeController().makePaymentOut(result.get(), login)) {
                                createAlertBox("Information", "Transaction was successful!");
                                accountInitialize(login);
                            } else createAlertBox("Error", "Something went wrong, please try again...");
                        } else createAlertBox("Error", "You cant payout more money than you have in account");
                    }
                } else createAlertBox("error", "You cant insert not-numbers value");
            } else createAlertBox("error", "You need to insert some value");
        });

        transferButton.setOnAction(event -> {
            String value = null;
            TextInputDialog dialog = new TextInputDialog();
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
            dialog.setTitle("Transfer transaction");
            dialog.setHeaderText("Please enter value of transaction");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (NumberUtils.isCreatable(result.get())) {
                    if (Integer.parseInt(result.get()) <= 0) {
                        createAlertBox("error", "You cant insert values lower than 0");
                    } else {
                        if (Float.parseFloat(result.get()) - Float.parseFloat(currentBalanceField.getText().replaceAll("[^0-9?!\\\\.]", "")) < 0) {
                            value = result.get();
                        } else createAlertBox("Error", "You cant transfer more money than you have in account");
                    }
                } else createAlertBox("error", "You cant insert not-numbers value");
            } else createAlertBox("error", "You need to insert some value");

            TextInputDialog dialog2 = new TextInputDialog();
            Stage stage2 = (Stage) dialog2.getDialogPane().getScene().getWindow();
            stage2.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
            dialog2.setTitle("Transfer transaction");
            dialog2.setHeaderText("Please enter account number of receiver");
            Optional<String> resultaccnum = dialog2.showAndWait();
            if (resultaccnum.isPresent()) {
                if (NumberUtils.isCreatable(resultaccnum.get())) {
                    if (Integer.parseInt(resultaccnum.get()) < 10000000 && Integer.parseInt(resultaccnum.get()) > 99999999 && resultaccnum.get().equals(accountNumberField.getText())) {
                        createAlertBox("error", "Please insert correct account number");
                    } else {
                        if (initializeController().makeTransfer(resultaccnum.get(), login, value)) {
                            createAlertBox("Information", "Transaction was successful!");
                            accountInitialize(login);
                        } else createAlertBox("Error", "Something went wrong, please try again...");

                    }
                } else createAlertBox("error", "You cant insert not-numbers value");
            } else createAlertBox("error", "You need to insert some value");

        });


        creditButton.setOnAction(event ->{
            TextInputDialog dialog = new TextInputDialog();
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
                dialog.setTitle("Credit operation");
                dialog.setHeaderText("Please enter value of operation");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    if (NumberUtils.isCreatable(result.get())) {
                        if (Integer.parseInt(result.get()) <= 0) {
                            createAlertBox("error", "You cant insert values lower than 0");
                        } else {
                            if (Float.parseFloat(result.get()) + Float.parseFloat(creditBalanceField.getText().replaceAll("[^0-9?!\\\\.]", "")) <= Config.maxCreditValue) {
                                boolean newCreditStatus = Float.parseFloat(result.get()) + Float.parseFloat(creditBalanceField.getText().replaceAll("[^0-9?!\\\\.]", "")) != Config.maxCreditValue;
                                if (initializeController().makeCredit(result.get(), login, newCreditStatus)) {
                                    createAlertBox("Information", "Operation was successful!");
                                    accountInitialize(login);
                                } else createAlertBox("Error", "Something went wrong, please try again...");
                            } else createAlertBox("Error", "You cant take a credit on value, bigger than you credit status. " + creditStatusField.getText());
                        }
                    } else createAlertBox("error", "You cant insert not-numbers value");
                } else createAlertBox("error", "You need to insert some value");
            });

            deleteAccountButton.setOnAction(event->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    if(initializeController().deleteAccount(login)){
                        openNewScene(event,this,"application-login-screen.fxml");
                        createAlertBox("Information", "Your account successfully deleted!");
                    }else createAlertBox("error", "Something went wrong, please try again");

                }

            });


            operationListButton.setOnAction(event->{
                Account account = initializeController().getAccountInfo(login);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Objects.requireNonNull(getClass().getResource("application-operations.fxml")));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ApplicationOperationsController controller = loader.getController();
                controller.setAccount(account);
                controller.initData();

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));

                stage.show();


            });

    }






    public void accountInitialize(String login) {
        Account account = initializeController().getAccountInfo(login);
        accountNumberField.setText("Account number: " + account.getAccountNumber());
        currentBalanceField.setText("Current balance: " + account.getCurrentBalance());
        nameField.setText("Name: " + account.getOwnerName());
        surnameField.setText("Surname: " + account.getOwnerSurname());
        usernameField.setText("Login: " + account.getLogin());
        creditBalanceField.setText("Credit balance: " + account.getCreditBalance());

        if (account.isCreditStatus()) {
            creditStatusField.setText("Credit available for next value: " + (Config.maxCreditValue - account.getCreditBalance()));
        } else creditStatusField.setText("Credit is unavailable, please pay previous credits...");
    }

    public void setLogin(String login) {
        this.login = login;
    }

}