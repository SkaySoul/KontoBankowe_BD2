package com.bankaccount.applicationview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ApplicationMain extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Group group = new Group();
        Scene scene = new Scene(group, 600, 400);

        Parent content = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("application-login-screen.fxml")));
        BorderPane root = new BorderPane();
        root.setCenter(content);
        group.getChildren().add(root);

        primaryStage.setTitle("Bank Application");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("https://icons.iconarchive.com/icons/dooffy/characters/256/And-icon.png"));
        primaryStage.show();


    }
}
