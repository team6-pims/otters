#
# Lab 11:
# 
# CREATION NOTES:
#   FUN FACT: PYTHON CANT TELL THE DIFFERENCE BETWEEN 0/false
#             AND 1/true 

# / / / / / / / / / / / / / / 
#  Global Imports 
# / / / / / / / / / / / / / /
import random

# / / / / / /
# Main loop
# / / / / / /

def main():
  global win
  global lose
  global currentRoom


  # Player introduction
  playerName = newPlayer()
  
  # Output current room information
  printNow(roomData[currentRoom])
  
  
  # Prompt user for action
  playerInput = getUserInput()
  
  # Start main loop
  
  while playerInput != "quit":
    if currentRoom == 10:
      printNow("With each step crunching against the slush of snow, you have successfully ventured out with your life and freedom. You WIN!!!")
      return
    if lose == true:
      printNow("You have died. Sowwie")
      return
    else:
      displayRoom()
      playerInput = getUserInput()
  


def newPlayer():
  printNow("Heart racing and legs dragging, you narrowly avoid certain death from the Machine by falling into an abandoned mine.")
  printNow("You may have found refuge here, but you must keep moving\n")
  playerName = requestString("What is your name, lost one?")
  while playerName == "":
    playerName = requestString("What is your name, lost one?")
  printMenu()
  return playerName


# Get user input
def getUserInput():
  global currentRoom
  global hintData
  # Request user input
  req = requestString("What would you like to do?").lower()
  
  # Store old room to see if any room changes
  oldRoom = currentRoom
  
  if req == "north" or req == "n":
    printNow("You head North.")
    currentRoom = goNorth()
  elif req == "east" or req == "e":
    printNow("You head East.")
    currentRoom = goEast()
  elif req == "south" or req == "s":
    printNow("You head South.")
    currentRoom = goSouth()
  elif req == "west" or req == "w":
    printNow("You head West.")
    oldRoom = currentRoom
    currentRoom = goWest()
  elif req == "inspect" or req == "i":
    printNow(roomData[currentRoom])
    inspectRoom()
  elif req == "fight" or req == "f":
    printNow("You pull up your sleeves and prepare to fight.")
    # fightFunction
  elif req == "jump" or req == "j":
    printNow("You jump")
    # jump function
    currentRoom = jump(playerInventory[0])
  elif req == "hint" or req == "h":
    printNow(hintData[currentRoom])
  elif req == "grab" or req == "g":
   # printNow("Grabbing...")
    grabItem()
   
  elif req == "main" or req == "m":
    printMenu()
             
             
  if oldRoom <> currentRoom:
      printNow(roomData[currentRoom])
  
  return req

def printMenu():
  mainMenu = "m - List available commands" \
             "\nh - Show hints" \
             "\nn - Head North" \
             "\ne - Head East" \
             "\ns - Head South" \
             "\nw - Head West" \
             "\ni - Inspect Room" \
             "\ng - Grab item" \
             "\nf - FIGHT!" \
             "\nj - Jump" \
             "\nb - Show inventory"
  printNow(mainMenu)
# / / / / / / / / / / / / / / 
#  Display function 
# / / / / / / / / / / / / / / 


# displayRoom(currentRoom, roomData ,optionsAvailable )
#     takes in one argument, the current room(an integer), then displays the options to the user
#     based on their current position
def displayRoom():
  
  printNow(currentRoom)
  printNow("Here are your possible actions:")

  printNow(optionsAvailable[currentRoom])
  for j in optionsAvailable[currentRoom]:
    printNow(optionsList[j])
  printNow("What will you do now?\n")



# / / / / / / / / / / / / / / / / /
# Creation and decision functions
# / / / / / / / / / / / / / / / / /

#
# create room map
#    roomMap contains tuple in form of (direction available,  room# in that direction)
#    accessable via roomMap[currentRoom][0=direction, 1=roomNbr]
def createRoomMap():
  roomMap = list()
  roomMap.insert(0, 1 )
  roomMap.insert(1, (2 ,3 ,4) )
  roomMap.insert(2,  1 )
  roomMap.insert(3,  2 )
  roomMap.insert(4, (1 ,5) )
  roomMap.insert(5, (4 ,6) )
  roomMap.insert(6, (5 ,7, 8) )
  roomMap.insert(7,  6 )
  roomMap.insert(8, (6 ,9) )
  roomMap.insert(9, 10 )
  roomMap.insert(10, (None) )
  return roomMap

