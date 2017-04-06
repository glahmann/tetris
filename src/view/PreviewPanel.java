/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.MovableTetrisPiece;

/**
 * Builds a preview window for the next tetromino queued.
 * 
 * @author Garrett Lahmann
 * @version 2 December 2016
 */
public class PreviewPanel extends JPanel implements Observer {
    
    /** Auto-generated serial user ID. */
    private static final long serialVersionUID = -3968433374408452080L;
    
    /** Amber color for 'Z' piece. */
    private static final Color AMBER = new Color(255, 223, 0);

    /** The arc radius for rounded rectangles. */
    private static final int ARC_RADIUS = 10;
    
    /** The scale used to set drawing sizes. */
    private static final int SCALE = 30;
    
    /** The number of characters wide a Tetris piece is expected to be. */
    private static final int BOX_WIDTH = 5;
    
    /** The preview panel width. */
    private static final int WIDTH = 150;
    
    /** The preview panel height. */
    private static final int HEIGHT = 150;
    
    /** The minimum dimensions for the panel. */
    private static final Dimension MIN_SIZE = new Dimension(150, 150);
    
    /** String representation of the next queued tetromino. */
    private String myShapeString;
    
    /** The width of the next queued tetromino. */
    private int myShapeWidth;
    
    /** The height of the next queued tetromino. */
    private int myShapeHeight;
    
    /** A map containing letter:color pairs for shape coloring. */
    private final Map<Character, Color> myColors;
    
    /**
     * Construct a new preview panel.
     */
    public PreviewPanel() {
        super();
        myColors = new HashMap<Character, Color>();
        setColors();
        setInits();
    }
    
    /**
     * Initialize class fields to specified start requirements.
     */
    private void setInits() {
        setBackground(Color.WHITE);
        setOpaque(true);
        setPreferredSize(MIN_SIZE);
        setMinimumSize(MIN_SIZE);
        setMaximumSize(MIN_SIZE); // TODO Make function of size factor
        myShapeString = "";
    }
    
    /**
     * Sets tetromino piece colors to match Tetris DX.
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
        for (int i = 0; i < myShapeString.length(); i++) {
            if (myColors.containsKey(myShapeString.charAt(i))) {
                int xInit = i % BOX_WIDTH;
                int yInit = i / BOX_WIDTH;
                if (myShapeString.charAt(i) ==  'I') {
                    yInit -= 1;
                } else if (myShapeString.charAt(i) ==  'O') {
                    xInit -= 1;
                }
                final int x = SCALE * xInit + (WIDTH - SCALE * myShapeWidth) / 2;
                final int y = SCALE * yInit 
                               + (HEIGHT - SCALE * myShapeHeight * 2) / 2;
                
                g2d.setPaint(myColors.get(myShapeString.charAt(i)));
                g2d.fillRoundRect(x, y, SCALE, SCALE, ARC_RADIUS, ARC_RADIUS);
                g2d.setPaint(Color.GRAY);
                g2d.drawRoundRect(x, y, SCALE, SCALE, ARC_RADIUS, ARC_RADIUS);
            }
        }
        
        
    }

    /**
     * Observes a Tetris board and updates when a movable Tetris piece is received.
     * 
     * @param theObservable the observable object.
     * @param theObject     the object broadcasted by the observable object.    
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        // TODO Auto-generated method stub
        if (theObject instanceof MovableTetrisPiece) {
            myShapeString = theObject.toString();
            myShapeWidth = ((MovableTetrisPiece) theObject).getWidth();
            myShapeHeight = ((MovableTetrisPiece) theObject).getHeight();
            repaint();
        }
    }

}
