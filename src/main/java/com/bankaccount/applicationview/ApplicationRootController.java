package com.bankaccount.applicationview;

import com.bankaccount.applicationsource.ModelController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;


public class ApplicationRootController {


    public ModelController initializeController() {
        return new ModelController();
    }

    public void openNewScene(Event event, Object o, String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(o.getClass().getResource(path)));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
        stage.setTitle("Bank Application");
        stage.show();
        Node node = (Node) event.getSource();
        Stage prevStage = (Stage) node.getScene().getWindow();
        prevStage.close();
    }


    public void createAlertBox(String type, String text) {
        String typeof = type.toUpperCase(Locale.ENGLISH);
        Alert alert = new Alert(Alert.AlertType.valueOf(typeof));
        alert.setContentText(text);
        Button closeButton = new Button();
        closeButton.setOnAction(actionEvent -> {
            alert.close();
        });
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
        alert.show();
    }

}

