package hw4;

public class BarcodeImage implements Cloneable{

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

      //potential for strData to be too big
      //if so just initialize to all 0s
      if (!checkSize(strData)) {
         imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];

         for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
               imageData[i][j] = false;
            }   
         }
         return;
      }
      
      int rows = strData.length;
      int cols = strData[0].length();
      
      imageData = new boolean[rows][cols];

      for (int i = (rows - 1); i >= 0 ; i--) {
         for (int j = 0; j < cols; j++) {
            imageData[i][j] = char2bool( strData[i].charAt(j) );
         }   
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

   private boolean char2bool (char c) {
      if (c == '1') {
         return true;
      } else {
         return false;
      }
   }
   
   private char bool2char(boolean bool) {
      if (bool) {
         return '1';
      }
      return '0';
   }

}
