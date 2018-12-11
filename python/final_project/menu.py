
def mainMenu():
  #provide user display information
  openingStr = "Welcome to Manipulating Your Image.\nPlease enter your choice of option:\n"
  openingStr1 = "1: Applying your choice of filter to an image\n"
  openingStr2 = "2: Applying your choice of filter to images in a directory\n"
  openingStr3 = "3: Converting an image to sound\n"
  exitStr = "Type in exit to quit the program"
  stringTotal = openingStr + openingStr1 + openingStr2+ openingStr3 + exitStr
  
  errorStr = "You have not selected an option, try again"
  
  #get choice
  choice = requestString(stringTotal)
  print choice
  
  #check choice with menus
  #currently fillers until other code has been added
  if choice == 1:
    return choice
  elif choice == 2:
    return choice
  elif choice == 3:
    return choice
  else:
    print errorStr

######################
# Image Driver
######################
def image_driver():
  # Declare file variables
    batchDir = getBatchDir()
    filePathNames = getFilePaths(batchDir)
    
    # Loop through every file in directory
    for i in range(0, len(filePathNames)):
        # Change to whatever filter
        #roseColoredGlasses(makePicture(filePathNames[i]), filePathNames[i])
        copyWrite(makePicture(filePathNames[i]), filePathNames[i], 'Team PIMS')
        #negative(makePicture(filePathNames[i]), filePathNames[i])
        #blackAndWhite(makePicture(filePathNames[i]), filePathNames[i])
        #redEyeReduction(makePicture(filePathNames[i]), filePathNames[i])
        #artify(makePicture(filePathNames[i]), filePathNames[i])

######################
# Audio Driver
######################
def audio_driver():
  # Declare file variables
    batchDir = getBatchDir()
    filePathNames = getFilePaths(batchDir)
    
    # Loop through every file in directory
    for i in range(0, len(filePathNames)):
        # Apply to whatever filter
        #increaseVolume(makeSound(filePathNames[i]), filePathNames[i], 1000)
        #decreaseVolume(makeSound(filePathNames[i]), filePathNames[i], 90)
        #normalize(makeSound(filePathNames[i]), filePathNames[i])
        reverse(makeSound(filePathNames[i]), filePathNames[i])

############################
# Driver helper functions   
############################
def getBatchDir():
    # Get batch directory
    return requestString("Please enter the directory that contains the files you wish to manipulate")

def getFileList(batchDir):
    # File list
    fileList = []
    
    # Determine which files in the directory are actually files and not folders
    for f in os.listdir(os.path.normcase(batchDir)):
        filePath = os.path.join(batchDir, f)
        if os.path.isfile(filePath):
            fileList.append(f)
    
    # Output
    return fileList

def getSound():
  return makeSound(pickAFile())

def getFilePaths(batchDir):
    # File path list
    filePathList = []
    
    # Determine which files in the directory are actually files and not folders
    for f in os.listdir(os.path.normcase(batchDir)):
        filePath = os.path.join(batchDir, f)
        if os.path.isfile(filePath):
            filePathList.append(filePath)
    
    # Output
    return filePathList