
public class assign3 {

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      
      Card newCard1 = new Card('j', Card.Suit.CLUBS);
      Card newCard2 = new Card('Q', Card.Suit.HEARTS);
      Card newCard3 = new Card('2', Card.Suit.SPADES);
      
      System.out.println("Card1: " + newCard1.toString());
      System.out.println("Card2: " + newCard2.toString());
      System.out.println("Card3: " + newCard3.toString());
      
      /*Now we make a good card bad
      System.out.println("Now we make a good card turn bad");
      newCard1.set('D', Card.Suit.HEARTS);
      
      
      System.out.println("Card1: " + newCard1.toString());
      System.out.println("Card2: " + newCard2.toString());
      System.out.println("Card3: " + newCard3.toString());
      
      */
      //Make a hand
      Hand hand = new Hand();
      while (hand.getNumCards() < hand.MAX_CARDS) {
         hand.takeCard(newCard1);
         hand.takeCard(newCard2);
         hand.takeCard(newCard3);
      }
      System.out.println("Hand full.\nShowing hand.");
      System.out.println(hand.toString());
      
      //Play half the hand, inspect a bad card, then play the remainder
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

class Hand {
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

class Card {

   public enum Suit {
      CLUBS, DIAMONDS, HEARTS, SPADES;
   }

   public char value;
   public Suit suit;
   public boolean errorFlag = false;


   
   /* Constructors */
   public Card(char value, Suit suit) {
      set(value, suit);
   }

   public Card() {
      set('A', Suit.SPADES );
   }
   
   public Card (Card card2copy) {
      set( card2copy.value, card2copy.suit );
   }
   
   // getters
   
   public char getValue() {
      return this.value;
   }
   
   public Suit getSuit() {
      return this.suit;
   }
   
   public boolean getErrorFlag () {
      return this.errorFlag;
   }
   
   /* toString
    * Return a string representation of class
    * or returns [Invaid Card] if errorFlag
    * is true*/
   public String toString() {
      if ( errorFlag ) {
         return "[Invalid Card]";
      } else {
         return value + " of " + suit;
      }
   }

   /*set
    * setter for class variables calls
    * isValid to check for correctness of
    * inputs. If all valid, sets variables
    * if not, then does not set and sets errorFlag
    * to true*/
   public boolean set(char value, Suit suit) {

      value = Character.toUpperCase(value);

      if(isValid(value, suit)) {
         this.value = value;
         this.suit = suit;
         return true;
      } else {
         this.errorFlag = true;
         return false;
      }
   }

   
   /*equals
    * Check for equality, true if equal
    * false if not*/
   public boolean equals( Card card ) {
      return ( ( card.value == this.value ) && ( this.suit == card.suit  ) );
   }


   /*
    * isValid 
    *   Fcn only needs to validate card value,
    *   since input to calling functions take
    *   enum as input, it is automatically
    *   validated
    *   
    *   input is expected to be a capitol character or number as a char
    *   */
   private boolean isValid (char value, Suit suit) {
      char[] acceptableCards = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 
            'T', 'J', 'Q', 'K', };      
      for (char card: acceptableCards) {
         if (card == value) {
            return true;
         }
      }
      return false;   
   }
}