#
# roomData
#
def createRoomData():
  roomData = list()
  roomData.insert(0, "The earthquake collapsed the tunnel behind you, no where to go but forward" )
  roomData.insert(1, "You see a door to the North, a door to the East and a door to the South"  )
  roomData.insert(2, "You see a pair of running shoes on the floor, they look about your size"  )
  roomData.insert(3, "You see a sword on the floor, looks rusty"  )
  roomData.insert(4, "You see a crevace in front of you, looks really far..."  )
  roomData.insert(5, "Sweet cheesus that was close! you jumped and somehow made it. Hey look, a shield!"  )
  roomData.insert(6, "You see a floating eye staring menacingly... it charges you!"  )
  roomData.insert(7, "An empty room... WHERES THE LOOT! ... ugh i expected that thing to guard a treasure"  )
  roomData.insert(8, "All seems quiet... too quiet..."  )
  roomData.insert(9, "What is this, Indiana Jones??! You see the exit towards the West!"  )
  roomData.insert(10,"Congrats, you made it out. there's literally a light at the end of the tunnel"  )
  return roomData


#
# hintData(room_in)
#    if there are hints, they'll be shown in this list
def createHintData():
  hintData = list()
  hintData.insert(0, None )
  hintData.insert(1, None )
  hintData.insert(2, None )
  hintData.insert(3, None )
  hintData.insert(4, "HINT: I wish I wasn't wearing these heavy boots" )
  hintData.insert(5, None )
  hintData.insert(6, "HINT: Why fight with your bare hands?!" )
  hintData.insert(7, None )
  hintData.insert(8, "HINT: This cant be this good, right?" )
  hintData.insert(9, None )
  hintData.insert(9, "HINT: YOU WON" )      
  return hintData
#
# createUserChoices
#    return full list of what actions you can take
def createOptionsList():
  optionsList = list()
  optionsList.insert(0, "Quit")
  optionsList.insert(1, "Go North")
  optionsList.insert(2, "Go South")
  optionsList.insert(3, "Go East")
  optionsList.insert(4, "Go West")
  optionsList.insert(5, "Try and Jump")
  optionsList.insert(6, "FIGHT!")
  optionsList.insert(7, "Grab Item")
  optionsList.insert(8, "Inspect Room")
  return optionsList


#
# createOptionsAvailableList
#     optionsAvailable returns n-tuple of options from optionsList
#     that are currently available 

def createOptionsAvailableList():
  optionsAvailable = list()
  optionsAvailable.insert(0, (0,3) )
  optionsAvailable.insert(1, (0,1,2,3,4) )
  optionsAvailable.insert(2, (0,2,7) )
  optionsAvailable.insert(3, (0,4,7) )
  optionsAvailable.insert(4, (0,1,2,5) )
  optionsAvailable.insert(5, (0,1,3,7) )
  optionsAvailable.insert(6, (0,4,5) ) #when miniboss is beat this gets rewritten to (0,2,3,4)
  optionsAvailable.insert(7, (0,4) )
  optionsAvailable.insert(8, (0,1,2,8) )
  optionsAvailable.insert(9, (0,1,4) )
  optionsAvailable.insert(10,(0) )
  return optionsAvailable


#
# actionsList
#    list that holds function handles
#    MUST BE MAPPED 1:1 WITH OPTIONSLIST!
def createActionsList():   
  actionList = list()
  actionList.insert(0, quit_game  )
  actionList.insert(1, goNorth  )
  actionList.insert(2, goSouth  )
  actionList.insert(3, goEast  )
  actionList.insert(4, goWest  )
  actionList.insert(5, attemptJump  )
  actionList.insert(6, fight  )
  actionList.insert(7, grabItem  )
  actionList.insert(8, inspectRoom  )
  return actionList

#
# Initialize player inventory
#                  Shoes, Sword, blank, blank, blank, blank
def createInventory():
  playerInventory = [false] * 6
  return playerInventory
#  playerInventory = list()
#  playerInventory.insert(0, (0))
#  playerInventory.insert(1, (0))
#  playerInventory.insert(2, (0))
#  playerInventory.insert(3, (0))
 # playerInventory.insert(4, (0))
  #playerInventory.insert(5, (0))
  
#
# 
# / / / / / / / / / / / / / / / / / / / / /
# Mini-boss is defeated functions
# / / / / / / / / / / / / / / / / / / / / /
    
#
# whenMiniBossIsDefeated_OA
#   rewrites optionsAvailable when miniboss is defeated

def whenMiniBossIsDefeated_OA(optionsAvailable):
  optionsAvailable.insert(6, (0,2,3,4) )
  return optionsAvailable

#
# whenMiniBossIsDefeated_RD
#   rewrites roomData when miniboss is defeated

def whenMiniBossIsDefeated_RD(roomData):
  roomData.insert(6, "You see the grotesque remains of the Eye, laying lifeless")
  return roomData
  
 


