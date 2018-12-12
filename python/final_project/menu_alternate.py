import sys
import os

def mainMenu():
  # define our menu lists
  mainMenu = ["1 - Image manipulation",
             "2 - Audio manipulation",
             "3 - Image to Sound converter",
             "4 - Quit this program"]
  
  imageMenu = ["1 - Rose Colored Glasses",
              "2 - Negative",
              "3 - Black and White",
              "4 - Reduce red eyes",
              "5 - Artify",
              "6 - Add watermark",
              "7 - Go back"]
  
  imageSubText = ["Adds a rosy tint to your image!",
                 "Inverts your picture to give you the negative!",
                 "Makes your image black and white!",
                 "Removes the red eye from your picture!",
                 "Converts your picture into an artsy poster!",
                 "Adds a text watermark to your image in the lower right corner!"]
                 
  audioMenu = ["1 - Normalize Sound",
              "2 - Reverse Sound",
              "3 - Increase Volume",
              "4 - Decrease Volume",
              "5 - Go back"]
  
  audioSubText = ["Normalizes the volume!",
                 "Reverses your audio file!",
                 "Increases the volume of your audio file",
                 "Decreases the volume of your audio file"]
  
  imageToAudioMessage = "This feature converts a picture of your choice into a song!"
                        
  welcomeMessage = "Welcome to JES Swiss Army Knife!\nPlease make your selection:\n"
  
  imageFinished = "\nDone.\nYour new image has been saved in the same directory as the original.\nGoing back to the image menu.\n"
  audioFinished = "\nDone.\nYour new sound has been saved in the same directory as the original.\nGoing back to the audio menu.\n"
  
  printNow(welcomeMessage)
  currentMenu = mainMenu
  selection = "0"
  while true:
    
    if selection == "0":
      printMenu(currentMenu)
      selection = raw_input("\nYour selection please: ")
    
    if selection == "1":
      printNow("Welcome to the image manipulation menu!\n")
      currentMenu = imageMenu
      printMenu(currentMenu)
      imageSelection = raw_input("\nYour selection please: ")
      
      while true:
        if imageSelection == "1":
          printNow(imageSubText[0])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow("\nNo picture selected. Going back to image menu.\n")
            selection = "1"
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(roseColoredGlasses(pictureObject, picturePath))
            printNow(imageFinished)
          
        elif imageSelection == "2":
          printNow(imageSubText[1])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow("\nNo picture selected. Going back to image menu.\n")
            selection = "1"
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(negative(pictureObject, picturePath))
            printNow(imageFinished)
          
        elif imageSelection == "3":
          printNow(imageSubText[2])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow("\nNo picture selected. Going back to image menu.\n")
            selection = "1"
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(blackAndWhite(pictureObject, picturePath))
            printNow(imageFinished)
          
        elif imageSelection == "4":
          printNow(imageSubText[3])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow("\nNo picture selected. Going back to image menu.\n")
            selection = "1"
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(redEyeReduction(pictureObject, picturePath))
            printNow(imageFinished)
          
        elif imageSelection == "5":
          printNow(imageSubText[4])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow("\nNo picture selected. Going back to image menu.\n")
            selection = "1"
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(artify(pictureObject, picturePath))
            printNow(imageFinished)
          
        elif imageSelection == "6":
          printNow(imageSubText[5])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow("\nNo picture selected. Going back to image menu.\n")
            selection = "1"
            break
          else:
            text = requestString("Please enter the text you want to add as a watermark:")
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(copyWrite(pictureObject, picturePath, text))
            printNow(imageFinished)
          
        elif imageSelection == "7":
          printNow("\nGoing back to main menu.\n")
          currentMenu = mainMenu
          selection = "0"
          break
          
        else:
          imageSelection = raw_input("Invalid selection, try again: ")
      
    elif selection == "2":
      printNow("Welcome to the audio manipulation menu!\n")
      currentMenu = audioMenu
      printMenu(currentMenu)
      audioSelection = raw_input("\nYour selection please: ")
      
      while true:
        if audioSelection == "1":
          printNow(audioSubText[0])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow("No sound selected. Going back to audio menu.")
            selection = "2"
            break
          else:
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(normalize(soundObject, soundPath))
            printNow(audioFinished)
          
        elif audioSelection == "2":
          printNow(audioSubText[1])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow("No sound selected. Going back to audio menu.")
            selection = "2"
            break
          else:
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(reverse(soundObject, soundPath))
            printNow(audioFinished)
          
        elif audioSelection == "3":
          printNow(audioSubText[2])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow("No sound selected. Going back to audio menu.")
            selection = "2"
            break
          else:
            value = float(raw_input("Please enter the value(0-100) that you want to increase the volume by: "))
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(increaseVolume(soundObject, soundPath, value))
            printNow(audioFinished)
      
        elif audioSelection == "4":
          printNow(audioSubText[3])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow("No sound selected. Going back to audio menu.")
            selection = "2"
            break
          else:
            value = float(raw_input("Please enter the value(0-100) that you want to decrease the volume by: "))
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(decreaseVolume(soundObject, soundPath, value))
            printNow(audioFinished)
          
        elif audioSelection == "5":
          printNow("\nGoing back to main menu.\n")
          currentMenu = mainMenu
          selection = "0"
          break
    
        else:
          selection = raw_input("Invalid selection, try again: ")
        
    elif selection == "3":
      printNow(imageToAudioMessage)
      showInformation("In the following dialog box, please select your image of choice.")
      picturePath = pickAFile()
      if not os.path.exists(picturePath):
        printNow("\nNo picture selected. Going back to main menu.\n")
        selection = "0"
      else:
        printNow("Please wait, converting your image.")
        explore(convert_pic2audio(picturePath))
        printNow("Your new song has been saved in the same directory as the image.")
        printNow("Going back to the main menu.")
        currentMenu = mainMenu
      
    elif selection == "4":
      printNow("\nThank you, come again!")
      break
    else:
      selection = raw_input("Invalid selection, try again: ")
      
  
