
def mainMenu():
  #provide user display information
  openingStr = "Welcome to Manipulating Your Image.\nPlease enter your choice of option:\n"
  openingStr1 = "1: Applying your choice of filter to an image\n"
  openingStr2 = "2: Applying your choice of filter to images in a directory\n"
  openingStr3 "3: Converting an image to sound\n"
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
    
  
