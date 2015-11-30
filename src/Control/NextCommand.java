package Control;

import Model.Image;
import Ui.ImageDisplay;

public class NextCommand implements Command{
    private final ImageDisplay imagePanel;

    public NextCommand(ImageDisplay imagePanel) {
        this.imagePanel = imagePanel;
    }

    @Override
    public void execute() {
        Image image = imagePanel.getImage();
        imagePanel.show(image.next());
    }
}
