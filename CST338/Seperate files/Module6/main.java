package hw6;

class main {
   
   public static void main(String[] args) {
      GUICard.loadCardIcons();
      GUIView gui = new GUIView(2, 7);
      GUIModel data = new GUIModel();
      GUIController control = new GUIController(gui, data);
      
      gui.setVisible(true);
   }
}
