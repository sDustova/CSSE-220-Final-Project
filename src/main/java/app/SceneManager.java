package app;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneManager {
    public static final double W = 960, H = 540;
    private final Scene scene = new Scene(new StackPane(), W, H);
    private String playerName;
    private int unlockedLevel = 0;   // index of the highest playable level
    private double totalTime = 0;    // seconds across the current run


    public SceneManager(Stage stage) {
        stage.setTitle("Rose Runner");
        stage.setScene(scene);
        stage.setResizable(false);   // keeps backgrounds and coordinates predictable
        stage.show();
    }

    public void showWelcome()     { scene.setRoot(new WelcomeView(this, W, H).getRoot()); }
    public void showLevelSelect() { scene.setRoot(new LevelSelectView(this, W, H).getRoot()); }
    public void startLevel(int i) {
        GameScene game = new GameScene(this, i, W, H); // PLAYER HOOK: GameScene now needs the manager and level index
        scene.setRoot(game.getRoot());
        game.getRoot().requestFocus();                 // PLAYER HOOK: so key presses reach the game
    }
    public void showEnd()         { scene.setRoot(new EndView(this, W, H).getRoot()); }

    public void setPlayerName(String n) { playerName = n; }
    public String getPlayerName()       { return playerName; }

    public int getUnlockedLevel() { return unlockedLevel; }
    public double getTotalTime()  { return totalTime; }

    // Call this from GameScene when the player collects every Rose Point
    public void completeLevel(int index, double seconds) {
        totalTime += seconds;
        unlockedLevel = Math.max(unlockedLevel, index + 1);
        if (index + 1 >= Levels.ALL.size()) {
            showEnd();
        } else {
            startLevel(index + 1);
        }
    }
}