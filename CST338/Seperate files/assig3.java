package hw3;
import hw3.Card;
import hw3.Hand;

public class assign3 {

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
      
      //Reset cards
      Card newCard1 = new Card('j', Card.Suit.CLUBS);
      Card newCard2 = new Card('Q', Card.Suit.HEARTS);
      Card newCard3 = new Card('2', Card.Suit.SPADES);

      //Make a hand
      Hand hand = new Hand();
      while (hand.getNumCards() < hand.MAX_CARDS) {
         hand.takeCard(newCard1);
         hand.takeCard(newCard2);
         hand.takeCard(newCard3);
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
   }
}
