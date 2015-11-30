package ImageView;

import Control.Command;
import Control.NextCommand;
import Control.PrevCommand;
import Persistance.ImageReader;
import Ui.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Application extends JFrame {

    public final int width = 600, height = 400;
    protected Map<String, Command> commandMap = new HashMap<>();
    private ImageDisplay imagePanel;

    public static void main(String[] args){
        new Application().setVisible(true);
    }

    public Application() {
        deployUi();
        initCommands();
    }

    private void initCommands() {
        commandMap.put("Prev",new PrevCommand(this.imagePanel));
        commandMap.put("Next",new NextCommand(this.imagePanel));
    }

    private void deployUi() {
        this.setTitle("Image Display");
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            this.setContentPane(panel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel panel() throws IOException {
        JPanel contentPanel = new JPanel(new BorderLayout());

        ImageReader reader = new FileImageReader("/home/alberto/Imágenes");
        imagePanel = new ImagePanel();
        imagePanel.show(reader.getImage(0));
        ((JPanel)imagePanel).addMouseListener(doShift());
        contentPanel.add((JPanel) imagePanel, BorderLayout.CENTER);
        contentPanel.add(controlPanel(), BorderLayout.SOUTH);
        return contentPanel;
    }

    private MouseListener doShift() {
        return new MouseListener() {
            public int startDrag;
            public int minOffset = width/5;

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                this.startDrag = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int endDrag = e.getX();
                if(endDrag - startDrag > minOffset) execute("Next");
                if(endDrag - startDrag < -minOffset) execute("Prev");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }

    private JPanel controlPanel() {
        JPanel panel = new JPanel();
        JButton prev = new JButton("<");
        prev.addActionListener(doExecute("Prev"));
        JButton next = new JButton(">");
        next.addActionListener(doExecute("Next"));
        panel.add(prev);
        panel.add(next);
        return panel;
    }

    private ActionListener doExecute(final String command) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execute(command);
            }
        };
    }

    private void execute(String command) {
        commandMap.get(command).execute();
    }


}
