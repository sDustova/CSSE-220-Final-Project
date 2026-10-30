package scenes;

import java.util.function.IntConsumer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Shows one button per level. Clicking a button starts that level.
 */
public class LevelSelectScene {
    private final Scene scene;
    private final int levelCount;
    private final IntConsumer onSelect; // gets the chosen level number
    private final HBox levelButtons = new HBox(15);

    public LevelSelectScene(int levelCount, IntConsumer onSelect) {
        this.levelCount = levelCount;
        this.onSelect = onSelect;

        Label title = new Label("Choose a Level");
        title.setFont(Font.font(32));

        levelButtons.setAlignment(Pos.CENTER);
        displayLevels();

        VBox root = new VBox(25, title, levelButtons);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root, GameScene.WIDTH, GameScene.HEIGHT);
    }

    /**
     * Makes a button for each level.
     * TODO: disable buttons for locked levels once Level has isUnlocked.
     */
    public void displayLevels() {
        levelButtons.getChildren().clear();
        for (int i = 1; i <= levelCount; i++) {
            int levelNumber = i; // lambdas need a variable that doesn't change
            Button button = new Button("Level " + levelNumber);
            button.setOnAction(event -> selectLevel(levelNumber));
            levelButtons.getChildren().add(button);
        }
    }

    public void selectLevel(int levelNumber) {
        onSelect.accept(levelNumber);
    }

    public Scene getScene() {return scene;}
}
