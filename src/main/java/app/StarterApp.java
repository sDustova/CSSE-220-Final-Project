package app;

import entities.Player;
import game.ScoreManager;
import javafx.application.Application;
import javafx.stage.Stage;
import scenes.EndScene;
import scenes.GameScene;
import scenes.LevelSelectScene;
import scenes.WelcomeScene;

/**
 * Main app for Rose Runner. Switches between the game's scenes:
 * Welcome -> Level Select -> Game -> End -> back to Level Select.
 */
public class StarterApp extends Application {
    // ROSE RUNNER HOOK: scene controller added (StarterApp plays the role of RoseRunnerApp in the UML)
    private static final int LEVEL_COUNT = 2;

    private Stage stage;
    private Player player;
    private final ScoreManager scoreManager = new ScoreManager();

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        player = new Player("Player", 0, 0); // one Player for the whole run; name set on Welcome
        stage.setTitle("Rose Runner");
        showWelcomeScene();
        stage.show();
    }

    public void showWelcomeScene() {
        WelcomeScene welcome = new WelcomeScene(player, this::showLevelSelectScene);
        stage.setScene(welcome.getScene());
    }

    public void showLevelSelectScene() {
        LevelSelectScene levelSelect = new LevelSelectScene(LEVEL_COUNT, this::showGameScene);
        stage.setScene(levelSelect.getScene());
    }

    public void showGameScene(int levelNumber) {
        GameScene game = new GameScene(player, levelNumber, time -> showEndScene(levelNumber, time));
        stage.setScene(game.getScene());
        game.start();
    }

    /** Saves the time, then shows the results. */
    public void showEndScene(int levelNumber, double time) {
        scoreManager.saveTime(player.getName(), levelNumber, time);
        EndScene end = new EndScene(player.getName(), levelNumber, time,
                scoreManager.getBestTime(levelNumber), this::showLevelSelectScene);
        stage.setScene(end.getScene());
    }
    // END ROSE RUNNER HOOK

    public static void main(String[] args) {
        launch(args);
    }
}
