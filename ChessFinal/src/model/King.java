package model;



/**
 * Created with IntelliJ IDEA.
 * User: Scott
 * Date: 3/7/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class King extends ChessPiece {
    public King(Player p) {
        super(p, "King");
    }
    /**
     * Validates whether the given move is legal
     * @param move-the move to check
     * @param iChessPieces-the board on which the move is made
     * @return -Boolean value of the given move(true if legal, false otherwise)
     */
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] iChessPieces) {
        return this.clearPath(move, iChessPieces);
    }

    /**
     * Checks to see if the path of the piece is clear
     * @param move-The move that is being checked
     * @param iChessPieces-The board on which the piece is moving
     * @return -Boolean value to return(true if valid false otherwise)
     */
    public boolean clearPath(Move move, IChessPiece[][] iChessPieces) {
        int rEnd = move.toRow;
        int rStart = move.fromRow;
        int cEnd = move.toColumn;
        int cStart = move.fromColumn;

        int deltaRow =  Math.abs(rEnd-rStart);
        int deltaCol = Math.abs(cEnd-cStart);
        if(deltaRow==0&&deltaCol==2){
        	return castleMove(move,iChessPieces);
        }
        if(deltaRow>1 || deltaCol>1)
        {
            return false;
        }else
            return true;

    }
    public boolean castleMove(Move move, IChessPiece[][] iChessPieces){
    	if(move.toColumn==2){
    		Move plusOneCol=new Move(move.fromRow,move.fromColumn,move.toRow,move.toColumn-1);
    		return clearPathRow(plusOneCol, iChessPieces);
    	}else if(move.toColumn==6){
    		return clearPathRow(move,iChessPieces);
    	}else
    		return false;
    }

}

