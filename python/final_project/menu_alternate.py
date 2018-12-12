import sys
import os

def mainMenu():
  # define our menu lists
  mainMenu = ["\nMain Menu:",
             "1 - Image manipulation",
             "2 - Audio manipulation",
             "3 - Image to Sound converter",
             "4 - Quit this program"]
  
  imageMenu = ["\nImage Manipulation Menu:",
              "1 - Rose Colored Glasses",
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
                 
  audioMenu = ["\nAudio Manipulation Menu:",
              "1 - Normalize Sound",
              "2 - Reverse Sound",
              "3 - Increase Volume",
              "4 - Decrease Volume",
              "5 - Go back"]
  
  audioSubText = ["Normalizes the volume!",
                 "Reverses your audio file!",
                 "Increases the volume of your audio file",
                 "Decreases the volume of your audio file"]
  
  # define some strings to minimize text in conditionals
  imageToAudioMessage = "This feature converts a picture of your choice into a song!"
  welcomeMessage = "Welcome to JES Swiss Army Knife!\nPlease make your selection:"
  imageFinished = "Done.\nYour new image has been saved in the same directory as the original.\nGoing back to the image menu."
  audioFinished = "Done.\nYour new sound has been saved in the same directory as the original.\nGoing back to the audio menu."
  errorImage1 = "\nNo picture selected. Going back to image menu."
  errorImage2 = "\nNot a valid picture file type. Must be .jpg, .jpeg, or .png. Going back to image menu."
  errorSound1 = "\nNo sound selected. Going back to audio menu."
  errorSound2 = "\nNot a valid sound file type. Must be a .wav file. Going back to audio menu."
  
  # initialize program and greet the user. set currentMenu to mainMenu list
  printNow(welcomeMessage)
  currentMenu = mainMenu
  selection = "0"
  
  
  while true:
    # 'go back' selections in sub menues fault back to this conditional
    # also prints the main menu upon initialization
    if selection == "0":
      printMenu(currentMenu)
      selection = raw_input("\nYour selection please: ")
    
    # prints out imageMenu list then enters a sub loop.
    elif selection == "1":
      #printNow("Welcome to the image manipulation menu!\n")
      currentMenu = imageMenu
      printMenu(currentMenu)
      imageSelection = raw_input("\nYour selection please: ")
      
      # while in this loop, the user is on the image sub menu, will break only when user
      # chooses to. In each sub option conditional, has failsafes if user does not select 
      # image (returning back to image menu). Otherwise passes the path to each specific function
      # then displays the final product to the user using explore() and is saved within the 
      # function procedure to the original directory.
      while true:
        # rose colored glasses option
        if imageSelection == "1":
          printNow(imageSubText[0])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow(errorImage1)
            selection = "1"
            break
          elif not checkPicturePath(picturePath):
            printNow(errorImage2)
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(roseColoredGlasses(pictureObject, picturePath))
            printNow(imageFinished)
            break
        
        # negative option
        elif imageSelection == "2":
          printNow(imageSubText[1])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow(errorImage1)
            selection = "1"
            break
          elif not checkPicturePath(picturePath):
            printNow(errorImage2)
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(negative(pictureObject, picturePath))
            printNow(imageFinished)
            break
        
        # black and white option
        elif imageSelection == "3":
          printNow(imageSubText[2])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow(errorImage1)
            selection = "1"
            break
          elif not checkPicturePath(picturePath):
            printNow(errorImage2)
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(blackAndWhite(pictureObject, picturePath))
            printNow(imageFinished)
            break
        
        # red eye reduction option
        elif imageSelection == "4":
          printNow(imageSubText[3])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow(errorImage1)
            selection = "1"
            break
          elif not checkPicturePath(picturePath):
            printNow(errorImage2)
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(redEyeReduction(pictureObject, picturePath))
            printNow(imageFinished)
            break
          
        # artify option
        elif imageSelection == "5":
          printNow(imageSubText[4])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow(errorImage1)
            selection = "1"
            break
          else:
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(artify(pictureObject, picturePath))
            printNow(imageFinished)
            break
        
        # add watermark function
        elif imageSelection == "6":
          printNow(imageSubText[5])
          showInformation("In the following dialog box, please select your image of choice.")
          picturePath = pickAFile()
          if not os.path.exists(picturePath):
            printNow(errorImage1)
            selection = "1"
            break
          elif not checkPicturePath(picturePath):
            printNow(errorImage2)
            break
          else:
            text = requestString("Please enter the text you want to add as a watermark:")
            printNow("\nPlease wait...\n")
            pictureObject = makePicture(picturePath)
            explore(copyWrite(pictureObject, picturePath, text))
            printNow(imageFinished)
            break
          
        # go back to main menu
        elif imageSelection == "7":
          printNow("\nGoing back to main menu.")
          currentMenu = mainMenu
          selection = "0"
          break
          
        # all invalid entries fault to here. Prompt user again for selection
        else:
          imageSelection = raw_input("Invalid selection, try again: ")
    
    # prints out audioMenu list and enters sub loop  
    elif selection == "2":
      #printNow("Welcome to the audio manipulation menu!\n")
      currentMenu = audioMenu
      printMenu(currentMenu)
      audioSelection = raw_input("\nYour selection please: ")
      
      # works exactly like the image sub loop but with sound functions. 
      while true:
        # normalize option
        if audioSelection == "1":
          printNow(audioSubText[0])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow(errorSound1)
            selection = "2"
            break
          elif not checkSoundPath(soundPath):
            printNow(errorSound2)
            break
          else:
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(normalize(soundObject, soundPath))
            printNow(audioFinished)
            break
        
        # reverse option
        elif audioSelection == "2":
          printNow(audioSubText[1])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow(errorSound1)
            selection = "2"
            break
          elif not checkSoundPath(soundPath):
            printNow(errorSound2)
            break
          else:
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(reverse(soundObject, soundPath))
            printNow(audioFinished)
            break
        
        # increase volume option
        elif audioSelection == "3":
          printNow(audioSubText[2])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow(errorSound1)
            selection = "2"
            break
          elif not checkSoundPath(soundPath):
            printNow(errorSound2)
            break
          else:
            value = float(raw_input("Please enter the value(0-100) that you want to increase the volume by: "))
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(increaseVolume(soundObject, soundPath, value))
            printNow(audioFinished)
            break
        
        # decrease volume option
        elif audioSelection == "4":
          printNow(audioSubText[3])
          showInformation("In the following dialog box, please select your sound file of choice.")
          soundPath = pickAFile()
          if not os.path.exists(soundPath):
            printNow(errorSound1)
            selection = "2"
            break
          elif not checkSoundPath(soundPath):
            printNow(errorSound2)
            break
          else:
            value = float(raw_input("Please enter the value(0-100) that you want to decrease the volume by: "))
            printNow("\nPlease wait...\n")
            soundObject = makeSound(soundPath)
            explore(decreaseVolume(soundObject, soundPath, value))
            printNow(audioFinished)
            break
        
        # go back to main menu
        elif audioSelection == "5":
          printNow("\nGoing back to main menu.")
          currentMenu = mainMenu
          selection = "0"
          break
        
        # all erroneous entries fault to here. Prompt user to choose option again
        else:
          audioSelection = raw_input("Invalid selection, try again: ")
    
    # this option does not have a sub menu. Instead it will prompt the user for an image to be converted
    # into a song
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
      
    # quit option. Breaks the outer loop and ends the program.
    elif selection == "4":
      printNow("\nThank you, come again!")
      break
      
    # invalid entries fault to here. Prompts user again for entry
    else:
      selection = raw_input("Invalid selection, try again: ")
      
#
# helper function for mainMenu()
#    Accepts: list of strings
#    Prints out the menu list to console
#    Returns: nothing
#

def printMenu(menu):
  for line in menu:
    printNow(line)
    

def checkPicturePath(path):
  splitPath = os.path.split(path)
  filename = splitPath[1]

  if filename.find('.jpg') > 0:
    return true
  elif filename.find('.jpeg') > 0:
    return true
  elif filename.find('.png') > 0:
    return true
  else:
    return false
    
def checkSoundPath(path):
  splitPath = os.path.split(path)
  filename = splitPath[1]

  if filename.find('.wav') > 0:
    return true
  else:
    return false
    
###################################################################################
# Unique function created for this final project.
# Converts an image into a song 
###################################################################################


#
# convert_pic2audio
#    Accepts: a file path pointing to an image
#    Returns: a completed song
#

def convert_pic2audio(filePath):
  # create our picture object
  picIn = makePicture(filePath)

  # convert the image into an list of ints, print to user where we are
  # in the process
  soundValues = pixelToNotes(picIn)
  printNow("\nCompleted picture deconstruction...")
  
  # using the above list, construct a new sound file.
  numSamples = len(soundValues) * get_numSamples()
  sampleRate = get_clipSampleRate()
  printNow("Initialized song length...")
  outputSong = makeEmptySound( numSamples , int(sampleRate) )
  printNow("Creating song.\nPlease wait, this will take some time...")
  # return the completed song back to mainMenu()
  return assemble_ouputSong(soundValues, outputSong)
  
  
#
# get_correctSound(soundIndex)
#    Accepts: a list of ints ranging in value 0-6
#    Returns: sound object representing a pitch based on soundIndex
#

def get_correctSound(soundIndex):
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
#     Accepts: sound object list of ints
#     Returns: assembled song
#

def assemble_ouputSong(clipIndexList, outputSong):
  for i in range(0,len(clipIndexList)):
    currentClip = get_correctSound(clipIndexList[i])
    outputSong = copy(currentClip, outputSong, i*get_numSamples() )
  return outputSong 

#
# get_clipSampleRate()
#      returns sampling rate
#

def get_clipSampleRate():    
  return getSamplingRate( get_correctSound(0) )

#
# get_numSmpls()
#      Returns: number of samples in a sound clip
#      All sound clips are the same length, use
#      clip representing middle C
#

def get_numSamples():
  return getLength( get_correctSound(0) )
  
#
#  copy()
#    Accepts: Source is the source sound (the short clip)
#    Target is the target sound (empty sound object)
#    Start is the index of target where source is copied to
#    Returns: modified sound object
#

def copy(source , target, start = 0):  
  sourceLength = getLength(source) - 1
  for i in range(0, sourceLength):
    setSampleValueAt(target, start + i, getSampleValueAt(source, i))    
  return target

#
#  pixelToNotes(picture)
#
#    Accepts: a picture object
#    Returns: a list containing integers ranging from 0-6,
#    representing tones on the C major scale
#

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
  # nested loops
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
  
#  findShrinkFactor(picWidth, picHeight, maxArea, factor)
#
#    Accepts: requires four arguments, height and width of a picture,
#    an integer for the maximum picture area and an integer that represents how many
#    pixels are to be skipped for each loop iteration. 
#    If the current picture area after being divided by the initial factor 1,
#    is greater than the threshold, we'll increase the factor by 1, and have the function
#    call itself with the updated value. It'll continue until the picture is 'shrunk'
#    to size. 
#    Returns: one integer to be used in parent function

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
# The following functions are selected image functions from earlier in the class
# modified to work in this program.
# Most image filters take 2 arguments, 3 arguments if it requires an input value
# Picture object, and file path (batch folder path + file name)
# Each function returns a picture object to be used in mainMenu()
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


###################################################################################
# The following functions are selected audio functions from earlier in the class
# modified to work in this program.
# Most audio filters take 2 arguments, 3 arguments if it requires an input value
# Audio object, and file path (batch folder path + file name)
# Each function returns a sound object to be used in mainMenu()
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
