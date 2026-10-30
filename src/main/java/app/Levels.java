package app;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public final class Levels {
    public static final List<Level> ALL = List.of(
            new Level("Tutorial", "Tutorial.png",
                    new Point2D(50, 400),
                    List.of(new Rectangle2D(0, 480, 960, 60),      // ground
                            new Rectangle2D(300, 380, 150, 20)),
                    List.of(new Point2D(350, 340), new Point2D(700, 440))),

            new Level("White Chapel", "Chapel.png",
                    new Point2D(50, 400),
                    List.of(new Rectangle2D(0, 480, 960, 60),
                            new Rectangle2D(200, 400, 120, 20),
                            new Rectangle2D(450, 320, 120, 20)),
                    List.of(new Point2D(240, 360), new Point2D(490, 280))),

            new Level("Moench Hall", "Moench.png", /* ... */ new Point2D(50, 400),
                    List.of(new Rectangle2D(0, 480, 960, 60)), List.of()),

            new Level("Mussallem Union", "Union.png", /* ... */ new Point2D(50, 400),
                    List.of(new Rectangle2D(0, 480, 960, 60)), List.of())
    );
}