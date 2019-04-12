package hw6;

/*INCOMPLETE*/

import javax.swing.JFrame;

/*The View component is used for 
 * all the UI logic of the application. 
 * For example, the Customer view will 
 * include all the UI components such as 
 * text boxes, dropdowns, etc. that the 
 * final user interacts with.*/
public class GUIView {
   private CardTable cardtable;
   private int NUM_PLAYERS;
   private int NUM_CARDS_PER_HAND;
   
   private static int SIZE_ROW = 800;
   private static int SIZE_COL = 600;
   
   public GUIView() {
      NUM_PLAYERS = 2;
      NUM_CARDS_PER_HAND = 7;
      String defaultName = new String();
      defaultName = NUM_PLAYERS + " Person Table";
      cardtable = new CardTable(defaultName,NUM_CARDS_PER_HAND, NUM_PLAYERS );
      constructorCommonality();
   }
   
   public GUIView(int NUM_PLAYERS, int NUM_CARDS_PER_HAND) {
      this.NUM_PLAYERS = NUM_PLAYERS;
      this.NUM_CARDS_PER_HAND = NUM_CARDS_PER_HAND;
      String defaultName = new String();
      defaultName = NUM_PLAYERS + " Person Table";
      cardtable = new CardTable(defaultName,NUM_CARDS_PER_HAND, NUM_PLAYERS );
      constructorCommonality();
   }
   
   public GUIView(String TableName,  int NUM_PLAYERS, int NUM_CARDS_PER_HAND) {
      this.NUM_PLAYERS = NUM_PLAYERS;
      this.NUM_CARDS_PER_HAND = NUM_CARDS_PER_HAND;
      cardtable = new CardTable(TableName,NUM_CARDS_PER_HAND, NUM_PLAYERS );
      constructorCommonality();
   }
   
   private boolean constructorCommonality() {
      try {
         cardtable.setSize(SIZE_ROW, SIZE_COL);
         cardtable.setLocationRelativeTo(null);
         cardtable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         return true;
      } catch (Exception e){
         return false;
      } 
   }
   
   public boolean makeVisible() {
      try {
         cardtable.setVisible(true);
         return true;
      } catch (Exception e) {
         return false;
      }
   }
   
   /*Getters*/
   public static int getSizeRow() {
      return SIZE_ROW;
   }

   public static int getSizeCol() {
      return SIZE_COL;
   }
   
   /*Getters*/
   public static boolean setSizeRow(int newRow) {
      if (newRow < 0) {
         return false;
      }
      SIZE_ROW = newRow;
      return true;
   }

   public static boolean setSizeCol(int newCol) {
      if (newCol < 0) {
         return false;
      }
      SIZE_COL = newCol;
      return true;
   }
   
}









