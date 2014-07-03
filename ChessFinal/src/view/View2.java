package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import model.Bishop;
import model.ChessModel;
import model.GameStatus;
import model.IChessPiece;
import model.Knight;
import model.Move;
import model.Player;
import model.Queen;
import model.Rook;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings({ "serial", "unused" })
public class View2 extends JPanel {
	//Sets up all variables
	ImageIcon bP, bB, bK, bKI, bR, bQ, wP, wB, wK, wKI, wR, wQ, empty;
	JButton[][] buttons;
	ImageIcon lastIcon;
	JButton quitButton;
	JButton resetButton;
	String WhiteTurn,BlackTurn;
	Color whiteSmoke;
	JLabel turnLabel;
	public ChessModel game;
	JFrame frame;
	JPanel board, functions, main,info;
	Move lastMove;
	private final int rows = 8;
	private final int columns = 8;
    //Constructor to create and initialize all variables
	public View2(ChessModel model) {
		whiteSmoke = new Color(245,245,245);
		game = model;
		lastIcon = null;
		buttons = new JButton[8][8];
		quitButton = new JButton("Quit");
		resetButton = new JButton("Reset");
		BlackTurn = "It is BLACK's turn";
		WhiteTurn = "It is WHITE's turn";
		frame = new JFrame("Welcome to CHESS!?!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		turnLabel = new JLabel(WhiteTurn);
		turnLabel.setFont(new Font("Helvetica",Font.BOLD,18));
		
		
		//Create and set up info panel
		info =new JPanel();
		info.setLayout(new FlowLayout());
		info.setBackground(whiteSmoke);
		info.add(turnLabel);
		info.setPreferredSize(new Dimension(600, 36));
		
		//Create and set up board panel
		board = new JPanel();
		board.setLayout(new GridLayout(rows, columns));
		board.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20,
				Color.DARK_GRAY));
		board.setPreferredSize(new Dimension(600, 600));
		
		//Create and set up functions panel
		functions = new JPanel();
		functions.setLayout(new FlowLayout());
		functions.setBackground(whiteSmoke);
		functions.add(quitButton);
		functions.add(resetButton);
		
		//Create and set up main panel
		main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setBorder(BorderFactory.createMatteBorder(0, 36, 0, 36, whiteSmoke));

		createImages();
		setBoard();
		main.add(board, BorderLayout.CENTER);
		main.add(functions, BorderLayout.SOUTH);
		main.add(info, BorderLayout.NORTH);
		frame.add(main);
		frame.pack();
		frame.setVisible(true);

	}
	//Creates Buttons for the board,alternates background color,sets Icons
	public void setBoard() {
		
		for (int r = 0; r < buttons.length; r++) {
			for (int c = 0; c < buttons[0].length; c++) {
				buttons[r][c] = new JButton();
				board.add(buttons[r][c], r, c);
			}
		}

		for (int r = 0; r < buttons.length; r++) {
			if (r % 2 == 0) {
				for (int c = 0; c < buttons[0].length; c++) {
					if(c%2==0){
						buttons[r][c].setBackground(Color.GRAY);
					}else{
						buttons[r][c].setBackground(Color.WHITE);
					}
						
				}
			} else {
				for (int c = 0; c < buttons[0].length; c++) {
					if(c%2==0){
						buttons[r][c].setBackground(Color.WHITE);
					}else{
						buttons[r][c].setBackground(Color.GRAY);
					}
						
				}
			}

		}

		buttons[0][0].setIcon(bR);
		buttons[0][1].setIcon(bK);
		buttons[0][2].setIcon(bB);
		buttons[0][3].setIcon(bQ);
		buttons[0][4].setIcon(bKI);
		buttons[0][5].setIcon(bB);
		buttons[0][6].setIcon(bK);
		buttons[0][7].setIcon(bR);
		for (int i = 0; i < 8; i++) {
			buttons[1][i].setIcon(bP);
		}

		buttons[7][0].setIcon(wR);
		buttons[7][1].setIcon(wK);
		buttons[7][2].setIcon(wB);
		buttons[7][3].setIcon(wQ);
		buttons[7][4].setIcon(wKI);
		buttons[7][5].setIcon(wB);
		buttons[7][6].setIcon(wK);
		buttons[7][7].setIcon(wR);
		for (int i = 0; i < 8; i++) {
			buttons[6][i].setIcon(wP);
		}

	}
	//Sets icons for the pieces
	public void createImages() {
		bP = loadIcon("bp.png");
		bB = loadIcon("bb.png");
		bK = loadIcon("bk.png");
		bKI = loadIcon("bKi.png");
		bR = loadIcon("br.png");
		bQ = loadIcon("bq.png");
		wP = loadIcon("wp.png");
		wB = loadIcon("wb.png");
		wK = loadIcon("wk.png");
		wKI = loadIcon("wKi.png");
		wR = loadIcon("wr.png");
		wQ = loadIcon("wq.png");
		empty = loadIcon("empty.jpg");
	}
