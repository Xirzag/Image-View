package ImageView;

import Model.Image;
import Ui.ImageDisplay;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel implements ImageDisplay {
    private Image image;

    public ImagePanel() {

    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(image!=null)g.drawImage(image.bitmap(), 0, 0, getWidth(), getHeight(), this);
        this.repaint();
    }

    @Override
    public Image getImage() {
        return image;
    }
}
