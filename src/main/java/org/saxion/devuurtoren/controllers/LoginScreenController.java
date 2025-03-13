package org.saxion.devuurtoren.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.saxion.devuurtoren.util.WindowHelper;

public class LoginScreenController {

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorText;
    @FXML
    protected void onButtonClick() {

        String user = userTextField.getText();
        String password = passwordTextField.getText();

        if (password.equalsIgnoreCase("vuurtoren@administrator") && user.equalsIgnoreCase("@org.vt")) {
            errorText.setVisible(false);

            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            WindowHelper.openWindow("main-menu.fxml", "Toernooi Manager", 600, 400, currentStage);
        } else {
            errorText.setVisible(true);
            passwordTextField.setText("");
        }
    }
}
