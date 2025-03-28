package org.saxion.devuurtoren.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.MotionBlur;
import javafx.util.Duration;

public class MainMenuController {

    @FXML
    public void onTeamsButtonClick() {
        System.out.println("Open Teams Tab");
    }

    @FXML
    public void onTournamentsButtonClick() {
        System.out.println("Open Tournaments Tab");
    }

    @FXML
    public void onStandsButtonClick() {
        System.out.println("Open nieuwe scherm voor standen op een andere beeld.");
    }

}
