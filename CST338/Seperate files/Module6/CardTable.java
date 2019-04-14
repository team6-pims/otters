package hw6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class CardTable extends JFrame{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
   private int numCardsPerHand;
   private int numPlayers;
   public JPanel panelComputerHand, panelHumanHand, panelPlayArea, panelTimer, panelDeck;

   public CardTable(String title, int NumCardsPerHand, int NumPlayers)  {
      super(title);
      
      if (!(NumCardsPerHand > 0 && NumCardsPerHand < MAX_CARDS_PER_HAND)) 
         numCardsPerHand = MAX_CARDS_PER_HAND;;

      if (!(NumPlayers > 0 && NumPlayers < MAX_PLAYERS))
         numPlayers = MAX_PLAYERS;
      
      numCardsPerHand = NumCardsPerHand;
      numPlayers = NumPlayers;
      
      setLayout(new BorderLayout());

      panelComputerHand = new JPanel();
      panelComputerHand.setLayout(new FlowLayout());
      panelComputerHand.setBorder(new TitledBorder(new LineBorder(Color.black),
            "Computer's Hand:"));
      add(panelComputerHand, BorderLayout.NORTH);
      
      panelPlayArea = new JPanel();
      panelPlayArea.setLayout(new GridLayout(2, numPlayers));
      panelPlayArea.setBorder(new TitledBorder(new LineBorder(Color.black),
            "Play Area:"));
      add(panelPlayArea, BorderLayout.CENTER);
      
      panelHumanHand = new JPanel();
      panelHumanHand.setLayout(new FlowLayout());
      panelHumanHand.setBorder(new TitledBorder(new LineBorder(Color.black),
            "Player's Hand:"));
      add(panelHumanHand, BorderLayout.SOUTH);
      
      panelTimer = new JPanel();
      panelTimer.setLayout(new GridLayout(2,1));
      panelTimer.setBorder(new LineBorder(Color.black));
      add(panelTimer, BorderLayout.WEST);
      
      panelDeck = new JPanel();
      panelDeck.setLayout(new GridLayout(3,1));
      panelDeck.setBorder(new LineBorder(Color.black));
      add(panelDeck, BorderLayout.EAST);
   }
   
   /**Getters*/
   public int getNumCardsPerHand() {
      return numCardsPerHand;
   }

   public int getNumPlayers() {
      return numPlayers;
   }

   /**Setters
    * Probably not needed?*/
   public boolean setNumCardsPerHand(int newNum) {
      if (!(numCardsPerHand > 0 && numCardsPerHand < MAX_CARDS_PER_HAND))
         return false;
      
      numCardsPerHand = newNum;
      return true;
   }

   public boolean setNumPlayers(int newNum) {
      if (!(numPlayers > 0 && numPlayers < MAX_PLAYERS))
         return false;
      
      numPlayers = newNum;
      return true;
   }
}
