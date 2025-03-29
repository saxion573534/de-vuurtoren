package org.saxion.devuurtoren.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.saxion.devuurtoren.util.WindowHelper;

public class MainMenuController {

    @FXML
    Label optionsLabel;

    @FXML
    protected void onLogoutButtonClick() {
        Stage currentStage = (Stage) optionsLabel.getScene().getWindow();
        WindowHelper.openWindow("login-screen.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    @FXML
    protected void onTeamsButtonClick() {
        Stage currentStage = (Stage) optionsLabel.getScene().getWindow();
        WindowHelper.openWindow("teams-screen.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    @FXML
    protected void onTournamentsButtonClick() {
        System.out.println("Open Tournaments Tab");
    }

    @FXML
    protected void onStandsButtonClick() {
        System.out.println("Open nieuwe scherm voor standen op een andere beeld.");
    }

}
