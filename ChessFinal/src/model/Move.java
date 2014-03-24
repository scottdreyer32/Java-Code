package model;

public class Move {
	@SuppressWarnings("unused")
	private static final int UNINITIALIZED = -328439;
	public int fromRow;
	public int fromColumn;
	public int toRow;
	public int toColumn;

	public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}

	public Move() {
		this(-328439, -328439, -328439, -328439);
	}

}
