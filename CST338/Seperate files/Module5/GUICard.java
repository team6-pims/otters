
import javax.swing.*;
import Card;

public class GUICard {
   
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   
   public GUICard() {
      loadCardIcons();
   }
   
   /*Load Card Icons + Helpers*/
   public static void loadCardIcons() {
      
      if (iconCards != null) {
         return;
      }
      
      final int MAX_CARD_VALUES = 13;
      final int MAX_CARD_SUITS = 3;
      String filename = "images/";

      // build suits
      for (int i = 0; i < MAX_CARD_SUITS; i++) {
         for (int j = 0; j < MAX_CARD_VALUES; j++) {
            filename += turnIntIntoCardValue(j) + turnIntIntoCardSuit(i) +
                  ".gif";
            
            iconCards[j][i] = new ImageIcon(filename);
            filename = "images/";
         }    
      }
      
      iconBack = new ImageIcon("images/BK.gif");
      
   }
   static String turnIntIntoCardValue(int k) {
      switch(k) {
      case 0:
         return "X";
      case 1:
         return "A";
      case 2:
         return "2";
      case 3:
         return "3";
      case 4:
         return "4";
      case 5:
         return "5";
      case 6:
         return "6";
      case 7:
         return "7";
      case 8:
         return "8";
      case 9:
         return "9";
      case 10:
         return "T";
      case 11:
         return "J";
      case 12: 
         return "Q";
      case 13:
         return "K";
      default:
         return String.valueOf(k);
      }
   }
   private static String turnIntIntoCardSuit(int k) {
      String suit = "";

      switch(k) {
      case 0:
         return suit = "C";
      case 1:
         return suit = "D";
      case 2:
         return suit = "H";
      case 3:
         return suit = "S";
      default:
         return suit;
      }
   }
   
   /*getIcon and helpers*/
   public static Icon getIcon(Card card) {
      if (card.errorFlag) {
         //return error?
         
      }
      int cardValIdx = getIconValueIdx(card.value);
      int suitIdx = getIconSuitIdx(card.suit);
      
      //icon does not support clone() AND icon doesnt 
      // have a copy constructor... nope their safe :)
      return iconCards[cardValIdx][suitIdx];
   }
   private static int getIconValueIdx(char val) {
      switch (val) {
      case 'X':
         return 0;
      case 'A':
         return 1;
      case '2':
         return 2;
      case '3':
         return 3;
      case '4':
         return 4;
      case '5':
         return 5;
      case '6':
         return 6;
      case '7':
         return 7;
      case '8':
         return 8;
      case '9':
         return 9;
      case 't':
         return 10;
      case 'J':
         return 11;
      case 'Q': 
         return 12;
      case 'K':
         return 13;
      default:
         return 13;
      }
   }
   private static int getIconSuitIdx(Card.Suit suit) {
      switch (suit) {
      case CLUBS:
         return 0;
      case DIAMONDS:
         return 1;
      case HEARTS:
         return 2;
      case SPADES:
         return 3;
      default:
         return 0;
      }
   }
   
   /*get BackCardIcon*/
   public static Icon getBackCardIcon() {
      return iconBack;
   }
    
   
}
