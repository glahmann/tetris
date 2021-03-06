/*
 * TCSS 305
 * Assignment 6 - Tetris
 */
package view;

import java.awt.EventQueue;

/**
 * The driver class for Tetris.
 * 
 * @author Garrett Lahmann
 * @version 2 December 2016
 */
public final class TetrisMain {

    /**
     * Private constructor to prevent instantiation.
     */
    private TetrisMain() {
        // Do nothing.
    }

    /**
     * Runs the Tetris program.
     * 
     * @param theArgs   collects command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final TetrisGUI view = new TetrisGUI();                 
                view.start();
            }
        });
    }
}
