package org.saxion.devuurtoren.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String[] getTeams() {
        List<String> teamList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("teams_data.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 2);
                if (parts.length > 0) {
                    teamList.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teamList.toArray(new String[0]);
    }
    public static String[] getPlayersInTeam(String team) {
        List<String> playerList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("all_players_data.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 3);
                if (parts.length == 3 && parts[2].equals(team)) {
                    playerList.add(parts[0] + " " + parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return playerList.toArray(new String[0]);
    }
}
