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

    private String teamName;

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
                    String fullName = name.trim();
                    String[] nameParts = fullName.split(" ", 2);

                    String firstName = nameParts[0];
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";

                    removePlayer(firstName, lastName);
                });
            }

            playersContainer.getChildren().add(teamNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removePlayer(String firstName, String lastName) {
        for (Node playerNode : playersContainer.getChildren()) {
            Label nameLabel = (Label) playerNode.lookup("#playerNameLabel");
            if (nameLabel != null && nameLabel.getText().equals(firstName + " " + lastName)) {
                playersContainer.getChildren().remove(playerNode);
                break;
            }
        }

        String filePath = "all_players_data.csv";
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3 && parts[0].equals(firstName) && parts[1].equals(lastName) && parts[2].equals(oldSchoolName)) {
                    continue;
                }
                lines.add(line);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < lines.size(); i++) {
                    writer.write(lines.get(i));
                    if (i < lines.size() - 1) {
                        writer.newLine();
                    }
                }
                WindowHelper.showAlert("Speler '" + firstName + " " + lastName + "' is uit de team gehaald!", Alert.AlertType.INFORMATION);
            }
        } catch (IOException e) {
            System.err.println("Error removing player from CSV: " + e.getMessage());
        }
    }

    @FXML
    TextField playerFirstNameTextField;

    @FXML
    TextField playerLastNameTextField;


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
        updatePlayersTeamName(teamName, schoolName);
        updateCSVFile(schoolName, schoolAddress);
        oldSchoolName = schoolName;
        oldSchoolAddress = schoolAddress;
        this.teamName = schoolName;
    }

    @FXML
    protected void onAddPlayerButtonClick() {
        String firstName = playerFirstNameTextField.getText().trim();
        String lastName = playerLastNameTextField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            WindowHelper.showAlert("Vul zowel de naam als de achternaam van het teamlid in.", Alert.AlertType.ERROR);
            return;
        }

        addPlayer(firstName + " " + lastName);
        addPlayerToCSV(firstName, lastName);
        WindowHelper.showAlert("Speler '" + firstName + " " + lastName + "' is toevoegd aan de team!", Alert.AlertType.INFORMATION);

        playerFirstNameTextField.setText("");
        playerLastNameTextField.setText("");
    }

    public void initialize(String schoolName, String schoolAddress) {
        schoolNameTextField.setText(schoolName);
        schoolAddressTextField.setText(schoolAddress);
        this.oldSchoolName = schoolName;
        this.oldSchoolAddress = schoolAddress;
        this.teamName = schoolName;
        generatePlayers();
    }

    private void addPlayerToCSV(String firstName, String lastName) {
        String filePath = "all_players_data.csv";
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (file.length() > 0) {
                writer.newLine();
            }
            writer.write(firstName + ";" + lastName + ";" + teamName);
        } catch (IOException e) {
            System.err.println("Error adding player to CSV: " + e.getMessage());
        }
    }

    private void updatePlayersTeamName(String oldTeamName, String newTeamName) {
        String filePath = "all_players_data.csv";
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 3 && parts[2].equals(oldTeamName)) {
                    // Update the team name for the player
                    parts[2] = newTeamName;
                    line = String.join(";", parts);
                }

                lines.add(line);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < lines.size(); i++) {
                    writer.write(lines.get(i));
                    if (i < lines.size() - 1) {
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating player team names in CSV: " + e.getMessage());
        }
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
