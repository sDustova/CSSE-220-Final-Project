package scenes;

import entities.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * First screen: the player types a name and presses Start.
 */
public class WelcomeScene {
    private final Scene scene;
    private final Player player;
    private final Runnable onStart; // what the app does when Start is pressed
    private final TextField nameField = new TextField();

    public WelcomeScene(Player player, Runnable onStart) {
        this.player = player;
        this.onStart = onStart;

        Label title = new Label("Rose Runner");
        title.setFont(Font.font(40));

        Label prompt = new Label("Enter your name:");
        nameField.setPromptText("Your name");
        nameField.setMaxWidth(200);
        nameField.setOnAction(event -> startGame()); // pressing Enter also starts

        Button startButton = new Button("Start");
        startButton.setOnAction(event -> startGame());

        VBox root = new VBox(15, title, prompt, nameField, startButton);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root, GameScene.WIDTH, GameScene.HEIGHT);
    }

    /** Copies the typed name into the Player. Uses "Player" if the box is empty. */
    public void enterName() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            name = "Player";
        }
        player.setName(name);
    }

    public void startGame() {
        enterName();
        onStart.run();
    }

    public Scene getScene() {return scene;}
}
