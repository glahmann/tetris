/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;

/**
 * Builds a panel to display tetriminos during gameplay.
 * 
 * @author Garrett Lahmann
 * @version 10 December 2016
 */
public class GamePanel extends JPanel implements Observer {
    
    /** Auto-generated serial version user ID. */
    private static final long serialVersionUID = -7664600103187011216L;
    
    /** Amber color for 'Z' piece. */
    private static final Color AMBER = new Color(255, 223, 0);

    /** The arc radius for rounded rectangle corners. */
    private static final int ARC_RADIUS = 10;
    
    /** The scale of the board. */
    private static final int SCALE = 30;
    
    /** The number of lines to skip at beginning of board string. */
    private static final int SKIP = 5; 
    
    /** The timer delay in milliseconds. */
    private static final int TIMER_DELAY = 1000;    
    
    /** A boolean representing whether the game is paused or not. */
    private boolean myPauseBool;
    
    /** A boolean representing whether the game is over or not. */
    private boolean myGameOverBool;
    
    /** A boolean representing on or off state of grid lines. */
    private boolean myGridBool;
    
    /** The height of the panel. */
    private int myHeight;   
    
    /** The width of the panel. */
    private int myWidth;
    
    /** String representation of a Tetris game board. */
    private String[] myBoardArray;

    /** A Tetris board. */
    private final Board myBoard;
    
    /** A game timer. */
    private Timer myTimer;
    
    /** A map containing letter:color pairs for shape coloring. */
    private final Map<Character, Color> myColors;
    
    
    /**
     * Constructs a new "Canvas" JLabel to hold graphics.
     * 
     * @param theBoard  the Tetris board.
     */
    public GamePanel(final Board theBoard) {
        super();
        myBoard = theBoard;
        myColors = new HashMap<Character, Color>();
        setColors();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setInits();
    }
    
    /**
     * Initialize class fields to specified start requirements.
     */
    private void setInits() {
        myWidth = myBoard.getWidth() * SCALE;
        myHeight = myBoard.getHeight() * SCALE;
        myPauseBool = false;
        myGameOverBool = false;
        setOpaque(true);
        setPreferredSize(new Dimension(myWidth, myHeight));
        setMinimumSize(new Dimension(myWidth, myHeight));
        myTimer = new Timer(TIMER_DELAY, new TimeListener());
    }
    
    /**
     * Sets tetrimino piece colors to match Tetris DX.
     */
    private void setColors() {
        myColors.put('I', Color.ORANGE);
        myColors.put('J', Color.CYAN);
        myColors.put('L', Color.RED);
        myColors.put('O', Color.YELLOW);
        myColors.put('S', Color.MAGENTA);
        myColors.put('T', Color.GREEN);
        myColors.put('Z', AMBER);
    }
    
    /**
     * Paints shapes to the canvas.
     * 
     * @param theGraphic    the graphic.
     */
    @Override
    public void paintComponent(final Graphics theGraphic) {
        super.paintComponent(theGraphic);
        final Graphics2D g2d = (Graphics2D) theGraphic;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                               RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, SCALE));
        if (myPauseBool) {  // Displays pause screen
            setBackground(Color.LIGHT_GRAY);
            g2d.drawString("Paused", myWidth / 2 - SCALE - ARC_RADIUS, myHeight / 2 - SCALE); 
        } else { // Displays game board
            setBackground(Color.WHITE);
            for (int i = SKIP; i < myBoardArray.length; i++) { //Start at visible line
                for (int j = 0; j < myBoardArray[i].length(); j++) {
                    final char square = myBoardArray[i].charAt(j);
                    if (square == ' ' && myGridBool) {
                        g2d.setPaint(Color.GRAY);
                        g2d.drawRoundRect((j - 1) * SCALE, (i - SKIP) * SCALE, SCALE, SCALE, 
                                          ARC_RADIUS, ARC_RADIUS);
                    } else if (myColors.containsKey(square)) {
                        g2d.setPaint(myColors.get(square));
                        g2d.fillRoundRect((j - 1) * SCALE, (i - SKIP) * SCALE, SCALE, SCALE, 
                                          ARC_RADIUS, ARC_RADIUS);
                        g2d.setPaint(Color.GRAY);
                        g2d.drawRoundRect((j - 1) * SCALE, (i - SKIP) * SCALE, SCALE, SCALE, 
                                          ARC_RADIUS, ARC_RADIUS);
                    }  
                }
            }
            if (myGameOverBool) { 
                g2d.setPaint(Color.BLACK);
                g2d.drawString("Game Over", myWidth / 2 - SCALE * 2, myHeight / 2 - SCALE); 
            }
        } 
    }

    /**
     * Observes a Tetris board and updates when a new string representation of the board is 
     * received.
     * 
     * @param theObservable the observable object.
     * @param theObject     the object broadcasted by the observable object.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof String) {
            myBoardArray = ((String) theObject).split("\n");
            repaint();
        }
    }    
    
    // ********** Setters **********
    
    /**
     * Sets the width of the panel.
     * 
     * @param theWidth  the new panel width.
     */
    public void setWidth(final int theWidth) {
        myWidth = theWidth;
    }
    
    /**
     * Sets the height of the panel.
     * 
     * @param theHeight the new panel height.
     */
    public void setHeight(final int theHeight) {
        myHeight = theHeight;
    }
    
    /**
     * Sets pause state.
     */
    public void setPause() {
        myPauseBool = !myPauseBool;
        if (myPauseBool) {
            myTimer.stop();
        } else {
            myTimer.start();
        }
        repaint();
    }
    
    /**
     * Turns grid lines on and off.
     * 
     * @param theGridBool   whether or not the grid is selected.
     */
    public void setGrid(final boolean theGridBool) {
        myGridBool = theGridBool;
        repaint();
    }
    
    /**
     * Sets a new timer delay based on game level.
     * 
     * @param theLevel  current level in game.
     */
    public void setTimerDelay(final int theLevel) {
        final int base = TIMER_DELAY / theLevel;
        myTimer.setDelay(base + base / 2);
    }
    
    /**
     * Starts the game timer.
     */
    public void start() {
        myTimer.start();
        firePropertyChange("GameStart", null, myTimer.isRunning());
    }
    
    /**
     * Ends the current game.
     */
    public void endGame() {
        firePropertyChange("GameOver", myGameOverBool, true);
        myGameOverBool = true;
        myTimer.stop();
        repaint();
    }
    
    // ********** Inner Classes **********
    
    /**
     * Action listener for timer.
     * 
     * @author Garrett Lahmann
     * @version 10 December 2016
     */
    class TimeListener implements ActionListener {
        /**
         * Calls board's down method at each event.
         * 
         * @param theEvent  a timer event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.down();     // Move piece down on every timer event
        }
    }
}