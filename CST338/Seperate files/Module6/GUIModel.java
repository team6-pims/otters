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
   private int[] winCounter;
   public CardGameFramework highCardGame;
   
   // default constructor
   public GUIModel() {
      this.NUM_CARDS_PER_HAND = 7;
      this.NUM_PLAYERS = 2;
      this.numPacksPerDeck = 1;
      this.numJokersPerPack = 2;
      this.numUnusedCardsPerPack = 0;
      this.unusedCardsPerPack = null;
      this.winCounter = new int[NUM_PLAYERS];
      
      this.highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      
      this.computerWinnings = new Card[highCardGame.getNumCardsRemainingInDeck()];
      this.playerWinnings = new Card[highCardGame.getNumCardsRemainingInDeck()];
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
      this.winCounter = new int[NUM_PLAYERS];
      
      this.highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      
      this.computerWinnings = new Card[highCardGame.getNumCardsRemainingInDeck()];
      this.playerWinnings = new Card[highCardGame.getNumCardsRemainingInDeck()];
   }
   
   // methods
   public void startGame() {
      highCardGame.deal();
   }
   
   public int getDeckSize() {
      return highCardGame.getNumCardsRemainingInDeck();
   }
   
   public Hand getPlayerHand(int playerIndex) {
      return highCardGame.getHand(playerIndex);
   }
   
   // getters
   public int getHandSize() {
      return NUM_CARDS_PER_HAND;
   }
   
   public int getWinCount(int player) {
      return winCounter[player];
   }
   
   public int getNumPlayers() {
      return NUM_PLAYERS;
   }
   
   public Card getPlayedCard(int playerIndex, int cardIndex) {
      return highCardGame.playCard(playerIndex, cardIndex);
   }
   // setters
   public void setPlayerWinnings(Card card, int index) {
      playerWinnings[index] = card;
   }
   
   public void setComputerWinnings(Card card, int index) {
      computerWinnings[index] = card;
   }
   
   public void setWinCounter(int player) {
      winCounter[player] += 2;
   }
}