def printMenu(menu):
  for line in menu:
    printNow(line)
    
    
    
    
    
    
    
    


#
# convert_pic2audio
#    enterance fcn to picture -> audio 
#    conversion. takes in a filePath
#
# NOTE: get_correctSound fcn should be updated
#       so basePath points to correct audio path
#
def convert_pic2audio(filePath):
  if not os.path.exists(filePath):
    print 'not found'
    return -1
  
  try:
    picIn = makePicture(filePath)
  except:
    print 'not found'
    return -1
  
  soundValues = pixelToNotes(picIn)
   
  #all sound clips of same length
  numSamples = len(soundValues) * get_numSamples()
  sampleRate = get_clipSampleRate()
  print numSamples, int(sampleRate)
  outputSong = makeEmptySound( numSamples , int(sampleRate) ) 
  return assemble_ouputSong(soundValues, outputSong)
  
  
#
# get_correctSound(soundIndex)
#    returns sound clip corresponding to sndIdx
#    should be updated to reflect your local machine
#    path to sounds
def get_correctSound(soundIndex):
  import os
  basePath = os.path.dirname(os.path.abspath(__file__))
 
  a = os.path.join(basePath, 'clippedAudio', 'clipped_A.wav')
  b = os.path.join(basePath, 'clippedAudio', 'clipped_B.wav')
  c = os.path.join(basePath, 'clippedAudio', 'clipped_C.wav')
  d = os.path.join(basePath, 'clippedAudio', 'clipped_D.wav') 
  e = os.path.join(basePath, 'clippedAudio', 'clipped_E.wav')
  f = os.path.join(basePath, 'clippedAudio', 'clipped_F.wav')
  g = os.path.join(basePath, 'clippedAudio', 'clipped_G.wav')
   
  if soundIndex == 0:
    return makeSound(a)
  if soundIndex == 1:
    return makeSound(b)
  if soundIndex == 2:
    return makeSound(c)
  if soundIndex == 3:
    return makeSound(d)
  if soundIndex == 4:
    return makeSound(e)
  if soundIndex == 5:
    return makeSound(f)
  if soundIndex == 6:
    return makeSound(g)
  

#
# assemble_ouputSong()
#     take in clip index list and output-empty-song obj
#     returns assembled song
def assemble_ouputSong(clipIndexList, outputSong):
  for i in range(0,len(clipIndexList)):
    currentClip = get_correctSound(clipIndexList[i])
    outputSong = copy(currentClip, outputSong, i*get_numSamples() )
  return outputSong 

#
# get_clipSampleRate()
#      returns sampling rate
def get_clipSampleRate():    
  return getSamplingRate( get_correctSound(0) )

#
# get_numSmpls()
#      get nbr of samples in a music clip
def get_numSamples():
  return getLength( get_correctSound(0) )
#
#  copy()
#    Source is the source sound (the short clip)
#    Target is the target sound
def copy(source , target, start = 0):  
  sourceLength = getLength(source) - 1
  for i in range(0, sourceLength):
    setSampleValueAt(target, start + i, getSampleValueAt(source, i))    
  return target



#     pixelToNotes(picture)
#
# takes in one argument, a variable that has been assigned the contents of makePicture()
# processes the pixel data to create an array containing integers ranging from 0-6,
# representing musical tones ranging from middle C to B in a Cmajor scale.
# This function calls findShrinkFactor() to 'shrink' the image if need be. This is 
# done to ensure the resulting "song" is not too long.

