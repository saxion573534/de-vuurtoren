package org.saxion.devuurtoren.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.saxion.devuurtoren.Main;
import org.saxion.devuurtoren.util.WindowHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModifyTeamScreenController {

    private void addPlayer(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("player-template.fxml"));
            Node teamNode = fxmlLoader.load();

            Label nameLabel = (Label) teamNode.lookup("#playerNameLabel");
            if (nameLabel != null) {
                nameLabel.setText(name);
            }

            Button deleteButton = (Button) teamNode.lookup("#deletePlayerButton");
            if (deleteButton != null) {
                deleteButton.setOnAction(event -> {
//                    removeTeam(schoolName, schoolAddress);
                });
            }

            playersContainer.getChildren().add(teamNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    VBox playersContainer;

    @FXML
    Label spelersLabel;

    @FXML
    TextField schoolNameTextField;

    @FXML
    TextField schoolAddressTextField;

    private String oldSchoolName;
    private String oldSchoolAddress;

    @FXML
    protected void onBackButtonClick() {
        Stage currentStage = (Stage) spelersLabel.getScene().getWindow();
        WindowHelper.openWindow("teams-screen.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    @FXML
    protected void onSaveButtonClick() {
        String schoolName = schoolNameTextField.getText().trim();
        String schoolAddress = schoolAddressTextField.getText().trim();

        if (schoolName.isEmpty() || schoolAddress.isEmpty()) {
            WindowHelper.showAlert("Vul zowel de naam als het adres van de school in.", Alert.AlertType.ERROR);
            return;
        }

        schoolNameTextField.setText(schoolName);
        schoolAddressTextField.setText(schoolAddress);

        updateCSVFile(schoolName, schoolAddress);
        oldSchoolName = schoolName;
        oldSchoolAddress = schoolAddress;
    }

    public void initialize(String schoolName, String schoolAddress) {
        schoolNameTextField.setText(schoolName);
        schoolAddressTextField.setText(schoolAddress);
        this.oldSchoolName = schoolName;
        this.oldSchoolAddress = schoolAddress;

        generatePlayers();
    }

    private void generatePlayers() {
        String filePath = "all_players_data.csv";
        File file = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 3) {
                    String playerName = parts[0] + " " + parts[1];
                    String teamName = parts[2];

                    if (teamName.equals(oldSchoolName)) {
                        addPlayer(playerName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCSVFile(String newSchoolName, String newSchoolAddress) {

        String filePath = "teams_data.csv";
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    if (parts[0].equals(oldSchoolName) && parts[1].equals(oldSchoolAddress)) {
                        lines.add(newSchoolName + ";" + newSchoolAddress);
                    } else {
                        lines.add(line);
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                WindowHelper.showAlert("Nieuwe Team data is opgeslagen!");
            }
        } catch (IOException e) {
            System.err.println("Error updating the CSV file: " + e.getMessage());
        }
    }
}
