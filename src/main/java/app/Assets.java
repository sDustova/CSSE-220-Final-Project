package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class Assets {
    private Assets() {}

    public static Image image(String fileName) {
        var url = Assets.class.getResource("/images/" + fileName);
        if (url == null) throw new IllegalArgumentException("Missing image: " + fileName);
        return new Image(url.toExternalForm());
    }

    public static ImageView background(String fileName, double w, double h) {
        ImageView view = new ImageView(image(fileName));
        view.setFitWidth(w);
        view.setFitHeight(h);
        view.setPreserveRatio(false);
        return view;
    }
}