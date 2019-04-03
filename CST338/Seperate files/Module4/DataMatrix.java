package hw4;

import hw4.BarcodeImage;

public class DataMatrix implements BarcodeIO {
   
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
      int asteriskASCII = 42;
      int index = 0;

      clearImage();
      actualHeight = 10; // 8 bits plus bottom and top spline
      actualWidth = text.length() + 2; // + 2 for left and right spline
      // write top spline row to image
      
      for (int i = 0; i < actualWidth; i++) {
         if (i % 2 == 0) {
            image.setPixel(0,i,true);
         }
         else {
            image.setPixel(0,i,false);
         }
      }
      
      // first column should be black chars
      writeCharToCol(index, asteriskASCII);
      
      // fill data
      while (index < text.length()) {
         asciiValue = (int) text.charAt(index);
         writeCharToCol(index + 1, asciiValue); // Offset by 1 to give room for left border
         index++;
      }

      // write border column
      writeCharToCol(index, asteriskASCII);
      
      // Write bottom spline
      for (int i = 0; i < actualWidth; i++) {
         image.setPixel(actualHeight, i, true);
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
      //Assume that image is the data plus spline. To output, need to add border
      // Print line of dashes.
      for (int i = 0; i < (actualWidth + 2); i++) {
         System.out.print("-");
      }
      // print vertical border, then data row
      for (int i = 0; i < actualHeight; i++) {
         System.out.print("|");
         for (int j = 0; j < actualWidth; j++) {
            if (image.getPixel(i,j)) {
               System.out.print(BLACK_CHAR);
            } 
            else {
               System.out.print(WHITE_CHAR);
            }
         }
         System.out.println("|");
      }
      
      // print out the bottom dash line
      for (int i = 0; i < (actualWidth + 2); i++) {
         System.out.print("-");
      }
      //System.out.println("TODO: displayImageToConsole()");
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
      int ASTERISK_PER_COLUMN = 8;
      // code is asterisk, special column to write
      if ((col == 0) && (code == 42)) {
         for (int i = 0; i < ASTERISK_PER_COLUMN; i++) {
            image.setPixel(col,i,true);
         }
      }
      else if ((col == actualWidth) && (code == 42)) {
         for (int i = 0; i < ASTERISK_PER_COLUMN; i++) {
            if (( i % 2) == 0) {
               image.setPixel(col,i,true);
            }
         }
      else {
      /* Max height of data is 8, due to 2^8 = 128
         Anything past it is a weird symbol */
         
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

