package model;


public class Card{
    Suits suit;
    Rank rank;
    
    public Card(Suits suit, Rank rank){
        this.suit = suit;
        this.rank=rank;
    }

    public Suits getSuit(){
        return suit;
    }

    public Rank getRank(){
        return rank;
    }

    public int getCardValue(){
        return rank.getValue();
    }
    
}