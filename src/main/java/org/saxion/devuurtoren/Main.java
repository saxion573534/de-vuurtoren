package org.saxion.devuurtoren;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.saxion.devuurtoren.controllers.TeamsScreenController;
import org.saxion.devuurtoren.util.WindowHelper;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image("logo.png"));
        stage.setResizable(false);
        WindowHelper.openWindow("login-screen.fxml", "De Vuurtoren", 600, 400, stage);
    }

    public static void main(String[] args) {
        launch();
    }
}