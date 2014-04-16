package model;


/**
 * Created with IntelliJ IDEA. User: Scott Date: 3/18/13 Time: 3:47 PM To change
 * this template use File | Settings | File Templates.
 */

public class ChessModel implements IChessModel {

	final int numRows = 8;
	final int numColumns = 8;
	Player player;
	public static IChessPiece[][] gameBoard;
	IChessPiece piece;
	int playerCount;
	
	int bKingRow;
	int bKingCol;
	int wKingRow;
	int wKingCol;
	int wKingMoves;
	int bKingMoves;
	int ulr;
	int urr;
	int drr;
	int dlr;
	boolean complete;
	GameStatus status;
	static Move lastMove;

	/**
	 * Sets up the model with pieces in the proper spots
	 */
	public ChessModel() {
		lastMove = null;
		this.player = Player.WHITE;
		playerCount = 1;
		status = GameStatus.IN_PROGRESS;
		gameBoard = new IChessPiece[8][8];

		gameBoard[0][0] = new Rook(Player.BLACK);
		gameBoard[0][1] = new Knight(Player.BLACK);
		gameBoard[0][2] = new Bishop(Player.BLACK);
		gameBoard[0][3] = new Queen(Player.BLACK);
		gameBoard[0][4] = new King(Player.BLACK);
		gameBoard[0][5] = new Bishop(Player.BLACK);
		gameBoard[0][6] = new Knight(Player.BLACK);
		gameBoard[0][7] = new Rook(Player.BLACK);
		gameBoard[1][0] = new Pawn(Player.BLACK);
		gameBoard[1][1] = new Pawn(Player.BLACK);
		gameBoard[1][2] = new Pawn(Player.BLACK);
		gameBoard[1][3] = new Pawn(Player.BLACK);
		gameBoard[1][4] = new Pawn(Player.BLACK);
		gameBoard[1][5] = new Pawn(Player.BLACK);
		gameBoard[1][6] = new Pawn(Player.BLACK);
		gameBoard[1][7] = new Pawn(Player.BLACK);

		gameBoard[7][0] = new Rook(Player.WHITE);
		gameBoard[7][1] = new Knight(Player.WHITE);
		gameBoard[7][2] = new Bishop(Player.WHITE);
		gameBoard[7][3] = new Queen(Player.WHITE);
		gameBoard[7][4] = new King(Player.WHITE);
		gameBoard[7][5] = new Bishop(Player.WHITE);
		gameBoard[7][6] = new Knight(Player.WHITE);
		gameBoard[7][7] = new Rook(Player.WHITE);
		gameBoard[6][0] = new Pawn(Player.WHITE);
		gameBoard[6][1] = new Pawn(Player.WHITE);
		gameBoard[6][2] = new Pawn(Player.WHITE);
		gameBoard[6][3] = new Pawn(Player.WHITE);
		gameBoard[6][4] = new Pawn(Player.WHITE);
		gameBoard[6][5] = new Pawn(Player.WHITE);
		gameBoard[6][6] = new Pawn(Player.WHITE);
		gameBoard[6][7] = new Pawn(Player.WHITE);

		bKingRow = 0;
		bKingCol = 4;
		wKingRow = 7;
		wKingCol = 4;

		wKingMoves = 0;
		bKingMoves = 0;
		complete = false;
	}

	// Returns the number of rows
	public int numRows() {
		return numRows;
	}

	// Returns the number of columns
	public int numColumns() {
		return numColumns;
	}

	/**
	 * Returns the piece at the given location
	 * 
	 * @param i
	 *            -the index of the row to check
	 * @param i2
	 *            -the index of the column to check
	 * @return -Returns the game piece of the location
	 */
	public IChessPiece pieceAt(int i, int i2) {
		piece = gameBoard[i][i2];
		return piece;
	}

	// Checks to see if the game is done
	public boolean isComplete() {
		return complete;
	}

	// Returns gamestate
	public GameStatus isOver() {
		return status;
	}

	// Returns the current player
	public Player currentPlayer() {
		return player;
	}

