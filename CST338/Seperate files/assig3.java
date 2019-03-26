package hw3;
import hw3.Card;
import hw3.Hand;

public class assig3 {

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      
      Card newCard1 = new Card('j', Card.Suit.CLUBS);
      Card newCard2 = new Card('Q', Card.Suit.HEARTS);
      Card newCard3 = new Card('D', Card.Suit.SPADES);
      
      System.out.println("Card1: " + newCard1.toString());
      System.out.println("Card2: " + newCard2.toString());
      System.out.println("Card3: " + newCard3.toString());
      
      //Now we make a good card bad
      System.out.println("Now we make a good card turn bad");
      newCard1.set('D', Card.Suit.HEARTS);
      
      
      System.out.println("Card1: " + newCard1.toString());
      System.out.println("Card2: " + newCard2.toString());
      System.out.println("Card3: " + newCard3.toString());
      
      //New Cards cards
      Card newCard11 = new Card('j', Card.Suit.CLUBS);
      Card newCard22 = new Card('Q', Card.Suit.HEARTS);
      Card newCard33 = new Card('2', Card.Suit.SPADES);

      //Make a hand
      Hand hand = new Hand();
      while (hand.getNumCards() < hand.MAX_CARDS) {
         hand.takeCard(newCard11);
         hand.takeCard(newCard22);
         hand.takeCard(newCard33);
      }
      System.out.println("Hand full.\nShowing hand.");
      System.out.println(hand.toString());
      
      //Play the hand
      int i;
      Card showCard;
      Card inspectedCard;
      
      System.out.println("Testing inspectCard()");
      inspectedCard = hand.inspectCard(hand.MAX_CARDS - 1);
      System.out.println(inspectedCard.toString());
      inspectedCard = hand.inspectCard(hand.MAX_CARDS);
      System.out.println(inspectedCard.toString());
      
      System.out.println("\nPlaying hand:\n");
      for (i = 0; i < (hand.MAX_CARDS); i++) {
         showCard = hand.playCard();
         System.out.println((hand.getNumCards() + 1) + ": " 
         + showCard.toString());
      }
      
      System.out.println("\nEmpty hand:\n" + hand.toString());
      
      
      int numPacks = 2;
      System.out.println();
      System.out.println("Now we create a Deck with " + numPacks + " packs of card in it");
      Deck myDeck = new Deck(numPacks);
      
      System.out.println("Now we deal all the cards");
      
      for (int i = 0; i < Deck.CARDS_PER_PACK * numPacks; i++) {
         Card dealtCard = myDeck.dealCard();
         System.out.println("Card" + i + ": " + dealtCard.toString());   
      }
      
      System.out.println("End of dealing " + numPacks + "pack(s) of unshuffled cards");
      System.out.println();
      System.out.println("Re-Initializing Deck with " + numPacks + "packs of cards");
      myDeck = new Deck(numPacks);
      
      System.out.println("Shuffing deck of cards");
      myDeck.shuffle();
      System.out.println("Dealing shuffled cards");
      for (int i = 0; i < Deck.CARDS_PER_PACK * numPacks; i++) {
         Card dealtCard = myDeck.dealCard();
         System.out.println("Card" + i + ": " + dealtCard.toString());   
      }
      System.out.println("End of dealing " + numPacks + "pack(s) of shuffled cards");
      System.out.println();
      
      System.out.println("Repeating above exercise with 1 pack of cards");
      System.out.println("Instantiating single deck with default constructor");
      myDeck = new Deck();
      System.out.println("Dealing unshuffled cards");
      for (int i = 0; i < Deck.CARDS_PER_PACK * 1; i++) {
         Card dealtCard = myDeck.dealCard();
         System.out.println("Card" + i + ": " + dealtCard.toString());   
      }
      System.out.println("End of dealing unshuffled single deck");
      System.out.println("Re-instantiating single deck");
      myDeck = new Deck();
      System.out.println("Now shuffling the single deck");
      myDeck.shuffle();
      System.out.println("Deck is now shuffled");
      System.out.println("Now dealing shuffled cards");
      for (int i = 0; i < Deck.CARDS_PER_PACK * 1; i++) {
         Card dealtCard = myDeck.dealCard();
         System.out.println("Card" + i + ": " + dealtCard.toString());   
      }
      System.out.println("End of dealing shuffled cards");
      
      
      
   }
}
