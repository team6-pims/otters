package hw4;

import hw4.BarcodeImage;

public class DataMatrix implements BarcodeIO{
   
   /* Data */
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   
   private BarcodeImage image;
   private String text;
   private int actualWidth, actualHeight;
   
   /* Constructors */
   public DataMatrix() {
      image = new BarcodeImage();
      actualWidth = 0;
      actualHeight = 0;
      text = null;
   }
   
   public DataMatrix(BarcodeImage image) {
      scan(image);
   }
   
   public DataMatrix(String text) {
      readText(text);
   }
   
   /* Methods */
   public boolean readText(String text) {
      this.text = text;
   }
   
   public boolean scan(BarcodeImage bc) {
      
   }
   
   public boolean generateImageFromText() {
      
   }

   public boolean translateImageToText() {
      
   }

   public void displayTextToConsole() {
      
   }
   
   public void displayImagetoConsole() {
      
   }
   
   private int computeSignalWidth() {
      
   }
   
   private int computeSignalHeight() {
      
   }
   
   private void cleanImage() {
      
   }
   
   /* Helper methods */
   
   /*
    * cleanImage helper functions
    */
   private void moveImageToLowerLeft() {
      
   }
   
   private void shiftImageDown(int offset) {
      
   }
   
   private void shiftImageLeft(int offset) {
      
   }
   
   /*
    * Text, image helper functions
    */
   
   private char readCharFromCol(int col) {
      
   }
   
   private boolean writeChartoCol(int col, int code) {
      
   }
   
   /*
    * displayRawImage used for debugging purposes
    */
   public void displayRawImage() {
      for (int i = 0; i < BarcodeImage.MAX_WIDTH; i++) {
         for (int j = 0; i < BarcodeImage.MAX_HEIGHT; j++) {
            if (image.getPixel(i, j)) {
               System.out.println(this.BLACK_CHAR);
            } else {
               System.out.println(this.WHITE_CHAR);
            }
         }
      }
   }
   
   /* Accessors */
   public int getActualWidth() {
      return this.actualWidth;
   }
   
   public int getActualHeight() {
      return this.actualHeight;
   }
   

}
