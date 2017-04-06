/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;

import layout.SpringUtilities;
import model.Board;


/**
 * This class creates a panel that shows current keybindings and allows for their modification.
 * 
 * @author Garrett Lahmann
 * @version 10 December 2016
 */
public class KeyPanel extends JPanel {

    /** Auto-generated serial version user ID. */
    private static final long serialVersionUID = -5198445984202468054L;

    /** Strings representing available controls for Tetris. */ 
    private static final String[] CONTROLS = {"Left", "Right", "Down", "Rotate", "Drop",
                                              "Pause"};
    
    /** Initial control keys. */ 
    private static final String[] INIT_KEYS = {"LEFT", "RIGHT", "DOWN", "UP", "SPACE", "P"};
    
    /** The number of controls for the game. */
    private static final int CONTROL_PAIRS = CONTROLS.length;
    
    /** An integer to set the location and padding of the Spring Layout. */
    private static final int PAD = 6;
    
    /** A list of key binding labels. */
    private final List<JLabel> myLabels;
    
    /** A list of key binding text fields. */
    private final List<JTextField> myTextFields;
    
    /** A set of current key binding Strings. */
    private final Set<String> myBindings;
    
    /** A Tetris game board. */
    private Board myBoard;
    
    /** The pause state of the game. */
    private boolean myPauseBool;
    
    /** A game panel. */
    private GamePanel myGamePanel;
    
    /** An action map for key binding. */
    private ActionMap myActionMap;
    
    /** An input map for key binding. */
    private InputMap myInputMap;
    
    /**
     * Builds a key binding panel.
     * 
     * @param theBoard      a Tetris game board.
     * @param theGamePanel  a panel to attach key bindings to.
     */
    public KeyPanel(final Board theBoard, final GamePanel theGamePanel) {
        super(new SpringLayout());
        myBoard = theBoard;
        myActionMap = theGamePanel.getRootPane().getActionMap();
        myInputMap = theGamePanel.getRootPane().getInputMap();
        myBindings = new HashSet<String>();
        myGamePanel = theGamePanel;
        myTextFields = new ArrayList<JTextField>();
        myLabels = new ArrayList<JLabel>();
        setControls();
    }
    
    /**
     * Sets key bindings and populates panel with initial control pairings.
     */
    private void setControls() {
        for (int i = 0; i < CONTROL_PAIRS; i++) {
            final JLabel label = new JLabel(CONTROLS[i], JLabel.TRAILING);
            add(label);
            final JTextField textField = new JTextField(INIT_KEYS[i], 10);
            label.setLabelFor(textField);
            
            // Bind controls to initial keys
            myInputMap.put(KeyStroke.getKeyStroke(INIT_KEYS[i]), CONTROLS[i]);
            myActionMap.put(CONTROLS[i], new MoveAction(CONTROLS[i]));
            myBindings.add(INIT_KEYS[i]);
            
            textField.addKeyListener(new TextKeyListener(INIT_KEYS[i], textField));
            
            myLabels.add(label);
            myTextFields.add(textField);
            add(textField);
        }
        // Formats Spring layout grid
        // Parameters: (component, rows, columns, initial X, initial Y, xPad, yPad)
        SpringUtilities.makeCompactGrid(this, CONTROL_PAIRS, 2, PAD, PAD, PAD, PAD);
    }
    
    /**
     * Clears all current bindings.
     */
    public void clearBindings() {
        final KeyStroke[] keyStrokes = myInputMap.keys();
        
        for (int i = 0; i < keyStrokes.length; i++) {
            myInputMap.put(keyStrokes[i], "NONE"); 
        }
    }
    
    /**
     * Resets key bindings for new board and panel.
     * 
     * @param theBoard      a Tetris board.
     * @param theGamePanel  a Tetris game panel.
     */
    public void resetBindings(final Board theBoard, final GamePanel theGamePanel) {
        myBoard = theBoard;
        myGamePanel = theGamePanel;
        myActionMap = myGamePanel.getRootPane().getActionMap();
        myInputMap = myGamePanel.getRootPane().getInputMap();
        
        // Remove old labels
        for (final JLabel jl: myLabels) {
            remove(jl);
        }
        
        // Remove old text fields
        for (final JTextField jtf: myTextFields) {
            remove(jtf);
        }
        setControls(); // Reset bindings
    }
    
    // ********** Inner Classes **********
    
    /**
     * A key listener class that re-binds a control to the key entered in a text field. 
     * 
     * @author Garrett Lahmann
     * @version 10 December 2016
     */
    private class TextKeyListener extends KeyAdapter {
        /** The key attached to the game control. */
        private String myOldKey;
        
        /** The text field for game control entry. */
        private final JTextField myTextField;
        
        /**
         * Constructs a key listener.
         * 
         * @param theOldKey     the old key.
         * @param theTextField  a text field for control selection.
         */
        TextKeyListener(final String theOldKey, final JTextField theTextField) {
            super();
            myOldKey = theOldKey;
            myTextField = theTextField;
        }
        
        /**
         * Re-binds a control to a key pressed in a text field.
         * 
         * @param theEvent  a key pressed event.
         */
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final String newKey = KeyEvent.getKeyText(theEvent.getKeyCode()).toUpperCase();
            
            // Check if keystroke is used by other binding
            if (!myBindings.contains(newKey)) {     
                final Object oldBinding = myInputMap.get(KeyStroke.getKeyStroke(myOldKey));
                
                // Remove old binding
                myInputMap.remove(KeyStroke.getKeyStroke(myOldKey));
                myBindings.remove(myOldKey);
                
                // Re-bind with key stroke from key event
                myInputMap.put(KeyStroke.getKeyStrokeForEvent(theEvent),
                               oldBinding);
                myBindings.add(newKey);
    
                myOldKey = newKey;
            }
        }

        /**
         * Sets a text field to show the new key binding.
         * 
         * @param theEvent  a key release event.
         */
        @Override
        public void keyReleased(final KeyEvent theEvent) {
            myTextField.setText(myOldKey);
        }        
    }
    
    /**
     * Action listener that calls one of the Tetris board's movement methods when activated.
     * 
     * @author Garrett Lahmann
     * @version 10 December 2016
     */
    private class MoveAction extends AbstractAction {
        
        /** Auto-generated serial version user ID. */
        private static final long serialVersionUID = -6471751320992180187L;
        
        /** An integer representing which control is attached. */
        private final String myControl;
        
        /**
         * Constructs a move action listener.
         * 
         * @param theControl an integer representing which control is attached.
         */
        MoveAction(final String theControl) {
            super();
            myControl = theControl;
        }
        
        /**
         * Calls one of the board's movement methods when and action event occurs.
         * 
         * @param theEvent  an action event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myPauseBool) {
                if (myControl.equals(CONTROLS[0])) {
                    myBoard.left();
                } else if (myControl.equals(CONTROLS[1])) {
                    myBoard.right();
                } else if (myControl.equals(CONTROLS[2])) {
                    myBoard.down();
                } else if (myControl.equals(CONTROLS[CONTROL_PAIRS / 2])) { 
                    myBoard.rotate();
                } else if (myControl.equals(CONTROLS[CONTROL_PAIRS - 2])) {
                    myBoard.drop();
                }
            }
            if (myControl.equals(CONTROLS[CONTROL_PAIRS - 1])) {
                myGamePanel.setPause();
                myPauseBool = !myPauseBool;
            }
        }
    }
}
