package hw6;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
   }
   
   class startGameListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
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
                  (theData.getNumPlayers() * theData.getHandSize(0));
            theData.startGame();
            theGUI.startGame(playerHand, deckSize, theData.getLeftCard(), 
                  theData.getRightCard());
            theGUI.setSkipCounter(theData.getSkipCounter());
            theGUI.addCardListener(new cardPressListener());
            theGUI.addLeftPileListener(new leftPileListener());
            theGUI.addRightPileListener(new rightPileListener());
            theGUI.addPassButtonListener(new passButtonListener());
         }
      }
   }
   
   class cardPressListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         JButton[] playerCardSelection = theGUI.getHumanLabels();
         
         for (int i = 0; i < theData.getHandSize(1); i++) {
            
            // Match button data with card index from player's hand
            if (e.getSource() == playerCardSelection[i]) {
               Hand playerHand = theData.getPlayerHand(1);
               int playerHandSize = playerHand.getNumCards();
               
               //relate what user chose to hand
               for (int jj = 0; jj < playerHandSize; jj++ ) {
                  if (GUICard.getIcon(playerHand.inspectCard(jj)) 
                        == playerCardSelection[i].getIcon()) {
                     GUIView.resetCardColors();
                     //System.out.println("You selected " + i);
                     theData.setPlayerSelection(jj);
                     ((JButton)e.getSource()).setBackground(Color.GREEN);
                     break;
                  }
               }
               //playRound(humanLabelToHand, currentDeckSize);

               //if (theData.getHandSize() == 1) {
               //   endGame();
               //   break;
               //}

               break;
            }
         }
         theGUI.setVisible(true);
      }
   };

   class quitButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         System.exit(0);
      }
   };
   
   class passButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // button is always pushed by player, so we increment their counter
         theData.incrementSkipCounter(1);
         theData.setPlayerPassStatus(true);
         theGUI.setSkipCounter(theData.getSkipCounter());
         
         // call computer round stuff here
         computerTurn();
      }
   }
   
   class leftPileListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // Get value of current selected card from player
         Hand playerHand = theData.getPlayerHand(1);
         Hand computerHand = theData.getPlayerHand(0);
         int deckSize = theData.getDeckSize();
         int curSelection = theData.getPlayerSelection();;
         
         // First check player card before taking it
         Card playerCard = theData.getCardAtIndex(1, curSelection);
         Card pileCard = theData.getLeftCard();
         boolean isCardGood = isPlayerChoiceValid(pileCard, playerCard);
         
         if (isCardGood) {  // good card
            playerCard = theData.playHand(1, curSelection);
            theGUI.reDrawPlayCard(playerCard, true);
            theData.setLeftPile(playerCard);
            adjustHand(1);
            GUIView.resetCardColors();
            theGUI.reDrawHands(playerHand, computerHand, deckSize);
            
            // Now that we've went, now it's computer's turn
            computerTurn();
         }
      }
   }

   class rightPileListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         // Get value of current selected card from player
         Hand playerHand = theData.getPlayerHand(1);
         Hand computerHand = theData.getPlayerHand(0);
         int deckSize = theData.getDeckSize();
         int curSelection = theData.getPlayerSelection();
         
         // First check player card before taking it
         Card playerCard = theData.getCardAtIndex(1, curSelection);
         Card pileCard = theData.getRightCard();
         boolean isCardGood = isPlayerChoiceValid(pileCard, playerCard);
         
         if (isCardGood) {  // good card
            playerCard = theData.playHand(1, curSelection);
            theGUI.reDrawPlayCard(playerCard, false);
            theData.setRightPile(playerCard);
            adjustHand(1);
            GUIView.resetCardColors();
            theGUI.reDrawHands(playerHand, computerHand, deckSize);
            computerTurn();
         }
      }
   }
   
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
   
   /**After the player has played their card to the play area, the card is
    * removed from their hand. Then sort the hands of the players, then 
    * take a card from the deck.
    */
   public void adjustHand(int playerIndex) {
      // adds a card to the player
      theData.playerTakeCard(playerIndex);
      theData.sortAllHands();
   }
   
   /**Checks the value of the cards, disregarding suit, and returns a bool
    * based on that comparison
    * 
    * @param pileCard: card on deck
    * @param cardToCheck: card to be played
    * @return: true if it's a good card to play on the card compared
    */
   public boolean isPlayerChoiceValid(Card pileCard, Card cardToCheck) {
      boolean isValid = false;
      int pileCardValue = GUICard.getIconValueIndex(pileCard.getValue());
      int cardToCheckValue = GUICard.getIconValueIndex(cardToCheck.getValue());
      
      switch (pileCardValue) {
      // If Ace, then King or 2 is valid
      case 1:
         if (cardToCheckValue == 13 || cardToCheckValue == 2) {
            isValid = true;
         }
         break;
      // If King, then Queen or Ace is valid
      case 13:
         if (cardToCheckValue == 12 || cardToCheckValue == 1) {
            isValid = true;
         }
         break;
      // All other cases 
      default:
         if (Math.abs(pileCardValue - cardToCheckValue) == 1) {
            isValid = true;
         }
         break;
      }
      return isValid;
   }
   
   /**Helper function that dictates how the computer plays.
    * 
    * @return: true if the computer does NOT play a card
    */
   public boolean computerPlay() {
      Card computerCard;
      Card pileCard;
      boolean isCardGood = false;
      String displayText = "";
      
      for(int i = 0; i < theData.getHandSize(0); i++) {
         computerCard = theData.getCardAtIndex(0, i);   // checks each card
   
         // left pileCard check. if good, play. if not, continue
         pileCard = theData.getLeftCard();
         isCardGood = isPlayerChoiceValid(pileCard, computerCard);
         if (isCardGood) {
            computerCard = theData.playHand(0, i);
            displayText = "<html>Computer places " + computerCard.toString()
                           + " on left pile!</html>";
            theGUI.reDrawPlayCard(computerCard, true);
            theData.setLeftPile(computerCard);
            adjustHand(0);
            theGUI.setDeckCounter(theData.getDeckSize());
            theGUI.updateDisplayLabelText(displayText);
            return false;
         }
   
         // right pileCard check
         pileCard = theData.getRightCard();
         isCardGood = isPlayerChoiceValid(pileCard, computerCard);
         if (isCardGood) {
            computerCard = theData.playHand(0, i);
            displayText = "<html>Computer places " + computerCard.toString() 
                           + " on right pile!</html>";
            theGUI.reDrawPlayCard(computerCard, false);
            theData.setRightPile(computerCard);
            adjustHand(0);
            theGUI.setDeckCounter(theData.getDeckSize());
            theGUI.updateDisplayLabelText(displayText);
            return false;
         }
      }
      // if no good cards, computer skips.
      displayText = "Computer skips!";
      theData.incrementSkipCounter(0);
      theGUI.setSkipCounter(theData.getSkipCounter());
      theGUI.updateDisplayLabelText(displayText);
      return true;
   }
   
   /**Parent function which calls computerPlay. It also determines the end
    * of the game.
    * 
    */
   public void computerTurn() {
      boolean isDeckEmpty = false;
      String displayText = "";

      // computer's turn to play a card
      theData.setComputerPassStatus(computerPlay());

      // check passes after computer's turn to update piles
      if (theData.getComputerPassStatus() && theData.getPlayerPassStatus()) {
         // if both players pass, and the deck is empty, no more possible moves
         // end the game
         isDeckEmpty = newPile(); // false if cannot add cards
         if (!isDeckEmpty) {
            endGame();
         }
         displayText = "<html>Grabbing two cards from deck!</html>";
         theGUI.updateDisplayLabelText(displayText);
         theGUI.setDeckCounter(theData.getDeckSize());
      }
      theData.setComputerPassStatus(false);
      theData.setPlayerPassStatus(false);
   }
   
   /**Checks the deck and deals cards to the piles if no player can play a card
    * 
    * @return: true if there are cards in the deck, false if not.
    */
   public boolean newPile() {
      boolean enoughCards = false;
      
      if (theData.getDeckSize() > 1) {
         enoughCards = true;
         
         // Draw two new cards
         Card leftCard = theData.getCardFromDeck();
         Card rightCard = theData.getCardFromDeck();
         
         // Place new cards onto piles
         theData.setLeftPile(leftCard);
         theData.setRightPile(rightCard);
         theGUI.reDrawPlayCard(leftCard, true);
         theGUI.reDrawPlayCard(rightCard, false);
      }
      return enoughCards;
   }
   
   /**Ends the game. Tells the GUI to redraw the board to this end.
    * 
    */
   public void endGame() {
      theGUI.endGame(theData.getWinCount(1), theData.getWinCount(0));
      theGUI.addQuitListener(new quitButtonListener());
   }
}
