package model;

public interface IChessPiece {
	  public abstract Player player();

	  public abstract String name();

	  public abstract boolean isValidMove(Move paramMove, IChessPiece[][] paramArrayOfIChessPiece);

}
