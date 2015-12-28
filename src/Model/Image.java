package Model;

public interface Image {
    Image next() throws NoImageException;
    Image prev() throws NoImageException;
    Object bitmap();
    String getName();
    Dimension dimension();
}
