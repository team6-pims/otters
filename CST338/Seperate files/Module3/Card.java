package hw3;

public class Card {

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








