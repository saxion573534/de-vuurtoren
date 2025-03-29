package org.saxion.devuurtoren.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

        if (login(user, password)) {
            errorText.setVisible(false);

            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            WindowHelper.openWindow("main-menu.fxml", "Toernooi Manager", 600, 400, currentStage);
        } else {

            // TODO: Remove when done testing...
            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            WindowHelper.openWindow("main-menu.fxml", "Toernooi Manager", 600, 400, currentStage);
            // TODO: Remove when done testing...

            errorText.setVisible(true);
            passwordTextField.setText("");
        }
    }

    protected boolean login(String user, String password) {
        return user.equals("@org.vt") && password.equals("vuurtoren@administrator");
    }
}
