package model;

import java.util.Collections;
import java.util.Stack;


public class GameEngine {
	int credits,bet;
	Stack<Card> holderDeck,tempDeck;
	Card[] playerHand;
	boolean[] handWin;
	String Message;

	public GameEngine(int credits) {
		this.credits = credits;
		this.holderDeck = new Stack<Card>();
		tempDeck = new Stack<Card>();
		makeDeck();
		playerHand = new Card[5];
		handWin = new boolean[5];
		Message = "Last Hand Didn't Win";
	}
	public String getMessage(){
		return Message;
	}
	public Card[] getPlayerHand(){
		return playerHand;
	}
	public void addCredits(int toAdd){
		credits+=toAdd;
	}

	public void makeDeck() {
		for (Suits suit : Suits.values()) {
			for (Rank rank : Rank.values()) {
				holderDeck.add(new Card(suit, rank));
			}
		}
		Collections.shuffle(holderDeck);
	}

	public void deal() {
		holderDeck.removeAllElements();
		makeDeck();
		for (int i = 0; i < 5; i++) {
			playerHand[i]=holderDeck.pop();
		}
	}
	public void draw(boolean[] selected){
		for(int i=0;i<5;i++){
			if(selected[i]==false){
				playerHand[i]= holderDeck.pop();
			}
		}
		getPayout();
		
	}
	public void changeBet(int newBet){
		bet = newBet;
	}
	private void getPayout() {
		int[] tempArr= new int[15];
		for(int k=0;k<15;k++){
			tempArr[k]=0;
		}
		for(int i=0;i<5;i++){
			if(playerHand[i].getRank()==Rank.ACE){
				tempArr[playerHand[i].getCardValue()]++;
				tempArr[14]++;
			}else{
				tempArr[playerHand[i].getCardValue()]++;
			}
		}
		if(fourAces(tempArr)==true){
			Message="4 ACES";
			credits+= (400*bet);
		}else if(royalFlush(tempArr)==true){
			Message="Royal Flush";
			credits+= (250*bet);
		}else if(fourLow(tempArr)==true){
			Message="4 of a Kind";
			credits+= (100*bet);
		}else if(straightFlush(tempArr)==true){
			Message="Straight Flush";
			credits+= (60*bet);
		}else if(fourHigh(tempArr)==true){
			Message="4 of a Kind";
			credits+= (50*bet);
		}else if(fullHouse(tempArr)==true){
			Message="Full House";
			credits+= (7*bet);
		}else if(flush(tempArr)==true){
			Message="Flush";
			credits+= (5*bet);
		}else if(straight(tempArr)==true){
			Message="Straight";
			credits+= (4*bet);
		}else if(threeKind(tempArr)==true){
			Message="3 of a Kind";
			credits+= (3*bet);
		}else if(twoPair(tempArr)==true){
			Message = "Two Pair";
			credits+= (1*bet);
		}else if(jackBetter(tempArr)==true){
			Message = "Jacks or Better";
			credits+= (1*bet);
		}else{
			Message = "LOSS";
			credits-=bet;
		}
			
	}
	private boolean straightFlush(int[] tempArr) {
		int row=0;
		for(int i=0;i<4;i++){
			if(playerHand[i].getSuit()!=playerHand[i+1].getSuit()){
				return false;
			}
		}
		for(int k=0;k<15;k++){
			if(tempArr[k]>1){
				return false;
			}else if(tempArr[k]==1){
				row++;
				if(row==5){
					return true;
				}
			}else
				row=0;
		}
		return false;
		
	}
	private boolean jackBetter(int[] tempArr) {
		for(int i=11;i<15;i++){
			if(tempArr[i]>1){
				return true;
			}
		}
		return false;
	}
	private boolean twoPair(int[] tempArr) {
		int pairCount=0;
		for(int i=1;i<14;i++){
			if(tempArr[i]==2){
				pairCount++;
			}
			if(pairCount==2){
				return true;
			}
		}
		return false;
	}
	private boolean threeKind(int[] tempArr) {
		for(int i=1;i<14;i++){
			if(tempArr[i]==3){
				return true;
			}
		}
		return false;
	}
	private boolean straight(int[] tempArr) {
		int row=0;
		for(int k=0;k<15;k++){
			if(tempArr[k]>1){
				return false;
			}else if(tempArr[k]==1){
				row++;
				if(row==5){
					return true;
				}
			}else
				row=0;
		}
		return false;
	}
	private boolean flush(int[] tempArr) {
		for(int i=0;i<4;i++){
			if(playerHand[i].getSuit()!=playerHand[i+1].getSuit()){
				return false;
			}
		}
		return true;
	}
	private boolean fullHouse(int[] tempArr) {
		int threeCount=0;
		int twoCount=0;
		for(int i=1;i<15;i++){
			if(tempArr[i]==3){
				threeCount++;
			}else if(tempArr[i]==2){
				twoCount++;
			}
			if(twoCount==1&&threeCount==1){
				return true;
			}
		}
		return false;
	}
	private boolean fourHigh(int[] tempArr) {
		for(int i=5;i<14;i++){
			if(tempArr[i]==4){
				return true;
			}
		}
		return false;
	}
	private boolean fourLow(int[] tempArr) {
		for(int i=2;i<5;i++){
			if(tempArr[i]==4){
				return true;
			}
		}
		return false;
	}
	private boolean fourAces(int[] tempArr){
		if(tempArr[1]==4){
			return true;
		}else
		return false;
	}
	private boolean royalFlush(int[] tempArr){
		for(int i=0;i<4;i++){
			if(playerHand[i].getSuit()!=playerHand[i+1].getSuit()){
				return false;
			}
		}
		for(int i=10;i<15;i++){
			if(tempArr[i]!=1){
				return false;
			}
		}
		return true;
	}
	public int getCredits() {
		return credits;
	}

}
