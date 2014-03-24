package model;

import gvprojects.sttt.model.CellState;
import gvprojects.sttt.model.GameStatus;
import org.junit.Test;
import org.junit.Assert;
import java.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Scott
 * Date: 2/19/13
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameEngineTest {




    @Test (expected = IllegalArgumentException.class)
    public void constructorNumRowsTooHigh()throws Throwable{
        GameEngine engine = new GameEngine(11, 3, 5);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorNumColsTooHigh()throws Throwable{
        GameEngine engine = new GameEngine(7, 11, 5);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorWinLengthTooHigh()throws Throwable{
        GameEngine engine = new GameEngine(4, 3, 5);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorNumRowsTooLow()throws Throwable{
        GameEngine engine = new GameEngine(0, 3, 5);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorNumColsTooLow()throws Throwable{
        GameEngine engine = new GameEngine(7, 0, 5);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorWinLengthTooLow()throws Throwable{
        GameEngine engine = new GameEngine(4, 3, 1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorNumRowsNeg()throws Throwable{
        GameEngine engine = new GameEngine(-1, 3, 5);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorNumColsNeg()throws Throwable{
        GameEngine engine = new GameEngine(7, -1, 5);
    }
    @Test (expected = IllegalArgumentException.class)
    public void constructorWinLengthNeg()throws Throwable{
        GameEngine engine = new GameEngine(4, 3, -1);
    }


    @Test
    public void constructorSetsNumRowsCorrect()throws Throwable{
        GameEngine engine = new GameEngine(10, 3, 5);
        assertEquals(10, engine.numRows());
    }

    @Test
    public void constructorSetsNumColsCorrect()throws Throwable{
        GameEngine engine = new GameEngine(10, 3, 5);
        assertEquals(3, engine.numColumns());
    }

    @Test
    public void constructorCreatesAnEmptyBoard() throws Throwable {
        GameEngine engine = new GameEngine(4, 3, 2);
        for (int r = 0; r < engine.numRows(); r++) {
            for (int c = 0; c < engine.numColumns(); c++) {
                assertEquals(CellState.EMPTY, engine.cellStatus(r, c));
            }
        }
    }

    @Test
    public void selectSetsCell() throws Throwable {
        GameEngine engine = new GameEngine(10, 7, 5);
        engine.select(3, 4);
        assertEquals(CellState.X, engine.cellStatus(3, 4));
    }
    @Test
    public void testCellStatusO() throws Exception {
        GameEngine engine = new GameEngine(10, 7, 5);
        engine.select(3, 4);
        engine.select(4,3);
        assertEquals(CellState.O, engine.cellStatus(4, 3));
    }
    @Test
    public void testCellStatusX() throws Exception {
        GameEngine engine = new GameEngine(10, 7, 5);
        engine.select(9, 4);
        engine.select(4,3);
        assertEquals(CellState.X, engine.cellStatus(9, 4));
    }
    @Test
    public void testCellStatusEmpty() throws Exception {
        GameEngine engine = new GameEngine(10, 7, 5);
        engine.select(3, 4);
        engine.select(4,3);
        assertEquals(CellState.EMPTY, engine.cellStatus(7, 3));
    }

    @Test
    public void engineDetectsWin() throws Throwable {
        GameEngine engine = new GameEngine(10, 7, 3);
        engine.select(0, 0);
        assertEquals(GameStatus.IN_PROGRESS, engine.status());
        engine.select(1, 0);
        assertEquals(GameStatus.IN_PROGRESS, engine.status());
        engine.select(0, 1);
        assertEquals(GameStatus.IN_PROGRESS, engine.status());
        engine.select(1, 1);
        assertEquals(GameStatus.IN_PROGRESS, engine.status());
        engine.select(0, 2);
        assertEquals(GameStatus.X_WON, engine.status());
    }

    private CellState[][] buildGameBoard(String[] description) {
        CellState[][] answer = new CellState[description.length][description[0].length()];
        for (int r = 0; r < description.length; r++) {
            for (int c = 0; c < description[r].length(); c++) {
                switch (description[r].charAt(c)) {
                    case 'x':
                        answer[r][c] = CellState.X;
                        break;
                    case 'o':
                        answer[r][c] = CellState.O;
                        break;
                    case '.':
                        answer[r][c] = CellState.EMPTY;
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal format character");
                }
            }
        }
        return answer;
    }



    @Test
    public void emptyBoardReturnsInProgress() throws Throwable {
        String[] board_description = {".....", ".....", "....."};
        CellState[][] game_board = buildGameBoard(board_description);
        assertEquals(GameStatus.IN_PROGRESS, GameEngine.checkForWin(game_board, 4,CellState.X ));
    }
    @Test
    public void boardDetectsWinLeft() throws Throwable{
        String[] board_description = {"xo...", "xo...", "x...."};
        CellState[][] game_board = buildGameBoard(board_description);
        assertEquals(GameStatus.X_WON, GameEngine.checkForWin(game_board, 3,CellState.X ));
    }

    @Test
    public void boardDetectsWinRight() throws Throwable{
        String[] board_description = {"xo...", "xo...", "x...."};
        CellState[][] game_board = buildGameBoard(board_description);
        assertEquals(GameStatus.X_WON, GameEngine.checkForWin(game_board, 3,CellState.X ));
    }

    @Test
    public void boardDetectsWinWrapRow() throws Throwable{
        String[] board_description = {"xxo.x", "xo...", "o...."};
        CellState[][] game_board = buildGameBoard(board_description);
        assertEquals(GameStatus.X_WON, GameEngine.checkForWin(game_board, 3,CellState.X ));
    }

    @Test
    public void boardDetectsWinTop() throws Throwable{
        String[] board_description = {"xxxx.", "ooo..", "xo..."};
        CellState[][] game_board = buildGameBoard(board_description);
        assertEquals(GameStatus.X_WON, GameEngine.checkForWin(game_board, 4,CellState.X ));
    }

    @Test
    public void boardDetectsWinTopO() throws Throwable{
        String[] board_description = {"oooo.", "xxx..", "x...."};
        CellState[][] game_board = buildGameBoard(board_description);
        assertEquals(GameStatus.O_WON, GameEngine.checkForWin(game_board, 4,CellState.O ));
    }

    @Test
    public void boardDetectsWinWrapCol() throws Exception {
        String[] board_description = {"o..x.", "...x.", "...o.", "...x."};
        CellState[][] game_board = buildGameBoard(board_description);
        assertEquals(GameStatus.X_WON, GameEngine.checkForWin(game_board, 3,CellState.X ));
    }
}
