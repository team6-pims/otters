package hw6

class CardStackModel {
   private int NUM_CARDS_PER_HAND;
   private int NUM_PLAYERS;
   private int numPacksPerDeck;
   private int numJokersPerPack;
   private int UnusedCardsPerPack;
   private Card[] unusedCardsPerPack;

   //used to define how many stacks to be played
   private int stacks;
   //used to define what card is on each stack
   private Card[] stackCard;
   //used to define how many times the turn was skipped per player
   private int[] skipCounter;

   public CardGameFramework cardStackGame;

   //default constructor
   public CardStackModel() {
      this.NUM_CARDS_PER_HAND = 7;
      this.NUM_PLAYERS = 2;
      this.numPacksPerDeck = 1;
      this.numJokersPerPack = 0;
      this.numUnusedCardsPerPack = 0;
      this.unusedCardsPerPack = null;
      this.stacks = 2;
      this.stackCard = new Card[stacks];
      this.skipCounter = new int[NUM_PLAYERS];

      this.cardStackGame = new CardGameFramework(
         numPacksPerDeck, numJokersPerPack,
         numUnusedCardsPerPack, unusedCardsPerPack,
         NUM_PLAYERS, NUM_CARDS_PER_HAND);
   }

   //customized constructor
   //jokers and unused cards not included in this gamemode
   public CardStackModel(int players, int cardsPerHand, int packsPerDeck, int cardStacks) {
      this.NUM_CARDS_PER_HAND = cardsPerHand;
      this.NUM_PLAYERS = players;
      this.numPacksPerDeck = packsPerDeck;
      this.numJokersPerPack = 0;
      this.numUnusedCardsPerPack = 0;
      this.unusedCardsPerPack = null;
      this.stacks = cardStacks;
      this.stackCard = new Card[stacks];
      this.skipCounter = new int[NUM_PLAYERS];

      this.cardStackGame = new CardGameFramework(
         numPacksPerDeck, numJokersPerPack,
         numUnusedCardsPerPack, unusedCardsPerPack,
         NUM_PLAYERS, NUM_CARDS_PER_HAND);
   }

   //methods
   public void startGame() {
      cardStackGame.deal();
   }


   //getters
   public int getDeckSize() {
      cardStackGame.getNumCardsRemainingInDeck();
   }

   public Hand getPlayerHand(int playerIndex) {
      return cardStackGame.getHand(playerIndex);
   }

   public int getHandSize() {
      return NUM_CARDS_PER_HAND;
   }

   public Card getStackCard(int stack) {
      return stackCard[stack];
   }

   public getSkipCount(int player) {
      return skipCounter[player];
   }

   public int getNumPlayers() {
      return NUM_PLAYERS;
   }

   public Card getPlayedCard(int playerIndex, int cardIndex) {
      return cardStackGame.playCard(playerIndex, cardIndex);
   }

   //setters
   public void setStackCard(Card card, int stack) {
      stackCard[stack] = card;
   }

   public void setSkipCounter(int player) {
      skipCounter[player] += 1;
   }