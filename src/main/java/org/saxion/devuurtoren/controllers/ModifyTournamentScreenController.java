package org.saxion.devuurtoren.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.saxion.devuurtoren.Main;
import org.saxion.devuurtoren.util.Utils;
import org.saxion.devuurtoren.util.WindowHelper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyTournamentScreenController {

    private static final String CSV_FILE = "tournaments_data.csv";
    private static int maxTeams = -1;
    private static int teamCount = 0;

    private void addTeam(String schoolName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tournament-team-template.fxml"));
            Node teamNode = fxmlLoader.load();

            Label nameLabel = (Label) teamNode.lookup("#nameLabel");
            if (nameLabel != null) {
                nameLabel.setText(schoolName);
            }
            teamCount++;
            Button deleteButton = (Button) teamNode.lookup("#deleteTeamButton");
            if (deleteButton != null) {
                deleteButton.setOnAction(event -> {
//                    removeTeamFromTournament(schoolName);
                    choiceBox.getItems().add(schoolName);
                    teamCount--;
                    teamsContainer.getChildren().removeIf(node -> {
                        Label nameLabel2 = (Label) node.lookup("#nameLabel");
                        return nameLabel2 != null && nameLabel2.getText().equals(schoolName);
                    });
                    WindowHelper.showAlert("Team verwijderd uit toernooi.");
                });
            }

            teamsContainer.getChildren().add(teamNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    Label teamsLabel;

    @FXML
    Label tournamentNameLabel;

    @FXML
    Label locationLabel;

    @FXML
    Label fieldsLabel;

    @FXML
    Label dateLabel;

    @FXML Label sportLabel;

    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    VBox teamsContainer;

    @FXML
    protected void onBackButtonClick() {
        Stage currentStage = (Stage) teamsLabel.getScene().getWindow();
        WindowHelper.openWindow("tournaments-screen.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    @FXML
    protected void onAddTeamButtonClick() {
        if (choiceBox.getValue() == null) {
            WindowHelper.showAlert("U heeft geen team geselecteerd!", Alert.AlertType.ERROR);
            return;
        }
        String team = choiceBox.getValue();
        String[] players = Utils.getPlayersInTeam(team);
        int teamSize = players.length;

        if (teamSize < 4) {
            WindowHelper.showAlert("Team '" + team + "' heeft niet genoeg spelers! (" + teamSize + "/5).", Alert.AlertType.ERROR);
            return;
        }

        if (teamCount >= maxTeams) {
            WindowHelper.showAlert("Maximaal aantal teams in het toernooi!", Alert.AlertType.ERROR);

            return;
        }

        addTeam(team);
//        addTeamToTournament(team);
        choiceBox.setValue(null);
        choiceBox.getItems().remove(team);
        WindowHelper.showAlert("Team toegevoegd aan toernooi.");
    }

    @FXML
    public void initialize(String name, String location, int numberOfFields, String formattedDate, String sport) {
        tournamentNameLabel.setText(name);
        locationLabel.setText(location);
        fieldsLabel.setText("Velden: " + numberOfFields);
        dateLabel.setText(formattedDate);
        sportLabel.setText(sport.toUpperCase());

        maxTeams = numberOfFields;
        teamCount = 0;
        String[] teams = Utils.getTeams();
        for (int i = 0; i < teams.length; i++) {
            if (teamCount >= maxTeams) {
                choiceBox.getItems().add(teams[i]);
            } else {
                addTeam(teams[i]);
            }
        }
    }
}