//Adds button listeners to the board
	public void addButtonListener(int r, int c, ActionListener al) {
		buttons[r][c].addActionListener(al);
	}
//Loads the icons and throws exception if unable to load
	private static ImageIcon loadIcon(String name) {
		URL imageUrl = View2.class.getResource(name);
		if (imageUrl == null) {
			throw new RuntimeException("Icon resource not found.");
		}
		return new ImageIcon(imageUrl);
	}
//Attaches Icon to new spot
	public void attachIcon(int r1, int c1, int r2, int c2) {
		lastMove = new Move(r1, c1, r2, c2);
		ImageIcon curImage = (ImageIcon) buttons[r1][c1].getIcon();
		if (buttons[r2][c2].getIcon() != null) {
			lastIcon = (ImageIcon) buttons[r2][c2].getIcon();
		} else {
			lastIcon = null;
		}

		buttons[r2][c2].setIcon(curImage);
		buttons[r1][c1].setIcon(null);
	}
//Redraws board with the correct icons at each spot
	public void redrawBoard() {
		
		if(game.currentPlayer()==Player.BLACK){
			turnLabel.setText(BlackTurn);
		}else{
			turnLabel.setText(WhiteTurn);
		}
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				buttons[r][c].setIcon(getImage(game.pieceAt(r, c)));

			}
		}
	}
//Returns the proper image of the chess piece in param
	public ImageIcon getImage(IChessPiece thePiece) {
		if (thePiece == null) {
			return null;
		}

		if (thePiece.player() == Player.WHITE) {
			if (thePiece.name().equals("King")) {
				return wKI;
			} else if (thePiece.name().equals("Queen")) {
				return wQ;
			} else if (thePiece.name().equals("Knight")) {
				return wK;
			} else if (thePiece.name().equals("Bishop")) {
				return wB;
			} else if (thePiece.name().equals("Rook")) {
				return wR;
			} else
				return wP;
		} else {
			if (thePiece.name().equals("King")) {
				return bKI;
			} else if (thePiece.name().equals("Queen")) {
				return bQ;
			} else if (thePiece.name().equals("Knight")) {
				return bK;
			} else if (thePiece.name().equals("Bishop")) {
				return bB;
			} else if (thePiece.name().equals("Rook")) {
				return bR;
			} else
				return bP;
		}
	}
//Resets the board to original state
	public void reset() {
		board.removeAll();
		setBoard();
	}
//Adds listener to quit button
	public void addQuitButtonListener(ActionListener quitButtonListener) {
		quitButton.addActionListener(quitButtonListener);
	}
	//Adds listener to reset button
	public void addResetButtonListener(ActionListener resetButtonListener) {
		resetButton.addActionListener(resetButtonListener);
	}
//getter method for the last move executed
	public Move getMove() {
		return lastMove;
	}
//Unused didnt want to have to interact with pane everytime a move was invalid
	public void invalidMessage() {
		JOptionPane.showMessageDialog(null, "Invalid Move Try Again");
	}
