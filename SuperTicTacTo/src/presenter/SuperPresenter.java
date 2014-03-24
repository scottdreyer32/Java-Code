package presenter;

import model.CellState;
import model.GameStatus;
import model.AlreadyDoneException;
import model.GameEngine;
import model.IllegalUndoException;
import view.View;

import javax.swing.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: dreyersc Date: 2/18/13 Time: 4:58 PM To
 * change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unused")
public class SuperPresenter {

	private JButton[][] board;
	private View view;
	private GameEngine game;

	public SuperPresenter(GameEngine engine, View view) {
		this.game = engine;
		this.view = view;
		this.view.addResetButtonListener(new ResetButtonListener());
		this.view.addQuitButtonListener(new QuitButtonListener());
		this.view.addUndoButtonListener(new UndoButtonListener());

		for (int r = 0; r < engine.numRows(); r++) {
			for (int c = 0; c < engine.numColumns(); c++) {
				view.addButtonListener(r, c, new ButtonListener(r, c));
			}
		}

	}

	class QuitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent al) {
			System.exit(0);
		}
	}

	class UndoButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent al) {
			view.undo(game.undo());

		}
	}

	class ResetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent al) {
			game.reset();
			view.reset();
		}
	}

	class ButtonListener implements ActionListener {
		int row = 0;
		int column = 0;

		public ButtonListener(int r, int c) {
			row = r;
			column = c;
		}

		public void actionPerformed(ActionEvent event) {
			try {
				game.select(row, column);
			} catch (AlreadyDoneException exception) {
				JOptionPane.showMessageDialog(null, "That spot is taken.");
			}

			CellState cell = game.cellStatus(row, column);
			GameStatus status = game.status();

			if (cell.equals(CellState.O)) {
				view.oIcon(row, column);
			} else if (cell.equals(CellState.X)) {
				view.xIcon(row, column);
			}

			if (!(status.equals(GameStatus.IN_PROGRESS))) {
				view.gameOver(status);
			}

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		ArrayList<Integer> rows = new ArrayList<Integer>();
		Object[] possibilities = { 3, 4, 5, 6, 7, 8 };
		for (int i = 0; i < possibilities.length; i++) {
			rows.add((Integer) possibilities[i]);
		}
		ArrayList<Integer> winLength = new ArrayList<Integer>();
		for (int i = 0; i < possibilities.length; i++) {
			winLength.add((Integer) possibilities[i]);
		}

		JComboBox xField = new JComboBox(rows.toArray());
		JComboBox yField = new JComboBox(winLength.toArray());

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Number of rows and columns:"));
		myPanel.add(xField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("Number in a row to win:"));
		myPanel.add(yField);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Select numBer of rows and the win length!",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {

			int numRows = (int) xField.getSelectedItem();
			int numWin = (int) yField.getSelectedItem();
			if (numWin > numRows) {
               
				while(numWin>numRows){
            	  int selectedValue = (int) JOptionPane.showInputDialog(null,
						"WinLength cannot be bigger than row length", "Enter win length.",
						JOptionPane.INFORMATION_MESSAGE, null, possibilities,
						possibilities[0]);
            	  numWin=selectedValue;
               }
				
				GameEngine engine = new GameEngine(numRows,numRows,numWin);
				View view = new View(numRows,numRows);
				new SuperPresenter(engine, view);

			} else {
				GameEngine engine = new GameEngine(numRows,numRows,numWin);
				View view = new View(numRows,numRows);
				new SuperPresenter(engine, view);
			}

		}

	}

}
