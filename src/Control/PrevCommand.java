package Control;

import Model.Image;
import Model.NoImageException;
import View.Ui.ApplicationDisplay;

public class PrevCommand implements Command {
    private final ApplicationDisplay applicationDisplay;

    public PrevCommand(ApplicationDisplay applicationDisplay) {
        this.applicationDisplay = applicationDisplay;
    }
    @Override
    public void execute() {
        try {
            Image imageToDisplay = applicationDisplay.getImageDisplay().getImage().prev();
            applicationDisplay.getImageDisplay().show(imageToDisplay);
            applicationDisplay.setTitle(imageToDisplay.getName());
        } catch (NoImageException e) {

        }
    }
}
