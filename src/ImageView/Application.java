package ImageView;

import Control.Command;
import Control.NextCommand;
import Control.PrevCommand;
import Model.NoImageException;
import View.Persistance.ImageReader;
import View.Ui.ApplicationDisplay;
import View.Ui.ImageDisplay;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Application extends JFrame implements ApplicationDisplay {

    public final int width = 600, height = 400;
    protected Map<String, Command> commandMap = new HashMap<>();
    private ImagePanel imagePanel;
    private static String imageFolder = System.getProperty("user.home");

    public static void main(String[] args) {
        selectImageFolder(args);
        new Application().setVisible(true);
    }

    public Application() {
        deployUi();
        initCommands();
    }

    public static void selectImageFolder(String[] args) {
        if(args.length >= 1)
            Application.imageFolder = args[0];
    }

    private void initCommands() {
        commandMap.put("Prev",new PrevCommand(this));
        commandMap.put("Next",new NextCommand(this));
    }

    private void deployUi() {
        this.setTitle("Image Display");
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setImageContentPane();
    }

    private void setImageContentPane() {
        try {
            this.setContentPane(panel());
        } catch (IOException e) {

        }
    }

    private JPanel panel() throws IOException {
        JPanel contentPanel = new JPanel(new BorderLayout());

        createImagePanel();

        doShortcuts();

        contentPanel.add(imagePanel, BorderLayout.CENTER);
        contentPanel.add(controlPanel(), BorderLayout.SOUTH);
        return contentPanel;
    }

    private void createImagePanel() {
        ImageReader reader = new FileImageReader(imageFolder);
        imagePanel = new ImagePanel();
        try {
            Model.Image image = reader.getImage(0);
            this.setTitle(image.getName());
            imagePanel.show(image);
        } catch (NoImageException e) {
            JOptionPane.showMessageDialog(this,
                    "No hay imágenes en la carpeta",
                    "ImageDisplay error",
                    JOptionPane.ERROR_MESSAGE);
        }
        imagePanel.addMouseListener(doShift());
    }

    private void doShortcuts() {
        createNextShortcut();
        createPrevShortcut();
    }
/*
    @Override
    public void setTitle(String name) {
        this.setTitle(name);
    }
*/
    @Override
    public ImageDisplay getImageDisplay() {
        return this.imagePanel;
    }

    enum shortcutKeys{
        next(KeyEvent.VK_RIGHT), prev(KeyEvent.VK_LEFT), exit(KeyEvent.VK_ESCAPE);
        private final int code;

        private shortcutKeys(int keyCode) {
            this.code =  keyCode;
        }

        public int getCode(){
            return code;
        }
    };

    private void createPrevShortcut() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(shortcutKeys.prev.getCode(), 0);
        createShorcut(keyStroke, "Prev");
    }

    private void createNextShortcut() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(shortcutKeys.next.getCode(), 0);
        createShorcut(keyStroke, "Next");
    }

    private void createShorcut(KeyStroke keyStroke, final String command){
        imagePanel.getInputMap().put(keyStroke, keyStroke);
        imagePanel.getActionMap().put(keyStroke, new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                execute(command);
            }
        });
    }

    private MouseListener doShift() {
        return new MouseAdapter() {
            public int startDrag;
            public int minOffset = width/5;

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

        };
    }

    private JPanel controlPanel() {
        JPanel panel = new JPanel();
        addPrevButtonTo(panel);
        addNextButtonTo(panel);
        return panel;
    }

    private void addPrevButtonTo(JPanel panel) {
        JButton prev = new JButton("<");
        prev.addActionListener(doExecute("Prev"));
        panel.add(prev);
    }

    private void addNextButtonTo(JPanel panel) {
        JButton next = new JButton(">");
        next.addActionListener(doExecute("Next"));
        panel.add(next);
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
