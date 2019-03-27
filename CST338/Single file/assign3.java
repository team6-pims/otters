/* 
   Team 6 - PIMS
   Alejandro Caicedo
   Ivan Alejandre
   Matthew Chan
   Randy Son

   CST338 - Software Engineering
   Week 3 - Deck of Cards

   Summary:
   This project simulates a deck of cards by utilizing
   three classes: Card, Hand, Deck. Hand and Deck are
   extensions of the Card class. With this project,
   we are able to play with up to 6 packs of cards,
   and shuffle, deal those cards to players' hands.
   The public class Assig3 brings it all together
   by testing each class and their interaction with 
   each other.
*/


import java.util.*;

public class assign3 {

   public static void main(String[] args) {
      System.out.println("--------Card Tests--------");

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
      System.out.println("--------Hand Tests--------");
      Hand hand = new Hand();
      while (hand.getNumCards() < Hand.MAX_CARDS) {
         hand.takeCard(newCard11);
         hand.takeCard(newCard22);
         hand.takeCard(newCard33);
      }
      System.out.println("Hand full.\nShowing hand.");
      System.out.println(hand.toString());
      
      //Play the hand
      Card showCard;
      Card inspectedCard;
      
      System.out.println("Testing inspectCard()");
      inspectedCard = hand.inspectCard(Hand.MAX_CARDS - 1);
      System.out.println(inspectedCard.toString());
      inspectedCard = hand.inspectCard(Hand.MAX_CARDS);
      System.out.println(inspectedCard.toString());
      
      System.out.println("\nPlaying hand:\n");
      for (int i = 0; i < (Hand.MAX_CARDS); i++) {
         showCard = hand.playCard();
         System.out.println((hand.getNumCards() + 1) + ": " 
         + showCard.toString());
      }
      
      System.out.println("\nEmpty hand:\n" + hand.toString());
      
      System.out.println("\n--------Deck Tests--------");
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
      
      System.out.println("\n--------Deck & Hand Interaction--------");

      // Declare P4 variables
      int numPlayers, numCards;
      
      final int MIN_PLAYERS = 1;
      final int MAX_PLAYERS = 10;

      boolean validInput = false;
      Scanner userInput = new Scanner(System.in);  // Instantiate scanner object for user input
      
      do {
         /* Request user input and verify it is within parameters
            Exit loop if input is good */
         System.out.print("Enter number of players in game (1-10): ");
         numPlayers = userInput.nextInt();

         if (numPlayers >= MIN_PLAYERS && numPlayers <= MAX_PLAYERS) {
            validInput = true;
         }

      } while (!validInput);
      
      // Instantiate game objects
      Deck deck = new Deck();                   // Single deck, not shuffled
      Hand[] players = new Hand[numPlayers];    // Array containing player hands
      
      // Give each player a hand and retrieve number of cards in deck 

      for (int i = 0; i < numPlayers; i++) {
         players[i] = new Hand();
      }
      
      numCards = deck.topCard();                
      
      /* Deal cards to players by checking top of deck and verifying card is valid.
       * If valid card, then give it to the player
       */
      for (int i = 0; i <= numCards; i++) {
         Card tmpCard = deck.dealCard();

         if (!tmpCard.getErrorFlag()) {
            players[i % players.length].takeCard(tmpCard);
         }
      }

      // Output players' hands
      System.out.println("------Unshuffled Deck------");

      for (int i = 1; i <= players.length; i++) {
         System.out.println("Player " + i + " Hand: ");
         System.out.println(players[i-1].toString());
      }

      /* Reset player hands, reset and shuffle deck */
      for (int i = 0; i < players.length; i++) {
         players[i].resetHand();
      }
      
      deck.init(1);
      deck.shuffle();

      /* Deal cards to players by checking top of deck and verifying card is valid.
       * If valid card, then give it to the player
       */
      for (int i = 0; i <= numCards; i++) {
         Card tmpCard = deck.dealCard();

         if (!tmpCard.getErrorFlag()) {
            players[i % players.length].takeCard(tmpCard);
         }
      }

      // Output players' hands
      System.out.println("\n------Shuffled Deck------");

      for (int i = 1; i <= players.length; i++) {
         System.out.println("Player " + i + " Hand: ");
         System.out.println(players[i-1].toString());
      }

      // Clean up
      userInput.close();
   }
}

