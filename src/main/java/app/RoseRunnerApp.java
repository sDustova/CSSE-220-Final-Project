package app;

import app.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

// RoseRunnerApp.java
public class RoseRunnerApp extends Application {
    @Override public void start(Stage stage) { new SceneManager(stage).showWelcome(); }
    public static void main(String[] args) { launch(args); }
}