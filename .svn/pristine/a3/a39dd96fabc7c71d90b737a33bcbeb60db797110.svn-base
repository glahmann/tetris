/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Creates a menu bar for use on a Tetris GUI.
 * 
 * @author Garrett Lahmann
 * @version 10 December 2016
 */
public final class TetrisMenuBar extends JMenuBar {

    /** Auto-generated serial version user ID. */
    private static final long serialVersionUID = 6953223849934404758L;
    
    /** Major tick spacing for slider. */
    private static final int MAJOR_TICK = 10;
    
    /** Minor tick spacing for slider. */
    private static final int MINOR_TICK = 5;
    
    /** An icon to be used on the frame. */
    private static final String ICON = "./images/mrSkeltalMini.png";
    
    /** A key binder panel. */
    private final KeyPanel myKeyPanel;
    
    /**
     * Constructor for Tetris menu bar.
     * 
     * @param theKeyPanel       a key panel.
     * @param theWidthSlider    a JSlider for width.
     * @param theHeightSlider   a JSlider for height.
     * @param theStartButton    a start game menu item.
     * @param theEndButton      an end game menu item.
     * @param theGridCheck      a check box menu item.
     */
    public TetrisMenuBar(final KeyPanel theKeyPanel, final JSlider theWidthSlider, 
                         final JSlider theHeightSlider, final JMenuItem theStartButton,
                         final JMenuItem theEndButton, final JCheckBoxMenuItem theGridCheck) {
        super();
        myKeyPanel = theKeyPanel;

        // Build menus
        final JMenu fileMenu = buildFileMenu(theStartButton, theEndButton);
        final JMenu optionMenu = buildOptionMenu(theWidthSlider, theHeightSlider, 
                                                 theGridCheck);
        final JMenu helpMenu = buildHelpMenu();
        
        add(fileMenu);
        add(optionMenu);
        add(helpMenu);
    }
    
    /**
     * Builds and returns a complete file menu.
     * 
     * @param theStartButton    a start game menu item.
     * @param theEndButton      an end game menu item.
     * @return a complete file menu.
     */
    private JMenu buildFileMenu(final JMenuItem theStartButton,
                                final JMenuItem theEndButton) {
        final JMenu menu = new JMenu("File");
        
        menu.add(theStartButton);
        menu.add(theEndButton);
        return menu;
    }

    /**
     * Builds an option menu.
     * 
     * @param theWidthSlider    a JSlider for width.
     * @param theHeightSlider   a JSlider for height.
     * @param theGridCheck      a check box menu item.
     * @return a complete options menu.
     */
    private JMenu buildOptionMenu(final JSlider theWidthSlider, 
                                  final JSlider theHeightSlider, 
                                  final JCheckBoxMenuItem theGridCheck) {
        final JMenu menu = new JMenu("Options");
        menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
        final JMenuItem controls = buildControlItem();
        final JMenu boardSize = new JMenu("Size"); 
        
        final JLabel widthLabel = new JLabel("Width", JLabel.CENTER);
        widthLabel.setAlignmentX(CENTER_ALIGNMENT);
        final JLabel heightLabel = new JLabel("Height", JLabel.CENTER);
        heightLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        theWidthSlider.setMajorTickSpacing(MAJOR_TICK);
        theWidthSlider.setMinorTickSpacing(MINOR_TICK);
        theWidthSlider.setPaintTicks(true);
        theWidthSlider.setPaintLabels(true);
        
        theHeightSlider.setMajorTickSpacing(MAJOR_TICK);
        theHeightSlider.setMinorTickSpacing(MINOR_TICK);
        theHeightSlider.setPaintTicks(true);
        theHeightSlider.setPaintLabels(true);
        
        boardSize.add(widthLabel);
        boardSize.add(theWidthSlider);
        boardSize.add(heightLabel);
        boardSize.add(theHeightSlider);
        
        menu.add(controls);
        menu.add(boardSize);
        menu.addSeparator();
        menu.add(theGridCheck);
        return menu;
    }
    
    /**
     * Builds a help menu.
     * 
     * @return a complete help menu.
     */
    private JMenu buildHelpMenu() {
        final JMenu menu = new JMenu("Help");
        
        final JMenuItem scoring = new JMenuItem("Scoring...");
        final JMenuItem credits = new JMenuItem("Credits...");
        
        final JPanel scoringPanel = new JPanel();
        final JPanel creditsPanel = new JPanel();
        scoringPanel.setLayout(new BoxLayout(scoringPanel, BoxLayout.PAGE_AXIS));
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.PAGE_AXIS));
        
        // Make labels explaining scoring algorithm
        final JLabel lineOne = new JLabel("1 Line   - 100 points");
        final JLabel lineTwo = new JLabel("2 Lines - 200 points");
        final JLabel lineThree = new JLabel("3 Lines - 400 points");
        final JLabel lineFour = new JLabel("4 Lines - 800 points");
        
        // Make labels for credits
        final JLabel skeltal = new JLabel("'Mr. Skeltal image' - http://www.funnyjunk.com/"
                        + "Thanks+mr+skeltal/funny-pictures/5341511/");
        
        scoringPanel.add(lineOne);
        scoringPanel.add(lineTwo);
        scoringPanel.add(lineThree);
        scoringPanel.add(lineFour);
        
        creditsPanel.add(skeltal);
        
        scoring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final ImageIcon img = new ImageIcon(ICON);
                JOptionPane.showMessageDialog(TetrisMenuBar.this, scoringPanel, "Scoring", 
                                              JOptionPane.INFORMATION_MESSAGE, img);
            }
        });
        
        credits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final ImageIcon img = new ImageIcon(ICON);
                JOptionPane.showMessageDialog(TetrisMenuBar.this, creditsPanel, "Credits", 
                                              JOptionPane.INFORMATION_MESSAGE, img);
            }
        });
        
        menu.add(scoring);
        menu.add(credits);
        return menu;
    }
    
    /**
     * Builds control menu item.
     * 
     * @return a menu item with action listener.
     */
    private JMenuItem buildControlItem() {
        final JMenuItem item = new JMenuItem("Controls...");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(TetrisMenuBar.this, myKeyPanel, "Controls", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return item;
    }
}
