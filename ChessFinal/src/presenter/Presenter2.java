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

	class QuitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent al) {
			System.exit(0);
		}
	}

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

		public void actionPerformed(ActionEvent event) {
			if (state == ActionState.SOURCE) {
				fromRow = row;
				fromCol = column;
				state = ActionState.DESTINATION;

			} else {
				boolean valid = false;
				Move move = new Move(fromRow, fromCol, row, column);
				valid = game.isValidMove(move);

				if (valid == true) {
					game.move(move);
					boolean kingMe = game.validKingMe();
					if (kingMe == true) {
						game.KingMe(view.askUser());
					}
					view.redrawBoard();
				} else {
					view.invalidMessage();
				}
				state = ActionState.SOURCE;
				view.gameOver(game.isOver());
			}

		}

	}

	public static void main(String[] args) {
		ChessModel engine = new ChessModel();
		View2 view = new View2(engine);
		new Presenter2(engine, view);

	}

}
