package org.saxion.devuurtoren.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginScreenController {

    private int counter = 0;

    @FXML
    private Label counterText;

    @FXML
    protected void onButtonClick() {
        counterText.setText("Counter: %d".formatted(++counter));
    }
}
