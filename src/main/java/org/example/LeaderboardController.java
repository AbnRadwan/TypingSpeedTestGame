package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LeaderboardController {

    @FXML private TableView<LeaderboardEntry> leaderboardTable;
    @FXML private TableColumn<LeaderboardEntry, Integer> rankColumn;
    @FXML private TableColumn<LeaderboardEntry, String> nameColumn;
    @FXML private TableColumn<LeaderboardEntry, Integer> scoreColumn;

    @FXML
    public void initialize() {
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.setItems(getLeaderboardData());
    }

    private ObservableList<LeaderboardEntry> getLeaderboardData() {
        ObservableList<LeaderboardEntry> data = FXCollections.observableArrayList();

        try {
            java.nio.file.Path path = java.nio.file.Paths.get("leaderboard.txt");
            if (!java.nio.file.Files.exists(path)) return data;

            List<String> lines = java.nio.file.Files.readAllLines(path);
            List<LeaderboardEntry> entries = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    entries.add(new LeaderboardEntry(0, name, score)); // temporary rank 0
                }
            }

            // Sort by score descending
            entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

            // Set actual ranks
            for (int i = 0; i < entries.size(); i++) {
                LeaderboardEntry e = entries.get(i);
                data.add(new LeaderboardEntry(i + 1, e.getUsername(), e.getScore()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }


    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
            Parent root = loader.load();

            // Get current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,600,400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