	/**
	 * Checks to see if the move is valid
	 * 
	 * @param move
	 *            -The move that is being checked
	 * @return -Boolean value to return(true if valid false otherwise)
	 */
	public boolean isValidMove(Move move) {
		IChessPiece movePiece = gameBoard[move.fromRow][move.fromColumn];
		IChessPiece toPiece = gameBoard[move.toRow][move.toColumn];
		boolean result=true;
		if (movePiece == null||movePiece.player() != this.currentPlayer()||(toPiece != null && toPiece.player() == movePiece.player())) {
			return false;
		} 
		
		if(movePiece.name()=="King"&&movePiece.player()==Player.WHITE){
			int num=0;
			int moves=0;
			if(move.toColumn==2&&move.fromColumn==4){
				Move onlyOne= new Move(move.fromRow,move.fromColumn,move.toRow,move.toColumn+1);
			    result=checkMove(movePiece,onlyOne);
			    num=ulr;
			    moves=wKingMoves;
			}else if(move.toColumn==6&&move.fromColumn==4){
				Move onlyOne= new Move(move.fromRow,move.fromColumn,move.toRow,move.toColumn-1);
			    result=checkMove(movePiece,onlyOne);
			    num=urr;
			    moves=wKingMoves;
			}
			if(result==false||moves>0||num>0){
				return false;
			}
		}else if(movePiece.name()=="King"&&movePiece.player()==Player.BLACK){
			int num=0;
			int moves=0;
			if(move.toColumn==2&&move.fromColumn==4){
				Move onlyOne= new Move(move.fromRow,move.fromColumn,move.toRow,move.toColumn+1);
			    result=checkMove(movePiece,onlyOne);
			    num=dlr;
			    moves=bKingMoves;
			}else if(move.toColumn==6&&move.fromColumn==4){
				Move onlyOne= new Move(move.fromRow,move.fromColumn,move.toRow,move.toColumn-1);
			    result=checkMove(movePiece,onlyOne);
			    num=drr;
			    moves=bKingMoves;
			}
			if(result==false||moves>0||num>0){
				return false;
			}
		}
		
			return checkMove(movePiece, move);
		

	}
	/**
	 * Checks to see if the move puts the player in check.
	 * @param checker the piece that wants to move
	 * @param mtc the move to check
	 * @return boolean value of true if the move doesnt put player in check
	 */
	public boolean checkMove(IChessPiece checker, Move mtc) {

		IChessPiece temp = gameBoard[mtc.toRow][mtc.toColumn];
		if (checker.isValidMove(mtc, gameBoard)) {

			if (checker.name() == "King") {
				if (checker.player() == Player.WHITE) {
					wKingRow = mtc.toRow;
					wKingCol = mtc.toColumn;
				} else {
					bKingRow = mtc.toRow;
					bKingCol = mtc.toColumn;
				}
			}
			gameBoard[mtc.toRow][mtc.toColumn] = checker;
			gameBoard[mtc.fromRow][mtc.fromColumn] = null;

		} else {
			return false;
		}
		boolean result = inCheck(checker.player());

		if (checker.name() == "King") {
			if (checker.player() == Player.WHITE) {
				wKingRow = mtc.fromRow;
				wKingCol = mtc.fromColumn;
			} else {
				bKingRow = mtc.fromRow;
				bKingCol = mtc.fromColumn;
			}
		}

		gameBoard[mtc.fromRow][mtc.fromColumn] = checker;
		gameBoard[mtc.toRow][mtc.toColumn] = temp;
		return !result;

	}

