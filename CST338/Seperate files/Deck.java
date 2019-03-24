package hw3;

public class Deck {
   //holds up to 6 packs of 52 cards
   public static final int MAX_PACKS = 6;
   private static final int CARDS_PER_PACK = 52;
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard = 0;
   private int numPacks;

   //Constructor with selected deck packs

   //Constructor with selected deck packs
   public Deck(int numPacks) {
      if (( 1 <= numPacks ) && ( MAX_PACKS <= 6 )) {
         System.out.println("Deck size must be: 1 < numPacks < 6");
         System.exit(0);
      }
      
      this.numPacks = numPacks;
      allocateMasterPack();
      cards = new Card[numPacks * CARDS_PER_PACK];
      init(numPacks);
   }
   //General constructor
   // default to creating 1 pack of cards
   public Deck() {
      allocateMasterPack();
      cards = new Card[CARDS_PER_PACK];
      init(1);
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
      topCard = placement + 1;
   }

   //Shuffle the deck
   public void shuffle() {
      Card tempValue = new Card();
      int rand = 0;
      //go through each card and randomly replace with card in unshuffled deck
      for(int i = 0; i < (topCard - 1); i++) {
         //get a random placement in unshuffled deck
         rand = (int)(Math.random() * (cards.length - i) + i);
         tempValue = cards[i];
         cards[i] = cards[rand];
         cards[rand] = tempValue;
      }
   }

   //returns the top card and remove it from the deck.
   public Card dealCard() {
      Card dealCard = cards[topCard-1];
      topCard--;
      return dealCard;
   }

   //returns the int of the top card
   public int topCard() {
      return this.topCard;
   }

   //returns the specified card at a given position
   public Card inspectCard(int k) {
      if ( (k > topCard) || ( k < 0 ) ){
         //return exception if the entered number is out of bounds
         Card badCard = new Card();
         badCard.errorFlag = true;
         return badCard;
      } else {
         //return card if it is within the deck's values
         Card goodCard = cards[k-1];
         return goodCard;
      }
   }

   private static void allocateMasterPack() {
      //checks if the masterPack has already been allocated
      if (masterPack.length == 0){
         masterPack = new Card[52];
         char[] value = {'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'A', };

         //inserts unique cards into the deck
         int placement = 0;
         for(int j = 0; j < value.length; j++) {
            Card placementCard = new Card(value[j], Card.Suit.CLUBS);
            masterPack[placement] = placementCard;
            placement++;
         }
         for(int j = 0; j < value.length; j++) {
            Card placementCard = new Card(value[j], Card.Suit.DIAMONDS);
            masterPack[placement] = placementCard;
            placement++;

         }
         for(int j = 0; j < value.length; j++) {
            Card placementCard = new Card(value[j], Card.Suit.HEARTS);
            masterPack[placement] = placementCard;
            placement++;
         }
         for(int j = 0; j < value.length; j++) {
            Card placementCard = new Card(value[j], Card.Suit.SPADES);
            masterPack[placement] = placementCard;
            placement++;
         }
      }
   }
}
