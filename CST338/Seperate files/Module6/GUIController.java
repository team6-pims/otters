package hw6;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.*;


/**Controllers act as an interface between Model 
 * and View components to process all the business 
 * logic and incoming requests, manipulate data 
 * using the Model component and interact with 
 * the Views to render the final output. For 
 * example, the Customer controller will handle 
 * all the interactions and inputs from the Customer 
 * View and update the database using the Customer 
 * Model. The same controller will be used to view 
 * the Customer data.*/

class GUIController {
   private GUIView theGUI;
   private GUIModel theData;
   
   private int secs = 0;
   private int mins = 0;
   private Timer timer;
   
   public GUIController(GUIView theGUI, GUIModel theData) {
      this.theGUI = theGUI;
      this.theData = theData;
      
      TimerListener timerEar = new TimerListener();
      timer = new Timer(1000, timerEar);

      this.theGUI.addStartListener(new startGameListener());
      
      
      //this.theGUI.addQuitListener(new quitButtonListener());
   }
   
   /*Implement start game/timer listener class*/
   class startGameListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // since the same button starts the game, need logic to sense a 'stop'???
         if (theGUI.getStartStop() == "Stop Timer") {
            timer.stop();
            theGUI.setStartStop("Start Timer");
         }
         else if (theGUI.getStartStop() == "Start Timer") {
            timer.start();
            theGUI.setStartStop("Stop Timer");
         }
         else {  // initial press - start game
            timer.start();
            Hand playerHand = theData.getPlayerHand(1);

            int deckSize = theData.getDeckSize() - 
                  (theData.getNumPlayers() * theData.getHandSize());
            theData.startGame();
            theGUI.startGame(playerHand, deckSize, theData.getLeftCard(), theData.getRightCard());
            theGUI.addCardListener(new cardPressListener());
            theGUI.addLeftPileListener(new leftPileListener());
         }
      }
   }
   
   /*Implement local action listener class*/
   class cardPressListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         int humanLabelToHand = 0;
         int currentDeckSize = theData.getDeckSize();
         JButton[] playerCardSelection = theGUI.getHumanLabels();
         
         for (int i = 0; i < theData.getHandSize(); i++) {
            
            // Match button data with card index from player's hand
            if (e.getSource() == playerCardSelection[i]) {
               Hand playerHand = theData.getPlayerHand(1);
               int playerHandSize = playerHand.getNumCards();
               
               //relate what user chose to hand
               for (int jj = 0; jj < playerHandSize; jj++ ) {
                  if (GUICard.getIcon(playerHand.inspectCard(jj)) 
                        == playerCardSelection[i].getIcon()) {
                     humanLabelToHand = jj;
                     GUIView.resetCardColors();
                     System.out.println("You selected " + i);
                     theData.setPlayerSelection(jj);
                     ((JButton)e.getSource()).setBackground(Color.GREEN);
                     break;
                  }
               }
               //humanLabels[i].setVisible(false);
               //computerLabels[i].setVisible(false);

               //playRound(humanLabelToHand, currentDeckSize);

               //usedCards[usedCardCtr] = i;
               //usedCardCtr++;

               //if (theData.getHandSize() == 1) {
               //   endGame();
               //   break;
               //}

              // for (JLabel label: playedCardLabels)
                //  cardTable.panelPlayArea.add(label);
               break;
            }
         }
         theGUI.setVisible(true);
      }
   };

   /*End local action listener class*/
   
   /*Begin local quit button listener*/
   class quitButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         System.exit(0);
      }
   };
   /*End local quit button listener*/
   
   class leftPileListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // Get value of current selected card from player
         Hand playerHand = theData.getPlayerHand(1);
         int curSelection = theData.getPlayerSelection();
         //int curValue = playerHand.getCardAtIndex(curSelection);
         System.out.println("curSelec: " + curSelection);
         //System.out.println("curValue: " + curValue);
         System.out.println("curSuit : " + playerHand.getCardAtIndex(curSelection).getSuit());
      }
   }

   class rightPileListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         
      }
   }
   /* begin timer class */
   class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == timer)
            secs++;
         if (secs == 60) {
            mins++;
            secs = 0;
         }
         theGUI.setTimer(mins, secs);
         
      }
   }
   public void playRound(int humanChoice, int deckSize) {
      // play area card display
      Card userCard = new Card(theData.getPlayedCard(1, humanChoice));
      Icon userIcon = new ImageIcon();
      userIcon = GUICard.getIcon(userCard);
      theGUI.setPlayAreaIcon(1, userIcon);
      
      Card computerCard = new Card(theData.getPlayedCard(0, humanChoice));
      Icon computerIcon = new ImageIcon();
      computerIcon = GUICard.getIcon(computerCard);
      theGUI.setPlayAreaIcon(0, computerIcon);
      
      adjustHand();
      //adjustScore(userCard, computerCard);
      //theGUI.reDrawPlayerHand(theData.getPlayerHand(1), deckSize);
   }
   
   /**After the player has played their card to the play area, the card is
    * removed from their hand. Then sort the hands of the players, then 
    * take a card from the deck.
    */
   public void adjustHand() {
      theData.buildGame.sortHands();
      theData.buildGame.sortHands();
      
      // starts at computer(0), to max players (num players - 1)
      for (int i = 0; i < theData.getNumPlayers(); i++) {
         theData.buildGame.takeCard(i);
      }
   }
   
   /*public void adjustScore(Card playerCard, Card computerCard) {
      int currentPlayerWins = theData.getWinCount(1);
      int currentCompWins = theData.getWinCount(0);
      if (Card.cardGreaterThan(playerCard, computerCard)) {
         theData.setPlayerWinnings(playerCard, currentPlayerWins);
         theData.setPlayerWinnings(computerCard, currentPlayerWins + 1);
         theData.setWinCounter(1);
      }
      else {
         theData.setComputerWinnings(playerCard, currentCompWins);
         theData.setComputerWinnings(computerCard, currentCompWins + 1);
         theData.setWinCounter(0);
      }
   }*/
   
   public void endGame() {
      theGUI.endGame(theData.getWinCount(1), theData.getWinCount(0));
      theGUI.addQuitListener(new quitButtonListener());
   }
   
}
