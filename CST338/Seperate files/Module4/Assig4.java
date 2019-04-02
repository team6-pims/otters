/**
 * Assignment 4 - Optical Barcode Readers and Writers
 */

public class Assig4 {

    public static void main(String[] args)
    {
      System.out.println("Now begin Barcode Image class test");
      System.out.println("Test default constructor and print to screen\n");
      BarcodeImage bc0 = new BarcodeImage();
      
      bc0.displayToConsole();
      
      String[] str = new String[3];
      str[0] = "101";
      str[1] = "010";
      str[2] = "101";
      
      System.out.println("\nTest string constructor");
      BarcodeImage bc1 = new BarcodeImage(str);
      BarcodeImage bc2 = new BarcodeImage(str);
      
      System.out.println("Test print to screen with str constructor\n");
      bc1.displayToConsole();
      
      System.out.println("\nTest equals on 2 equivalent objects");
      
      System.out.println("BCI1 == BCI2? " + bc1.equals(bc2));
      
      System.out.println("Now lets test setters");
      System.out.println("Change first row to all 1s and second to all 0s");
      
      bc1.setPixel(1, 0, false);
      bc1.setPixel(1, 1, false);
      
      bc1.setPixel(0, 0, true);
      bc1.setPixel(0, 1, true);
      
      System.out.println("End");
      System.out.println("Lets test\n");
      bc1.displayToConsole();
      
      System.out.println("\nNow lets test getters");
      System.out.println("Lets get bottom row\n");
      System.out.println(bc1.getPixel(2, 0));
      System.out.println(bc1.getPixel(2, 1));
      System.out.println(bc1.getPixel(2, 2));
      System.out.println("\nSuccess");
      System.out.println("End Barcode Image class test");
      System.out.println("\nNow begin Instructor defined test");
        
      String[] sImageIn =
      {
         "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",  
         "                                               ",
         "                                               ",
         "                                               "
      };
      
      String[] sImageIn_2 =
      {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    ",
            "                                          ",
            "                                          ",
            "                                          ",
            "                                          "
      };

      BarcodeImage bc = new BarcodeImage(sImageIn);

      System.out.println("Original image: ");
      bc.displayToConsole();
      DataMatrix dm = new DataMatrix(bc);
      
      System.out.println("Cleaned image: ");
      dm.displayRawImage();
      
      // First secret message
      System.out.println("First secret message: ");
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImagetoConsole();
       
      // Second secret message
      System.out.println("\nSecond secret message: ");
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImagetoConsole();
       
       // Ceate your own message
       dm.readText("What a great resume builder this is!");
       dm.generateImageFromText();
       dm.displayRawImage();
       dm.displayTextToConsole();
       dm.displayImagetoConsole();
    }   
}

/**
 * BarcodeIO
 */

interface BarcodeIO {

   public boolean scan(BarcodeImage bc);

   public boolean readText(String text);

   public boolean generateImageFromText();

   public boolean translateImageToText();

   public void displayTextToConsole();
   
   public void displayImagetoConsole();
}

/**
 * BarcodeImage
 */
class BarcodeImage implements Cloneable {

   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   //This data will be false for elements 
   //that are white, and true for elements that are black.
   private boolean[][] imageData;

   /*Constructors*/
   public BarcodeImage() {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];

      for (int i = 0; i < MAX_HEIGHT; i++) {
         for (int j = 0; j < MAX_WIDTH; j++) {
            imageData[i][j] = false;
         }   
      }
   }

   public BarcodeImage(String[] stringData) {
      //potential for strData to be too big
      //if so just initialize to all 0s
      if (!checkSize(stringData)) {
         imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];

         for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
               imageData[i][j] = false;
            }   
         }
         return;
      }
      
      int rows = stringData.length;
      int columns = stringData[0].length();
      imageData = new boolean[rows][columns];

      for (int i = (rows - 1); i >= 0 ; i--) {
         for (int j = 0; j < columns; j++) {
            imageData[i][j] = charToBoolean(stringData[i].charAt(j));
         }   
      }
   }

   /*Setters & Getters*/
   public boolean setPixel(int row, int col, boolean value) {
      if (checkRow(row) && checkColumn(col)) {
         imageData[row][col] = value;
         return true;
      }
      return false;
   }

   public boolean getPixel(int row, int col) {
      if (checkRow(row) && checkColumn(col)) {
         return imageData[row][col];
      }
      return false;
   }

   /*Cloneable interface requisite*/
   public Object clone() {
      try {
         BarcodeImage otherBC = (BarcodeImage) super.clone();
         otherBC.imageData = imageData.clone();
         return otherBC;
      } catch (CloneNotSupportedException e) {
         return null;
      }
   }

   /* public helper function*/
   public boolean equals (Object otherObject) {
      if (otherObject == null) {
         return false;
      } else if (getClass() != otherObject.getClass() ) {
         return false;
      } else {
         BarcodeImage otherBCI = (BarcodeImage) otherObject;
         return java.util.Arrays.deepEquals(imageData, otherBCI.imageData);
      }
   }

   /*professor defined optional methods*/
   private boolean checkSize(String[] data) {
      if (data == null) {
         return false;
      }
      else if (checkRow(data.length - 1) && checkColumn(data[0].length() - 1)) {
         return true;
      }
      else {
         return false;
      }
   }

   public void displayToConsole() {
      for (int i = 0; i < imageData.length; i++) {
         for (int j = 0; j < imageData[i].length; j++) {
            System.out.print(booleanToChar(imageData[i][j]));
         }   
         System.out.println();
      }
   }

   /*Helper functions */
   private boolean checkRow(int rowToCheck) {
      if ((rowToCheck >= 0) && (rowToCheck < MAX_HEIGHT)) {
         return true;
      } 
      else {
         return false;
      }
   }

   private boolean checkColumn(int columnToCheck) {
      if ((columnToCheck >= 0) && (columnToCheck < MAX_WIDTH)) {
         return true;
      } 
      else {
         return false;
      }
   }

   private boolean charToBoolean (char c) {
      if (c == '1' || c == DataMatrix.BLACK_CHAR) {
         return true;
      } 
      else {
         return false;
      }
   }
   
   private char booleanToChar(boolean bool) {
      if (bool) {
         return '1';
      } 
      else {
         return '0';
      }
   }
}


