package behaviors;

import javafx.geometry.Bounds;

public interface Collidable {
    public boolean isColliding(Collidable b);
    public Bounds getBounds();
}
