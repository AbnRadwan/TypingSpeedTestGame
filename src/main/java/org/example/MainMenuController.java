package org.example;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class MainMenuController {
    @FXML private Label welcomeLabel;

    public static String currentUsername;

    public void setUsername(String username) {
        currentUsername = username;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username + "!");
        }
    }

    @FXML
    private void handlePlayGame(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModeSelection.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleLeaderboard(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Leaderboard.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    private void handleLogout(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
