package hw3;

public class Hand {
   public static final int MAX_CARDS = 50;
   private Card[] myCards;
   private int numCards = 0;
   
   // Constructor selected hand size
   public Hand(int handSize) {
      if (handSize <= 0) {
         System.out.println("Hand size cannot be equal to or less than zero.");
         System.exit(0);
      }
      myCards = new Card[MAX_CARDS];
   }
   
   // General constructor
   public Hand() {
      myCards = new Card[MAX_CARDS];
   }
   
   // resets the numCards count, essentially resetting the hand
   void resetHand() {
      if (numCards == 0) {
         System.out.println("Empty hand.");
         return;
      }
      else {
         numCards = 0;
         return;
      }
   }
   
   // Adds a card to the hand up to the limit (50)
   boolean takeCard(Card card) {
      if (numCards >= MAX_CARDS) {
         return false;
      }
      
      if (card.getErrorFlag() == false) {
         myCards[numCards] = new Card(card);
         numCards++;
         return true;
      }
      else {
         return false;
      }
   }
   
   // Returns a card object, then shortens the amount of cards in the hand by 1
   public Card playCard() {
      Card playedCard = new Card(myCards[numCards - 1]);
      numCards--;
      return playedCard;
   }
   
   // Creates a string of the contents of the hand with formatting
   public String toString() {
      String handString = "";
      
      if (numCards == 0) {
         return handString = "Hand: ( )";
      }
      
      handString = "Hand:\n( ";
      
      for (int i = 0; i <= numCards - 1; i++) {
         handString += myCards[i].toString();
         if (i == 0) {
            handString += " , ";
         }
         else if (i == numCards - 1) {
            handString += " )\n";
         }
         else if (i%4 == 0) {
            handString += "\n";
         }
         else {
            handString += " , ";
         }
      }
      return handString;
   }
   
   // Accessor for numCards
   public int getNumCards() {
      return numCards;
   }
   
   // If the passed index is out of bounds or indicates an empty index, it
   // returns a bad card, otherwise returns the card at the index
   public Card inspectCard(int k) {
      if ((k >= MAX_CARDS) || (k < 0) || (myCards[k] == null)) {
         Card badCard = new Card();
         badCard.errorFlag = true;
         return badCard;
      }
      else {
         Card goodCard = myCards[k];
         return goodCard;
      }
   }
}