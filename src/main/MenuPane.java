package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * JPanel for the menu.
 */
public class MenuPane extends JPanel {

    // imageSources are originals and shouldn't be modified (used for good resizing)
    // normal images can be modified and are used for drawing
    private BufferedImage backgroundSource, background;

    private BufferedImage[] playButtonIconSource = new BufferedImage[3];         // icons: normal, rollover, clicked
    private BufferedImage[] playButtonIcons = new BufferedImage[3];

    private JButton playButton;

    /**
     * Instantiates a panel with the given width and height.
     * @param width the width of the panel
     * @param height the height of the panel
     */
    public MenuPane(int width, int height) {
        // no need for layout manager since all drawing done manually
        super(null);
        setSize(width, height);

        // load images
        try {
            backgroundSource = ImageIO.read(new File("res/menu_background.png"));
            playButtonIconSource[0] = ImageIO.read(new File("res/button_play_normal.png"));
            playButtonIconSource[1] = ImageIO.read(new File("res/button_play_rollover.png"));
            playButtonIconSource[2] = ImageIO.read(new File("res/button_play_clicked.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateImages();

        // add listener for resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // resize button
                playButton.setSize(getWidth(), getHeight());

                // button should be located two squares to the right and three squares down (square in middle column, fourth row)
                playButton.setLocation(2 * getWidth() / 5, 3 * getHeight() / 5);

                updateImages();

                repaint();
            }
        });

        // create play button to fit in one square of the chess board
        playButton = new JButton();
        playButton.setSize(width / 5, height / 5);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // tell frame to switch to chess pane
                Main.showChessPane();
            }
        });

        // add icons and remove border from button
        playButton.setBorderPainted(false);
        playButton.setIcon(new ImageIcon(playButtonIcons[0]));
        playButton.setRolloverIcon(new ImageIcon(playButtonIcons[1]));
        playButton.setPressedIcon(new ImageIcon(playButtonIcons[2]));

        // button should be located two squares to the right and three squares down (square in middle column, fourth row)
        playButton.setLocation(2 * width / 5, 3 * height / 5);
        add(playButton);


        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        // draw background
        g.drawImage(background, 0, 0, null);
    }

    /**
     * Resizes all the images in this panel to scale with the size of this panel.
     */
    private void updateImages() {
        // scale images
        Image scaledBackground = backgroundSource.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

        Image[] scaledPlayButtonIcons = new Image[3];
        for (int i = 0; i < playButtonIconSource.length; i++) {
            // width / 5 and height / 5 are the dimensions of one square in the image
            scaledPlayButtonIcons[i] = playButtonIconSource[i].getScaledInstance(getWidth() / 5, getHeight() / 5, Image.SCALE_SMOOTH);
        }

        // replace old images with new ones
        background = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = background.getGraphics();
        g.drawImage(scaledBackground, 0, 0, null);
        g.dispose();

        for (int i = 0; i < playButtonIcons.length; i++) {
            // width / 5 and height / 5 are the dimensions of one square in the image
            playButtonIcons[i] = new BufferedImage(getWidth() / 5, getHeight() / 5, BufferedImage.TYPE_INT_ARGB);
            g = playButtonIcons[i].getGraphics();
            g.drawImage(scaledPlayButtonIcons[i], 0, 0, null);
            g.dispose();
        }
    }
}
