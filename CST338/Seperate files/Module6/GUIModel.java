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
   private int[] skipCounter;
   private int playerSelectedCardIndex;
   public CardGameFramework buildGame;
   private boolean computerPass = false;
   private boolean playerPass = false;
   
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
   }
   
   // methods
   /**Fills the game data. 
    * 
    */
   public void startGame() {
      buildGame.deal();
   }
   
   /**Plays a card from that player's hand.
    * 
    * @param playerIndex: int, which player
    * @param cardIndex: int, card in hand
    * @return: Card object corresponding to selected card
    */
   public Card playHand(int playerIndex, int cardIndex) {
      return buildGame.playCard(playerIndex, cardIndex);
   }
   
   /**Similar to playHand, except it doesn't take the card from the player
    * 
    * @param playerIndex: int, player ID
    * @param cardIndex: int, card ID
    * @return: Card object corresponding to selected card
    */
   public Card getCardAtIndex(int playerIndex, int cardIndex) {
      return buildGame.getCardAtIndex(playerIndex, cardIndex);
   }
   
   /**Sorts all the hands.
    * 
    */
   public void sortAllHands() {
      buildGame.sortHands();
   }
   
   /**Gives a card from the deck to the player
    * 
    * @param playerIndex: int, player ID
    */
   public void playerTakeCard(int playerIndex) {
      buildGame.takeCard(playerIndex);
   }
   
   // getters
   public int[] getSkipCounter() {
      return skipCounter;
   }
   
   public int getDeckSize() {
      return buildGame.getNumCardsRemainingInDeck();
   }
   
   public Hand getPlayerHand(int playerIndex) {
      return buildGame.getHand(playerIndex);
   }
   
   public int getHandSize(int playerIndex) {
      Hand hand = buildGame.getHand(playerIndex);
      return hand.getNumCards();
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
   
   public boolean getComputerPassStatus() {
      return computerPass;
   }
   
   public boolean getPlayerPassStatus() {
      return playerPass;
   }
   
   // setters
   public void setLeftPile(Card card) {
      buildGame.setLeftCard(card);
   }
   
   public void setRightPile(Card card ) {
      buildGame.setRightCard(card);
   }
   
   public void incrementSkipCounter(int player) {
      skipCounter[player]++;
   }
   
   public void setPlayerSelection(int handIndex) {
      playerSelectedCardIndex = handIndex;
   }
   
   public boolean setComputerPassStatus(boolean pass) {
      computerPass = pass;
      return true;
   }
   
   public boolean setPlayerPassStatus(boolean pass) {
      playerPass = pass;
      return true;
   }
   
   public Card getCardFromDeck() {
      return buildGame.getCardFromDeck();
   }
}
