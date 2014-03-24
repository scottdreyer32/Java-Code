package view;
import model.GameStatus;
import model.GameEngine;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Scott
 * Date: 2/18/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class View extends JPanel{
    JButton[][] buttons;
    JButton quitButton;
    JButton resetButton;
    JButton undoButton;
    GameEngine game;
    JFrame frame;
    JPanel board, functions, main;
    String[] parts;


    public View(int numRows, int numColumns)
    {
        buttons = new JButton[numRows][numColumns];
        quitButton = new JButton("Quit");
        resetButton = new JButton("Reset");
        undoButton = new JButton("Undo");

        frame = new JFrame("Super TicTicToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main = new JPanel();
        functions = new JPanel();
        board = new JPanel();
        board.setLayout(new GridLayout(numRows, numColumns));
        functions.setLayout(new FlowLayout());
        main.setLayout(new BorderLayout());

        functions.add(quitButton);
        functions.add(resetButton);
        functions.add(undoButton);

        board.setPreferredSize(new Dimension(500,500));


        for(int r=0; r<buttons.length; r++)
        {
            for(int c=0; c<buttons[0].length; c++)
            {
                buttons[r][c]=new JButton(loadIcon("empty.jpg"));
                board.add(buttons[r][c], r, c);
            }
        }
        main.add(board, BorderLayout.PAGE_START);
        main.add(functions, BorderLayout.CENTER);
        frame.add(main);
        frame.pack();
        frame.setVisible(true);

    }
    public void addButtonListener(int r, int c, ActionListener al)
    {
        buttons[r][c].addActionListener(al);
    }

    private static ImageIcon loadIcon(String name) {
        java.net.URL imgURL = View.class.getResource(name);
        if (imgURL == null) {
            throw new RuntimeException("Icon resource not found.");
        }
        return new ImageIcon(imgURL);
    }

    public void reset()
    {
        for (int r=0; r<buttons.length; r++)
        {
            for(int c=0; c<buttons[0].length; c++)
            {
                buttons[r][c].setIcon(loadIcon("empty.jpg"));
                buttons[r][c].setEnabled(true);
            }
        }
    }

    public void addQuitButtonListener(ActionListener quitButtonListener)
    {
        quitButton.addActionListener(quitButtonListener);
    }
    public void addResetButtonListener(ActionListener resetButtonListener)
    {
        resetButton.addActionListener(resetButtonListener);
    }
    public void addUndoButtonListener(ActionListener undoButtonListener)
    {
        undoButton.addActionListener(undoButtonListener);
    }

    public void oIcon(int row, int col)
    {
        buttons[row][col].setIcon(loadIcon("o.jpg"));
    }

    public void xIcon(int row, int col)
    {
        buttons[row][col].setIcon(loadIcon("x.jpg"));
    }

    public void gameOver(GameStatus status)
    {
        if(status.equals(GameStatus.X_WON))
        {
            JOptionPane.showMessageDialog(null, "X Wins");
        }
        else if(status.equals(GameStatus.O_WON))
        {
            JOptionPane.showMessageDialog(null, "O Wins");
        }
        else if(status.equals(GameStatus.CATS))
        {
            JOptionPane.showMessageDialog(null, "Tie Game");
        }

        for (int r=0; r<buttons.length; r++)
        {
            for(int c=0; c<buttons[0].length; c++)
            {
                buttons[r][c].setEnabled(false);
            }
        }
    }

    public void undo(String str)
    {
        int row;
        int col;

        parts = str.split(":");

        row = Integer.parseInt(parts[0]);
        col = Integer.parseInt(parts[1]);

        buttons[row][col].setIcon(loadIcon("empty.jpg"));
    }
}
