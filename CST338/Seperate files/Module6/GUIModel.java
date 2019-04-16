package hw6;

/**The Model component corresponds to all 
 * the data-related logic that the user 
 * works with. This can represent either 
 * the data that is being transferred between 
 * the View and Controller components or any 
 * other business logic-related data. For example, 
 * a Customer object will retrieve the customer 
 * information from the database, manipulate it 
 * and update it data back to the database or use 
 * it to render data.*/

class GUIModel {
   private int NUM_CARDS_PER_HAND;
   private int  NUM_PLAYERS;
   private int numPacksPerDeck;
   private int numJokersPerPack;
   private int numUnusedCardsPerPack;
   private Card[] unusedCardsPerPack;
   private Card[] computerWinnings, playerWinnings;
   private int[] skipCounter;
   private int playerSelectedCardIndex;
   public CardGameFramework buildGame;
   
   // default constructor
   public GUIModel() {
      this.NUM_CARDS_PER_HAND = 7;
      this.NUM_PLAYERS = 2;
      this.numPacksPerDeck = 1;
      this.numJokersPerPack = 0;
      this.numUnusedCardsPerPack = 0;
      this.unusedCardsPerPack = null;
      this.skipCounter = new int[NUM_PLAYERS];
      
      this.buildGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);


   }

   // custom constructor
   public GUIModel(int cardsPerHand, int players, int packsPerDeck,
         int jokersPerPack, int unusedCards) {
      this.NUM_CARDS_PER_HAND = cardsPerHand;
      this.NUM_PLAYERS = players;
      this.numPacksPerDeck = packsPerDeck;
      this.numJokersPerPack = jokersPerPack;
      this.numUnusedCardsPerPack = unusedCards;
      this.unusedCardsPerPack = new Card[unusedCards * packsPerDeck];
      this.skipCounter = new int[NUM_PLAYERS];
      
      this.buildGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      
      this.computerWinnings = new Card[buildGame.getNumCardsRemainingInDeck()];
      this.playerWinnings = new Card[buildGame.getNumCardsRemainingInDeck()];
   }
   
   // methods
   public void startGame() {
      buildGame.deal();
   }
   
   public int getDeckSize() {
      return buildGame.getNumCardsRemainingInDeck();
   }
   
   public Hand getPlayerHand(int playerIndex) {
      return buildGame.getHand(playerIndex);
   }
   
   // Place 2 new cards onto piles
   public boolean newPile() {
      boolean enoughCards = false;
      
      if (buildGame.getNumCardsRemainingInDeck() > 1) {
         enoughCards = true;
         buildGame.setLeftCard(buildGame.getCardFromDeck());
         buildGame.setRightCard(buildGame.getCardFromDeck());
      }
      return enoughCards;
   }
   
   /* Determines of card is playable or not */
   public boolean viablePlay(Card pileCard, Card cardToCheck) {
      boolean isValid = false;
      int pileCardValue = pileCard.getValue();
      int cardToCheckValue = cardToCheck.getValue();
      
      switch (pileCardValue) {
      // If Ace, then King or 2 is valid
      case 1:
         if (pileCardValue == 13 || pileCardValue == 2) {
            isValid = true;
         }
         break;
      // If King, then Queen or Ace is valid
      case 13:
         if (pileCardValue == 12 || pileCardValue == 1) {
            isValid = true;
         }
         break;
      // All other cases 
      default:
         if (Math.abs(pileCardValue - cardToCheckValue) == 1) {
            isValid = true;
         }
         break;
      }
      return isValid;
   }
   
   // getters
   public int getHandSize() {
      return NUM_CARDS_PER_HAND;
   }
   
   public int getWinCount(int player) {
      return skipCounter[player];
   }
   
   public int getNumPlayers() {
      return NUM_PLAYERS;
   }
   
   public Card getLeftCard() {
      return buildGame.getLeftCard();
   }
   
   public Card getRightCard() {
      return buildGame.getRightCard();
   }
   
   public int getPlayerSelection() {
      return playerSelectedCardIndex;
   }
   
   public Card getPlayedCard(int playerIndex, int cardIndex) {
      return buildGame.playCard(playerIndex, cardIndex);
   }
   
   // setters
   public void setPlayerWinnings(Card card, int index) {
      playerWinnings[index] = card;
   }
   
   public void setComputerWinnings(Card card, int index) {
      computerWinnings[index] = card;
   }
   
   public void setWinCounter(int player) {
      skipCounter[player] += 1;
   }
   
   public void setPlayerSelection(int handIndex) {
      playerSelectedCardIndex = handIndex;
   }
}
