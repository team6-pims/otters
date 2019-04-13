package hw6;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/*INCOMPLETE
 * 
 * Still need to figure out how to use CardGameFramework
 *    > Should it be a static in the GUIView class
 *       Or should it be in the Model?
 *       TBD
 * */

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*The View component is used for 
 * all the UI logic of the application. 
 * For example, the Customer view will 
 * include all the UI components such as 
 * text boxes, dropdowns, etc. that the 
 * final user interacts with.*/
public class GUIView {
   
   private CardTable cardtable;
   private static  int NUM_PLAYERS;
   private static  int NUM_CARDS_PER_HAND;
   
   private static int SIZE_ROW = 800;
   private static int SIZE_COL = 600;
   
   private static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   private static JButton[] humanLabels = new JButton[NUM_CARDS_PER_HAND];  
   
   private static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
   private static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
   
   private static int numPacksPerDeck = 1;
   private static int numJokersPerPack = 2;
   private static int numUnusedCardsPerPack = 0;
   private static Card[] unusedCardsPerPack = null;
   
   
   /*Implement local action listener class*/
   public class cardPressListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         int humanLableToHand = 0;

         for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
            if (e.getSource() == humanLabels[i]) {

               Hand playerHand = highCardGame.getHand(0);
               int playerHandSize = playerHand.getNumCards();
               //Hand computerHand = highCardGame.getHand(1);
               //int compHandSize = playerHand.getNumCards();

               //relate what user chose to hand
               for (int jj = 0; jj < playerHandSize; jj++ ) {
                  if (GUICard.getIcon(playerHand.inspectCard(jj)) == humanLabels[i].getIcon()) {
                     humanLableToHand = jj;
                     break;
                  }
               }

               humanLabels[i].setVisible(false);
               computerLabels[i].setVisible(false);

               Card userCard = new Card(highCardGame.playCard(1, humanLableToHand));
               Icon userIcon = new ImageIcon();
               userIcon = GUICard.getIcon(userCard);
               playedCardLabels[0].setIcon(userIcon);

               Card computerCard = new Card(highCardGame.playCard(0, humanLableToHand ));
               Icon computerIcon = new ImageIcon();
               computerIcon  = GUICard.getIcon(computerCard);
               playedCardLabels[1].setIcon(computerIcon);

               usedCards[usedCardCtr] = i;
               usedCardCtr++;

               if (Card.cardGreaterThan(userCard, computerCard)) {
                  playerWinnings[playerWinCounter] = userCard;
                  playerWinnings[playerWinCounter + 1] = computerCard;
                  playerWinCounter += 2;
               }
               else {
                  computerWinnings[computerWinCounter] = userCard;
                  computerWinnings[computerWinCounter + 1] = computerCard;
                  computerWinCounter += 2;
               }

               if (playerHandSize == 1) {
                  cardtable.panelPlayArea.removeAll();
                  cardtable.panelPlayArea.setLayout(new GridLayout(0,1));
                  JLabel playerScore = new JLabel(String.valueOf(
                        playerWinCounter));
                  JLabel computerScore = new JLabel(String.valueOf(
                        computerWinCounter));
                  JLabel winner = new JLabel("Winner is:", JLabel.CENTER);
                  JLabel winValue;
                  if (playerWinCounter > computerWinCounter) 
                     winValue = new JLabel("YOU!", JLabel.CENTER);
                  else 
                     winValue = new JLabel("NOT YOU!", JLabel.CENTER);

                  JButton quitButton = new JButton("Quit.");
                  quitButtonListener quitButtonEar = new quitButtonListener();
                  quitButton.addActionListener(quitButtonEar);
                  cardtable.panelPlayArea.add(winner);
                  cardtable.panelPlayArea.add(winValue);
                  cardtable.panelPlayArea.add(quitButton);
                  cardtable.panelComputerHand.add(computerScore);
                  cardtable.panelHumanHand.add(playerScore);
                  break;
               }

               for (JLabel label: playedCardLabels)
                  cardtable.panelPlayArea.add(label);
               break;

            }
         }
      }
   };

   /*End local action listener class*/
   
   /*Begin local quit button listener*/
   public class  quitButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         System.exit(0);
      }
   };
   /*End local quit button listener*/
   
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
         
         cardPressListener listener = new cardPressListener();
         
         // blank cards for computer
         for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
            computerLabels[i] = new JLabel(GUICard.getBackCardIcon());
         }
                  
         // cards shown for player
         for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
            humanLabels[i] = new JButton(GUICard.getIcon(highCardGame.getHand(0).inspectCard(i)));
            humanLabels[i].setBorderPainted(false);
            humanLabels[i].addActionListener(listener);
         }
         
         // create the played text
         playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
         playLabelText[1] = new JLabel("Player", JLabel.CENTER);
         
         // ADD LABELS TO PANELS -----------------------------------------
         for (int i = 0; i < NUM_CARDS_PER_HAND; i++) 
            cardtable.panelComputerHand.add(computerLabels[i]);

         for (int i = 0; i < NUM_CARDS_PER_HAND; i++) 
            cardtable.panelHumanHand.add(humanLabels[i]);

         for (int i = 0; i < NUM_PLAYERS; i++) 
            playedCardLabels[i] = new JLabel(GUICard.getBackCardIcon());

         for (JLabel label: playedCardLabels) 
            cardtable.panelPlayArea.add(label);

         for (JLabel label: playLabelText) 
            cardtable.panelPlayArea.add(label);
         
         return true;
      } catch (Exception e){
         return false;
      } 
   }
   
   public boolean setVisible(boolean visibility) {
      try {
         cardtable.setVisible(visibility);
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
   
   
   public static int getNumPacksPerDeck() {
      return numPacksPerDeck;
   }

   public static int getNumJokersPerPack() {
      return numJokersPerPack;
   }

   public static int getNumUnusedCardsPerPack() {
      return numUnusedCardsPerPack;
   }

   public static Card[] getUnusedCardsPerPack() {
      return unusedCardsPerPack;
   }
   
   

   public static boolean setNumPacksPerDeck(int newNum) {
      if (newNum < 0) {
         return false;
      }
      numPacksPerDeck = newNum;
      return true;
   }

   public static boolean setNumJokersPerPack(int newNum) {
      if (newNum < 0) {
         return false;
      }
      numJokersPerPack = newNum;
      return true;
   }


   public static boolean setNumUnusedCardsPerPack(int newNum) {
      if (newNum < 0) {
         return false;
      }
      numUnusedCardsPerPack = newNum;
      return true;
   }
   
}
















