/**
 * DataMatrix
 */
class DataMatrix implements BarcodeIO {
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
      if (text == null) {
         return false;
      }
      else {
         this.text = text;
         return true;
      }
   }
   
   public boolean scan(BarcodeImage bc) {
      try {
         image = (BarcodeImage) bc.clone();
         shiftImageDown(3);
         shiftImageLeft(4);
         cleanImage();
      } catch (Exception e) {
         return false;
      }
      return true;
   }
   
   public boolean generateImageFromText() {
      return true;
   }

   public boolean translateImageToText() {
      return true;
   }

   public void displayTextToConsole() {
      
   }
   
   public void displayImagetoConsole() {
      for (int i = 0; i < actualWidth; i++) {
         for (int j = 0; i < actualHeight; j++) {
            System.out.println();
         }
      }
   }
   
   private int computeSignalWidth() {
      int width = 0;
      
      return width;
   }
   
   private int computeSignalHeight() {
      int height = 0;

      return height;
   }
   
   private void cleanImage() {
      System.out.println("call CleanImage");
      moveImageToLowerLeft();
   }
   
   /* Helper methods */
   
   /*
    * cleanImage helper functions
    */
   private void moveImageToLowerLeft() {
      System.out.println("call move to corner");
      // find first pixel in row that is true
      // find last pixel that is true under limit
      // continue until no more rows have set pixels
      // find offsets
      int widthOffset = 0, heightOffset = 0;
      
      for (int i = 3; i < BarcodeImage.MAX_HEIGHT; i++) {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
            if (image.getPixel(i, j)) {
               widthOffset = j;
               heightOffset = i;
            }
         }
      }
      
      shiftImageLeft(widthOffset);
      shiftImageDown(heightOffset);
   }
   
   private void shiftImageDown(int offset) {
      System.out.println("shift down");
      for (int i = BarcodeImage.MAX_HEIGHT; i > 0; i--) {
         for (int j = 0; j < (BarcodeImage.MAX_WIDTH); j++) {
            image.setPixel(i, j, image.getPixel(i - offset, j));
         }
      }
   }
   
   private void shiftImageLeft(int offset) {
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++) {
         for (int j = 0; j < (BarcodeImage.MAX_WIDTH - offset); j++) {
            image.setPixel(i, j, image.getPixel(i, j + offset));
         }
      }
   }
   
   /*
    * Text, image helper functions
    */
   
   private char readCharFromColumn(int column) {
      for (int i = 1; i < column; i++) {
         
      }
      return 'A';
   }
   
   private boolean writeCharToColumn(int column, int code) {
      return true;
   }
   
   /*
    * displayRawImage used for debugging purposes
    */
   public void displayRawImage() {
      /*for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++) {
         for (int j = 0; i < BarcodeImage.MAX_WIDTH; j++) {
            System.out.println("i: " + Integer.toString(i));
            System.out.println("j: " + Integer.toString(j));
            if (image.getPixel(i, j)) {
               System.out.println(DataMatrix.BLACK_CHAR);
            } else {
               System.out.println(DataMatrix.WHITE_CHAR);
            }
         }
      }*/
      image.displayToConsole();
   }
   
   /* Accessors */
   public int getActualWidth() {
      return this.actualWidth;
   }
   
   public int getActualHeight() {
      return this.actualHeight;
   }
}
