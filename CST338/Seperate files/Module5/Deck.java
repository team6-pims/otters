package hw5;

public class Deck {
   //holds up to 6 packs of 52 cards and 4 jokers
   public static final int MAX_PACKS = 6;
   public static final int CARDS_PER_PACK = 56;
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard = 0;
   private int numPack = 0;

   //Constructor with selected deck packs
   public Deck(int numPacks) {
      if ((numPacks < 1) && (numPacks > MAX_PACKS)) {
         System.out.println("Deck size must be: 1 <= numPacks <= 6");
         System.exit(0);
      }
      
      allocateMasterPack();
      cards = new Card[numPacks * CARDS_PER_PACK];
      init(numPacks);
      this.numPack = numPacks;
   }
   //General constructor
   // default to creating 1 pack of cards
   public Deck() {
      allocateMasterPack();
      cards = new Card[CARDS_PER_PACK];
      init(1);
      this.numPack = numPacks;
   }

   //Create deck based on how many packs entered.
   public void init(int numPacks) {
      int placement = 0;
      //create the deck
      for(int i = 0; i < numPacks; i++) {
         for(int j = 0; j < CARDS_PER_PACK; j++) {
            cards[placement] = masterPack[j];
            placement++;
         }
      }
      //assign value to top card
      topCard = placement - 1;
   }

   //Adds card into the deck
   public boolean addCard(Card card) {
      //checks if theres too many instances of the card being added
      Card checkCard = new Card();
      int instances = 0;
      for(int i = 0; i <= topCard; i++) {
         checkCard = inspectCard(i+1);
         if(checkCard.equals(card))
            instances++;
      }

      //return false if too many instances per pack
      if(instances >= numPacks)
         return false;
      cards[topCard + 1] = card;
      topCard++;
      return true;
   }

   //Removes card from the deck
   public boolean removeCard(Card card) {
      //checks if there is an instance of the card in the deck
      Card checkCard = new Card();
      for(int i = 0; i <= topCard; i++) {
         checkCard = inspectCard(i+1);
         if(checkCard.equals(card)) {
            //replaces target card with top card
            cards[i] = cards[topCard];
            topCard--;
            return true;
         }
      return false;
   }

   //Shuffle the deck
   public void shuffle() {
      Card tempValue;
      int rand = 0;
      //go through each card and randomly replace with card in unshuffled deck
      for(int i = 0; i < (topCard); i++) {
         //get a random placement in unshuffled deck
         rand = (int)(Math.random() * (cards.length - i) + i);
         tempValue = cards[i];
         cards[i] = cards[rand];
         cards[rand] = tempValue;
      }
   }

   //Sort the deck
   public void sort() {
      arraySort(cards, topCard + 1);
   }

   //returns number of cards in deck
   public int getNumCards() {
      return (topCard + 1);
   }

   //returns the top card and remove it from the deck.
   public Card dealCard() {
      Card dealCard = cards[topCard];
      topCard--;
      return dealCard;
   }

   //returns the int of the top card
   public int topCard() {
      return this.topCard;
   }

   //returns the specified card at a given position
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
      //checks if the masterPack has already been allocated
      if (masterPack == null){
         masterPack = new Card[CARDS_PER_PACK];
         char[] value = {'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'A', };
         Card placementCard;
         int placement = 0;
         
         //inserts unique cards into the deck
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
