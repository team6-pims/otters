# Week 3 - Lab 6
# TEAM PIMS
# Matt Chan, Alejandro Caicedo, Ivan Alejandre, Randy Son


# Modular function used in functions below
# Choose a picture and return it
def getPic():
  return makePicture(pickAFile())

#/////////////////////
# warm up
#/////////////////////

def redeye(getFunky = False, pickPics = True):
  if pickPics:
    pic0 = pickAFile()
    pic1 = pickAFile()
  else:
    pic0 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//red_eye//red_eye1.png'
    pic1 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//red_eye//red_eye2.png'
  
  threshold = 140
  pic0 = makePicture(pic0)
  pic1 = makePicture(pic1)
  pic_list = list([ pic0, pic1 ])
  
  pic0_height = getHeight(pic0)
  pic1_height = getHeight(pic1)
  height_list = list([pic0_height , pic1_height])
  
  pic0_width = getWidth(pic0)
  pic1_width = getWidth(pic1)
  width_list = list([pic0_width, pic1_width])
  
  pureRed = makeColor(255,0,0)
  
  for idx in range(0, 2):
    currpic = pic_list[idx]
    for x in range(0, width_list[idx] ):
      for y in range(0, height_list[idx] ):
        currPix = getPixel(currpic , x, y)
        currColor = getColor(currPix)
        if (distance(pureRed, currColor) < threshold):
          if getFunky:
            setColor(currPix, makeColor(60, 200, 8))
          else:
            setColor(currPix, black)
    show(currpic)

# //////////////////
# Problem 1 - Sepia
# /////////////////

# this function changes the image to grayscale
def betterBnW(picToBeChanged):
  for x in range(0, getWidth(picToBeChanged)):
      for y in range(0, getHeight(picToBeChanged)):
        # get pixel info
        p = getPixel(picToBeChanged, x, y)
        
        # find colors
        redColor = getRed(p) * 0.299
        blueColor = getBlue(p) * 0.587
        greenColor = getGreen(p) * 0.114
        
        # find the luminance or average of rgb
        luminance = (redColor + blueColor + greenColor)
        
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

# //////////////////////////
# Problem 2 - Posterization
# /////////////////////////
def artify():
  pic = getPic()
  
  for x in range(0, getWidth(pic)):
    for y in range(0, getHeight(pic)):
      # Get pixel details
      p = getPixel(pic, x, y)
      
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
  show(pic)

# Number filter
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
  
# //////////////////////////////
#    Problem #3 - ChromaKey
# //////////////////////////////
  
  
def chromakey(selectOwnPhotos = True, threshHold = 45):
  if selectOwnPhotos:
    #have user select
    foreground0 = getPic()
    foreground1 = getPic()
    foreground2 = getPic()
    background0 = getPic()
    background1 = getPic()
    background2 = getPic()
  else:
    #use my file paths
    foreground0 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//green_screen//airplane_585x390.png'
    foreground1 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//green_screen//jack_black_585x390.png'
    foreground2 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//green_screen//minion_585x390.png'
    background0 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//backgrounds_for_greenscreen//shark_585x390.png'
    background1 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//backgrounds_for_greenscreen//cave_585x390.png'
    background2 = '//home//captain//CSUMB//CST205//JES_workspace//lab6//backgrounds_for_greenscreen//everest_hike_585x390.png'
  
  fore0_pic = makePicture(foreground0)
  fore1_pic = makePicture(foreground1)
  fore2_pic = makePicture(foreground2)
  fore_lst = list([fore0_pic, fore1_pic, fore2_pic])
  
  back0_pic = makePicture(background0)
  back1_pic = makePicture(background1)
  back2_pic = makePicture(background2)
  back_lst = list([back0_pic, back1_pic, back2_pic])
  
  pic0_height = getHeight(fore0_pic)
  pic1_height = getHeight(fore1_pic)
  pic2_height = getHeight(fore2_pic)
  height_list = list([pic0_height , pic1_height , pic2_height ])
  
  pic0_width = getWidth(back0_pic)
  pic1_width = getWidth(back1_pic)
  pic2_width = getWidth(back2_pic)
  width_list = list([pic0_width, pic1_width, pic2_width ])
  
  pureGreen = makeColor(0,255,0)
  for idx in range(0, 3):
    currfore = fore_lst[idx]
    currback = back_lst[idx]
    for x in range(0, width_list[idx] ):
      for y in range(0, height_list[idx] ):
        #do things
        currPix = getPixel(currfore, x, y)
        currColor = getColor(currPix)
        if (distance(pureGreen, currColor) < threshHold):
          #swap
          backPix = getPixel(currback, x, y)
          backCol = getColor(backPix)
          setColor(currPix, backCol)

    show(currfore)
