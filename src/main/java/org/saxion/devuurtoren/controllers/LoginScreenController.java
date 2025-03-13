package org.saxion.devuurtoren.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.MotionBlur;
import javafx.util.Duration;

public class LoginScreenController {

    private int counter = 0;

    @FXML
    private Label counterText;

    @FXML
    protected void onButtonClick() {

        MotionBlur motionBlur = new MotionBlur(90, 0);
        counterText.setEffect(motionBlur);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(90), new KeyValue(motionBlur.radiusProperty(), 20)),
                new KeyFrame(Duration.millis(190), new KeyValue(motionBlur.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(140), event -> {
                    counterText.setText("Counter: %d".formatted(++counter));
                })
        );
        timeline.setCycleCount(1);
        timeline.play();

    }
}
