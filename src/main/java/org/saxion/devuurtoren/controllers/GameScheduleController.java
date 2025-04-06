package org.saxion.devuurtoren.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.saxion.devuurtoren.util.WindowHelper;

public class GameScheduleController {

    @FXML
    private TextField teamAvsB;
    @FXML
    private TextField teamAvsC;
    @FXML
    private TextField teamAvsD;
    @FXML
    private TextField teamBvsA;
    @FXML
    private TextField teamBvsC;
    @FXML
    private TextField teamBvsD;
    @FXML
    private TextField teamCvsA;
    @FXML
    private TextField teamCvsB;
    @FXML
    private TextField teamCvsD;
    @FXML
    private TextField teamDvsA;
    @FXML
    private TextField teamDvsB;
    @FXML
    private TextField teamDvsC;

    @FXML
    private TextField teamAvsB2;
    @FXML
    private TextField teamBvsA2;
    @FXML
    private TextField teamAvsC2;
    @FXML
    private TextField teamCvsA2;
    @FXML
    private TextField teamAvsD2;
    @FXML
    private TextField teamDvsA2;
    @FXML
    private TextField teamBvsC2;
    @FXML
    private TextField teamCvsB2;
    @FXML
    private TextField teamDvsB2;
    @FXML
    private TextField teamBvsD2;
    @FXML
    private TextField teamCvsD2;
    @FXML
    private TextField teamDvsC2;

    @FXML
    Label title;

    @FXML
    protected void onBackButtonClick() {
        Stage currentStage = (Stage) title.getScene().getWindow();
        WindowHelper.openWindow("tournaments-screen.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    @FXML
    protected void onSaveButtonClick() {
        WindowHelper.showAlert("Toernooi Poule data opgeslagen.");
    }

    @FXML
    protected void initialize(){
        setupNumericValidation(teamAvsB);
        setupNumericValidation(teamAvsC);
        setupNumericValidation(teamAvsD);
        setupNumericValidation(teamBvsA);
        setupNumericValidation(teamBvsC);
        setupNumericValidation(teamBvsD);
        setupNumericValidation(teamCvsA);
        setupNumericValidation(teamCvsB);
        setupNumericValidation(teamCvsD);
        setupNumericValidation(teamDvsA);
        setupNumericValidation(teamDvsB);
        setupNumericValidation(teamDvsC);

        syncTextFields();
    }

    private void setupNumericValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(oldValue);
                WindowHelper.showAlert("Je mag alleen nummers invoeren!", Alert.AlertType.ERROR);
            }
        });
    }

    private void syncTextFields() {
//        syncField(teamAvsB, teamAvsB2);

        syncField(teamAvsB, teamAvsB2);
        syncField(teamAvsC, teamAvsC2);
        syncField(teamAvsD, teamAvsD2);

        syncField(teamBvsA, teamBvsA2);
        syncField(teamBvsC, teamBvsC2);
        syncField(teamBvsD, teamBvsD2);

        syncField(teamCvsA, teamCvsA2);
        syncField(teamCvsB, teamCvsB2);
        syncField(teamCvsD, teamCvsD2);

        syncField(teamDvsA, teamDvsA2);
        syncField(teamDvsB, teamDvsB2);
        syncField(teamDvsC, teamDvsC2);

        syncField(teamAvsB2, teamAvsB);
        syncField(teamAvsC2, teamAvsC);
        syncField(teamAvsD2, teamAvsD);

        syncField(teamBvsA2, teamBvsA);
        syncField(teamBvsC2, teamBvsC);
        syncField(teamBvsD2, teamBvsD);

        syncField(teamCvsA2, teamCvsA);
        syncField(teamCvsB2, teamCvsB);
        syncField(teamCvsD2, teamCvsD);

        syncField(teamDvsA2, teamDvsA);
        syncField(teamDvsB2, teamDvsB);
        syncField(teamDvsC2, teamDvsC);
    }

    private void syncField(TextField source, TextField target) {
        source.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                target.setText(newValue);
            }
        });
    }
}
