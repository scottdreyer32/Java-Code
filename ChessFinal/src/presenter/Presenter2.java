package presenter;

import model.ChessModel;
import model.ChessPiece;
import model.Move;
import view.View2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter2 {
	private View2 view;
	private ChessModel game;
	ChessPiece sourcePiece;
	ActionState state;
	int fromRow;
	int fromCol;
	int toRow;
	int toCol;

	public Presenter2(ChessModel engine, View2 view) {
		game = engine;
		this.view = view;
		this.view.addResetButtonListener(new ResetButtonListener());
		this.view.addQuitButtonListener(new QuitButtonListener());

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				view.addButtonListener(r, c, new ButtonListener(r, c));
			}
		}
		state = ActionState.SOURCE;
	}

	// Sets quitbutton to exit on click
	class QuitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent al) {
			System.exit(0);
		}
	}

	// Sets reset button to call a new game with the same view
	class ResetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent al) {
			game = new ChessModel();
			view.game = game;
			view.redrawBoard();
			view.setAllTrue();

		}
	}

	class ButtonListener implements ActionListener {
		int row;
		int column;
		int fRow;
		int fCol;

		public ButtonListener(int r, int c) {
			row = r;
			column = c;

		}

		// Gets source and destination clicks
		public void actionPerformed(ActionEvent event) {
			if (state == ActionState.SOURCE) {
				fromRow = row;
				fromCol = column;
				view.setSelected(fromRow, fromCol);
				state = ActionState.DESTINATION;
			} else {
				boolean valid = false;
				toRow = row;
				toCol = column;
				if (fromRow == toRow && fromCol == toCol) {
					state = ActionState.SOURCE;
					view.deselect(fromRow, fromCol);
				} else {
					Move move = new Move(fromRow, fromCol, toRow, toCol);
					valid = game.isValidMove(move);

					if (valid == true) {
						game.move(move);
						boolean kingMe = game.validKingMe();
						if (kingMe == true) {
							game.KingMe(view.askUser());
						}
						
					} else {
						view.blinkTimer(move);
					}
					view.deselect(fromRow, fromCol);
					state = ActionState.SOURCE;
				}
				view.redrawBoard();
				view.gameOver(game.isOver());
			}

		}

	}

	// Main method
	public static void main(String[] args) {
		ChessModel engine = new ChessModel();
		View2 view = new View2(engine);
		new Presenter2(engine, view);

	}

}
