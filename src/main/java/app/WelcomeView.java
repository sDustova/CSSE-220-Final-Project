package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class WelcomeView {
    private final StackPane root = new StackPane();

    public WelcomeView(SceneManager manager, double w, double h) {
        Label title = new Label("Rose Runner");
        title.setStyle("-fx-font-size: 48px; -fx-text-fill: white; -fx-font-weight: bold;");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setMaxWidth(250);
        // INSTRUCTIONS HOOK: how to play, shown before the game starts
        Label instructions = new Label("Move: Left/Right arrows or A/D     Jump: Space, W, or Up\n"
                + "Collect every Rose Point to finish a level.\n"
                + "Fall off the screen and all the Rose Points come back!");
        instructions.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        instructions.setWrapText(true);
        instructions.setMaxWidth(460);
        Label error = new Label(); // INSTRUCTIONS HOOK: feedback when the name is blank
        error.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffb3b3; -fx-font-weight: bold;");

        Button start = new Button("Start");
        start.setOnAction(e -> {
            if (!nameField.getText().isBlank()) {
                manager.setPlayerName(nameField.getText().trim());
                manager.showLevelSelect();
            } else {
                error.setText("Please type your name first."); // INSTRUCTIONS HOOK
            }
        });
        nameField.setOnAction(e -> start.fire()); // INSTRUCTIONS HOOK: pressing Enter also starts

        VBox panel = new VBox(15, title, instructions, nameField, start, error); // INSTRUCTIONS HOOK: added instructions and error
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(30));
        panel.setMaxSize(540, 420); // INSTRUCTIONS HOOK: bigger to fit the instructions
        panel.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 12;");

        root.getChildren().addAll(Assets.background("Welcome.png", w, h), panel);
    }

    public StackPane getRoot() { return root; }
}