# Image filters

import sys
import os

###################################################################################
# Most image filters take 2 arguments, 3 arguments if it requires an input value
# Picture object, and file path (batch folder path + file name)
###################################################################################

def roseColoredGlasses(picObj, filePath):
  pixels = getPixels(picObj)
  for eachPix in pixels:
  
    newRed = getRed(eachPix) * 1.3
    newBlue = getBlue(eachPix) * 0.6
    newGreen = getGreen(eachPix) * 0.5
    
    setRed(eachPix, newRed)
    setBlue(eachPix, newBlue)
    setGreen(eachPix, newGreen)
  
  # Search for file extension and replace it with one annotated with the filter it was applied with
  writePictureTo(picObj, filePath.replace('.jpg', '_roseColored.jpg'))

def negative(picObj, filePath):
  pixels = getPixels(picObj)

  for eachPix in pixels:
    oppositeRed = 255 - getRed(eachPix)
    oppositeBlue = 255 - getBlue(eachPix)
    oppositeGreen = 255 - getGreen(eachPix)
    
    setRed(eachPix, oppositeRed)
    setBlue(eachPix, oppositeBlue)
    setGreen(eachPix, oppositeGreen)
  
  # Search for file extension and replace it with one annotated with the filter it was applied with
  writePictureTo(picObj, filePath.replace('.jpg', '_negative.jpg'))

def blackAndWhite(picObj, filePath):
  pixels = getPixels(picObj)

  for eachPix in pixels:
    redColor = getRed(eachPix)
    blueColor = getBlue(eachPix)
    greenColor = getGreen(eachPix)
    luminance = (redColor * 0.299 + blueColor * 0.587 + greenColor * 0.114)
    
    setRed(eachPix, luminance)
    setBlue(eachPix, luminance)
    setGreen(eachPix, luminance)
  
  # Search for file extension and replace it with one annotated with the filter it was applied with
  writePictureTo(picObj, filePath.replace('.jpg', '_blackAndWhite.jpg'))

def redEyeReduction(picObj, filePath):
  # Set the red threshold
  threshold = 140
  
  # Loop through each pixel
  for x in range(0, getWidth(picObj)):
    for y in range(0, getHeight(picObj)):
      p = getPixel(picObj, x, y)
      c = getColor(p)
      
      # Take the cartesian distance between red and the pixel selected
      # to see if it falls within our threshold. Change to black if it does
      if (distance(red, c) < threshold):
        setColor(p, black)
  
  # Search for file extension and replace it with one annotated with the filter it was applied with
  writePictureTo(picObj, filePath.replace('.jpg', '_redEyeReduction.jpg'))

def artify(picObj, filePath):
  for x in range(0, getWidth(picObj)):
     for y in range(0, getHeight(picObj)):
       # Get pixel details
       p = getPixel(picObj, x, y)
       
       # Get individual color values
       r = getRed(p)
       g = getGreen(p)
       b = getBlue(p)
       
       # Run each color through our filter
       r = posterizer(r)
       g = posterizer(g)
       b = posterizer(b)
       
       # Combine the values into a Color object
       c = makeColor(r, g, b)
       
       # Change current pixel to new color
       setColor(p, c)
  
  # Search for file extension and replace it with one annotated with the filter it was applied with
  writePictureTo(picObj, filePath.replace('.jpg', '_artify.jpg'))

# Artify Number filter
def posterizer(n):
   if n < 64:
     return 46
   if n > 63 and n < 128:
     return 95
   if n > 127 and n < 192:
     return 159
   if n > 191 and n < 256:
     return 223
   return 0
   
 
def copyWrite(picObj, filePath, text):
  # Define picture boundaries
  w = getWidth(picObj)
  h = getHeight(picObj)
  
  # Add text to the bottom
  textStyle = makeStyle("sanSerif", italic, int((w - h) / (w*9/h)))
  addTextWithStyle(picObj, w - (len(text) * int((w - h) / (w*9/h))), h - 7, '(c) ' + text, textStyle, white)
  writePictureTo(picObj, filePath.replace('.jpg', '_copyWrited.jpg'))

main()
