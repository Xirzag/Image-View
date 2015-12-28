package View.Persistance;

import Model.NoImageException;
import Model.Image;

public interface ImageReader {

    public Image getImage(int index) throws NoImageException;

}