	/**
	 * Moves the piece to a location and sets from location to null
	 * 
	 * @param move
	 *            -The move being executed
	 */
	public void move(Move move) {
		piece = gameBoard[move.fromRow][move.fromColumn];
		if (piece.name() == "King") {
			if (piece.player() == Player.WHITE) {
				wKingRow = move.toRow;
				wKingCol = move.toColumn;
				wKingMoves++;
			} else {
				bKingRow = move.toRow;
				bKingCol = move.toColumn;
				bKingMoves++;
			}
			if(move.fromColumn==4&&move.toColumn==2){
				gameBoard[move.toRow][move.toColumn] = piece;
				gameBoard[move.fromRow][move.fromColumn] = null;
				gameBoard[move.toRow][3]=gameBoard[move.fromRow][0];
				gameBoard[move.fromRow][0]=null;
				lastMove = move;
				status = GameStatus.IN_PROGRESS;
				player = player.next();
			}else if(move.fromColumn==4&&move.toColumn==6){
				gameBoard[move.toRow][move.toColumn] = piece;
				gameBoard[move.fromRow][move.fromColumn] = null;
				gameBoard[move.toRow][5]=gameBoard[move.fromRow][7];
				gameBoard[move.fromRow][7]=null;
				lastMove = move;
				status = GameStatus.IN_PROGRESS;
				player = player.next();
			}else{
				gameBoard[move.toRow][move.toColumn] = piece;
				gameBoard[move.fromRow][move.fromColumn] = null;
				lastMove = move;
				status = GameStatus.IN_PROGRESS;
				player = player.next();
			}
		}else if (piece.name() == "Pawn"
				&& ((Pawn) piece).getPassant() == true) {
			gameBoard[move.toRow][move.toColumn] = piece;
			gameBoard[move.fromRow][move.fromColumn] = null;
			gameBoard[lastMove.toRow][lastMove.toColumn] = null;
			lastMove = move;
			status = GameStatus.IN_PROGRESS;
			player = player.next();
		} else{
			gameBoard[move.toRow][move.toColumn] = piece;
			gameBoard[move.fromRow][move.fromColumn] = null;
			lastMove = move;
			status = GameStatus.IN_PROGRESS;
			player = player.next();
		}
		
		if(piece.player()==Player.WHITE&&piece.name()=="Rook"){
			if(move.fromColumn==0&&move.fromRow==7){
				ulr++;
			}else if(move.fromColumn==7&&move.fromRow==7){
				urr++;
			}
		}else if(piece.player()==Player.BLACK&&piece.name()=="Rook"){
			if(move.fromColumn==0&&move.fromRow==0){
				dlr++;
			}else if(move.fromColumn==7&&move.fromRow==0){
				drr++;
			}
		}

		if (inCheck(this.currentPlayer()) == true
				&& this.currentPlayer() == Player.WHITE) {
			if (checkMate() == true) {
				status = GameStatus.CHECKMATEWHITE;
				complete = true;
			} else {
				status = GameStatus.WHITEINCHECK;

			}
		} else if (inCheck(this.currentPlayer()) == true
				&& this.currentPlayer() == Player.BLACK) {
			if (checkMate() == true) {
				status = GameStatus.CHECKMATEBLACK;
				complete = true;
			} else {
				status = GameStatus.BLACKINCHECK;
			}
		}else if(inCheck(this.currentPlayer())==false){
			if(checkMate()==true){
				status= GameStatus.STALEMATE;
			}
		}

	}

	// Checks to see if the player is in check
	public boolean inCheck(Player player) {
		if (player == Player.WHITE) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (gameBoard[r][c] != null
							&& gameBoard[r][c].player() != Player.WHITE) {
						Move move = new Move(r, c, wKingRow, wKingCol);
						if (pieceAt(r, c).isValidMove(move, gameBoard)) {
							return true;
						}
					}
				}
			}
		} else {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (gameBoard[r][c] != null
							&& gameBoard[r][c].player() != Player.BLACK) {
						Move move = new Move(r, c, bKingRow, bKingCol);
						if (pieceAt(r, c).isValidMove(move, gameBoard)) {
							return true;
						}
					}
				}
			}
		}

		return false;

	}
