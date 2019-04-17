package hw6;

import java.text.*;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
/*The View component is used for 
 * all the UI logic of the application. 
 * For example, the Customer view will 
 * include all the UI components such as 
 * text boxes, dropdowns, etc. that the 
 * final user interacts with.*/
public class GUIView {
   private static CardTable cardTable;
   private static int NUM_PLAYERS;
   private static int NUM_CARDS_PER_HAND;
   
   private static int SIZE_ROW = 800;
   private static int SIZE_COL = 600;
   private static JLabel[] computerLabels;
   private static JButton[] humanLabels, playedCardLabels;  
   private static JButton quitButton, start, leftPile, rightPile, passTurn;
   private static JLabel timerLabel, deckIcon, cardsInDeckRemaining, 
   cardsRemainString, playerSkip, computerSkip;
   private static JPanel skipCounterArea;
   
   private static int numPacksPerDeck = 1;
   private static int numJokersPerPack = 2;
   private static int numUnusedCardsPerPack = 0;
   private static Card[] unusedCardsPerPack = null;
    
   private static JLabel dispTextLbl;  
   private static JLabel rulesLbl;
   
   public GUIView() {
      NUM_PLAYERS = 2;
      NUM_CARDS_PER_HAND = 7;
      String defaultName = new String();
      defaultName = NUM_PLAYERS + " Person Table";
      cardTable = new CardTable(defaultName, NUM_CARDS_PER_HAND, NUM_PLAYERS);
      constructorCommonality();
   }
   
   public GUIView(int NUM_PLAYERS, int NUM_CARDS_PER_HAND) {
      GUIView.NUM_PLAYERS = NUM_PLAYERS;
      GUIView.NUM_CARDS_PER_HAND = NUM_CARDS_PER_HAND;
      String defaultName = new String();
      defaultName = NUM_PLAYERS + " Person Table";
      cardTable = new CardTable(defaultName, NUM_CARDS_PER_HAND, NUM_PLAYERS );
      constructorCommonality();
   }
   
   public GUIView(String TableName,  int NUM_PLAYERS, int NUM_CARDS_PER_HAND) {
      GUIView.NUM_PLAYERS = NUM_PLAYERS;
      GUIView.NUM_CARDS_PER_HAND = NUM_CARDS_PER_HAND;
      cardTable = new CardTable(TableName, NUM_CARDS_PER_HAND, NUM_PLAYERS );
      constructorCommonality();
   }
   
   private boolean constructorCommonality() {
      try {
         computerLabels = new JLabel[NUM_CARDS_PER_HAND];
         humanLabels = new JButton[NUM_CARDS_PER_HAND];  
         playedCardLabels  = new JButton[NUM_PLAYERS];

         cardTable.setSize(SIZE_ROW, SIZE_COL);
         cardTable.setLocationRelativeTo(null);
         cardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  
         /* player pushes start to deal their hand and populate computer hand
          * also starts the timer. handled by controller*/
         start = new JButton("Start game.");
         cardTable.panelTimer.add(start);
         rulesLbl = new JLabel(getRules());
         rulesLbl.setHorizontalAlignment(SwingConstants.CENTER);
         rulesLbl.setVerticalAlignment(SwingConstants.CENTER);
         cardTable.panelPlayArea.add(rulesLbl);
         
         return true;
      } catch (Exception e) {
         return false;
      } 
   }
   
   private String getRules() {
      String retStr = "";
      retStr += "<html>Welcome to HALF SPEED! <BR><BR> Here are the rules: "
            + "<BR>";
      retStr += "<BR>  1) You and the computer take turns playing cards";
      retStr += "<BR>  2) You want to place the cards in ASC or DESC order";
      retStr += "<BR>  3) After you play a card, you get another card from the"
            + " deck";
      retStr += "<BR>  4) If you cant play, tap the I CANNOT PLAY button";
      retStr += "<BR>  5) whoever has the least I CANNOT PLAY presses at the "
            + "end wins";
      retStr += "<BR>  <BR> Can you tackle the computer?</html>";
      
      return retStr;
   }
   
   void addStartListener(ActionListener listenForStart) {
      start.addActionListener(listenForStart);
   }
   
