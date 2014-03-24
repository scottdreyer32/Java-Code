package model;

public interface IChessModel {
	public abstract int numRows();

	  public abstract int numColumns();

	  public abstract IChessPiece pieceAt(int paramInt1, int paramInt2);

	  public abstract boolean isComplete();

	  public abstract boolean isValidMove(Move paramMove);

	  public abstract void move(Move paramMove);

	  public abstract boolean inCheck(Player paramPlayer);

	  public abstract Player currentPlayer();

}