# / / / / / / / / / / / / / / / / / / / / /
# actions functions
# / / / / / / / / / / / / / / / / / / / / /

#
# goNorth()
#    moves current room when user goes north
def goNorth():
  if currentRoom == 1:
    return 2
  elif currentRoom == 4:
    return 1
  elif currentRoom == 5:
    return 4
  elif currentRoom == 8:
    return 6
  elif currentRoom == 9:
    return 8
  else:
    printNow("The jagged cavern walls prevent you from moving in that direction")
    return currentRoom
    
#
# goSouth()
#    returns what room user is in when goes south
def goSouth():
  global lose
  global trapEnabled
  if currentRoom == 2:
    return 1
  elif currentRoom == 1:
    return 4
  elif currentRoom == 4:
    # don't need this here: attemptJump()
    printNow("Walking South won't work here...")
    return currentRoom
  elif currentRoom == 6:
    return 8
  elif currentRoom == 8 and trapEnabled == false:
    return 9
  elif currentRoom == 8 and trapEnabled == true:  
    printNow("You hear a sudden click of a trap activating and whooshing of arrows surging across the distance, skewering your body. \nMaybe you should've inspected your surroundings...")
    lose = true
    return 8
  else:
    printNow("The jagged cavern walls prevent you from moving in that direction")
    return currentRoom
    
  
#
# goEast()
#    returns what room user is in when goes south
def goEast():  
  if currentRoom == 0:
    return 1
  elif currentRoom == 1:
    return 3
  elif currentRoom == 5:
    return 6
  elif currentRoom == 6:
    return 7
  else:
    printNow("The jagged cavern walls prevent you from moving in that direction")
    return currentRoom
  

#
# goWest()
#
def goWest():
  global win
  
  if currentRoom == 3:
    return 1
  elif currentRoom == 6:
    return 5
  elif currentRoom == 7:
    return 6
  elif currentRoom == 9:
    win == true
    return 10
  else:
    printNow("The jagged cavern walls prevent you from moving in that direction")
    return currentRoom

#
# quit()
#     suddenly ends game
# NOTE: quit is a reserved python word
def quit_game():
  return -1

#
# jump(shoes_bool)
# attempts jump, if user has shoes odds increase
# returns true if success, else false
def jump(shoes_bool):
  #gets the chance of success from 1 to 100
  chance = random.randint(1,101)
  #with shoes - success rate 50%
  if shoes_bool == true and currentRoom == 4:
    if chance <= 50:
      return 5
    else:
      lose = false
  #no shoes - success rate 15%
  elif shoes_bool == false and currentRoom == 4:
    if chance <= 15:
      return 5
    else:
      lose = false
  # anywhere else, shame the user!
  else:
    printNow("You jump around the room. This isn't the time nor place to jump around willy-nilly!")
  
#
# fight(sword_bool)
# fights miniboss odds increase with sword 
def fight(sword_bool, shield_bool):
  #gets the chance of success from 1 to 100
  chance = random.randint(1,101)
  #with sword - success rate 95%
  if sword_bool and shield_bool == true:
    if chance <= 95:
      return true
    else:
      return false
  #no sword - success rate 70%
  if sword_bool == false:
    if chance <= 70:
      return true
    else:
      return false
  
  
#
# grabItem
#    returns your new inventory tuple

def grabItem():
 # global playerInventory
  if currentRoom == 2:
    printNow("These new shoes fit your feet perfectly.")
    playerInventory.insert(0, true)
    optionsAvailable.insert(2, (0,2) )
  elif currentRoom == 3:
    printNow("This sword is light enough to use with one hand!")
    playerInventory.insert(1, true)
    optionsAvailable.insert(3, (0,4) )
  elif currentRoom == 5:
    printNow("What's my other hand supposed to? Just hang there?")
    playerInventory.insert(2, true)
    optionsAvailable.insert(5, (0,3) )

#
# inspectRoom
#    returns false. set the trap boolean to false
def inspectRoom():
  global currentRoom
  global trapEnabled
  
  if currentRoom == 8:
    printNow("You inspect the seemingly quiet room and toss a rock into the distance infront of you.\n The trap triggers as you witness arrows snapping their behind them.")
    trapEnabled = False

# / / / / / / / / /
# Global variables
# / / / / / / / / /

currentRoom                                 = 0
roomMap                       = createRoomMap()
roomData                     = createRoomData()
hintData                     = createHintData()
optionsList               = createOptionsList()
optionsAvailable = createOptionsAvailableList()
playerInventory             = createInventory()
playerName                                 = ""
trapEnabled                              = True
win                                     = False
lose                                    = False
main()






