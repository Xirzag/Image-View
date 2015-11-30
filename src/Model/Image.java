package Model;

import java.awt.image.BufferedImage;

public interface Image {
    Image next();
    Image prev();
    BufferedImage bitmap();
    String getName();
}
