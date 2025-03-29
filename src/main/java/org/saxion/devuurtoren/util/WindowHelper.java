package org.saxion.devuurtoren.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import org.saxion.devuurtoren.Main;

public class WindowHelper {
    /***
     * Open a new window with the given fxml file
     * @param fxmlResource The fxml filename without path
     * @param title the title of the window
     * @param width the width of the window
     * @param height the height of the window
     */
    public static void openWindow(String fxmlResource, String title, int width, int height) {
        openWindow(fxmlResource, title, width, height, new Stage());
    }

    /***
     * Open a new window with the given fxml file
     * @param fxmlResource The fxml filename without path
     * @param title the title of the window
     * @param width the width of the window
     * @param height the height of the window
     * @param stage (optional) stage if you already have one
     */
    public static void openWindow(String fxmlResource, String title, int width, int height, Stage stage) {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlResource));
        try {

            var scene = new Scene(fxmlLoader.load(), width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            showAlert("Error opening window for " + fxmlResource + ".\n\n" + e, Alert.AlertType.ERROR);
        }
    }

    public static <T> T openWindow(String fxmlResource, String title, int width, int height, Stage stage, boolean shouldReturn) {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlResource));
        try {

            var scene = new Scene(fxmlLoader.load(), width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            showAlert("Error opening window for " + fxmlResource + ".\n\n" + e, Alert.AlertType.ERROR);
        }
        return fxmlLoader.getController();
    }

    /***
     * close any window
     * @param randomControlOnWindow One of the controls on the window. May be any control (button, label, ...)
     */
    public static void closeWindow(Control randomControlOnWindow) {
        Stage stage = (Stage) randomControlOnWindow.getScene().getWindow();
        stage.close();
    }

    /***
     * Show a popup dialog with a message
     * @param message the message to show
     */
    public static void showAlert(String message) {
        showAlert(message, Alert.AlertType.INFORMATION);
    }

    public static void showAlert(String message, Alert.AlertType type) {
        new Alert(type, message).show();
    }
}
