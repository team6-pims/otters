package hw3;

// Imports
import hw3.Card;
import hw3.Hand;
import java.util.*;

public class Assig3 {

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
      System.out.println("------Unshuffled Deck------");
      // Declare P4 variables
      int numPlayers, numCards;
      
      final int MIN_PLAYERS = 1;
      final int MAX_PLAYERS = 10;

      boolean validInput = false;
      Scanner userInput = new Scanner(System.in);  // Instantiate scanner object
      
      do
      {
         // Request user input
         System.out.print("Enter number of players in game (1-10): ");
         numPlayers = userInput.nextInt();

         // Verify input
         if (numPlayers >= MIN_PLAYERS && numPlayers <= MAX_PLAYERS)
            
            // Break loop if user input is good
            validInput = true;

      } while (!validInput);
      
      // Instantiate game objects
      Deck deck = new Deck();                   // Single deck, not shuffled
      Hand players[] = new Hand[numPlayers];    // Array containing player hands
      
      // Give each player a hand
      for (int i = 0; i < numPlayers; i++)
         players[i] = new Hand();
      
      // Get number of cards in deck and store it
      numCards = deck.topCard();                

      // Deal cards to players
      for (int i = 0; i <= numCards; i++)
      {
         // Get a card from top of deck
         Card tmpCard = deck.dealCard();

         // Check if card is valid
         if (!tmpCard.getErrorFlag())
         {
            // Give card to player
            players[i % players.length].takeCard(tmpCard);
         }
      }

      // Output players' hands
      for (int i = 1; i <= players.length; i++)
      {
         System.out.println("Player " + i + " Hand: ");
         System.out.println(players[i-1].toString());
      }

      // Get cards back from players
      for (int i = 0; i < players.length; i++)
         players[i].resetHand();
      
      // Reset and shuffle deck
      deck.init(1);
      deck.shuffle();
      System.out.println("\n------Shuffled Deck------");

      // Deal cards to players
      for (int i = 0; i <= numCards; i++)
      {
         // Get a card from top of deck
         Card tmpCard = deck.dealCard();

         // Check if card is valid
         if (!tmpCard.getErrorFlag())
         {
            // Give card to player
            players[i % players.length].takeCard(tmpCard);
         }
      }

      // Output players' hands
      for (int i = 1; i <= players.length; i++)
      {
         System.out.println("Player " + i + " Hand: ");
         System.out.println(players[i-1].toString());
      }

      // Clean up
      userInput.close();
   }
}
