package org.saxion.devuurtoren.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.saxion.devuurtoren.Main;
import org.saxion.devuurtoren.util.WindowHelper;

import java.io.IOException;
import java.util.Random;

public class TeamsScreenController {

    String[][] schools = new String[][]{
            {"St. Jans College", "Plein 123, 1011AB Amsterdam"},
            {"Verenigde Scholengemeenschap", "Laan van de Vrijheid 56, 1234CD Rotterdam"},
            {"Haven College", "Havenstraat 22, 2345EF Den Haag"},
            {"De Toekomstschool", "Toekomstlaan 10, 3456GH Utrecht"},
            {"Albert Einstein Lyceum", "Einsteinweg 4, 4567IJ Groningen"},
            {"Vrije School De Zonsopgang", "Zonsopgangstraat 8, 5678KL Haarlem"}
    };

    @FXML
    VBox teamsContainer;

    @FXML
    HBox template;

    @FXML
    Label teamsLabel;

    @FXML
    protected void onBackButtonClick() {
        Stage currentStage = (Stage) teamsLabel.getScene().getWindow();
        WindowHelper.openWindow("main-menu.fxml", "Toernooi Manager", 600, 400, currentStage);
    }

    protected void generateTeam() {
        for (String[] school : schools) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("team-template.fxml"));
                Node teamNode = fxmlLoader.load();

                Label nameLabel = (Label) teamNode.lookup("#nameLabel");
                if (nameLabel != null) {
                    nameLabel.setText(school[0]);
                }

                Label addressLabel = (Label) teamNode.lookup("#addressLabel");
                if (addressLabel != null) {
                    addressLabel.setText(school[1]);
                }

                teamsContainer.getChildren().add(teamNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize() {
        generateTeam();
    }
}