def pixelToNotes(picture):
  # declare list to contain all note integers
  allNotes = list()
  
  # threshold for picture size to be passed as an argument
  maxArea = 2000
  
  # obtain dimensions of picture, and if odd, make even
  picWidth = getWidth(picture)
  picHeight = getHeight(picture)
  if picWidth%2 == 1:
    picWidth -= 1
  if picHeight%2 == 1:
    picHeight -= 1
  
  # find the factor which is how big a step we're going to iterate through in the following
  # recursion
  factor = findShrinkFactor(picWidth, picHeight, maxArea, 1)
  
  # distance() works by calculating the distance between two colors as if they were
  # mapped on a 3 dimensional cartesian graph. We'll use the keyword 'black' to 
  # represent (0,0,0). The resulting number that distance returns is saved in rawNote
  # Then we take that variable and use modulus to get a number 0-6 and append that
  # number to a list.
  for x in range(0, picWidth, factor):
    for y in range(0, picHeight, factor):
      pixel = getPixel(picture, x, y) 
      rawNote = int(distance(getColor(pixel), black)*1000)
      note = rawNote%7
      allNotes.append(note)
  
  # return the list of numbers
  return allNotes
  
#     findShrinkFactor(picWidth, picHeight, maxArea, factor)
#
# This function requires four arguments, height and width of a picture,
# an integer for the maximum picture area and an integer that represents how many
# pixels are to be skipped for each loop iteration. 
# If the current picture area after being divided by the initial factor 1,
# is greater than the threshold, we'll increase the factor by 1, and have the function
# call itself with the updated value. It'll continue until the picture is 'shrunk'
# to size. 
# Returns: one integer to be used in parent function

def findShrinkFactor(picWidth, picHeight, maxArea, factor):
  # obtain dimensions, then consider odd lengthed pictures
  newPicHeight = picHeight / factor
  newPicWidth = picWidth / factor
  
  # obtain current area
  picArea = newPicHeight * newPicWidth
  
  # compare to threshold and either 'shrink' if the conditional evaluates true
  # if not, pass back current value for factor
  if (picArea < maxArea):
    return factor - 1
  else:
    return findShrinkFactor(picWidth, picHeight, maxArea, factor + 1)


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
  return picObj

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
  return picObj

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
  return picObj

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
  return picObj

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
  return picObj

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
  return picObj



# Audio filters

import sys
import os

###################################################################################
# Most audio filters take 2 arguments, 3 arguments if it requires an input value
# Audio object, and file path (batch folder path + file name)
###################################################################################

def normalize(sndObj, filePath):
    # Initialize variable
    largestSample = 0.0

    # Find the largest sound sample
    for sample in getSamples(sndObj):
        if getSampleValue(sample) > largestSample:
            largestSample = getSampleValue(sample)
    
    # This is the factor that will get us as close to a sample value of 32767 without any sound clipping
    ampFactor = 32767.0 / largestSample

    # Loop through and apply ampFactor to every sample
    for sample in getSamples(sndObj):
        setSampleValue(sample, ampFactor * getSampleValue(sample))
    
    # Output
    writeSoundTo(sndObj, filePath.replace('.wav', '_normalized.wav'))
    return sndObj

def reverse(sndObj, filePath):
  # Get length of original sound file
  l = getLength(sndObj)
  
  # Create empty sound file
  revSound = makeEmptySound(l, int(getSamplingRate(sndObj)))
  
  # Start iterator at the highest position
  i = l - 1
  
  # Loop through original sound file
  for sample in range(0, getLength(sndObj)):
    # Set the sound values on the opposite end of the sound file
    setSampleValueAt(revSound, sample, getSampleValueAt(sndObj, i))
    i -= 1
  
  # Output
  writeSoundTo(revSound, filePath.replace('.wav', '_reverse.wav'))
  return sndObj

def increaseVolume(sndObj, filePath, value):
    # Loop through applying value factor
    for sample in getSamples(sndObj):
        setSampleValue(sample, (1.0 + (value/100.0)) * getSampleValue(sample))
    
    # Output
    writeSoundTo(sndObj, filePath.replace('.wav', '_increaseVolume.wav'))
    return sndObj

def decreaseVolume(sndObj, filePath, value):
    # Loop through applying value factor
    for sample in getSamples(sndObj):
        setSampleValue(sample, (1.0 - (value/100.0)) * getSampleValue(sample))
    
    # Output
    writeSoundTo(sndObj, filePath.replace('.wav', '_decreaseVolume.wav'))
    return sndObj
