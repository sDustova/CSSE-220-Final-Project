package app;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin {
    private final Group view;
    private final double FIT_HIEGHT = 150;
    private final double FIT_WIDTH= 150;

    public Coin(){
        Image image = null;
        ImageView coin = new ImageView(image);
        coin.setFitHeight(FIT_HIEGHT);
        coin.setFitWidth(FIT_WIDTH);
        coin.setPreserveRatio(true);
        view = new Group(coin);
    }
    public Coin(double x, double y){
        Image image = null;
        ImageView coin = new ImageView(image);
        coin.setFitHeight(FIT_HIEGHT);
        coin.setFitWidth(FIT_WIDTH);
        coin.setPreserveRatio(true);
        coin.setX(x);
        coin.setY(y);
        view = new Group(coin);
    }

    public Group getView() {return view;}
}
