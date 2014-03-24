package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import view.View;
import model.GameEngine;


public class Presenter {
	View vision;
	GameEngine engine;
	boolean[] cardSelected, betSelected;
	JButton[] cards,bets;
	int currentBet,credits;
	int count=1;
	public Presenter(GameEngine engine,View view){
		currentBet =-1;
		credits=0;
		this.vision = view;
		this.engine = engine;
		cardSelected = new boolean[5];
		betSelected = new boolean[5];
		for(int i=0;i<5;i++){
			cardSelected[i]=false;
		}
		
		for(int k=0;k<5;k++){
			betSelected[k]=false;
		}
		
		vision.addBetListener(new betButtonListener());
		vision.addHandButtonListener(new HandButtonListener());
		vision.addDealButtonListener(new DealButtonListener());
		vision.addDrawButtonListener(new DrawButtonListener());
		vision.addfundButtonListener(new FundButtonListener());
	}
	private class DealButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(currentBet==-1){
				JOptionPane.showMessageDialog(null,"You must select a bet first.");
			}else if(credits<currentBet){
				JOptionPane.showMessageDialog(null, "You have to add funds");
			}else{
				vision.deSelect();
				engine.deal();
			    vision.setImages(engine.getPlayerHand());
			    vision.disableDeal();
			    vision.disableBets();
			    vision.reEnableDraw();
			    
			    
			}
		}
	}
	private class DrawButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			engine.draw(vision.getHandSelection());
			vision.setImages(engine.getPlayerHand());
			vision.setMessageLabel(engine.getMessage());
			vision.setFundLabel(engine.getCredits());
			vision.reEnableDeal();
			vision.reEnableBets();
			credits = engine.getCredits();
			vision.deSelect();
			vision.disableDraw();
			
			
		}
	}
	private class FundButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int numCredits = (int) vision.funds.getSelectedItem();
			engine.addCredits(numCredits);
			vision.setFundLabel(engine.getCredits());
			credits = engine.getCredits();
		}
	}
	class HandButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent al) {
            int num = getButtonIndexPressed(al);
            vision.cardSelected(num);
            
        }

        public int getButtonIndexPressed(ActionEvent e) {
            cards = vision.getButtons();
            for (int i = 0; i < cards.length; i++)
                if (e.getSource() == cards[i])
                    return i;
            return -1;
        }
    }
	class betButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent al) {
            int num = getButtonIndexPressed(al);
            vision.betSelected(num);
            for(int i =0;i<5;i++){
            	betSelected[i]=false;
            }
			betSelected[num] = true;
			engine.changeBet(num+1);
			currentBet=num+1;
        }

        public int getButtonIndexPressed(ActionEvent e) {
            bets = vision.getBets();
            for (int i = 0; i < bets.length; i++)
                if (e.getSource() == bets[i])
                    return i;
            return -1;
        }
    }
	
	public static void main(String[] args) throws IOException {
		View vies = new View();
		GameEngine game = new GameEngine(0);
		new Presenter(game,vies);
	}

}
