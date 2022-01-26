package com.bankaccount.applicationview;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.sql.SQLException;

public class ApplicationRegisterController extends ApplicationRootController {


    @FXML
    private TextField login;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField password;

    @FXML
    private TextField surnameField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;


    @FXML
    void initialize() {
        registerButton.setOnAction(event -> {

            String loginText = login.getText().trim();
            String passwordText = password.getText().trim();
            String nameText = nameField.getText().trim();
            String surnameText = surnameField.getText().trim();

            if (!loginText.equals("") || !passwordText.equals("") || !nameText.equals("") || !surnameText.equals("")) {
                if (!NumberUtils.isCreatable(nameText) || !NumberUtils.isCreatable(surnameText)) {
                    if (nameText.length() < 20 || surnameText.length() < 20) {
                        if (loginText.length() > 6 || passwordText.length() > 8) {
                            try {
                                if (initializeController().addNewUser(nameText, surnameText, loginText, passwordText)) {
                                    openNewScene(event, this, "application-login-screen.fxml");
                                    createAlertBox("information", "You successfully registered in system!");
                                } else createAlertBox("error", "This login already in use, please try again...");
                            } catch (SQLException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        } else createAlertBox("Error", "Cant create login lower than 6 symbols and password lower than 8 symbols");
                    }else createAlertBox("Error", "Name and surname cant be bigger than 20 symbols");
                }else createAlertBox("Error", "Incorrect name or surname, please retry");
            } else createAlertBox("Error", "Some field is empty, please retry");

        });




        backButton.setOnAction(event -> {
            openNewScene(event, this, "application-login-screen.fxml");
        });
    }


}