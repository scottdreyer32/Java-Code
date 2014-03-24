package model;



/**
 * Created with IntelliJ IDEA.
 * User: Scott
 * Date: 3/7/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pawn extends ChessPiece {
    boolean isEnPassant;

    public Pawn(Player p) {
        super(p, "Pawn");
    }
    /**
     * Validates whether the given move is legal
     * @param move-the move to check
     * @param iChessPieces-the board on which the move is made
     * @return -Boolean value of the given move(true if legal, false otherwise)
     */
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] iChessPieces) {
    	isEnPassant = false;
        return oneWayPawn(move, iChessPieces) ;
    }

    /**
     * Checks to make sure the pawn is not moving in the wrong direction
     * @param move-The move that is being checked
     * @param iChessPieces-The board on which the piece is moving
     * @return -Boolean value to return(true if valid false otherwise)
     */
    public boolean oneWayPawn(Move move, IChessPiece[][] iChessPieces) {
        int rEnd = move.toRow;
        int rStart = move.fromRow;
        int cEnd = move.toColumn;
        int cStart = move.fromColumn;
        int deltaRow = Math.abs(rEnd - rStart);
        int deltaCol = Math.abs(cEnd - cStart);

        if(iChessPieces[rStart][cStart].player()==Player.WHITE&&rStart==6||
        		iChessPieces[rStart][cStart].player()==Player.BLACK&&rStart==1){
        	return firstPawnMove(move,iChessPieces);
        }else if(iChessPieces[rStart][cStart].player()==Player.WHITE){
        	if(rEnd>=rStart||deltaCol>1||deltaRow>1){
        		return false;
        	}else if(deltaCol==1&&iChessPieces[rEnd][cEnd]==null){
        		if(rStart==3){
        			return enPassant(move);
        		}else{
        			return false;
        		}
        	}else if(deltaCol==1 && iChessPieces[rEnd][cEnd].player()!=Player.BLACK){
        		return false;
        	}else if(deltaCol==0&&iChessPieces[rEnd][cEnd]!=null){
        		return false;
        	}else
        		return true;
        }else if(iChessPieces[rStart][cStart].player()==Player.BLACK){
        	if(rEnd<=rStart||deltaCol>1||deltaRow>1){
        		return false;
        	}else if(deltaCol==1 && iChessPieces[rEnd][cEnd]==null){
        		if(rStart==4){
        			return enPassant(move);
        		}else{
        			return false;
        		}
        	}else if(deltaCol==1&&iChessPieces[rEnd][cEnd].player()!=Player.WHITE){
        		return false;
        	}else if(deltaCol==0&&iChessPieces[rEnd][cEnd]!=null){
        		return false;
        	}else
        		return true;
        }else
        	return false;

    }
    /**
     * Checks to allow the pawn to move more than one row if it is the first
     * move the pawn makes
     * @param move-The move that is being checked
     * @param iChessPieces-The board on which the piece is moving
     * @return -Boolean value to return(true if valid false otherwise)
     */
    public boolean firstPawnMove(Move move, IChessPiece[][] iChessPieces) {
        int rEnd = move.toRow;
        int rStart = move.fromRow;
        int cEnd = move.toColumn;
        int cStart = move.fromColumn;
        
        int deltaRow = Math.abs(rEnd - rStart);
        int deltaCol = Math.abs(cEnd - cStart);

        if(this.player()==Player.WHITE){
        	if(deltaRow >2||deltaCol>1||rEnd>=rStart||deltaRow+deltaCol>2){
        	    return false;
        	}else if(deltaCol==1&&iChessPieces[rEnd][cEnd]==null){
        		return false;        			
        	}else if(deltaCol==1&&iChessPieces[rEnd][cEnd].player()==Player.WHITE){
        		return false;
        	}else if(deltaCol==1&&deltaRow==1){
        		return true;
        	}else if(deltaRow>1&&iChessPieces[rStart-1][cStart]!=null||iChessPieces[rEnd][cEnd]!=null){
        		return false;
        	}else
        		return true;
        }else {
        	if(deltaRow >2||deltaCol>1||rEnd<=rStart||deltaRow+deltaCol>2){
        	    return false;
        	}else if(deltaCol==1&&iChessPieces[rEnd][cEnd]==null){
        		return false;
        	}else if(deltaCol==1&&iChessPieces[rEnd][cEnd].player()==Player.BLACK){
        		return false;
        	}else if(deltaRow==1&&deltaCol==1){
        		return true;
        	}else if(deltaRow>1&&iChessPieces[rStart+1][cStart]!=null||iChessPieces[rEnd][cEnd]!=null){
        		return false;
        	}else
        		return true;
        }

    }
    public boolean enPassant(Move move){
    	Move last = ChessModel.lastMove;
    	int deltaRow = Math.abs(last.toRow - last.fromRow);
    
    	if(ChessModel.gameBoard[last.toRow][last.toColumn].name()=="Pawn"&&deltaRow==2&&
    			move.toColumn==last.toColumn&&move.fromRow==last.toRow){
    			 isEnPassant=true;
    			 return true;
    		}else{
    			return false;
    		}
     }
    public boolean getPassant(){
    	return isEnPassant;
    }
}


