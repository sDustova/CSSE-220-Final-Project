package app;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import level.LevelLoader;

public class SceneManager {
    public static final double W = 960, H = 540;
    private final Scene scene = new Scene(new StackPane(), W, H);
    private String playerName;
    private int unlockedLevel = 0;   // index of the highest playable level
    private double bestTime = 0;
    private double totalTime = 0;    // seconds across the current run
    private final LevelLoader loader = new LevelLoader("/levels.txt");

    public SceneManager(Stage stage) {
        stage.setTitle("Rose Runner");
        stage.setScene(scene);
        stage.setResizable(false);   // keeps backgrounds and coordinates predictable
        stage.show();
    }

    public int getNumLevels() {
        return this.loader.getLevels().size();
    }

    public String getLevelName(int index) {
        return this.loader.getLevelName(index);
    }

    public void showWelcome() {
        scene.setRoot(new WelcomeView(this, W, H).getRoot());
    }

    public void showLevelSelect() {
        this.loader.loadLevels();
        scene.setRoot(new LevelSelectView(this, W, H).getRoot());
    }

    public void startLevel(int i) {
        GameScene game = new GameScene(this, this.loader.getLevel(i), W, H); // PLAYER HOOK: GameScene now needs the manager and level index
        scene.setRoot(game.getRoot());
        game.getRoot().requestFocus();                 // PLAYER HOOK: so key presses reach the game
    }

    public void showEnd() {
        scene.setRoot(new EndView(this, W, H).getRoot());
    }

    public void setPlayerName(String n) { playerName = n; }
    public String getPlayerName()       { return playerName; }

    public int getUnlockedLevel() { return unlockedLevel; }
    public double getTotalTime()  { return totalTime; }

    // Call this from GameScene when the player collects every Rose Point
    public void completeLevel(int index, double seconds) {
        totalTime += seconds;
        unlockedLevel = Math.max(unlockedLevel, index + 1);
        if (index + 1 >= this.loader.getLevels().size()) {
            showEnd();
            this.totalTime = 0;
        } else {
            startLevel(index + 1);
        }
    }
}