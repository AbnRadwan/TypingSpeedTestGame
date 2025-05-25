package org.example;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class controllers {
            @FXML
            private TextField usernameField;
            @FXML
            private PasswordField passwordField;
            @FXML
            private Label statusLabel;

            @FXML
            private void handleLogin() throws IOException {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (database.login(username, password)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
                    Scene scene = new Scene(loader.load(), 600, 400);

                    MainMenuController controller = loader.getController();
                    controller.setUsername(username);

                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(scene);
                }
            }

            @FXML
            private void handleRegister() {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (database.register(username, password)) {
                    statusLabel.setText("Registration successful!");
                } else {
                    statusLabel.setText("Username already exists.");
                }
            }
        }



