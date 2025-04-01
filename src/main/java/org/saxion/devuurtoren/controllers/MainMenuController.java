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
        Stage currentStage = (Stage) optionsLabel.getScene().getWindow();
        WindowHelper.openWindow("tournaments-screen.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    private static Stage standsStage = null;

    @FXML
    protected void onStandsButtonClick() {
        if (standsStage == null || !standsStage.isShowing()) {
            standsStage = WindowHelper.openWindow("stands-screen.fxml", "De Vuurtoren", 800, 600, true);
        }
    }

}
