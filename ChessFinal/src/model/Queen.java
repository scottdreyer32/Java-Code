package model;



/**
 * Created with IntelliJ IDEA.
 * User: Scott
 * Date: 3/7/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Queen extends ChessPiece {
    public Queen(Player p) {
        super(p, "Queen");
    }
    /**
     * Validates whether the given move is legal
     * @param move-the move to check
     * @param iChessPieces-the board on which the move is made
     * @return -Boolean value of the given move(true if legal, false otherwise)
     */
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] iChessPieces) {
        if(move.toRow==move.fromRow){
            return clearPathRow(move, iChessPieces);
        }else if(move.toColumn==move.fromColumn){
            return clearPathCol(move, iChessPieces);
        }else if( Math.abs(move.fromRow-move.toRow) != Math.abs(move.fromColumn-move.toColumn)){
            return false;
         }else
            return clearPathDiag(move, iChessPieces);

}
}

