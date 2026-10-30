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
        Button start = new Button("Start");
        start.setOnAction(e -> {
            if (!nameField.getText().isBlank()) {
                manager.setPlayerName(nameField.getText().trim());
                manager.showLevelSelect();
            }
        });

        VBox panel = new VBox(15, title, nameField, start);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(30));
        panel.setMaxSize(450, 300);
        panel.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 12;");

        root.getChildren().addAll(Assets.background("Welcome.png", w, h), panel);
    }

    public StackPane getRoot() { return root; }
}