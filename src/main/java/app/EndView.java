package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

public class EndView {
    private final StackPane root = new StackPane();

    public EndView(SceneManager manager, double w, double h) {
        Label title = new Label("Congratulations!");
        title.setStyle("-fx-font-size: 44px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label message = new Label(manager.getPlayerName()
                + ", you collected every Rose Point and finished the campus run!");
        message.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        message.setWrapText(true);
        message.setMaxWidth(420);

        Label time = new Label(String.format("Total time: %.2f seconds", manager.getTotalTime()));
        time.setStyle("-fx-font-size: 22px; -fx-text-fill: gold; -fx-font-weight: bold;");

        Button playAgain = new Button("Play Again");
        playAgain.setPrefWidth(160);
        playAgain.setOnAction(e -> manager.showLevelSelect());

        Button quit = new Button("Quit");
        quit.setPrefWidth(160);
        quit.setOnAction(e -> Platform.exit());

        VBox panel = new VBox(16, title, message, time, playAgain, quit);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(30));
        panel.setMaxSize(500, 420);
        panel.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 12;");

        root.getChildren().addAll(Assets.background("End.png", w, h), panel);
    }

    public StackPane getRoot() { return root; }
}