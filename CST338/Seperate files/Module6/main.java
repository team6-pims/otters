package hw6;

public class main {
   
   public static void main(String[] args) {
      GUICard.loadCardIcons();
      GUIView gui = new GUIView();
      GUIModel data = new GUIModel();
      GUIController control = new GUIController(gui, data);
      
      gui.setVisible(true);
   }
}