/**
 * Determines if the game has ended with a checkmate
 * @return true if there is a checkmate
 */
	public boolean checkMate() {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (pieceAt(r, c) != null
						&& pieceAt(r, c).player() == this.currentPlayer()) {
					IChessPiece temp = pieceAt(r, c);
					if (temp.name() == "Rook") {
						if (rookMoves(r, c) > 0) {
							return false;
						}
					} else if (temp.name() == "Pawn") {
						if (pawnMoves(r, c) > 0) {
							return false;
						}
					} else if (temp.name() == "Bishop") {
						if (bishopMoves(r, c) > 0) {
							return false;
						}
					} else if (temp.name() == "Knight") {
						if (knightMoves(r, c) > 0) {
							return false;
						}
					} else if (temp.name() == "Queen") {
						if (queenMoves(r, c) > 0) {
							return false;
						}
					} else {
						if (kingMoves(r, c) > 0) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
/**
 * Checks to see if there is a valid rook move available
 * returns a number >1 if there is a move to be made
 */
	public int rookMoves(int r, int c) {
		// Row down
		for (int i = r; i < 8; i++) {
			Move move1 = new Move(r, c, i, c);
			if (isValidMove(move1) == true) {
				// System.out.println("Rook Has a move");
				return 1;
			}
		}
		// Row up
		for (int i = r; i > 0; i--) {
			Move move1 = new Move(r, c, i, c);
			if (isValidMove(move1) == true) {
				// System.out.println("Rook Has a move");
				return 1;
			}
		}
		// Column Left
		for (int i = c; i > 0; i--) {
			Move move1 = new Move(r, c, r, i);
			if (isValidMove(move1) == true) {
				// System.out.println("Rook Has a move");
				return 1;
			}
		}
		// Column Right
		for (int i = c; i < 8; i++) {
			Move move1 = new Move(r, c, r, i);
			if (isValidMove(move1) == true) {
				// System.out.println("Rook Has a move to the right");
				return 1;
			}
		}

		return 0;
	}
	/**
	 * Checks to see if there is a valid pawn move available
	 * returns a number >1 if there is a move to be made
	 */
	public int pawnMoves(int r, int c) {

		Move move1 = new Move(r, c, r - 1, c);
		Move move2 = new Move(r, c, r - 1, c - 1);
		Move move3 = new Move(r, c, r - 1, c + 1);
		Move move4 = new Move(r, c, r - 2, c);
		Move move5 = new Move(r, c, r + 1, c);
		Move move6 = new Move(r, c, r + 1, c - 1);
		Move move7 = new Move(r, c, r + 1, c + 1);
		Move move8 = new Move(r, c, r + 2, c);
		if (this.currentPlayer() == Player.WHITE) {
			if (r == 6 && c < 7 && c > 0) {
				if (isValidMove(move1) || isValidMove(move2)
						|| isValidMove(move3) || isValidMove(move4)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (r != 6 && c < 7 && c > 0) {
				if (isValidMove(move1) || isValidMove(move2)
						|| isValidMove(move3)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (r == 6 && c == 0) {
				if (isValidMove(move1) || isValidMove(move3)
						|| isValidMove(move4)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (c == 0) {
				if (isValidMove(move1) || isValidMove(move3)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (r == 6 && c == 7) {
				if (isValidMove(move1) || isValidMove(move2)
						|| isValidMove(move4)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (c == 7) {
				if (isValidMove(move1) || isValidMove(move2)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			}
			return 0;
		} else {
			if (r == 1 && c < 7 && c > 0) {
				if (isValidMove(move5) || isValidMove(move6)
						|| isValidMove(move7) || isValidMove(move8)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (r != 1 && c < 7 && c > 0) {
				if (isValidMove(move5) || isValidMove(move6)
						|| isValidMove(move7)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (r == 1 && c == 0) {
				if (isValidMove(move5) || isValidMove(move7)
						|| isValidMove(move8)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (c == 0) {
				if (isValidMove(move5) || isValidMove(move7)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (r == 1 && c == 7) {
				if (isValidMove(move5) || isValidMove(move6)
						|| isValidMove(move8)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			} else if (c == 7) {
				if (isValidMove(move5) || isValidMove(move6)) {
					// System.out.println("Pawn Has a move");
					return 1;
				}
			}

		}

		return 0;
	}
	/**
	 * Checks to see if there is a valid bishop move available
	 * returns a number >1 if there is a move to be made
	 */
	public int bishopMoves(int r, int c) {
		// Down right
		int dr = c;
		for (int i = r; i < 8 && dr < 8; i++, dr++) {
			Move move1 = new Move(r, c, i, dr);
			if (isValidMove(move1) == true) {
				// System.out.println("Bishop has a move");
				return 1;
			}
		}
		// Up right
		int ur = c;
		for (int i = r; i > 0 && ur < 8; i--, ur++) {
			Move move1 = new Move(r, c, i, ur);
			if (isValidMove(move1) == true) {
				// System.out.println("Bishop has a move");
				return 1;
			}
		}
		// Down Left
		int dl = c;
		for (int i = r; i < 8 && dl > 0; i++, dl--) {
			Move move1 = new Move(r, c, i, dl);
			if (isValidMove(move1) == true) {
				// System.out.println("Bishop has a move");
				return 1;
			}

		}
		// Up left
		int ul = c;
		for (int i = r; i > 0 && ul > 0; i--, ul--) {
			Move move1 = new Move(r, c, i, ul);
			if (isValidMove(move1) == true) {
				// System.out.println("Bishop has a move");
				return 1;
			}

		}

		return 0;
	}
	/**
	 * Checks to see if there is a valid knight move available
	 * returns a number >1 if there is a move to be made
	 */
	public int knightMoves(int r, int c) {
		Move move1 = new Move(r, c, r - 2, c - 1);
		Move move2 = new Move(r, c, r - 2, c + 1);
		Move move3 = new Move(r, c, r - 1, c + 2);
		Move move4 = new Move(r, c, r - 1, c - 2);
		Move move5 = new Move(r, c, r + 1, c + 2);
		Move move6 = new Move(r, c, r + 1, c - 2);
		Move move7 = new Move(r, c, r + 2, c + 1);
		Move move8 = new Move(r, c, r + 2, c - 1);
		if (r >= 2 && c >= 2 && r <= 5 && c <= 5) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move3)
					|| isValidMove(move4) || isValidMove(move5)
					|| isValidMove(move6) || isValidMove(move7)
					|| isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 1 && c >= 2 && c <= 5) {
			if (isValidMove(move3) || isValidMove(move4) || isValidMove(move5)
					|| isValidMove(move6) || isValidMove(move7)
					|| isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 0 && c >= 2 && c <= 5) {
			if (isValidMove(move5) || isValidMove(move6) || isValidMove(move7)
					|| isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 0 && c == 0) {
			if (isValidMove(move5) || isValidMove(move7)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 0 && c == 1) {
			if (isValidMove(move5) || isValidMove(move7) || isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 0 && c == 7) {
			if (isValidMove(move6) || isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 0 && c == 6) {
			if (isValidMove(move6) || isValidMove(move7) || isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 1 && c == 0) {
			if (isValidMove(move3) || isValidMove(move5) || isValidMove(move7)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 1 && c == 1) {
			if (isValidMove(move3) || isValidMove(move5) || isValidMove(move7)
					|| isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 1 && c == 7) {
			if (isValidMove(move4) || isValidMove(move6) || isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 1 && c == 6) {
			if (isValidMove(move4) || isValidMove(move7) || isValidMove(move8)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 6 && c >= 2 && c <= 5) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move3)
					|| isValidMove(move4) || isValidMove(move5)
					|| isValidMove(move6)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 7 && c >= 2 && c <= 5) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move3)
					|| isValidMove(move4)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 6 && c == 0) {
			if (isValidMove(move2) || isValidMove(move3) || isValidMove(move5)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 6 && c == 1) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move3)
					|| isValidMove(move5)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 6 && c == 6) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move4)
					|| isValidMove(move6)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 6 && c == 7) {
			if (isValidMove(move1) || isValidMove(move4) || isValidMove(move6)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 7 && c == 0) {
			if (isValidMove(move2) || isValidMove(move3)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 7 && c == 1) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move3)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 7 && c == 6) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move4)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		} else if (r == 7 && c == 7) {
			if (isValidMove(move1) || isValidMove(move4)) {
				// System.out.println("Knight has a move");
				return 1;
			}
		}

		return 0;
	}
	/**
	 * Checks to see if there is a valid king move available
	 * returns a number >1 if there is a move to be made
	 */
	public int kingMoves(int r, int c) {
		Move move1 = new Move(r, c, r + 1, c - 1);
		Move move2 = new Move(r, c, r + 1, c + 1);
		Move move3 = new Move(r, c, r + 1, c);
		Move move4 = new Move(r, c, r, c - 1);
		Move move5 = new Move(r, c, r, c + 1);
		Move move6 = new Move(r, c, r - 1, c - 1);
		Move move7 = new Move(r, c, r - 1, c + 1);
		Move move8 = new Move(r, c, r - 1, c);

		if (r != 0 && r != 7 && c != 0 && c != 7) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move3)
					|| isValidMove(move4) || isValidMove(move5)
					|| isValidMove(move6) || isValidMove(move7)
					|| isValidMove(move8)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (r == 0 && c == 0) {
			if (isValidMove(move2) || isValidMove(move3) || isValidMove(move5)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (r == 0 && c == 7) {
			if (isValidMove(move1) || isValidMove(move3) || isValidMove(move4)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (r == 0) {
			if (isValidMove(move1) || isValidMove(move2) || isValidMove(move3)
					|| isValidMove(move4) || isValidMove(move5)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (r == 7 && c == 7) {
			if (isValidMove(move4) || isValidMove(move6) || isValidMove(move8)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (r == 7 && c == 0) {
			if (isValidMove(move5) || isValidMove(move7) || isValidMove(move8)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (r == 7) {
			if (isValidMove(move4) || isValidMove(move5) || isValidMove(move6)
					|| isValidMove(move7) || isValidMove(move8)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (c == 0) {
			if (isValidMove(move2) || isValidMove(move3) || isValidMove(move5)
					|| isValidMove(move7) || isValidMove(move8)) {
				// System.out.println("King has valid move");
				return 1;
			}
		} else if (c == 7) {
			if (isValidMove(move1) || isValidMove(move3) || isValidMove(move4)
					|| isValidMove(move6) || isValidMove(move8)) {
				// System.out.println("King has valid move");
				return 1;
			}
		}

		return 0;
	}
	/**
	 * Checks to see if there is a valid queen move available
	 * returns a number >1 if there is a move to be made
	 */
	public int queenMoves(int r, int c) {
		// Row down
		for (int i = r; i < 8; i++) {
			Move move1 = new Move(r, c, i, c);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}
		}
		// Row up
		for (int i = r; i > 0; i--) {
			Move move1 = new Move(r, c, i, c);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}
		}
		// Column Left
		for (int i = c; i > 0; i--) {
			Move move1 = new Move(r, c, r, i);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}
		}
		// Column Right
		for (int i = c; i < 8; i++) {
			Move move1 = new Move(r, c, r, i);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}
		}

		// Down right
		int dr = c;
		for (int i = r; i < 8 && dr < 8; i++, dr++) {
			Move move1 = new Move(r, c, i, dr);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}
		}
		// Up right
		int ur = c;
		for (int i = r; i > 0 && ur < 8; i--, ur++) {
			Move move1 = new Move(r, c, i, ur);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}
		}
		// Down Left
		int dl = c;
		for (int i = r; i < 8 && dl > 0; i++, dl--) {
			Move move1 = new Move(r, c, i, dl);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}

		}
		// Up left
		int ul = c;
		for (int i = r; i > 0 && ul > 0; i--, ul--) {
			Move move1 = new Move(r, c, i, ul);
			if (isValidMove(move1) == true) {
				// System.out.println("Queen has a move");
				return 1;
			}

		}

		return 0;

	}

	/**
	 * Checks to see if there is a pawn that should be kinged
	 * returns true if kinging is available
	 */
	public boolean validKingMe() {
		if (lastMove.toRow == 0
				&& pieceAt(lastMove.toRow, lastMove.toColumn).name() == "Pawn") {
			return true;
		} else if (lastMove.toRow == 7
				&& pieceAt(lastMove.toRow, lastMove.toColumn).name() == "Pawn") {
			return true;
		} else
			return false;
	}
	/**
	 * Checks to see if the new piece would be valid
	 * Exchanges the pawn with the new piece
	 */
	public void KingMe(IChessPiece iChessPiece) {

		if (lastMove.toRow == 0
				&& pieceAt(lastMove.toRow, lastMove.toColumn).name() == "Pawn") {
			if (iChessPiece.name() == "Rook") {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Rook(
						iChessPiece.player());
			} else if (iChessPiece.name() == "Knight") {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Knight(
						iChessPiece.player());
			} else if (iChessPiece.name() == "Bishop") {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Bishop(
						iChessPiece.player());
			} else {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Queen(
						iChessPiece.player());
			}
			boolean checke = inCheck(Player.BLACK);
			if (checke == true) {
				if (checkMate() == true) {
					status = GameStatus.CHECKMATEBLACK;
					complete = true;
				} else {
					status = GameStatus.BLACKINCHECK;
				}

			}

		} else if (lastMove.toRow == 7
				&& pieceAt(lastMove.toRow, lastMove.toColumn).name() == "Pawn") {
			if (iChessPiece.name() == "Rook") {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Rook(
						iChessPiece.player());
			} else if (iChessPiece.name() == "Knight") {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Knight(
						iChessPiece.player());
			} else if (iChessPiece.name() == "Bishop") {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Bishop(
						iChessPiece.player());
			} else {
				gameBoard[lastMove.toRow][lastMove.toColumn] = new Queen(
						iChessPiece.player());
			}
			boolean checke = inCheck(Player.WHITE);
			if (checke == true) {
				if (checkMate() == true) {
					status = GameStatus.CHECKMATEWHITE;
					complete = true;
				} else {
					status = GameStatus.WHITEINCHECK;
				}
			}
		}

	}
//Resets the gameEngine
	public ChessModel reset() {
		ChessModel model = new ChessModel();
		return model;
	}
}
