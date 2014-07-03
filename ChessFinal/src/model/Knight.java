package model;


/**
 * Created with IntelliJ IDEA.
 * User: Scott
 */
public class Knight extends ChessPiece {

    public Knight(Player p) {
        super(p, "Knight");
    }
    /**
     * Validates whether the given move is legal
     * @param move-the move to check
     * @param iChessPieces-the board on which the move is made
     * @return -Boolean value of the given move(true if legal, false otherwise)
     */
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] iChessPieces) {
       return validHelper(move, iChessPieces);

    }
    /**
     * Checks to see if the knight is executing a valid move
     * @param move-The move that is being checked
     * @param iChessPieces-The board on which the piece is moving
     * @return -Boolean value to return(true if valid false otherwise)
     */
    public boolean validHelper(Move move, IChessPiece[][] iChessPieces) {
        int rEnd = move.toRow;
        int rStart = move.fromRow;
        int cEnd = move.toColumn;
        int cStart = move.fromColumn;


        int deltaRow = Math.abs(rEnd-rStart);
        int deltaCol = Math.abs(cEnd-cStart);

        if(deltaCol>2 || deltaRow>2 || deltaCol + deltaRow !=3)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

}
