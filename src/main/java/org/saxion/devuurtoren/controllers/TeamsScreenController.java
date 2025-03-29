package org.saxion.devuurtoren.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.saxion.devuurtoren.Main;
import org.saxion.devuurtoren.util.WindowHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamsScreenController {

    private void addTeam(String schoolName, String schoolAddress) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("team-template.fxml"));
            Node teamNode = fxmlLoader.load();

            Label nameLabel = (Label) teamNode.lookup("#nameLabel");
            if (nameLabel != null) {
                nameLabel.setText(schoolName);
            }

            Label addressLabel = (Label) teamNode.lookup("#addressLabel");
            if (addressLabel != null) {
                addressLabel.setText(schoolAddress);
            }

            Button deleteButton = (Button) teamNode.lookup("#deleteTeamButton");
            if (deleteButton != null) {
                deleteButton.setOnAction(event -> {
                    removeTeam(schoolName, schoolAddress);
                });
            }

            Button modifyButton = (Button) teamNode.lookup("#modifyTeamButton");
            if (modifyButton != null) {
                modifyButton.setOnAction(event -> {
                    Stage stage = (Stage) teamsLabel.getScene().getWindow();
                    ModifyTeamScreenController modifyTeamScreenController = WindowHelper.openWindow("modify-team-screen.fxml", "De Vuurtoren", 600, 400, stage, true);
                    modifyTeamScreenController.initialize(schoolName, schoolAddress);
                });
            }

            teamsContainer.getChildren().add(teamNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeTeam(String schoolName, String schoolAddress) {
        for (Node teamNode : teamsContainer.getChildren()) {
            Label nameLabel = (Label) teamNode.lookup("#nameLabel");
            Label addressLabel = (Label) teamNode.lookup("#addressLabel");

            if (nameLabel != null && addressLabel != null) {
                if (nameLabel.getText().equals(schoolName) && addressLabel.getText().equals(schoolAddress)) {
                    teamsContainer.getChildren().remove(teamNode);
                    WindowHelper.showAlert("Team '" + schoolName + "' is verwijderd.");
                    break;
                }
            }
        }
        String filePath = "teams_data.csv";
        File file = new File(filePath);

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && !(parts[0].equals(schoolName) && parts[1].equals(schoolAddress))) {
                    lines.add(line);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Error updating the CSV file: " + e.getMessage());
        }
    }

    @FXML
    VBox teamsContainer;

    @FXML
    Label teamsLabel;

    @FXML
    protected void onBackButtonClick() {
        Stage currentStage = (Stage) teamsLabel.getScene().getWindow();
        WindowHelper.openWindow("main-menu.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    @FXML
    TextField schoolAddressTextField;

    @FXML
    TextField schoolNameTextField;

    @FXML
    protected void onTeamCreateButtonClick() {

        String schoolName = schoolNameTextField.getText().trim();
        String schoolAddress = schoolAddressTextField.getText().trim();

        if (schoolName.isEmpty() || schoolAddress.isEmpty()) {
            WindowHelper.showAlert("Vul zowel de naam als het adres van de school in.", Alert.AlertType.ERROR);
            return;
        }

        String file = "teams_data.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.newLine();
            writer.write(schoolName + ";" + schoolAddress);
            addTeam(schoolName, schoolAddress);
            WindowHelper.showAlert("Team '" + schoolName + "' is gemaakt.");
            System.out.println(schoolName + ";" + schoolAddress);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        schoolNameTextField.setText("");
        schoolAddressTextField.setText("");

    }

    @FXML
    public void initialize() {
        String filePath = "teams_data.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String schoolName = parts[0];
                    String schoolAddress = parts[1];
                    addTeam(schoolName, schoolAddress);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}
