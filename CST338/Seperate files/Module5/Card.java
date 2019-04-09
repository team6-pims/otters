

public class Card {

   public enum Suit {
      CLUBS, DIAMONDS, HEARTS, SPADES;
   }

   public char value;
   public Suit suit;
   public boolean errorFlag = false;

   public static char[] valuRanks = {'2', '3', '4', '5', '6', '7', '8', '9', 
         'T', 'J', 'Q', 'K', 'A' ,'X' }; 

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
            'T', 'J', 'Q', 'K', 'X' };      
      for (char card: acceptableCards) {
         if (card == value) {
            return true;
         }
      }
      return false;   
   }

   /*THIS IS A BIG ASSUMPTION
    * I ASSUME THIS IS A SAFE BY REFERENCE PASSING
    * I.E. THE VOID DOES NOT RETURN A SORTED ARRAY
    * BUT INSTEAD SORTS THE CARD[] ARRAY PASSED IN*/
   static void arraySort(Card[] ary2sort, int arraySize) {

      // per prof spades, hearts, diamonds, clubs is the order of sorting

      boolean arraySorted = false;


      while (!arraySorted) {
         arraySorted = true;
         for (int i = 0; i < ( ary2sort.length - 1 ); i++) {
            if (suitGreaterThan(ary2sort[i].suit, ary2sort[i + 1].suit)) {
               // if suit is greater then we 
               // must swap
               Card tmp = new Card(ary2sort[i + 1]); // yay copy constructor
               ary2sort[i + 1] = ary2sort[i];
               ary2sort[i] = tmp;
               arraySorted = false;
            } else {
               // if suit is not greater than
               // we must check value
               if (ary2sort[i].suit == ary2sort[i + 1].suit && 
                     (valueGreaterThan( ary2sort[i].value, ary2sort[i + 1].value ))) {
                  Card tmp = new Card(ary2sort[i + 1]); // yay copy constructor
                  ary2sort[i + 1] = ary2sort[i];
                  ary2sort[i] = tmp;
                  arraySorted = false;
               } 
            }

         }

         if (arraySorted) {
            break;
         }

      }


   }

   /*return card1 > card2*/
   public static boolean cardGreaterThan(Card card1, Card card2) {
      boolean suitbool = suitGreaterThan(card1.suit, card2.suit);
      boolean valbool = valueGreaterThan(card1.value, card2.value);
      return suitbool || valbool;
   }

   /*suitGreaterThan
    * helper function for arraySort
    * returns suit1 > suit2 as per prof instructions
    * in order of: spades, hearts, diamonds, clubs*/
   private static boolean suitGreaterThan(Suit suit1, Suit suit2) {
      int suit1val;
      int suit2val;

      switch (suit1) {
      case CLUBS:
         suit1val = 0;
         break;
      case DIAMONDS:
         suit1val = 1;
         break;
      case HEARTS:
         suit1val = 2;
         break;
      case SPADES:
         suit1val = 3;
         break;
      default:
         suit1val = 0; // will never happen, compiler complained   
      }

      switch (suit2) {
      case CLUBS:
         suit2val = 0;
         break;
      case DIAMONDS:
         suit2val = 1;
         break;
      case HEARTS:
         suit2val = 2;
         break;
      case SPADES:
         suit2val = 3;
         break;
      default:
         suit2val = 0; // will never happen, compiler complained   
      }

      return (suit1val > suit2val);


   }

   /*valueGreaterThan
    * helper function for arraySort
    * returns val1 > val2 as per prof instructions
    * in order of: valuRanks*/
   private static boolean valueGreaterThan(char val1, char val2) {
      int val1int = -1;
      int val2int = -1;
      
      for (int i = 0; i < valuRanks.length; i++) {
         if (valuRanks[i] == val1) {
            val1int = i;
            break;
         }
      }

      for (int i = 0; i < valuRanks.length; i++) {
         if (valuRanks[i] == val2) {
            val2int = i;
            break;
         }
      }

      
      return (val1int > val2int);
      
      
   }

}








