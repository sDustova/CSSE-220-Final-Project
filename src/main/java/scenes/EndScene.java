package scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Shown after a level is finished: the player's time and the best time.
 */
public class EndScene {
    private final Scene scene;
    private final VBox root = new VBox(15);
    private final String playerName;
    private final int levelNumber;
    private final double time;
    private final Double bestTime; // null if no best time yet
    private final Runnable onContinue;

    public EndScene(String playerName, int levelNumber, double time, Double bestTime, Runnable onContinue) {
        this.playerName = playerName;
        this.levelNumber = levelNumber;
        this.time = time;
        this.bestTime = bestTime;
        this.onContinue = onContinue;

        root.setAlignment(Pos.CENTER);
        showResults();
        scene = new Scene(root, GameScene.WIDTH, GameScene.HEIGHT);
    }

    /** Fills the screen with the results and a button back to level select. */
    public void showResults() {
        Label title = new Label("Level " + levelNumber + " Complete!");
        title.setFont(Font.font(32));

        Label timeLabel = new Label(playerName + "'s time: " + String.format("%.2f s", time));
        String bestText = (bestTime == null) ? "none" : String.format("%.2f s", bestTime);
        Label bestLabel = new Label("Best time on this level: " + bestText);

        Button continueButton = new Button("Back to Level Select");
        continueButton.setOnAction(event -> onContinue.run());

        root.getChildren().setAll(title, timeLabel, bestLabel, continueButton);
    }

    public Scene getScene() {return scene;}
}
