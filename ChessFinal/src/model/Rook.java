package model;



/**
 * Created with IntelliJ IDEA.
 * User: Scott
 */
public class Rook extends ChessPiece {

    public Rook(Player p) {
        super(p, "Rook");
    }

    /**
     * Validates whether the given move is legal
     * @param move-the move to check
     * @param iChessPieces-the board on which the move is made
     * @return -Boolean value of the given move(true if legal, false otherwise)
     */
    public boolean isValidMove(Move move, IChessPiece[][] iChessPieces) {
        if(move.toRow==move.fromRow){
            return clearPathRow(move, iChessPieces);
        }else if(move.toColumn==move.fromColumn){
            return clearPathCol(move, iChessPieces);
        }else
            return false;
    	}
}
