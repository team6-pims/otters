def getPic():
  return makePicture(pickAFile())

# Problem 2 - Posterization
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

  
  
  
  
  
  
  
  
  
  
  
  
  
  
