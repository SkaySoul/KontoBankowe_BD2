package com.bankaccount.applicationview;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
                try {
                    if (initializeController().addNewUser(nameText, surnameText, loginText, passwordText)) {
                        openNewScene(event, this, "application-login-screen.fxml");
                        createAlertBox("information", "You successfully registered in system!");
                    } else createAlertBox("error", "This login already in use, please try again...");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                createAlertBox("Error", "Some field is empty, please retry");
            }
        });




        backButton.setOnAction(event -> {
            openNewScene(event, this, "application-login-screen.fxml");
        });
    }


}