package com.bankaccount.applicationview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ApplicationLoginController extends ApplicationRootController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;


    @FXML
    void initialize() {


        loginButton.setOnAction(event -> {

            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();

            if (!loginText.equals("") || !passwordText.equals("")) {

                try {
                    if (!initializeController().findAccount(loginText, passwordText)) {
                        createAlertBox("Error", "Login or password is incorrect, or login is does not exist!");
                    } else {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Objects.requireNonNull(getClass().getResource("application-account.fxml")));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));

                        //initializing account values
                        ApplicationAccountController controller = loader.getController();
                        controller.setLogin(loginText);
                        controller.accountInitialize(loginText);

                        stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
                        stage.show();
                        Node node = (Node) event.getSource();
                        Stage prevStage = (Stage) node.getScene().getWindow();
                        prevStage.close();
                    }

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else createAlertBox("Error", "Login or password field cant be empty!");
        });


        registerButton.setOnAction(event -> {
            registerButton.getScene().getWindow().hide();
            openNewScene(event, this, "application-register-screen.fxml");
        });


    }

}