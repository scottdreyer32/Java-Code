package model;

import model.CellState;
import model.GameStatus;


/**
 * Created with IntelliJ IDEA.
 * User: Scott Dreyer
 * Date: 2/3/13
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameEngine {
    private int numRows;
    private int numColumns;
    private int winLength;
    private int playerTurn;
    CellState[][] gameBoard;
    GameStatus status;
    String[] turns;
    String[] parts;

    /**
     *  Constructor that sets up the instance variables
     * @param rows - number of rows in the game board.
     * @param columns - number of columns in the game board.
     * @param winLength-number in a row needed to win.
     */
    public GameEngine(int rows, int columns, int winLength)
    {
        this.numRows = rows;
        this.numColumns = columns;
        this.winLength = winLength;
        playerTurn = 1;
       
        turns = new String[(rows * columns)+2];

        //Throws exception if board or win length is too big
        if(numRows>10 || numColumns>10 ||numRows<1 || numColumns<1)
        {
         throw new IllegalArgumentException("Board size invalid.");
        }
        else if(winLength>numColumns && winLength>numRows ||
                                                       winLength<2)
        {
         throw new IllegalArgumentException("Win length invalid");
        }
        else
        {   //creates the board
            gameBoard = new CellState[rows][columns];
           // turns = new String[(rows * columns)+1];
        }
        //calls reset to set all the cells to empty
        reset();
        status = GameStatus.IN_PROGRESS;
    }

    public int numRows()
    {
       return numRows;
    }

    public int numColumns()
    {
        return numColumns;
    }
    public int getPlayerTurn()
    {
        return playerTurn;
    }


    /**
     * Checks the cell state of a given cell
     * @param i- row index
     * @param i1-column index
     * @return - returns the cell state of index
     */
    public CellState cellStatus(int i, int i1)
    {
       return this.gameBoard[i][i1];
    }

    /**
     * Selects the given cell and updates cell status
     * @param i- the row of the selection
     * @param i1- the column of the selection
     */
    public void select(int i, int i1)
    {
       //checks cell state to make sure its empty
    	
       if (this.gameBoard[i][i1]== CellState.EMPTY)
       {  //updates game board with correct player selection
          if(playerTurn%2==1)
          {
              gameBoard[i][i1] = CellState.X;
              playerTurn ++;
              String str = i + ":" + i1;
              turns[playerTurn]= str;

          }
          else
          {
              gameBoard[i][i1] = CellState.O;
              playerTurn ++;
              String str = i + ":" + i1;
              turns[playerTurn]= str;

          }

       }
       else{//if cell is taken this throws an exception
           throw new AlreadyDoneException("That space is taken");
       }

    }

    /**
     * The logic for an unimplemented undo method
     * keeps track of player turn and takes back the last move
     */
    public String undo()
    {

        String str;

        int row;
        int col;


        if(playerTurn>=1)
        {
            str = turns[playerTurn];
            System.out.println("STR" + str);
            parts = str.split(":");
            System.out.println("Parts" + parts.toString() + " " + parts[0] + " " + parts[1]);

            row = Integer.parseInt(parts[0]);
            col = Integer.parseInt(parts[1]);
            gameBoard[row][col] = CellState.EMPTY;
            playerTurn--;

        }
        else
        {
            throw new IllegalArgumentException("You cant go " +
                                                  "back any more");
        }


        return str;

    }

    /**
     * Checks the game status of the current board.
     * @return - returns the status of the current game.
     */
    public GameStatus status()
    {
        GameStatus tempStatus = GameStatus.IN_PROGRESS;
      if(checkForWin(gameBoard, winLength, CellState.X)==GameStatus.X_WON)
      {
          tempStatus= GameStatus.X_WON;
      }
      else if(checkForWin(gameBoard, winLength, CellState.O)==GameStatus.O_WON)
      {
          tempStatus = GameStatus.O_WON;
      }
      else if(checkForWin(gameBoard, winLength, CellState.X)==GameStatus.CATS){
          tempStatus = GameStatus.CATS;
      }
        else
          tempStatus = GameStatus.IN_PROGRESS;

        return
              tempStatus;
}

    /**
     * Sets all cells of the game board to empty
     * resets player turn and total turns
     */
    public void reset()
    {
        this.numColumns();
        this.numRows();

        for(int i = 0; i<numRows; i++)
        {
            for (int j = 0; j < numColumns; j++)
            {
                gameBoard[i][j] = CellState.EMPTY;
            }
        }
        playerTurn=1;
      
    }

    /**
     * Checks the current board to see if there is a win.
     * @param board- the current game board to check
     * @param i- the length of win to check for.
     * @param x- the current cell to check
     * @return- returns the game status(ie. who wins)
     */
    public static GameStatus checkForWin(CellState[][] board, int i,
                                         CellState x)
    {

       //Checks the rows for a valid win
        CellState temp[];
        int emptyCheck = 0;
        GameStatus tempStatus = GameStatus.IN_PROGRESS;

        for (int row = 0; row < board.length; row++)
        {// Sets the temporary array with two sets of the row
            temp = new CellState[board[0].length * 2];
            int b = 0;
            for (int a = 0; a < temp.length; a++)
            {
                temp[a] = board[row][b];
                if (b == board[0].length - 1)
                    b = -1;
                b++;
            }
            

            // Checks the temp array for win length in a row
            int check = 0;

            for (int a = 0; a < temp.length; a++)
            {
                if (temp[a].equals(x))
                    check++;
                else
                    check = 0;
                if (check == i)
                    if (x.equals(CellState.X))
                        tempStatus = GameStatus.X_WON;
                    else
                        tempStatus = GameStatus.O_WON;

            }
        }
        int c=0;
        int loopCount=0;
        for(int a=board.length-1;a>=0;a--){
            if (board[a][loopCount].equals(x))
                c++;
          	 else
                c = 0;
            
            if (c == i)
                if (x.equals(CellState.X))
                    tempStatus = GameStatus.X_WON;
                else
                    tempStatus = GameStatus.O_WON;
        	
        	loopCount++;
        }

        int ck=0;
        for (int a=board.length-1;a>=0;a--){
        	if (board[a][a].equals(x))
                ck++;
            else
                ck = 0;
            if (ck == i)
                if (x.equals(CellState.X))
                    tempStatus = GameStatus.X_WON;
                else
                    tempStatus = GameStatus.O_WON;
        }

        // Checks columns for a valid win

        for (int f = 0; f < board[0].length; f++)
        {
            temp = new CellState[board[0].length * 2];
            int b = 0;
            for (int a = 0; a < temp.length; a++)
            {
                temp[a] = board[b][f];
                if (b == board.length - 1)
                    b = -1;
                b++;
            }
            int check = 0;
            for (int a = 0; a < temp.length; a++)
            {
                if (temp[a].equals(x))
                    check++;
                else
                    check = 0;
                if (check == i)
                    if (x.equals(CellState.X))
                        tempStatus = GameStatus.X_WON;
                    else
                        tempStatus = GameStatus.O_WON;
            }
            
        }
      
        if(tempStatus!=GameStatus.IN_PROGRESS){
            return tempStatus;
        }
        else
        {

            for (int r = 0; r < board.length; r++)
            {

                for (int l = 0; l < board[r].length; l++)
                {

                    if (board[r][l].equals(CellState.EMPTY)){
                        emptyCheck++;
                    }

                }


            }
            if(emptyCheck==0)
            {
                tempStatus = GameStatus.CATS;
            }
            else
                tempStatus = GameStatus.IN_PROGRESS;

        }

        return tempStatus;
        //if no win found checks for cats or in progress
    }
}
