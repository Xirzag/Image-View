package Control;

import Model.Image;
import Ui.ImageDisplay;

public class PrevCommand implements Command {
    private final ImageDisplay imagePanel;

    public PrevCommand(ImageDisplay imagePanel) {
        this.imagePanel = imagePanel;
    }

    @Override
    public void execute() {
        Image image = imagePanel.getImage();
        imagePanel.show(image.prev());
    }
}
