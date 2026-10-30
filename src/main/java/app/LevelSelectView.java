package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LevelSelectView {
    private final StackPane root = new StackPane();

    public LevelSelectView(SceneManager manager, double w, double h) {
        Label title = new Label("Select a Level");
        title.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label greeting = new Label("Welcome, " + manager.getPlayerName() + "!");
        greeting.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        VBox buttons = new VBox(12);
        buttons.setAlignment(Pos.CENTER);

        for (int i = 0; i < Levels.ALL.size(); i++) {
            final int index = i;
            boolean unlocked = i <= manager.getUnlockedLevel();

            String text = (i + 1) + ". " + Levels.ALL.get(i).name();
            Button b = new Button(unlocked ? text : text + "  (Locked)");
            b.setPrefWidth(300);
            b.setPrefHeight(40);
            b.setDisable(!unlocked);
            b.setOnAction(e -> manager.startLevel(index));
            buttons.getChildren().add(b);
        }

        // Stretch goal: add an "Infinite Mode" button here later.

        Button back = new Button("Back");
        back.setOnAction(e -> manager.showWelcome());

        VBox panel = new VBox(18, title, greeting, buttons, back);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(30));
        panel.setMaxSize(480, 460);
        panel.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 12;");

        root.getChildren().addAll(Assets.background("Welcome.png", w, h), panel);
    }

    public StackPane getRoot() { return root; }
}