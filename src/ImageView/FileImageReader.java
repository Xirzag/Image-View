package ImageView;

import Model.Image;
import Persistance.ImageReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;


public class FileImageReader implements ImageReader {

    File[] images;

    protected static String[] fileTypes = {".jpg",".png",".bmp"};

    public FileImageReader(String path){
        images = new File(path).listFiles(
        new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {

                for (String extension : fileTypes)
                    if(name.toLowerCase().endsWith(extension)) return true;
                return false;
            }
        });
    }

    public Image getImage(final int index){
        if(index < 0 || index >= images.length) return null;
        return new Image() {
            @Override
            public Image next() {
                return (index >= images.length-1)? getImage(0) : getImage(index+1);
            }

            @Override
            public Image prev() {
                return (index <= 0)? getImage(images.length-1) : getImage(index-1);
            }

            @Override
            public BufferedImage bitmap() {
                try {
                    return ImageIO.read(images[index]);
                } catch (IOException e) {;
                    return null;
                }
            }

            @Override
            public String getName() {
                return images[index].getName();
            }
        };
    }

}
