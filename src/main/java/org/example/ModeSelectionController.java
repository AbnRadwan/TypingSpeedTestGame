package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class ModeSelectionController {

    @FXML
    private void startEasyMode(ActionEvent event) throws Exception {
        startGameWithMode(event, "Easy");
    }

    @FXML
    private void startMediumMode(ActionEvent event) throws Exception {
        startGameWithMode(event, "Medium");
    }

    @FXML
    private void startHardMode(ActionEvent event) throws Exception {
        startGameWithMode(event, "Hard");
    }

    private void startGameWithMode(ActionEvent event, String mode) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game.fxml"));
        Parent root = loader.load(); // ✅ This initializes the GameController with FXML-injected nodes

        GameController controller = loader.getController(); // ✅ Get the actual controller instance
        controller.setUsername(MainMenuController.currentUsername); // ✅ Set username
        controller.setMode(mode); // ✅ Set mode and start game safely

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