   void addCardListener(ActionListener listenForCardPress) {
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
         humanLabels[i].addActionListener(listenForCardPress);
      }
   }
   
   void addLeftPileListener(ActionListener listenForPilePress) {
      leftPile.addActionListener(listenForPilePress);
   }
   
   void addRightPileListener(ActionListener listenForPilePress) {
      rightPile.addActionListener(listenForPilePress);
   }
   
   void addPassButtonListener(ActionListener listenForPass) {
      passTurn.addActionListener(listenForPass);
   }
   
   void addQuitListener(ActionListener listenForQuit) {
      quitButton.addActionListener(listenForQuit);
   }
   
   /**Redraws the board from the initial construction to the game format.
    * 
    * @param playerHand: to determine how many cards to draw per player
    * @param deckSize: cards in deck, for counter
    * @param leftCard: starting left card, for icon
    * @param rightCard: starting right card, for icon
    */
   public void startGame(Hand playerHand, int deckSize, Card leftCard, 
         Card rightCard) {
      
      // blank cards for computer
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++) 
         computerLabels[i] = new JLabel(GUICard.getBackCardIcon());
      
      // cards shown for player
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
         humanLabels[i] = new JButton(GUICard.getIcon(
               playerHand.inspectCard(i)));
         humanLabels[i].setBorderPainted(false);
      }
      
      passTurn = new JButton("Pass");
  
      cardTable.panelPlayArea.remove(rulesLbl); 
      
      // ADD LABELS TO PANELS -----------------------------------------
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++) 
         cardTable.panelComputerHand.add(computerLabels[i]);
      
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++) 
         cardTable.panelHumanHand.add(humanLabels[i]);
      
      // add the timer label to the board
      timerLabel = new JLabel("00:00", JLabel.CENTER);
      timerLabel.setBorder(new TitledBorder(new LineBorder(Color.black),
            "Time Elasped:"));
      cardTable.panelTimer.add(timerLabel);
      
      // Add card piles
      leftPile = new JButton(GUICard.getIcon(
            leftCard));
      rightPile = new JButton(GUICard.getIcon(
            rightCard));
      cardTable.panelPlayArea.add(leftPile);
      cardTable.panelPlayArea.add(rightPile);
      
      // place deck and counter
      deckIcon = new JLabel(GUICard.getBackCardIcon());
      cardTable.panelDeck.add(deckIcon);
      cardsRemainString = new JLabel("Cards left:");
      cardTable.panelDeck.add(cardsRemainString);
      int startDeckCount = deckSize - 2 - (playerHand.getNumCards() * 2);
      cardsInDeckRemaining = new JLabel(Integer.toString(startDeckCount), 
            JLabel.CENTER);
      
      cardTable.panelDeck.add(cardsInDeckRemaining);
      
      cardTable.panelPlayArea.add(passTurn);
      
      skipCounterArea = new JPanel();
      skipCounterArea.setLayout(new GridLayout(3,1));
      
      dispTextLbl = new JLabel();
      dispTextLbl.setHorizontalAlignment(SwingConstants.CENTER);
      dispTextLbl.setVerticalAlignment(SwingConstants.CENTER);
      skipCounterArea.add(dispTextLbl);
      
      playerSkip = new JLabel();
      playerSkip.setBorder(new TitledBorder(new LineBorder(Color.black), 
            "Player skips:"));
      skipCounterArea.add(playerSkip);
      
      computerSkip = new JLabel();
      computerSkip.setBorder(new TitledBorder(new LineBorder(Color.black), 
            "Computer skips:"));
      skipCounterArea.add(computerSkip);
      
      cardTable.panelPlayArea.add(skipCounterArea);
        
      // show everything to the user
      cardTable.setVisible(true);
      
      // timer
      start.setText("Stop Timer");
   }
   
   /**Updates a label on the game board which tells the player what the
    * computer's previous action was.
    * 
    * @param htmlString: String to be written
    * @return: true if successful
    */
   public boolean updateDisplayLabelText(String htmlString) {
      try {
         dispTextLbl.setText(htmlString);
         return true;
      } catch (Exception e) {
         return false;
      }
   }
   
   /**Redraws the played area card.
    * 
    * @param playedCard: card to be drawn.
    * @param isLeftPile: boolean, true if the card to be drawn goes on the left
    *           pile. false if it goes on the right
    */
   public void reDrawPlayCard(Card playedCard, boolean isLeftPile) {
      cardTable.panelPlayArea.setVisible(false);
      cardTable.panelPlayArea.removeAll();
      
      if (isLeftPile) {
         leftPile.setIcon(GUICard.getIcon(playedCard));
         
         cardTable.panelPlayArea.add(leftPile);
         cardTable.panelPlayArea.add(rightPile);
         cardTable.panelPlayArea.add(passTurn);
         cardTable.panelPlayArea.add(skipCounterArea);
      }
      else {
         rightPile.setIcon(GUICard.getIcon(playedCard));
         
         cardTable.panelPlayArea.add(leftPile);
         cardTable.panelPlayArea.add(rightPile);
         cardTable.panelPlayArea.add(passTurn);
         cardTable.panelPlayArea.add(skipCounterArea);
      }
      cardTable.panelPlayArea.setVisible(true);
   }
   
   /**Redraws the hands after a round of play.
    * 
    * @param playerHand: for current hand size. drawn card limit
    * @param computerHand: for current hand size. can differ from player
    *                towards the end.
    * @param deckSize: updates the deck card count.
    */
   public void reDrawHands(Hand playerHand, Hand computerHand, int deckSize) {
      if (deckSize == 0) {
         cardTable.panelHumanHand.setVisible(false);
         cardTable.panelHumanHand.removeAll();
         cardTable.panelComputerHand.removeAll();
         
         for (int i = 0; i < playerHand.getNumCards(); i++) {
            humanLabels[i].setIcon(GUICard.getIcon(playerHand.inspectCard(i)));
            humanLabels[i].setBorderPainted(false);
            cardTable.panelHumanHand.add(humanLabels[i]);
         }
         
         for (int i = 0; i < computerHand.getNumCards(); i++) {
            cardTable.panelComputerHand.add(computerLabels[i]);
         }
         
         cardsInDeckRemaining.setText(Integer.toString(deckSize));
         cardTable.panelHumanHand.setVisible(true);
      }
      else {
         for (int i = 0; i < playerHand.getNumCards(); i++) {
            humanLabels[i].setIcon(GUICard.getIcon(playerHand.inspectCard(i)));
            humanLabels[i].setBorderPainted(false);
         }  
     
         cardsInDeckRemaining.setText(Integer.toString(deckSize-2));
      }
   }
   
   /**Hides the table. Overloads default.
    * 
    * @param visibility: true to display, false to hide.
    * @return
    */
   public boolean setVisible(boolean visibility) {
      try {
         cardTable.setVisible(visibility);
         return true;
      } catch (Exception e) {
         return false;
      }
   }
   
   /**Redraws the game board for the end game. Displays final skip counts for
    * the player and computer. Displays winner.
    * 
    * @param playerSkips: int, total player skips
    * @param computerSkips: int, total computer skips
    */
   public void endGame(int playerSkips, int computerSkips) {
      cardTable.setVisible(false);
      skipCounterArea.removeAll();
      cardTable.panelPlayArea.remove(skipCounterArea);
      cardTable.panelPlayArea.remove(passTurn);
      cardTable.panelPlayArea.remove(rightPile);
      cardTable.panelPlayArea.remove(leftPile);
      cardTable.panelPlayArea.removeAll();
      cardTable.panelHumanHand.removeAll();
      cardTable.panelComputerHand.removeAll();
      
      cardTable.panelPlayArea.setLayout(new GridLayout(0,1));
      JLabel playerScore = new JLabel(String.valueOf(
            playerSkips));
      JLabel computerScore = new JLabel(String.valueOf(
            computerSkips));
      JLabel winner = new JLabel("Winner is:", JLabel.CENTER);
      JLabel winValue;
      if (playerSkips < computerSkips) 
         winValue = new JLabel("YOU!", JLabel.CENTER);
      else 
         winValue = new JLabel("NOT YOU!", JLabel.CENTER);

      quitButton = new JButton("Quit.");
      cardTable.panelPlayArea.add(winner);
      cardTable.panelPlayArea.add(winValue);
      cardTable.panelPlayArea.add(quitButton);
      cardTable.panelComputerHand.add(computerScore);
      cardTable.panelHumanHand.add(playerScore);
      cardTable.setVisible(true);
   }
   
   /*Getters*/
   public String getStartStop() {
      return start.getText();
   }
   
   public JButton[] getHumanLabels() {
      return humanLabels;
   }
   
   public static int getSizeRow() {
      return SIZE_ROW;
   }

   public static int getSizeCol() {
      return SIZE_COL;
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
   
   // setters
   public void setSkipCounter(int[] skipCounter) {
      computerSkip.setText(Integer.toString(skipCounter[0]));
      playerSkip.setText(Integer.toString(skipCounter[1]));
   }
   
   public void setDeckCounter(int deckSize) {
      cardsInDeckRemaining.setText(Integer.toString(deckSize));
   }
   
   public boolean setStartStop(String label) {
      if (!(label == null || label == "")) {
         start.setText(label);
         return true;
      }
      else
         return false;
   }
   
   public boolean setTimer(int mins, int secs) {
      DecimalFormat twoPosition = new DecimalFormat("00");
      String minute = twoPosition.format(mins);
      String second = twoPosition.format(secs);
      String time =  minute + ":" + second;

      timerLabel.setText(time);
      return false;
   }
   
   public boolean setPlayAreaIcon(int playerIndex, Icon icon) {
      if (playerIndex < NUM_PLAYERS && playerIndex >= 0) {
         playedCardLabels[playerIndex].setIcon(icon);
         return true;
      }
      else 
         return false;
   }
   
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
   
   public static boolean resetCardColors() {
      for (JButton btn: humanLabels) 
         btn.setBackground(null);
      return true;
   }
}
