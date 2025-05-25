package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GameController {

    @FXML private Label usernameLabel;
    @FXML private Label modeLabel;
    @FXML private Label timerLabel;
    @FXML private Label wordLabel;
    @FXML private Label scoreLabel;
    @FXML private TextField inputField;

    private String username;
    private String mode;
    private final List<String> wordList = new ArrayList<>();
    private final Random random = new Random();
    private int score = 0;
    private int timeLeft = 60;
    private String currentWord = "";
    private Timeline timeline;

    public void setUsername(String username) {
        this.username = username;
        if (usernameLabel != null)
            usernameLabel.setText("Player: " + username);
    }

    public void setMode(String mode) {
        this.mode = mode.toLowerCase();
        if (modeLabel != null)
            modeLabel.setText("Mode: " + mode);
        loadWords();
        Platform.runLater(this::startGame);
    }

    private void loadWords() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/words.txt");
            if (inputStream == null) {
                System.err.println("❌ words.txt not found in classpath!");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.trim().split(":");
                    if (parts.length == 2 && parts[0].equalsIgnoreCase(mode)) {
                        wordList.add(parts[1].trim());
                    }
                }
            }

            System.out.println("✅ Loaded words (" + mode + "): " + wordList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startGame() {
        nextWord();
        score = 0;
        scoreLabel.setText("Score: 0");
        timeLeft = 60;
        timerLabel.setText("Time: 60s");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft + "s");
            if (timeLeft <= 0) {
                timeline.stop();
                inputField.setDisable(true);
                wordLabel.setText("Time's up!");
                saveScoreToFile();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void nextWord() {
        if (wordList.isEmpty()) return;
        currentWord = wordList.get(random.nextInt(wordList.size()));
        wordLabel.setText(currentWord);
        inputField.clear();
    }

    @FXML
    private void handleInput(ActionEvent event) {
        String typed = inputField.getText().trim();
        if (typed.equals(currentWord)) {
            int multiplier = switch (mode) {
                case "Easy" -> 1;
                case "Medium" -> 2;
                case "Hard" -> 3;
                default -> 1;
            };
            score += 10 * multiplier;
            scoreLabel.setText("Score: " + score);
        }
        nextWord();
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        timeline.stop();
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(loader.load() , 600 , 400);
        org.example.MainMenuController controller = loader.getController();
        controller.setUsername(username);
        ((javafx.stage.Stage) inputField.getScene().getWindow()).setScene(scene);
    }

    //LeaderBoard thingyyy!!!!

    private void saveScoreToFile() {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("leaderboard.txt");
            String entry = username + ":" + score;

            java.nio.file.Files.write(path, (entry + System.lineSeparator()).getBytes(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
