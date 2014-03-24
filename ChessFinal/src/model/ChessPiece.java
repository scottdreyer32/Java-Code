package model;

public class ChessPiece implements IChessPiece {

	private Player player;
	private String name;

	public ChessPiece(Player player, String str) {
		this.player = player;
		name = str;

	}

	// Returns the player of the chess piece
	public Player player() {

		return player;
	}

	// Returns the name of the chess piece
	@Override
	public String name() {
		return name;
	}

	/**
	 * Validates whether the given move is legal
	 * 
	 * @param move
	 *            -the move to check
	 * @param iChessPieces
	 *            -the board on which the move is made
	 * @return -Boolean value of the given move(true if legal, false otherwise)
	 */
	@Override
	public boolean isValidMove(Move move, IChessPiece[][] iChessPieces) {
		int frRow = move.fromRow;
		int frCol = move.fromColumn;
		int toRow = move.toRow;
		int toCol = move.toColumn;

		if (frRow == toRow && frCol == toCol) {
			return false;
		} else if (iChessPieces[toRow][toCol] != null
				&& iChessPieces[frRow][frCol] != null
				&& iChessPieces[toRow][toCol].player() == iChessPieces[frRow][frCol]
						.player()) {
			return false;
		} else
			return true;

	}

	/**
	 * Checks to see if the path of the piece is clear in the row
	 * 
	 * @param move
	 *            -The move that is being checked
	 * @param iChessPieces
	 *            -The board on which the piece is moving
	 * @return -Boolean value to return(true if valid false otherwise)
	 */
	public boolean clearPathRow(Move move, IChessPiece[][] iChessPieces) {
		int rStart = move.fromRow;
		int cEnd = move.toColumn;
		int cStart = move.fromColumn;

		if (cEnd > cStart) {
			for (int c = cStart + 1; c < cEnd; c++) {
				if (iChessPieces[rStart][c] != null) {
					return false;
				}
			}
		} else {
			for (int c = cStart - 1; c > cEnd; c--) {
				if (iChessPieces[rStart][c] != null) {
					return false;
				}

			}
		}
		return true;

	}

	/**
	 * Checks to see if the path of the piece is clear in the column
	 * 
	 * @param move
	 *            -The move that is being checked
	 * @param iChessPieces
	 *            -The board on which the piece is moving
	 * @return -Boolean value to return(true if valid false otherwise)
	 */
	public boolean clearPathCol(Move move, IChessPiece[][] iChessPieces) {
		int rEnd = move.toRow;
		int rStart = move.fromRow;
		@SuppressWarnings("unused")
		int cEnd = move.toColumn;
		int cStart = move.fromColumn;

		if (rEnd > rStart) {
			for (int r = rStart + 1; r < rEnd; r++) {
				if (iChessPieces[r][cStart] != null) {
					return false;
				}

			}
		} else {
			for (int r = rStart - 1; r > rEnd; r--) {
				if (iChessPieces[r][cStart] != null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks to see if the path of the piece is clear along the diagonal
	 * 
	 * @param move
	 *            -The move that is being checked
	 * @param iChessPieces
	 *            -The board on which the piece is moving
	 * @return -Boolean value to return(true if valid false otherwise)
	 */
	public boolean clearPathDiag(Move move, IChessPiece[][] iChessPieces) {
		int rEnd = move.toRow;
		int rStart = move.fromRow;
		int cEnd = move.toColumn;
		int cStart = move.fromColumn;
		int diagonal;

		if (cEnd - cStart == 0 || rEnd - rStart == 0) {
			return false;
		} else {
			diagonal = Math.abs(rEnd - rStart) / Math.abs(cEnd - cStart);
		}

		if (diagonal != 1) {
			return false;
		} else {
			if (rEnd > rStart && cEnd > cStart) {
				int y = cStart + 1;
				for (int x = rStart + 1; x < rEnd; x++) {
					if (iChessPieces[x][y] != null) {
						return false;
					}

					y++;
				}
			} else if (rEnd < rStart && cEnd < cStart) {
				int y = cStart - 1;
				for (int x = rStart - 1; x > rEnd; x--) {
					if (iChessPieces[x][y] != null) {
						return false;
					}

					y--;
				}
			} else if (rEnd > rStart && cEnd < cStart) {
				int y = cStart - 1;
				for (int x = rStart + 1; x < rEnd; x++) {
					if (iChessPieces[x][y] != null) {
						return false;
					}

					y--;
				}
			} else if (rEnd < rStart && cEnd > cStart) {
				int y = cStart + 1;
				for (int x = rStart - 1; x > rEnd; x--) {
					if (iChessPieces[x][y] != null) {
						return false;
					}

					y++;
				}
			}
			return true;
		}
	}

}