class Card {

   public enum Suit {
      CLUBS, DIAMONDS, HEARTS, SPADES;
   }

   public char value;
   public Suit suit;
   public boolean errorFlag = false;
   
   // Constructors 
   public Card(char value, Suit suit) {
      set(value, suit);
   }

   public Card() {
      set('A', Suit.SPADES);
   }
   
   public Card (Card card2copy) {
      set( card2copy.value, card2copy.suit );
   }
   
   // Get functions
   
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
    * is true
    */
   public String toString() {
      if (errorFlag) {
         return "[Invalid Card]";
      } else {
         return value + " of " + suit;
      }
   }

   /* set
    * setter for class variables calls
    * isValid to check for correctness of
    * inputs. If all valid, sets variables
    * if not, then does not set and sets errorFlag
    * to true
    */
   public boolean set(char value, Suit suit) {
      value = Character.toUpperCase(value);

      if(isValid(value, suit)) {
         this.value = value;
         this.suit = suit;
         return true;
      } 
      else {
         this.errorFlag = true;
         return false;
      }
   }

   
   /* equals
    * Check for equality, true if equal
    * false if not
    */
   public boolean equals( Card card ) {
      return ((card.value == this.value) && (this.suit == card.suit));
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
      
      if (!card.getErrorFlag()) {
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
   
   /* If the passed index is out of bounds or indicates an empty index, it
    * returns a bad card, otherwise returns the card at the index
    */
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

class Deck {
   public static final int MAX_PACKS = 6;
   public static final int CARDS_PER_PACK = 52;
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard;

   // Constructor with selected deck packs
   public Deck(int numPacks) {
      if (( numPacks < 1) && ( numPacks > MAX_PACKS )) {
         System.out.println("Deck size must be: 1 <= numPacks <= 6");
         System.exit(0);
      }
      
      allocateMasterPack();
      cards = new Card[numPacks * CARDS_PER_PACK];
      init(numPacks);
   }
   
   // General constructor defaulting to creating 1 pack of cards
   public Deck() {
      allocateMasterPack();
      cards = new Card[CARDS_PER_PACK];
      init(1);
   }

   // Create deck based on how many packs entered.
   public void init(int numPacks) {
      int placement = 0;
      
      for(int i = 0; i < numPacks; i++) {
         for(int j = 0; j < CARDS_PER_PACK; j++) {
            cards[placement] = masterPack[j];
            placement++;
         }
      }
      // assign value to top card
      topCard = placement - 1;
   }

   /* shuffle() reassigns the pointers in cards[] based on a randomly
    * generated typcasted int which is the index for another card in
    * cards[].
    */
   public void shuffle() {
      Card tempValue;
      int rand = 0;
      for(int i = 0; i < (topCard - 1); i++) {
         rand = (int)(Math.random() * (cards.length - i) + i);
         tempValue = cards[i];
         cards[i] = cards[rand];
         cards[rand] = tempValue;
      }
   }

   // returns the top card and shortens the deck size.
   public Card dealCard() {
      Card dealCard = cards[topCard];
      topCard--;
      return dealCard;
   }

   // returns the size of the deck
   public int topCard() {
      return this.topCard;
   }

   // returns the specified card at a given position
   public Card inspectCard(int k) {
      if ((k > topCard) || (k < 0)){
         //return exception if the entered number is out of bounds
         Card badCard = new Card();
         badCard.errorFlag = true;
         return badCard;
      } else {
         //return card if it is within the deck's values
         Card goodCard = cards[k - 1];
         return goodCard;
      }
   }

   private static void allocateMasterPack() {
      // checks if the masterPack has already been allocated
      if (masterPack == null){
         masterPack = new Card[CARDS_PER_PACK];
         char[] value = {'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', 
               '3', '2', 'A', };
         Card placementCard;
         int placement = 0;
         
         // inserts unique cards into the deck
         for (Card.Suit singleSuit: Card.Suit.values()) {
            for (char singleValue: value) {
               placementCard = new Card(singleValue, singleSuit);
               masterPack[placement] = placementCard;
               placement++;
            }
         }
      }
   }
}

/***********************************OUTPUT*************************************
--------Card Tests--------
Card1: J of CLUBS
Card2: Q of HEARTS
Card3: [Invalid Card]
Now we make a good card turn bad
Card1: [Invalid Card]
Card2: Q of HEARTS
Card3: [Invalid Card]
--------Hand Tests--------
Hand full.
Showing hand.
Hand:
( J of CLUBS , Q of HEARTS , 2 of SPADES , J of CLUBS , Q of HEARTS
2 of SPADES , J of CLUBS , Q of HEARTS , 2 of SPADES
J of CLUBS , Q of HEARTS , 2 of SPADES , J of CLUBS
Q of HEARTS , 2 of SPADES , J of CLUBS , Q of HEARTS
2 of SPADES , J of CLUBS , Q of HEARTS , 2 of SPADES
J of CLUBS , Q of HEARTS , 2 of SPADES , J of CLUBS
Q of HEARTS , 2 of SPADES , J of CLUBS , Q of HEARTS
2 of SPADES , J of CLUBS , Q of HEARTS , 2 of SPADES
J of CLUBS , Q of HEARTS , 2 of SPADES , J of CLUBS
Q of HEARTS , 2 of SPADES , J of CLUBS , Q of HEARTS
2 of SPADES , J of CLUBS , Q of HEARTS , 2 of SPADES
J of CLUBS , Q of HEARTS , 2 of SPADES , J of CLUBS
Q of HEARTS )

Testing inspectCard()
Q of HEARTS
[Invalid Card]

Playing hand:

50: Q of HEARTS
49: J of CLUBS
48: 2 of SPADES
47: Q of HEARTS
46: J of CLUBS
45: 2 of SPADES
44: Q of HEARTS
43: J of CLUBS
42: 2 of SPADES
41: Q of HEARTS
40: J of CLUBS
39: 2 of SPADES
38: Q of HEARTS
37: J of CLUBS
36: 2 of SPADES
35: Q of HEARTS
34: J of CLUBS
33: 2 of SPADES
32: Q of HEARTS
31: J of CLUBS
30: 2 of SPADES
29: Q of HEARTS
28: J of CLUBS
27: 2 of SPADES
26: Q of HEARTS
25: J of CLUBS
24: 2 of SPADES
23: Q of HEARTS
22: J of CLUBS
21: 2 of SPADES
20: Q of HEARTS
19: J of CLUBS
18: 2 of SPADES
17: Q of HEARTS
16: J of CLUBS
15: 2 of SPADES
14: Q of HEARTS
13: J of CLUBS
12: 2 of SPADES
11: Q of HEARTS
10: J of CLUBS
9: 2 of SPADES
8: Q of HEARTS
7: J of CLUBS
6: 2 of SPADES
5: Q of HEARTS
4: J of CLUBS
3: 2 of SPADES
2: Q of HEARTS
1: J of CLUBS

Empty hand:
Hand: ( )

--------Deck Tests--------

Now we create a Deck with 2 packs of card in it
Now we deal all the cards
Card0: A of SPADES
Card1: 2 of SPADES
Card2: 3 of SPADES
Card3: 4 of SPADES
Card4: 5 of SPADES
Card5: 6 of SPADES
Card6: 7 of SPADES
Card7: 8 of SPADES
Card8: 9 of SPADES
Card9: T of SPADES
Card10: J of SPADES
Card11: Q of SPADES
Card12: K of SPADES
Card13: A of HEARTS
Card14: 2 of HEARTS
Card15: 3 of HEARTS
Card16: 4 of HEARTS
Card17: 5 of HEARTS
Card18: 6 of HEARTS
Card19: 7 of HEARTS
Card20: 8 of HEARTS
Card21: 9 of HEARTS
Card22: T of HEARTS
Card23: J of HEARTS
Card24: Q of HEARTS
Card25: K of HEARTS
Card26: A of DIAMONDS
Card27: 2 of DIAMONDS
Card28: 3 of DIAMONDS
Card29: 4 of DIAMONDS
Card30: 5 of DIAMONDS
Card31: 6 of DIAMONDS
Card32: 7 of DIAMONDS
Card33: 8 of DIAMONDS
Card34: 9 of DIAMONDS
Card35: T of DIAMONDS
Card36: J of DIAMONDS
Card37: Q of DIAMONDS
Card38: K of DIAMONDS
Card39: A of CLUBS
Card40: 2 of CLUBS
Card41: 3 of CLUBS
Card42: 4 of CLUBS
Card43: 5 of CLUBS
Card44: 6 of CLUBS
Card45: 7 of CLUBS
Card46: 8 of CLUBS
Card47: 9 of CLUBS
Card48: T of CLUBS
Card49: J of CLUBS
Card50: Q of CLUBS
Card51: K of CLUBS
Card52: A of SPADES
Card53: 2 of SPADES
Card54: 3 of SPADES
Card55: 4 of SPADES
Card56: 5 of SPADES
Card57: 6 of SPADES
Card58: 7 of SPADES
Card59: 8 of SPADES
Card60: 9 of SPADES
Card61: T of SPADES
Card62: J of SPADES
Card63: Q of SPADES
Card64: K of SPADES
Card65: A of HEARTS
Card66: 2 of HEARTS
Card67: 3 of HEARTS
Card68: 4 of HEARTS
Card69: 5 of HEARTS
Card70: 6 of HEARTS
Card71: 7 of HEARTS
Card72: 8 of HEARTS
Card73: 9 of HEARTS
Card74: T of HEARTS
Card75: J of HEARTS
Card76: Q of HEARTS
Card77: K of HEARTS
Card78: A of DIAMONDS
Card79: 2 of DIAMONDS
Card80: 3 of DIAMONDS
Card81: 4 of DIAMONDS
Card82: 5 of DIAMONDS
Card83: 6 of DIAMONDS
Card84: 7 of DIAMONDS
Card85: 8 of DIAMONDS
Card86: 9 of DIAMONDS
Card87: T of DIAMONDS
Card88: J of DIAMONDS
Card89: Q of DIAMONDS
Card90: K of DIAMONDS
Card91: A of CLUBS
Card92: 2 of CLUBS
Card93: 3 of CLUBS
Card94: 4 of CLUBS
Card95: 5 of CLUBS
Card96: 6 of CLUBS
Card97: 7 of CLUBS
Card98: 8 of CLUBS
Card99: 9 of CLUBS
Card100: T of CLUBS
Card101: J of CLUBS
Card102: Q of CLUBS
Card103: K of CLUBS
End of dealing 2pack(s) of unshuffled cards

Re-Initializing Deck with 2packs of cards
Shuffing deck of cards
Dealing shuffled cards
Card0: 2 of CLUBS
Card1: 4 of CLUBS
Card2: 8 of HEARTS
Card3: T of HEARTS
Card4: 6 of HEARTS
Card5: A of CLUBS
Card6: T of SPADES
Card7: 9 of HEARTS
Card8: 5 of CLUBS
Card9: T of CLUBS
Card10: 8 of SPADES
Card11: 3 of DIAMONDS
Card12: 6 of CLUBS
Card13: 9 of SPADES
Card14: 3 of HEARTS
Card15: 9 of DIAMONDS
Card16: 8 of CLUBS
Card17: 4 of HEARTS
Card18: Q of SPADES
Card19: 7 of HEARTS
Card20: 6 of SPADES
Card21: 9 of CLUBS
Card22: 7 of SPADES
Card23: Q of CLUBS
Card24: K of SPADES
Card25: K of HEARTS
Card26: 8 of HEARTS
Card27: Q of HEARTS
Card28: K of CLUBS
Card29: 5 of HEARTS
Card30: 3 of DIAMONDS
Card31: T of CLUBS
Card32: 2 of CLUBS
Card33: 7 of SPADES
Card34: T of HEARTS
Card35: J of DIAMONDS
Card36: Q of SPADES
Card37: 5 of CLUBS
Card38: 9 of CLUBS
Card39: 2 of DIAMONDS
Card40: Q of DIAMONDS
Card41: J of CLUBS
Card42: 3 of CLUBS
Card43: 9 of HEARTS
Card44: K of DIAMONDS
Card45: 6 of SPADES
Card46: 2 of HEARTS
Card47: 7 of HEARTS
Card48: 5 of SPADES
Card49: 4 of DIAMONDS
Card50: Q of CLUBS
Card51: A of HEARTS
Card52: 7 of CLUBS
Card53: A of HEARTS
Card54: 6 of HEARTS
Card55: 8 of DIAMONDS
Card56: J of SPADES
Card57: 4 of DIAMONDS
Card58: 7 of DIAMONDS
Card59: K of HEARTS
Card60: J of HEARTS
Card61: Q of DIAMONDS
Card62: 3 of SPADES
Card63: J of CLUBS
Card64: 4 of SPADES
Card65: 2 of DIAMONDS
Card66: 7 of DIAMONDS
Card67: 3 of HEARTS
Card68: K of CLUBS
Card69: 5 of HEARTS
Card70: 8 of SPADES
Card71: 5 of DIAMONDS
Card72: 6 of CLUBS
Card73: 3 of SPADES
Card74: A of SPADES
Card75: 6 of DIAMONDS
Card76: 2 of HEARTS
Card77: J of DIAMONDS
Card78: 2 of SPADES
Card79: 5 of DIAMONDS
Card80: A of CLUBS
Card81: K of SPADES
Card82: A of SPADES
Card83: T of SPADES
Card84: 2 of SPADES
Card85: 4 of SPADES
Card86: J of SPADES
Card87: 8 of DIAMONDS
Card88: 4 of CLUBS
Card89: T of DIAMONDS
Card90: 5 of SPADES
Card91: 4 of HEARTS
Card92: T of DIAMONDS
Card93: 9 of DIAMONDS
Card94: 6 of DIAMONDS
Card95: J of HEARTS
Card96: 8 of CLUBS
Card97: K of DIAMONDS
Card98: A of DIAMONDS
Card99: 3 of CLUBS
Card100: Q of HEARTS
Card101: 7 of CLUBS
Card102: A of DIAMONDS
Card103: 9 of SPADES
End of dealing 2pack(s) of shuffled cards

Repeating above exercise with 1 pack of cards
Instantiating single deck with default constructor
Dealing unshuffled cards
Card0: A of SPADES
Card1: 2 of SPADES
Card2: 3 of SPADES
Card3: 4 of SPADES
Card4: 5 of SPADES
Card5: 6 of SPADES
Card6: 7 of SPADES
Card7: 8 of SPADES
Card8: 9 of SPADES
Card9: T of SPADES
Card10: J of SPADES
Card11: Q of SPADES
Card12: K of SPADES
Card13: A of HEARTS
Card14: 2 of HEARTS
Card15: 3 of HEARTS
Card16: 4 of HEARTS
Card17: 5 of HEARTS
Card18: 6 of HEARTS
Card19: 7 of HEARTS
Card20: 8 of HEARTS
Card21: 9 of HEARTS
Card22: T of HEARTS
Card23: J of HEARTS
Card24: Q of HEARTS
Card25: K of HEARTS
Card26: A of DIAMONDS
Card27: 2 of DIAMONDS
Card28: 3 of DIAMONDS
Card29: 4 of DIAMONDS
Card30: 5 of DIAMONDS
Card31: 6 of DIAMONDS
Card32: 7 of DIAMONDS
Card33: 8 of DIAMONDS
Card34: 9 of DIAMONDS
Card35: T of DIAMONDS
Card36: J of DIAMONDS
Card37: Q of DIAMONDS
Card38: K of DIAMONDS
Card39: A of CLUBS
Card40: 2 of CLUBS
Card41: 3 of CLUBS
Card42: 4 of CLUBS
Card43: 5 of CLUBS
Card44: 6 of CLUBS
Card45: 7 of CLUBS
Card46: 8 of CLUBS
Card47: 9 of CLUBS
Card48: T of CLUBS
Card49: J of CLUBS
Card50: Q of CLUBS
Card51: K of CLUBS
End of dealing unshuffled single deck
Re-instantiating single deck
Now shuffling the single deck
Deck is now shuffled
Now dealing shuffled cards
Card0: 6 of CLUBS
Card1: Q of DIAMONDS
Card2: Q of SPADES
Card3: 8 of CLUBS
Card4: 5 of SPADES
Card5: 5 of CLUBS
Card6: T of HEARTS
Card7: 3 of DIAMONDS
Card8: J of SPADES
Card9: 9 of HEARTS
Card10: 3 of SPADES
Card11: 8 of DIAMONDS
Card12: 9 of CLUBS
Card13: 6 of HEARTS
Card14: A of CLUBS
Card15: K of SPADES
Card16: 2 of CLUBS
Card17: 3 of CLUBS
Card18: T of DIAMONDS
Card19: 2 of HEARTS
Card20: J of HEARTS
Card21: 8 of SPADES
Card22: T of CLUBS
Card23: Q of HEARTS
Card24: 4 of HEARTS
Card25: 4 of DIAMONDS
Card26: 6 of DIAMONDS
Card27: 5 of HEARTS
Card28: J of DIAMONDS
Card29: K of CLUBS
Card30: 3 of HEARTS
Card31: T of SPADES
Card32: 5 of DIAMONDS
Card33: A of SPADES
Card34: A of HEARTS
Card35: 2 of SPADES
Card36: 6 of SPADES
Card37: 4 of SPADES
Card38: 2 of DIAMONDS
Card39: J of CLUBS
Card40: 4 of CLUBS
Card41: Q of CLUBS
Card42: 9 of DIAMONDS
Card43: 7 of SPADES
Card44: 9 of SPADES
Card45: 7 of HEARTS
Card46: 8 of HEARTS
Card47: A of DIAMONDS
Card48: K of HEARTS
Card49: 7 of DIAMONDS
Card50: 7 of CLUBS
Card51: K of DIAMONDS
End of dealing shuffled cards

--------Deck & Hand Interaction--------
------Unshuffled Deck------
Enter number of players in game (1-10): 6
6
Player 1 Hand: 
Hand:
( A of SPADES , 7 of SPADES , K of SPADES , 6 of HEARTS , Q of HEARTS
5 of DIAMONDS , J of DIAMONDS , 4 of CLUBS , T of CLUBS )

Player 2 Hand: 
Hand:
( 2 of SPADES , 8 of SPADES , A of HEARTS , 7 of HEARTS , K of HEARTS
6 of DIAMONDS , Q of DIAMONDS , 5 of CLUBS , J of CLUBS )

Player 3 Hand: 
Hand:
( 3 of SPADES , 9 of SPADES , 2 of HEARTS , 8 of HEARTS , A of DIAMONDS
7 of DIAMONDS , K of DIAMONDS , 6 of CLUBS , Q of CLUBS )

Player 4 Hand: 
Hand:
( 4 of SPADES , T of SPADES , 3 of HEARTS , 9 of HEARTS , 2 of DIAMONDS
8 of DIAMONDS , A of CLUBS , 7 of CLUBS , K of CLUBS )

Player 5 Hand: 
Hand:
( 5 of SPADES , J of SPADES , 4 of HEARTS , T of HEARTS , 3 of DIAMONDS
9 of DIAMONDS , 2 of CLUBS , 8 of CLUBS )

Player 6 Hand: 
Hand:
( 6 of SPADES , Q of SPADES , 5 of HEARTS , J of HEARTS , 4 of DIAMONDS
T of DIAMONDS , 3 of CLUBS , 9 of CLUBS )


------Shuffled Deck------
Player 1 Hand: 
Hand:
( J of HEARTS , A of SPADES , J of DIAMONDS , 7 of DIAMONDS , K of HEARTS
9 of HEARTS , 7 of HEARTS , 8 of SPADES , 2 of CLUBS )

Player 2 Hand: 
Hand:
( K of DIAMONDS , T of SPADES , 6 of HEARTS , 5 of DIAMONDS , Q of DIAMONDS
3 of HEARTS , 3 of DIAMONDS , 4 of DIAMONDS , 9 of SPADES )

Player 3 Hand: 
Hand:
( A of CLUBS , 7 of SPADES , 8 of DIAMONDS , 4 of SPADES , 3 of SPADES
A of HEARTS , 3 of CLUBS , 9 of DIAMONDS , J of SPADES )

Player 4 Hand: 
Hand:
( 8 of HEARTS , Q of SPADES , 2 of SPADES , 9 of CLUBS , 5 of CLUBS
T of DIAMONDS , 5 of HEARTS , 6 of CLUBS , K of CLUBS )

Player 5 Hand: 
Hand:
( 8 of CLUBS , K of SPADES , 6 of DIAMONDS , 5 of SPADES , T of CLUBS
6 of SPADES , Q of HEARTS , 2 of DIAMONDS )

Player 6 Hand: 
Hand:
( T of HEARTS , 7 of CLUBS , A of DIAMONDS , J of CLUBS , Q of CLUBS
2 of HEARTS , 4 of HEARTS , 4 of CLUBS )
*/
