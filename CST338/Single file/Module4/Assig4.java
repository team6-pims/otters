/**
 * Assignment 4 - Optical Barcode Readers and Writers
 */

public class Assig4 {

    public static void main(String[] args)
    {
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

   public BarcodeImage(String[] strData) {

      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      //potential for strData to be too big
      //if so just initialize to all 0s
      if (!checkSize(strData)) {
         for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
               imageData[i][j] = false;
            }   
         }
         return;
      }
      
      int rowsIndex = strData.length - 1;
      //int cols = strData[0].length();

      for (int i = MAX_HEIGHT - 1; i >= 0 ; i--) {
         for (int j = 0; j < MAX_WIDTH; j++) {
            //imageData[i][j] = char2bool( strData[i].charAt(j) );
            if (rowsIndex >= 0 && j < strData[rowsIndex].length()) {
               if (strData[rowsIndex].charAt(j) == DataMatrix.BLACK_CHAR)
                  setPixel(i, j, true);
               else
                  setPixel(i, j, false);
            } else
               setPixel(i, j, false);

         }
         rowsIndex--;
      }

   }


   /*Setters & Getters*/

   public boolean setPixel(int row, int col, boolean value) {
      if ( check_row(row) && check_col(col) ) {
         imageData[row][col] = value;
         return true;
      }
      return false;
   }

   public boolean getPixel(int row, int col) {
      if ( check_row(row) && check_col(col) ) {
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

   /*prof defined optionals*/
   private boolean checkSize(String[] data) {
      if (data == null) {
         return false;
      }

      if ( check_row(data.length - 1) && check_col(data[0].length() - 1) ) {
         return true;
      }

      return false;

   }

   public void displayToConsole() {
      
      for (int i = 0; i < imageData.length; i++) {
         for (int j = 0; j < imageData[i].length; j++) {
            System.out.print(bool2char( imageData[i][j] ) );
         }   
         System.out.println();
      }
   }


   /*Helper functions */

   private boolean check_row(int row2chk) {
      if ((row2chk >= 0) && (row2chk < MAX_HEIGHT)) {
         return true;
      } 
      return false;
   }

   private boolean check_col(int col2chk) {
      if ((col2chk >= 0) && (col2chk < MAX_WIDTH)) {
         return true;
      } 
      return false;
   }

   /*private boolean char2bool (char c) {
      if (c == '1' || c == DataMatrix.BLACK_CHAR) {
         return true;
      } else {
         return false;
      }
   }*/
   
   private char bool2char(boolean bool) {
      if (bool) {
         return '1';
      }
      return '0';
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
   /**
    * We use the following variables when starting the 
    * loop from the top position.
    */
   private int rowsUpperLimit = BarcodeImage.MAX_HEIGHT - 1;
   private int colUpperLimit = BarcodeImage.MAX_WIDTH - 1;

   /* Constructors */
   public DataMatrix() {
      image = new BarcodeImage();
      actualWidth = 0;
      actualHeight = 0;
      text = "";
   }
   
   public DataMatrix(BarcodeImage image) {
      scan(image);
   }
   
   public DataMatrix(String text) {
      readText(text);
   }
   
   /* Methods */
   public boolean readText(String text) {
      if (text != "") {
         this.text = text;
         return true;
      }
      return false;
   }
   
   public boolean scan(BarcodeImage bc) {
      try {
         image = (BarcodeImage) bc.clone();
         cleanImage();
         actualWidth = computeSignalWidth();
         actualHeight = computeSignalHeight();
      } catch (Exception e) {
         return false;
      }
      return true;
   }
   
   public boolean generateImageFromText() {
      int asciiValue;
      int index = 0;

      clearImage();
      actualHeight = 10; // 8 bits plus bottom and top borders
      actualWidth = text.length() + 2; // + 2 for left and right borders

      while (index < text.length()) {
         asciiValue = (int) text.charAt(index);
         writeCharToCol(index + 1, asciiValue); // Offset by 1 to give room for left border
         index++;
      }

      return true;
   }

   public boolean translateImageToText() {
      text = "";

      for (int i = 1; i < actualWidth - 1; i++) {
         text += readCharFromCol(i);   
      }

      if (text != "")
         return true;
      else
         return false;
   }

   public void displayTextToConsole() {
      System.out.println(text);
   }
   /**
    * displayImageToConsole() should display only the relevant 
    * portion of the image, clipping the excess blank/white from
    * the top and right.  Also, show a border as in:
      ------------------------------------
      |* * * * * * * * * * * * * * * * * |
      |*                                *|
      |****   * ***** **** **** ******** |
      |*   *** ***************** ********|
      |*  * **  *   *   *  *    * **     |
      |* *       * *  **    * * *    ****|
      |*     *   *    ** * *  *  *  ** * |
      |** * *** *****  **     * *      **|
      |****  *   **** ** *   *   *  * *  |
      |**********************************|
    */

   public void displayImagetoConsole() {
      System.out.println("TODO: displayImageToConsole()");
   }
   
   /**
    * After the image is cleaned up, we only need to look
    * at the last row, last column. This time, we go from 
    * right to left, and count how many zeroes and subtract 
    * from MAX_WIDTH to determine signal width.
    */
   private int computeSignalWidth() {
      int zeroes = 0;

      for (int i = colUpperLimit; i >= 0; i--) {
         if (!image.getPixel(rowsUpperLimit, i))
            zeroes++;
         else
            break;
      }

      return BarcodeImage.MAX_WIDTH - zeroes;
   }
   
   /**
    * After the image is cleaned up, we only need to look
    * at the last row, first column.
    * This time, we go from bottom to top, and count how
    * many ones
    */
   private int computeSignalHeight() {
      int height = 0;

      for (int i = rowsUpperLimit; i >= 0; i--) {
         if (image.getPixel(i, 0))
            height++;
         else
            break;
      }
      return height;
   }
   
   private void cleanImage() {
      moveImageToLowerLeft();
   }
   
   /* Helper methods */

   /* 
   * moveImageToLowerLeft locates the spline of the image.
   * It does this by starting from the bottom and searching
   * from left to right. The first asterisk it finds will be
   * the corner piece, and we will have our (x, y) offsets to
   * transpose it.
   */
   private void moveImageToLowerLeft() {

      outerLoop:
      for (int i = rowsUpperLimit; i >= 0; i--) {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
            if (image.getPixel(i, j)) {
               shiftImageLeft(j);
               shiftImageDown(rowsUpperLimit - i);
               break outerLoop;
            }
         }
      }
   }
   
   /**
    * shiftImageDown approaches from the bottom, then proceeds
    * left to right. If an asterisk exists at i - offset, then
    * shift that pixel. Set to false if not. 
    */
   private void shiftImageDown(int offset) {
      for (int i = rowsUpperLimit; i >= 0; i--) {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
            if (image.getPixel(i - offset, j))
               image.setPixel(i, j, true);
            else
               image.setPixel(i, j, false);
         }
      }
   }
   
   /**
    * shiftImageLeft approaches from the bottom, then proceeds
    * left to right. If an asterisk exists at j + offset, then
    * set the current indices to an asterisk.
    */
   private void shiftImageLeft(int offset) {
      for (int i = rowsUpperLimit; i >= 0; i--) {
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
            if (image.getPixel(i, j + offset))
               image.setPixel(i, j, true);
            else
               image.setPixel(i, j, false);
         }
      }
   }
   
   /*
    * Text, image helper functions
    */
   
   /**
    * Given that our image is now properly aligned, we can
    * cast away the outer shell of the spline. We do this by
    * subtracting an additional 1 from rowsUpperLimit.
    *
    * We then loop from bottom to the top of the signal. Given
    * that the value ascends by powers of two, we use a running
    * total to add up values that have asterisks, while keeping
    * a separate counter for n, as in 2^n.
    */
   private char readCharFromCol(int col) {
      int asciiValue = 0;
      int nCounter = 0;
      int dataStart = rowsUpperLimit - 1;

      while (dataStart > BarcodeImage.MAX_HEIGHT - actualHeight) {
         if (image.getPixel(dataStart, col))
            asciiValue += Math.pow(2, nCounter);

         dataStart--;
         nCounter++;

      };

      return (char)asciiValue;
   }
   
   /**
    * writeCharToCol takes an ascii value and
    * converts it to binary in the form of
    * asterisks in the image. From top to bottom
    * we start with values of 128 and move down to 1
    * to represent our value.
    */
   private boolean writeCharToCol(int col, int code) {
      /* Max height of data is 8, due to 2^8 = 128
         Anything past it is a weird symbol */
      final int ASTERISK_PER_COLUMN = 8;
      int maxAsciiValue = 128;

      if (col > BarcodeImage.MAX_WIDTH)
         return false;
      
      for (int i = 0; i < ASTERISK_PER_COLUMN; i++) {
         if (code >= maxAsciiValue) {
            code -= maxAsciiValue;
            image.setPixel(i + 1, col, true); // Offset by one to give space for border
         }
         maxAsciiValue /= 2;
      }

      return true;
   }

   private void clearImage()
   {
      for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++) {        
         for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
            image.setPixel(i, j, false);
         }
      }
   }
   
   /*
    * displayRawImage used for debugging purposes
    */
   public void displayRawImage() {
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
