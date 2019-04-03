/**
 * Assignment 4 - Optical Barcode Readers and Writers
 */

public class Assig4 {

    public static void main(String[] args)
    {
      System.out.println("Now begin Barcode Image class test");
      System.out.println("Test default constructor and print to screen");
      BarcodeImage bc0 = new BarcodeImage();
      
      System.out.println();
      bc0.displayToConsole();
      System.out.println();
      
      String[] str = new String[3];
      
      str[0] = "101";
      str[1] = "010";
      str[2] = "101";
      
      System.out.println("Test string constructor");
      BarcodeImage bc1 = new BarcodeImage(str);
      BarcodeImage bc2 = new BarcodeImage(str);
      
      System.out.println("Test print to screen with str constructor");
      bc1.displayToConsole();
      
      System.out.println("Test equals on 2 equivalent objects");
      
      System.out.println("BCI1 == BCI2? " + bc1.equals(bc2));
      System.out.println();
      
      System.out.println("Now lets test setters");
      System.out.println("Change first row to all 1s and second to all 0s");
      
      bc1.setPixel(0, 0, false);
      bc1.setPixel(0, 2, false);
      
      bc1.setPixel(0, 0, true);
      bc1.setPixel(0, 2, true);
      
      System.out.println("End");
      System.out.println("Lets test");
      bc1.displayToConsole();
      
      System.out.println("Now lets test getters");
      System.out.println("Lets get bottom row");
      System.out.println();
      System.out.println(bc1.getPixel(2, 0));
      System.out.println(bc1.getPixel(2, 1));
      System.out.println(bc1.getPixel(2, 2));
      System.out.println();
      System.out.println("Success");
      System.out.println("End Barcode Image class test");
      System.out.println("Now begin Instructor defined test");  
        
        
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
      bc = new BarcodeImage(sImageIn_2, '*');
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