//Blinks the two selected spaces on the board red if move is invalid
//Replaces option pane above
	public void blinkTimer(Move move){
		final Move blinkMove = new Move(move.fromRow,move.fromColumn,move.toRow,move.toColumn);
		
		Timer tt = new Timer(200, new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if((blinkMove.fromColumn+blinkMove.fromRow) %2==0){
        			buttons[blinkMove.fromRow][blinkMove.fromColumn].setBackground(Color.GRAY);
        		}else{
        			buttons[blinkMove.fromRow][blinkMove.fromColumn].setBackground(Color.WHITE);
        		}
        		if((blinkMove.toRow+blinkMove.toColumn)%2==0){
        			buttons[blinkMove.toRow][blinkMove.toColumn].setBackground(Color.GRAY);
        		}else{
        			buttons[blinkMove.toRow][blinkMove.toColumn].setBackground(Color.WHITE);
        		}
        		
            }
        });
        tt.start();

        buttons[blinkMove.fromRow][blinkMove.fromColumn].setBackground(Color.RED);
        buttons[blinkMove.toRow][blinkMove.toColumn].setBackground(Color.RED);
        
    }

//Method used in testing
	public void firstClick(int r, int c) {
		String message = "First click was " + r + " " + c;
		JOptionPane.showMessageDialog(null, message);
	}
//Method used in testing
	public void didItMove() {
		JOptionPane.showMessageDialog(null, "icon should have moved");
	}
//Method used in testing
	public void secondClick(int r, int c) {
		String message = "Second click was " + r + " " + c;
		JOptionPane.showMessageDialog(null, message);
	}
//Displays option pane when a player is in check or the game is won or tied
	public void gameOver(GameStatus status) {
		if (status.equals(GameStatus.BLACKINCHECK)) {
			JOptionPane.showMessageDialog(null, "Black is in check!");
		} else if (status.equals(GameStatus.WHITEINCHECK)) {
			JOptionPane.showMessageDialog(null, "White is in check!");
		} else if (status.equals(GameStatus.CHECKMATEWHITE)) {
			JOptionPane.showMessageDialog(null, "Black wins!!");
			for (int r = 0; r < buttons.length; r++) {
				for (int c = 0; c < buttons[0].length; c++) {
					buttons[r][c].setEnabled(false);
				}
			}
		} else if (status.equals(GameStatus.CHECKMATEBLACK)) {
			JOptionPane.showMessageDialog(null, "White wins!!");
			for (int r = 0; r < buttons.length; r++) {
				for (int c = 0; c < buttons[0].length; c++) {
					buttons[r][c].setEnabled(false);
				}
			}
		}else if(status.equals(GameStatus.STALEMATE)){
			JOptionPane.showMessageDialog(null, "STALEMATE!!");
			for (int r = 0; r < buttons.length; r++) {
				for (int c = 0; c < buttons[0].length; c++) {
					buttons[r][c].setEnabled(false);
				}
			}
		}

	}
//Method for user to select kinged piece
	public IChessPiece askUser() {
		Object[] possibleValues = { "Rook", "Knight", "Bishop", "Queen" };
		Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
				"Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
				possibleValues[0]);
		IChessPiece piece;
		if (selectedValue.equals("Rook")) {
			return piece = new Rook(game.currentPlayer().next());
		} else if (selectedValue.equals("Knight")) {
			return piece = new Knight(game.currentPlayer().next());
		} else if (selectedValue.equals("Bishop")) {
			return piece = new Bishop(game.currentPlayer().next());
		} else if (selectedValue.equals("Queen")) {
			return piece = new Queen(game.currentPlayer().next());
		} else
			return null;
	}
//Sets all buttons enabled
	public void setAllTrue() {
		for(int i=0;i<8;i++){
			for(int k=0;k<8;k++){
				buttons[i][k].setEnabled(true);
			}
		}
		
	}
}
