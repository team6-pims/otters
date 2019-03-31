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
     //scans the BarcodeImage to determine where the image is
	  //checks the lower left for a true position in the array
	  boolean check = false;
	  int checkWidth = 0;
	  int checkHeight = 0;
	  while(check == false)
	  {
		  for(int i = 0; i < MAX_WIDTH; i++)
		  {
			  for(int j = (MAX_HEIGHT - 1); j >= 0 ; j--)
			  {
				  check = image.getPixel(i, j);
				  checkWidth = i;
				  checkHeight = MAX_HEIGHT - j;
				  if(check == true)
					  break;
			  }
			  if(check == true)
				  break;
		  }
	  }
	  shiftImageLeft(checkWidth);
	  shiftImageDown(checkHeight);
   }
   
   /* Helper methods */
   
   /*
    * cleanImage helper functions
    */
   
   private void shiftImageDown(int offset) {
      boolean value;
	   if(offset != 0)
      {
		   //for each width move the image down
		   for(int i = 0; i < MAX_WIDTH; i++)
		   {
			   for(int j = (MAX_Height - 1); j >= 0; j--)
			   {
				   value = image.getPixel(i, (j - offset));
				   image.setPixel(i, j, value);
				   image.setPixel(i, (j - offset), false);
			   }
		   }
	   }
   }
   
   private void shiftImageLeft(int offset) {
      boolean value;
      if(offset !=0)
      {
         //for each height move the image
    	   for(int i = 0; i < MAX_HEIGHT; i++)
    	   {
            for(int j = 0; j < (MAX_WIDTH - offset); j++)
            {
               value = image.getPixel((j + offset), i);
               image.setPixel(j, i, value);
               image.setPixel((j+offset), i, false);
            }
         }
      }
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
