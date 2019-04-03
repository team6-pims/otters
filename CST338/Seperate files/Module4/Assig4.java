/**
 * Assignment 4 - Optical Barcode Readers and Writers
 * 
 * Created by:
 * Alejandro Caicedo
 * Ivan Alejandre
 * Randy Son
 * Matthew Chan
 *
 * 4/2/19
 */

public class Assig4 {

    public static void main(String[] args)
    {
      System.out.println("Begin Barcode Image class test...\nPlease note:"
            + "BarcodeImage's display function uses 0s and 1s. The \nfollowing"
            + " tests use this function to print to console. This is \nto "
            + "demonstrate the size of the max size 2D array.\n");
      System.out.println("Testing default Barcode constructor...\n");
      BarcodeImage bc0 = new BarcodeImage();
      bc0.displayToConsole();
      
      String[] str = new String[4];
      str[0] = "101";
      str[1] = "010";
      str[2] = "101";
      
      System.out.println("\nTesting string constructor...");
      BarcodeImage bc1 = new BarcodeImage(str);
      BarcodeImage bc2 = new BarcodeImage(str);
      
      System.out.println("Printing string constructor...\n");
      bc1.displayToConsole();
      
      System.out.println("\nTest equals on 2 equivalent BarcodeImage()");
      
      System.out.println("BCI1 == BCI2? \n" + bc1.equals(bc2));
      
      System.out.println("\nNow lets test setters.");
      System.out.println("Change first row to all 1s and second to all 0s");
      
      bc1.setPixel(1, 0, false);
      bc1.setPixel(1, 1, false);
      bc1.setPixel(0, 0, true);
      bc1.setPixel(0, 1, true);
      
      System.out.println("Lets print the results\n");
      bc1.displayToConsole();
      
      System.out.println("\nNow lets test getters.");
      System.out.println("Lets get bottom row...\n");
      System.out.println(bc1.getPixel(2, 0));
      System.out.println(bc1.getPixel(2, 1));
      System.out.println(bc1.getPixel(2, 2));
      System.out.println("\nSuccess.");
      System.out.println("End Barcode Image class test.");
      System.out.println("\nNow begin Instructor defined tests...");
        
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

      BarcodeImage bc = new BarcodeImage(sImageIn, '*');

      System.out.println("\nOriginal image using Barcode display function: ");
      bc.displayToConsole();
      DataMatrix dm = new DataMatrix(bc);
      
      System.out.println("\nLeft lowered array from DataMatrix: ");
      dm.displayRawImage();
      
      // First secret message
      System.out.println("\nFirst secret message: ");
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
       
      // Second secret message
      System.out.println("\nSecond secret message: ");
      bc = new BarcodeImage(sImageIn_2, '*');
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
       
      // Create your own message
      System.out.println("\nCustom made message: ");
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      System.out.println("Raw Image:");
      dm.displayRawImage();
      System.out.println("\nCustom secret message: ");
      dm.displayTextToConsole();
      dm.displayImageToConsole();
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
   
   public void displayImageToConsole();
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
         for (int j = 0; j < MAX_WIDTH; j++)
            imageData[i][j] = false;  
      }
   }

   public BarcodeImage(String[] stringData) {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      //potential for strData to be too big
      //if so just initialize to all 0s
      if (!checkSize(stringData)) {
         for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) 
               imageData[i][j] = false;  
         }
         return;
      }
      
      int rows = stringData.length - 1;
      int cols = stringData[0].length();

      for (int i = (rows - 1); i >= 0 ; i--) {
         for (int j = 0; j < cols; j++) 
            imageData[i][j] = charToBoolean(stringData[i].charAt(j));
      }
   }

   public BarcodeImage(String[] stringData, char parseTrueChar) {

      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      //potential for strData to be too big
      //if so just initialize to all 0s
      if (!checkSize(stringData)) {
         for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) 
               imageData[i][j] = false; 
         }
         return;
      }
      
      int rowsIndex = stringData.length - 1;
      int columns = stringData[0].length();

      for (int i = rowsIndex; i >= 0 ; i--) {
         for (int j = 0; j < columns; j++) {
            if (doesCharEqualParse(stringData[i].charAt(j),parseTrueChar) ) 
               setPixel(i, j, true);
            else 
               setPixel(i, j, false);
         }
      }
   }


   /*Setters & Getters*/

   public boolean setPixel(int row, int column, boolean value) {
      if (checkRow(row) && checkColumn(column)) {
         imageData[row][column] = value;
         return true;
      }
      return false;
   }

   public boolean getPixel(int row, int column) {
      if (checkRow(row) && checkColumn(column)) {
         return imageData[row][column];
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
      if (data == null) 
         return false;

      if (checkRow(data.length - 1) && checkColumn(data[0].length() - 1)) 
         return true;

      return false;
   }

   public void displayToConsole() {      
      for (int i = 0; i < imageData.length; i++) {
         for (int j = 0; j < imageData[i].length; j++) {
            System.out.print(booleanToChar( imageData[i][j] ) );
         }   
         System.out.println();
      }
   }

   /*Helper functions */

   private boolean checkRow(int rowToCheck) {
      if ((rowToCheck >= 0) && (rowToCheck < MAX_HEIGHT)) 
         return true;
      
      return false;
   }

   private boolean checkColumn(int columnToCheck) {
      if ((columnToCheck >= 0) && (columnToCheck < MAX_WIDTH)) 
         return true;
      
      return false;
   }

   private boolean charToBoolean (char c) {
      if (c == '1') 
         return true;
      else 
         return false;
   }
   
   private char booleanToChar(boolean bool) {
      if (bool) 
         return '1';
      
      return '0';
   }
   
   private boolean doesCharEqualParse(char c, char parseChar) {
      if (c == parseChar) 
         return true;
      else 
         return false;
   }
}

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
      final int asteriskASCII = 42;
      int index = 0;

      clearImage();
      actualHeight = 10; // 8 bits plus bottom and top spline
      actualWidth = text.length() + 2; // + 2 for left and right spline
      
      // write top spline row to image
      for (int i = 0; i < actualWidth; i++) {
         if (i % 2 == 0) 
            image.setPixel(index,i,true);
         else 
            image.setPixel(index,i,false);
      }
      
      // left column should be all black chars
      writeCharToCol(index, asteriskASCII);
      
      // fill data
      while (index < (text.length())) {
         asciiValue = (int) text.charAt(index);
         writeCharToCol(index + 1, asciiValue); // Offset by 1 to give room for left border
         index++;
      }

      // right border column, alternating with black on odd rows
      writeCharToCol((index + 1), asteriskASCII);
      
      // Write bottom spline
      for (int i = 0; i < actualWidth; i++) {
         image.setPixel((actualHeight - 1), i, true);
      }
      
      // clean image to remove blank rows and columns
      cleanImage();
      return true;
   }

   public boolean translateImageToText() {
      text = "";

      for (int i = 1; i < actualWidth - 1; i++) 
         text += readCharFromColumn(i);   

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

   public void displayImageToConsole() {
      //Assume that image is the data plus spline. To output, need to add border
      // Print line of dashes.
      for (int i = 0; i < (actualWidth + 2); i++) 
         System.out.print("-");
      
      // print vertical border, then data row
      for (int i = (rowsUpperLimit - actualHeight + 1); i <= rowsUpperLimit; i++) {
         System.out.print("\n|");
         for (int j = 0; j <= (actualWidth - 1); j++) {
            if (image.getPixel(i,j)) 
               System.out.print(BLACK_CHAR);
            else 
               System.out.print(WHITE_CHAR);
         }
         System.out.print("|");
      }
      
      // print out the bottom dash line
      System.out.print("\n");
      for (int i = 0; i < (actualWidth + 2); i++) 
         System.out.print("-");
      
      System.out.println();
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
   private char readCharFromColumn(int column) {
      int asciiValue = 0;
      int nCounter = 0;
      int dataStart = rowsUpperLimit - 1;

      while (dataStart > BarcodeImage.MAX_HEIGHT - actualHeight) {
         if (image.getPixel(dataStart, column))
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
   private boolean writeCharToCol(int column, int code) {
      final int ASTERISK_PER_COLUMN = 8;
      int maxAsciiValue = 128;
      
      if (column > BarcodeImage.MAX_WIDTH)
         return false;
      
      // code is asterisk, special column to write
      if ((column == 0) && (code == 42)) {
         for (int i = 0; i < actualHeight; i++) 
            image.setPixel(i, column, true);
      }
      else if ((column == (actualWidth - 1) && (code == 42))) {
         for (int i = 1; i < (actualHeight); i++) {
            if ((i % 2) == 1) 
               image.setPixel(i, column, true);
         }
      }
      else {
      /* Max height of data is 8, due to 2^8 = 128
         Anything past it is a weird symbol */
      
         for (int i = 0; i < ASTERISK_PER_COLUMN; i++) {
            if (code >= maxAsciiValue) {
               code -= maxAsciiValue;
               // Offset by one to give space for border
               image.setPixel((i + 1), column, true); 
            }
            maxAsciiValue /= 2;
         }
      }
      return true;
   }

   private void clearImage() {
      image = new BarcodeImage();
      for (int i = 0; i < (BarcodeImage.MAX_HEIGHT-1); i++) {        
         for (int j = 0; j < (BarcodeImage.MAX_WIDTH-1); j++) 
            image.setPixel(i, j, false);
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

/********************************OUTPUT****************************************
 * Begin Barcode Image class test...
Please note:BarcodeImage's display function uses 0s and 1s. The 
following tests use this function to print to console. This is 
to demonstrate the size of the max size 2D array.

Testing default Barcode constructor...

00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000

Testing string constructor...
Printing string constructor...

10100000000000000000000000000000000000000000000000000000000000000
01000000000000000000000000000000000000000000000000000000000000000
10100000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000

Test equals on 2 equivalent BarcodeImage()
BCI1 == BCI2? 
true

Now lets test setters.
Change first row to all 1s and second to all 0s
Lets print the results

11100000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
10100000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000

Now lets test getters.
Lets get bottom row...

true
false
true

Success.
End Barcode Image class test.

Now begin Instructor defined tests...

Original image using Barcode display function: 
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000101010101010101010101010101010101010101010000000000000000000
00000100000000000000000000000000000000000000010000000000000000000
00000111111011110111111011111110110111011111000000000000000000000
00000100000100001111111111111111111111111111110000000000000000000
00000101100001010000000011001000010101000100000000000000000000000
00000100010000100111110000100010100010011001110000000000000000000
00000100110000010111011000110010000110011100100000000000000000000
00000111001011000110010001111000010010011010110000000000000000000
00000111110011100100101000110110110010001010000000000000000000000
00000111111111111111111111111111111111111111110000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000

Left lowered array from DataMatrix: 
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
10101010101010101010101010101010101010101000000000000000000000000
10000000000000000000000000000000000000001000000000000000000000000
11111101111011111101111111011011101111100000000000000000000000000
10000010000111111111111111111111111111111000000000000000000000000
10110000101000000001100100001010100010000000000000000000000000000
10001000010011111000010001010001001100111000000000000000000000000
10011000001011101100011001000011001110010000000000000000000000000
11100101100011001000111100001001001101011000000000000000000000000
11111001110010010100011011011001000101000000000000000000000000000
11111111111111111111111111111111111111111000000000000000000000000

First secret message: 
CSUMB CSIT online program is top notch.
-------------------------------------------
|* * * * * * * * * * * * * * * * * * * * *|
|*                                       *|
|****** **** ****** ******* ** *** *****  |
|*     *    ******************************|
|* **    * *        **  *    * * *   *    |
|*   *    *  *****    *   * *   *  **  ***|
|*  **     * *** **   **  *    **  ***  * |
|***  * **   **  *   ****    *  *  ** * **|
|*****  ***  *  * *   ** ** **  *   * *   |
|*****************************************|
-------------------------------------------

Second secret message: 
You did it!  Great work.  Celebrate.
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|
----------------------------------------

Custom made message: 
Raw Image:
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
00000000000000000000000000000000000000000000000000000000000000000
10101010101010101010101010101010101010000000000000000000000000000
10000000000000000000000000000000000001000000000000000000000000000
11111010111110111111011111110111101100000000000000000000000000000
10111111111111111111111111111111111111000000000000000000000000000
11001000010010101100001000010100100100000000000000000000000000000
10100000000000000010000110000011001001000000000000000000000000000
11001000101010010111001011100100000000000000000000000000000000000
11000000110000101000010000010000100101000000000000000000000000000
11010010101100011111001100100001101110000000000000000000000000000
11111111111111111111111111111111111111000000000000000000000000000

Custom secret message: 
What a great resume builder this is!
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|***** * ***** ****** ******* **** **  |
|* ************************************|
|**  *    *  * * **    *    * *  *  *  |
|* *               *    **     **  *  *|
|**  *   * * *  * ***  * ***  *        |
|**      **    * *    *     *    *  * *|
|** *  * * **   *****  **  *    ** *** |
|**************************************|
----------------------------------------*/
