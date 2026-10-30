package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Scenes extends Application {

    @Override
    public void start(Stage stage) {

        var url = getClass().getResource("/images/Chapel.png");

        if (url == null) {
            throw new RuntimeException("Cannot find /images/Chapel.png");
        }

        Image image = new Image(url.toExternalForm());
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        Scene scene = new Scene(new StackPane(imageView), 400, 300);

        stage.setScene(scene);
        stage.setTitle("Image Test");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}