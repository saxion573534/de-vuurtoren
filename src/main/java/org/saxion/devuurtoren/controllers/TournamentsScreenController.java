package org.saxion.devuurtoren.controllers;

import com.dlsc.formsfx.model.structure.DateField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.saxion.devuurtoren.Main;
import org.saxion.devuurtoren.util.WindowHelper;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TournamentsScreenController {

    public void createTournament(String name, String location, int numberOfFields, String formattedDate, String sport) {
        String filePath = "tournaments_data.csv";
        String data = "\n" + name + ";" + location + ";" + numberOfFields + ";" + formattedDate + ";" + sport + ";";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            addTournament(name, location, numberOfFields, formattedDate, sport);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readTournaments(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // skips header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length >= 5) {
                    String name = parts[0];
                    String location = parts[1];
                    int numberOfFields = Integer.parseInt(parts[2]);
                    String formattedDate = parts[3];
                    String sport = parts[4];

                    addTournament(name, location, numberOfFields, formattedDate, sport);
                } else {
                    System.err.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    private void removeTournament(String name, String location, String sport) {
        String filePath = "tournaments_data.csv";
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean removed = false;

            fileContent.append(reader.readLine()).append("\n");

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    String tournamentName = parts[0];
                    String tournamentLocation = parts[1];
                    String tournamentSport = parts[4];

                    if (tournamentName.equals(name) && tournamentLocation.equals(location) && tournamentSport.equals(sport)) {
                        removed = true;
                        continue;
                    }
                }

                fileContent.append(line).append("\n");
            }

            if (removed) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(fileContent.toString());
                    // tournament has been removed
                }
            } else {
                System.err.println("Unhandeled exception lol");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        for (Node node : tournamentsContainer.getChildren()) {
            Label nameLabel = (Label) node.lookup("#nameLabel");
            if (nameLabel != null && nameLabel.getText().contains(name) && nameLabel.getText().contains(sport)) {
                tournamentsContainer.getChildren().remove(node);
                break;
            }
        }
    }


    private void addTournament(String name, String location, int numberOfFields, String formattedDate, String sport) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tournament-template.fxml"));
            Node teamNode = fxmlLoader.load();

            Label nameLabel = (Label) teamNode.lookup("#nameLabel");
            if (nameLabel != null) {
                nameLabel.setText(name + " (" + sport + ")");
            }

            Label addressLabel = (Label) teamNode.lookup("#addressLabel");
            if (addressLabel != null) {
                addressLabel.setText(location);
            }

            Label fieldsLabel = (Label) teamNode.lookup("#fieldsLabel");
            if (fieldsLabel != null) {
                fieldsLabel.setText("Aantal velden: " + numberOfFields);
            }

            Label dateLabel = (Label) teamNode.lookup("#dateLabel");
            if (dateLabel != null) {
                dateLabel.setText("Datum: " + formattedDate);
            }

            Button deleteButton = (Button) teamNode.lookup("#deleteTournamentButton");
            if (deleteButton != null) {
                deleteButton.setOnAction(event -> {
                    removeTournament(name, location, sport);
                });
            }

            Button modifyButton = (Button) teamNode.lookup("#modifyTournamentButton");
            if (modifyButton != null) {
                modifyButton.setOnAction(event -> {
                    Stage stage = (Stage) tournamentLabel.getScene().getWindow();
                    ModifyTournamentScreenController modifyTournamentScreenController = WindowHelper.openWindow("modify-tournament-screen.fxml", "De Vuurtoren", 600, 400, stage, true);
                    modifyTournamentScreenController.initialize(name, location, numberOfFields, formattedDate, sport);
                });
            }

            tournamentsContainer.getChildren().add(teamNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    Label tournamentLabel;

    @FXML
    VBox tournamentsContainer;

    // Important data for tournament creation
    @FXML
    TextField fieldsNumberField;

    @FXML
    DatePicker dateField;

    @FXML
    TextField tournamentNameTextField;

    @FXML
    TextField locationTextField;

    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    protected void onBackButtonClick() {
        Stage currentStage = (Stage) tournamentLabel.getScene().getWindow();
        WindowHelper.openWindow("main-menu.fxml", "De Vuurtoren", 600, 400, currentStage);
    }

    @FXML
    protected void onTournamentCreateButtonClick() {

        String tournamentName = tournamentNameTextField.getText().trim();
        String tournamentLocation = locationTextField.getText().trim();

        if (tournamentName.isEmpty() || tournamentLocation.isEmpty()) {
            WindowHelper.showAlert("Vul zowel de naam als het locatie van de toernooi in.", Alert.AlertType.ERROR);
            return;
        }

        if (fieldsNumberField.getText().trim().isEmpty()) {
            WindowHelper.showAlert("Vul een nummer in voor het aantal velden.", Alert.AlertType.ERROR);
            return;
        }
        int fields = 0;
        try {
            fields = Integer.parseInt(fieldsNumberField.getText().trim());
        } catch (NumberFormatException e) {
            WindowHelper.showAlert("Vul een nummer in voor het aantal velden.", Alert.AlertType.ERROR);
            return;
        }
        if (fields <= 1) {
            WindowHelper.showAlert("Vul een reëel getal in voor het aantal velden.", Alert.AlertType.ERROR);
            return;
        }

        if (!dateField.getValue().isAfter(LocalDate.now())) {
            WindowHelper.showAlert("Vul een reëel datum in voor het toernooi datum.", Alert.AlertType.ERROR);
            return;
        }

        LocalDate date = dateField.getValue();

        if (choiceBox.getValue() == null) {
            WindowHelper.showAlert("Kies een sport.", Alert.AlertType.ERROR);
            return;
        }

        String sport = choiceBox.getValue();

        String pattern = "dd-MM-yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        System.out.println(tournamentName);
        System.out.println(tournamentLocation);
        System.out.println(fields);
        System.out.println(date.format(dateFormatter));
        System.out.println(sport);

        createTournament(tournamentName, tournamentLocation, fields, date.format(dateFormatter), sport);

        tournamentNameTextField.setText("");
        locationTextField.setText("");
        fieldsNumberField.setText("");
        dateField.setValue(LocalDate.now());
        choiceBox.setValue(null);

        WindowHelper.showAlert("Toernooi '" + tournamentName + "' is aangemaakt.", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void initialize() {
        dateField.setValue(LocalDate.now());
        choiceBox.getItems().addAll("Volleybal", "Voetbal", "Korfbal");
        readTournaments("tournaments_data.csv");

    }
}
