package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Card;
import model.Suits;

@SuppressWarnings("serial")
public class View extends JPanel {
	BufferedImage h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, hj, hq, hk, d1, d2,
			d3, d4, d5, d6, d7, d8, d9, d10, dj, dq, dk, s1, s2, s3, s4, s5,
			s6, s7, s8, s9, s10, sj, sq, sk, c1, c2, c3, c4, c5, c6, c7, c8,
			c9, c10, cj, cq, ck, nullImage, payOuts;
	BufferedImage[] cardsImages;
	String[][] payData;
	String[] columnNames;
	JButton[] buttons, betAmt;
	JButton draw, deal, fundButton;
	boolean[] CardsSelected, betSelected;
	JPanel hand, functions, payouts, bets, handFunc, gameBoard, info;
	JFrame frame;
	JTable pay;
	Color outLine;
	@SuppressWarnings("rawtypes")
	public JComboBox funds;
	ArrayList<Integer> adFunds;
	JLabel fundLabel, comboLabel, messageLabel;
	int credits, bet;
	MatteBorder cardOutline;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public View() throws IOException {
		credits = 0;
		bet =0;
		cardOutline = new MatteBorder(5,5,5,5,Color.white);
		adFunds = new ArrayList<Integer>();
		for (int i = 0; i < 100; ++i) {
			adFunds.add(i);
		}
		messageLabel = new JLabel("Add Funds to begin!");
		messageLabel.setFont(new Font("Arial Black", Font.BOLD, 13));
		fundLabel = new JLabel("You have Zero credits.");
		fundLabel.setFont(new Font("Arial Black", Font.BOLD, 13));
		comboLabel = new JLabel("Amount to Add");
		comboLabel.setFont(new Font("Arial Black", Font.BOLD, 13));
		funds = new JComboBox(adFunds.toArray());
		fundButton = new JButton("Add Credits");
		outLine = new Color(178, 34, 34);
		payData = new String[11][6];
		columnNames = new String[] { "Royal Flush", "250", "500", "750",
				"1000", "4000" };
		draw = new JButton("Draw");
		deal = new JButton("Deal");

		betAmt = new JButton[5];
		betAmt[0] = new JButton("Bet One");
		betAmt[0].setPreferredSize(betAmt[0].getPreferredSize());
		betAmt[1] = new JButton("Bet Two");
		betAmt[1].setPreferredSize(betAmt[1].getPreferredSize());
		betAmt[2] = new JButton("Bet Three");
		betAmt[2].setPreferredSize(betAmt[2].getPreferredSize());
		betAmt[3] = new JButton("Bet Four");
		betAmt[3].setPreferredSize(betAmt[3].getPreferredSize());
		betAmt[4] = new JButton("Bet Max");
		betAmt[4].setPreferredSize(betAmt[4].getPreferredSize());
		gameBoard = new JPanel();
		buildTable();
		pay = new JTable(payData, columnNames);
		hand = new JPanel();
		bets = new JPanel();
		handFunc = new JPanel();
		frame = new JFrame();
		buttons = new JButton[5];
		createImages();
		CardsSelected = new boolean[5];
		betSelected = new boolean[5];
		for (int i = 0; i < 5; i++) {
			buttons[i] = new JButton();
			buttons[i].setIcon(new ImageIcon(nullImage));
			buttons[i].setBorder(cardOutline);
			hand.add(buttons[i]);
		}
		for (int k = 0; k < 5; k++) {
			CardsSelected[k] = false;
		}
		for (int k = 0; k < 5; k++) {
			betSelected[k] = false;
		}

		functions = new JPanel(new FlowLayout());
		payouts = new JPanel(new GridLayout());
		gameBoard.setLayout(new GridLayout(0, 1));
		handFunc.setLayout(new GridLayout(0, 1));
		bets.setLayout(new FlowLayout());
		info = new JPanel(new GridLayout(0, 1));

		pay.setBackground(Color.blue);
		pay.setForeground(Color.yellow);
		pay.setAlignmentY(CENTER_ALIGNMENT);
		pay.setFont(new Font("Arial Black", Font.BOLD, 15));
		resizeColumnWidth(pay);
		hand.setBackground(Color.blue);
		hand.setAlignmentX(CENTER_ALIGNMENT);
		hand.setAlignmentY(CENTER_ALIGNMENT);
		;
		bets.setBackground(Color.blue);
		functions.setBackground(Color.blue);
		hand.setMinimumSize(getPreferredSize());
		bets.setPreferredSize(getPreferredSize());
		info.setBackground(Color.blue);
		fundLabel.setForeground(Color.yellow);
		messageLabel.setForeground(Color.yellow);
		comboLabel.setForeground(Color.yellow);
		handFunc.setAlignmentY(TOP_ALIGNMENT);

		functions.add(deal);
		functions.add(draw);
		for (int i = 0; i < 5; i++) {
			bets.add(betAmt[i]);
		}
		payouts.add(pay);
		handFunc.add(functions);
		handFunc.add(bets);
		info.add(messageLabel);
		info.add(fundLabel);
		info.add(comboLabel);
		info.add(funds);
		info.add(fundButton);
		hand.add(info);

		gameBoard.add(payouts);
		gameBoard.add(hand);
		gameBoard.add(handFunc);
		frame.add(gameBoard);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
	public void setMessageLabel(String message){
		messageLabel.setText(message);
	}
	public void setFundLabel(int numCreds){
		fundLabel.setText("You have "+numCreds+" credits.");
	}
	public void setImages(Card[] cards) {
		for (int i = 0; i < 5; i++) {
			buttons[i].setIcon(new ImageIcon(handHelper(cards[i])));
		}
	}

	public BufferedImage handHelper(Card card) {
		Card pcard = card;
		if (pcard.getSuit() == Suits.HEARTS) {
			return cardsImages[card.getRank().ordinal()];
		} else if (pcard.getSuit() == Suits.DIAMONDS) {
			return cardsImages[card.getRank().ordinal() + 13];
		} else if (pcard.getSuit() == Suits.SPADES) {
			return cardsImages[card.getRank().ordinal() + 26];
		} else {
			return cardsImages[card.getRank().ordinal() + 39];
		}
	}

	public void disableBets() {
		for (int i = 0; i < 5; i++) {
			betAmt[i].setEnabled(false);
		}
	}

	public void disableDeal() {
		deal.setEnabled(false);
	}

	public void disableDraw() {
		draw.setEnabled(false);
	}

	public void reEnableBets() {
		for (int i = 0; i < 5; i++) {
			betAmt[i].setEnabled(true);
		}
	}
	public void reEnableDeal(){
		deal.setEnabled(true);
	}
	public void reEnableDraw(){
		draw.setEnabled(true);
	}

	public void addfundButtonListener(ActionListener fundButtonListener) {
		fundButton.addActionListener(fundButtonListener);
	}

	public void addDealButtonListener(ActionListener dealButtonListener) {
		deal.addActionListener(dealButtonListener);
	}

	public void addDrawButtonListener(ActionListener drawButtonListener) {
		draw.addActionListener(drawButtonListener);
	}

	public void addHandButtonListener(ActionListener al) {
		for (int i = 0; i < 5; i++) {
			buttons[i].addActionListener(al);
		}
	}

	public void addBetListener(ActionListener al) {
		for (int i = 0; i < 5; i++) {
			betAmt[i].addActionListener(al);
		}
	}

	public void cardSelected(int num) {
		if (buttons[num].isSelected() == true) {
			buttons[num].setBorderPainted(false);
			buttons[num].setSelected(false);
			CardsSelected[num] = false;
		} else {
			buttons[num].setBorder(new MatteBorder(5, 5, 5, 5, outLine));
			buttons[num].setBorderPainted(true);
			
			buttons[num].setSelected(true);
			CardsSelected[num] = true;
		}

	}

	public void betSelected(int num) {
		for (int i = 0; i < 5; i++) {
			betAmt[i].setSelected(false);
			betAmt[i].setBorderPainted(false);
			betSelected[i] = false;
		}
		betAmt[num].setBorder(new MatteBorder(5, 5, 5, 5, outLine));
		betAmt[num].setBorderPainted(true);
		betAmt[num].setSelected(true);
		betSelected[num] = true;
		bet = num+1;
	}
	public int getBet(){
		return bet;
	}

	public void deSelect() {
		for (int i = 0; i < 5; i++) {
			buttons[i].setSelected(false);
			buttons[i].setBorderPainted(false);
			CardsSelected[i] = false;
		}
	}

	public JButton[] getButtons() {
		return buttons;
	}
	public boolean[] getHandSelection(){
		return CardsSelected;
	}
	public JButton[] getBets() {
		return betAmt;
	}
	public boolean[] getBetSelection(){
		return betSelected;
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	public void buildTable() {
		payData[0][0] = "Royal Flush";
		payData[0][1] = "250";
		payData[0][2] = "500";
		payData[0][3] = "700";
		payData[0][4] = "1000";
		payData[0][5] = "4000";

		payData[1][0] = "Straight Flush";
		payData[1][1] = "60";
		payData[1][2] = "120";
		payData[1][3] = "180";
		payData[1][4] = "240";
		payData[1][5] = "300";

		payData[2][0] = "4 Aces";
		payData[2][1] = "400";
		payData[2][2] = "800";
		payData[2][3] = "1200";
		payData[2][4] = "1600";
		payData[2][5] = "2000";

		payData[3][0] = "4 2s,3s and 4s";
		payData[3][1] = "100";
		payData[3][2] = "200";
		payData[3][3] = "300";
		payData[3][4] = "400";
		payData[3][5] = "500";

		payData[4][0] = "4 5s-Ks";
		payData[4][1] = "50";
		payData[4][2] = "100";
		payData[4][3] = "150";
		payData[4][4] = "200";
		payData[4][5] = "250";

		payData[5][0] = "Full House";
		payData[5][1] = "7";
		payData[5][2] = "14";
		payData[5][3] = "21";
		payData[5][4] = "28";
		payData[5][5] = "35";

		payData[6][0] = "Flush";
		payData[6][1] = "5";
		payData[6][2] = "10";
		payData[6][3] = "15";
		payData[6][4] = "20";
		payData[6][5] = "25";

		payData[7][0] = "Straight";
		payData[7][1] = "4";
		payData[7][2] = "8";
		payData[7][3] = "12";
		payData[7][4] = "16";
		payData[7][5] = "20";

		payData[8][0] = "Three of a Kind";
		payData[8][1] = "3";
		payData[8][2] = "6";
		payData[8][3] = "9";
		payData[8][4] = "12";
		payData[8][5] = "15";

		payData[9][0] = "Two Pair";
		payData[9][1] = "1";
		payData[9][2] = "2";
		payData[9][3] = "3";
		payData[9][4] = "4";
		payData[9][5] = "5";

		payData[10][0] = "Jacks or Better";
		payData[10][1] = "1";
		payData[10][2] = "2";
		payData[10][3] = "3";
		payData[10][4] = "4";
		payData[10][5] = "5";

	}
	private static BufferedImage loadIcon(String name) throws IOException {
		BufferedImage image = ImageIO.read(View.class.getResourceAsStream(name));
		if (image == null) {
			throw new RuntimeException("Icon resource not found.");
		}
		
		return image;
	}

	public BufferedImage[] createImages() throws IOException {

		cardsImages = new BufferedImage[53];
		cardsImages[0] = loadIcon("/images/h1.gif");
		cardsImages[1] = loadIcon("/images/h2.gif");;
		cardsImages[2] = loadIcon("/images/h3.gif");;
		cardsImages[3] = loadIcon("/images/h4.gif");;
		cardsImages[4] = loadIcon("/images/h5.gif");;
		cardsImages[5] = loadIcon("/images/h6.gif");;
		cardsImages[6] = loadIcon("/images/h7.gif");;
		cardsImages[7] = loadIcon("/images/h8.gif");;
		cardsImages[8] = loadIcon("/images/h9.gif");;
		cardsImages[9] = loadIcon("/images/h10.gif");;
		cardsImages[10] = loadIcon("/images/hj.gif");;
		cardsImages[11] = loadIcon("/images/hq.gif");;
		cardsImages[12] = loadIcon("/images/hk.gif");;
		cardsImages[13] = loadIcon("/images/d1.gif");
		cardsImages[14] = loadIcon("/images/d2.gif");;
		cardsImages[15] = loadIcon("/images/d3.gif");;
		cardsImages[16] = loadIcon("/images/d4.gif");;
		cardsImages[17] = loadIcon("/images/d5.gif");;
		cardsImages[18] = loadIcon("/images/d6.gif");;
		cardsImages[19] = loadIcon("/images/d7.gif");;
		cardsImages[20] = loadIcon("/images/d8.gif");;
		cardsImages[21] = loadIcon("/images/d9.gif");;
		cardsImages[22] = loadIcon("/images/d10.gif");;
		cardsImages[23] = loadIcon("/images/dj.gif");;
		cardsImages[24] = loadIcon("/images/dq.gif");;
		cardsImages[25] = loadIcon("/images/dk.gif");;
		cardsImages[26] = loadIcon("/images/s1.gif");
		cardsImages[27] = loadIcon("/images/s2.gif");
		cardsImages[28] = loadIcon("/images/s3.gif");
		cardsImages[29] = loadIcon("/images/s4.gif");
		cardsImages[30] = loadIcon("/images/s5.gif");
		cardsImages[31] = loadIcon("/images/s6.gif");
		cardsImages[32] = loadIcon("/images/s7.gif");
		cardsImages[33] = loadIcon("/images/s8.gif");
		cardsImages[34] = loadIcon("/images/s9.gif");
		cardsImages[35] = loadIcon("/images/s10.gif");
		cardsImages[36] = loadIcon("/images/sj.gif");
		cardsImages[37] = loadIcon("/images/sq.gif");
		cardsImages[38] = loadIcon("/images/sk.gif");
		cardsImages[39] = loadIcon("/images/c1.gif");
		cardsImages[40] = loadIcon("/images/c2.gif");
		cardsImages[41] = loadIcon("/images/c3.gif");
		cardsImages[42] = loadIcon("/images/c4.gif");
		cardsImages[43] = loadIcon("/images/c5.gif");
		cardsImages[44] = loadIcon("/images/c6.gif");
		cardsImages[45] = loadIcon("/images/c7.gif");
		cardsImages[46] = loadIcon("/images/c8.gif");
		cardsImages[47] = loadIcon("/images/c9.gif");
		cardsImages[48] = loadIcon("/images/c10.gif");
		cardsImages[49] = loadIcon("/images/cj.gif");
		cardsImages[50] = loadIcon("/images/cq.gif");
		cardsImages[51] = loadIcon("/images/ck.gif");

		nullImage = loadIcon("/images/null.gif");
		payOuts = loadIcon("/images/null.gif");
			
         return cardsImages;
	}
}
