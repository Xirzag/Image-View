package View.Ui;

import Model.Image;
import Model.NoImageException;

public interface ImageDisplay {
    public void show(Image image);
    public Image getImage() throws NoImageException;


}
