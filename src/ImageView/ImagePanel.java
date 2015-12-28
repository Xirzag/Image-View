package ImageView;

import Model.Dimension;
import Model.Image;
import Model.NoImageException;
import View.Ui.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
        fillBackground(g);
        if(image!=null && image.bitmap() instanceof BufferedImage) {
            Model.Dimension imageDim = calculateImageDimension();
            g.drawImage((BufferedImage) image.bitmap(),
                    centerImageWidth(imageDim), centerImageHeight(imageDim),
                    imageDim.getX(), imageDim.getY(), this);

            this.repaint();
        }
    }

    private void fillBackground(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,getWidth(),getHeight());
    }

    private int centerImageWidth(Dimension imageDim) {
        return (getWidth() - imageDim.getX())/2;
    }

    private int centerImageHeight(Dimension imageDim) {
        return (getHeight()-imageDim.getY())/2;
    }

    private Dimension calculateImageDimension() {
        if(imageMustBeResizedByWidth())
            return new Dimension(calculeWidth(),getHeight());

        return new Dimension(getWidth(), calculeHeight());
    }

    private boolean imageMustBeResizedByWidth() {
        return getWidth()/getHeight() >= image.dimension().getX()/image.dimension().getY();
    }

    private Integer calculeWidth() {
        return Math.round(image.dimension().getX() / new Float(image.dimension().getY()) * getHeight());
    }

    private Integer calculeHeight() {
        return Math.round(image.dimension().getY()/new Float(image.dimension().getX()) * getWidth());
    }

    @Override
    public Image getImage() throws NoImageException {
        if (image == null) throw new NoImageException();
        return image;
    }
}
