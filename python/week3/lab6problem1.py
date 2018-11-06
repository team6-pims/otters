def getPic():
  return makePicture(pickAFile())
  
# this function changes the image to grayscale

def betterBnW(picToBeChanged):
  for x in range(0, getWidth(picToBeChanged)):
      for y in range(0, getHeight(picToBeChanged)):
        # get pixel info
        p = getPixel(picToBeChanged, x, y)
        
        # find the luminance or average of rgb
        luminance = (getRed(p) + getBlue(p) + getGreen(p)) / 3
        
        # change each pixel color
        setRed(p, luminance)
        setBlue(p, luminance)
        setGreen(p, luminance)
        
  # return modified picture data to be saved.
  return picToBeChanged

# Since this function is going to be executed AFTER betterBnW does its work,
# the R, G, and B values are equal. We only need to retrieve one value to 
# apply the changes.
def sepia(pic):
  for x in range(0, getWidth(pic)):
      for y in range(0, getHeight(pic)):
        # get pixel info
        p = getPixel(pic, x, y)
        
        # grab red
        redColor = getRed(p)
        if redColor < 63:
          setRed(p, (redColor * 1.15))
          setBlue(p, (redColor * 0.85))
        elif redColor >= 63 and redColor <= 190:
          setRed(p, (redColor * 1.2))
          setBlue(p, (redColor * 0.80))
        else:
          if (redColor * 1.08) > 255:
            setRed(p, 255)
          else:
            setRed(p, (redColor * 1.08))
            setBlue(p, (redColor * 0.93))
  return pic

def sepiaFilter():
  
  # obtain image from user
  pic = getPic()
  
  show(sepia(betterBnW(pic)))
