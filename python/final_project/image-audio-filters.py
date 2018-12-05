# Image and Audio filters 

# FUNCTIONS IN THIS FILE:
# getPic()
# shrink()
# mirrorLeft()
# mirrorRight()
# mirrorTop()
# mirrorBottom()
# betterBnw()
# sepia()
# artify()

# FUNCTIONS NEEDED STILL: increase, reduce individual colors (more/lessRed)
# chromakey and sound functions
# batch functions

# ***********
# Randy, put your batch functions here.


#     getPic()
#
# No argument, prompts user to select a picture
# Returns picture data

def getPic():
  return makePicture(pickAFile())

#     shrink(picture)
#
# Takes in a picture containing pixel data, then shrinks said picture by half
# Returns: shrunk image

def shrink(picture):
  # consider odd lengthed images, if either dimension is odd, subtract 1 from
  # dimension
  if picWidth%2 == 1:
    picWidth -= 1
  if picHeight%2 == 1:
    picHeight -= 1
    
  # obtain final dimensions, and declare secondary x/y counters
  picWidth = getWidth(picture) / 2
  picHeight = getHeight(picture) / 2
  x2 = 0
  y2 = 0
  
  # create an empty picture container
  shrunkPic = makeEmptyPicture(picWidth, picHeight)
  
  # cycle through each column, iterating down then right for the final
  # dimension size. For each pixel, obtain pixel data from original picture
  # By cycling through the final picture dimensions, we multiply each coord
  # by 2 to skip every other original pixel. The result is an image shrunk by
  # half.
  for x in range (0, picWidth):
    for y in range (0, picHeight):
      currentPixel = getPixel(picture, (x * 2), (y * 2))
      newPixel = getPixel(shrunkPic, x, y)
      pixelColor = getColor(currentPixel)
      setColor(newPixel, pixelColor)
  
  return shrunkPic

#     mirrorLeft(picture)
#
# Takes a picture containing pixel data, the mirrors all pixels on the left side
# to the right side, along a center vertical axis. 
# Returns the modified picture

def mirrorLeft(picture):
  
  picHeight = getHeight(picture)
  picWidth = getWidth(picture)
  
  for x in range (0, picWidth/2):
    for y in range (0, picHeight):
      pixelCurrent = getPixel(picture, x, y)
      newPix = getPixel(picture, (picWidth - x - 1), y)
      pixelColor = getColor(pixelCurrent)
      setColor(newPix, pixelColor)
  
  return picture

#     mirrorRight(picture)
#
# Takes a picture containing pixel data, the mirrors all pixels on the right side
# to the left side, along a center vertical axis. 
# Returns the modified picture

def mirrorRight(picture):
  
  picHeight = getHeight(picture)
  picWidth = getWidth(picture)
  
  for x in range (picWidth/2, picWidth):
    for y in range (0, picHeight):
      currentPixel = getPixel(picture, x, y)
      newPixel = getPixel(picture, (picWidth - x), y)
      pixelColor = getColor(currentPixel)
      setColor(newPixel, pixelColor)
  
  return picture
  
#     mirrorTop(picture)
#
# Takes a picture containing pixel data, the mirrors all pixels on the top half
# to the bottom half, along a center horizontal axis. 
# Returns the modified picture

def mirrorTop(picture):
  
  picHeight = getHeight(picture)
  picWidth = getWidth(picture)
  
  for x in range (0, picWidth):
    for y in range (0, picHeight/2):
      currentPixel = getPixel(pic, x, y)
      newPixel = getPixel(picture, x, picHeight - y - 1)
      pixelColor = getColor(currentPixel)
      setColor(newPixel, pixelColor)
      
  return picture
  
#     mirrorTop(picture)
#
# Takes a picture containing pixel data, the mirrors all pixels on the bottom half
# to the top half, along a center horizontal axis. 
# Returns the modified picture

def mirrorBottom(picture):
  
  picHeight = getHeight(picture)
  picWidth = getWidth(picture)
  
  for x in range (0, picWidth):
    for y in range (picHeight/2, picHeight):
      currentPixel = getPixel(pic, x, y)
      newPixel = getPixel(picture, x, picHeight - y)
      pixelColor = getColor(currentPixel)
      setColor(newPixel, pixelColor)
      
  return picture

#     betterBnW(picture)
#
# Takes in variable containing pixel data
# Converts color image into greyscale
# Returns: greyscaled picture

def betterBnW(picture):  
  sourceWidth = getWidth(picture)
  sourceHeight = getHeight(picture)
 
  for x in range(0, sourceWidth):
    for y in range(0, sourceHeight):
      pix = getPixel(picture, x, y)
      
      redColor = getRed(pix) * 0.299
      blueColor = getBlue(pix) * 0.587
      greenColor = getGreen(pix) * 0.114
      
      luminance = (redColor + blueColor + greenColor)
      
      setRed(pix, luminance)
      setBlue(pix, luminance)
      setGreen(pix, luminance)
    
  return picture

#     sepia(picture)
#
# Takes variable containing pixel data
# Modifies red and blue colors to create a sepia toned filter
# *** MUST RUN betterBnW() BEFORE ***
# Returns sepia toned image

def sepia(picture):
  for x in range(0, getWidth(picture)):
      for y in range(0, getHeight(picture)):
        # get pixel info
        pixel = getPixel(picture, x, y)
        
        # grab red
        redColor = getRed(pixel)
        if redColor < 63:
          setRed(pixel, (redColor * 1.15))
          setBlue(pixel, (redColor * 0.85))
        elif redColor >= 63 and redColor <= 190:
          setRed(pixel, (redColor * 1.2))
          setBlue(pixel, (redColor * 0.80))
        else:
          if (redColor * 1.08) > 255:
            setRed(pixel, 255)
          else:
            setRed(pixel, (redColor * 1.08))
            setBlue(pixel, (redColor * 0.93))
  
  return picture

#     artify(picture)
#
# Takes in a variable containg pixel data
# For each pixel, depending on the value of each color, returns one of
# five values in order to create a artsy image.
# Returns the modified picture

def artify(picture):
  
  for x in range(0, getWidth(picture)):
    for y in range(0, getHeight(picture)):
      # Get pixel details
      pixel = getPixel(picture, x, y)
      
      # Get individual color values
      redColor = getRed(pixel)
      greenColor = getGreen(pixel)
      blueColor = getBlue(pixel)
      
      # Run each color through our filter
      r = posterizer(redColor)
      g = posterizer(greenColor)
      b = posterizer(blueColor)
      
      # Combine the values into a Color object
      newColor = makeColor(redColor, greenColor, blueColor)
      
      # Change current pixel to new color
      setColor(pixel, newColor)
  
  return picture

#     posterizer(number)
#
# Takes in an integer
# Helper function for artify()
# Returns an integer 

def posterizer(number):
  if number < 64:
    return 46
  if number > 63 and number < 128:
    return 95
  if number > 127 and number < 192:
    return 159
  if number > 191 and number < 256:
    return 223
  return 0
