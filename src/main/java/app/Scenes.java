package app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Scenes extends Application {
    @Override
    public void start(Stage stage) {
        Image image = new Image(
                getClass().getResource("/images/Chapel.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Scene scene = new Scene(new StackPane(imageView), 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
