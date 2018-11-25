#
# Lab 11:
# 
# CREATION NOTES:
#   FUN FACT: PYTHON CANT TELL THE DIFFERENCE BETWEEN 0/false
#             AND 1/true 

#
# main loop function
#
#def main():

    #NOTE: my recommendation is you use a tuple to keep track of items
    #there are only 2 so inv = (false, false). 
    #inv[0] = shoes boolen
    #inv[1] = sword boolen
    # thats how grabItem is set up, (tuples are immutable heads up)
    #for the trap springing, i say keep a bool outside of the loop 





# / / / / / / / / / / / / / / 
#  Display function 
# / / / / / / / / / / / / / / 


# displayRoom(currentRoom, roomData ,optionsAvailable )
#     takes in one argument, the current room(an integer), then displays the options to the user
#     based on their current position
def displayRoom(currentRoom, roomData ,optionsAvailable ):
  options = optionsAvailable(currentRoom)
  printNow(roomData[currentRoom])
  printNow()
  printNow("Here are your current options.")
  printNow()
  for i in len(optionsAvailable(currentRoom)):
    printNow(str(i) + ") " + options[i])
  printNow("What will you do now?")



# / / / / / / / / / / / / / / / / /
# Creation and decision functions
# / / / / / / / / / / / / / / / / /

#
# create room map
#    roomMap contains tuple in form of (direction available,  room# in that direction)
#    accessable via roomMap[room_in][0=direction, 1=roomNbr]
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
  roomData.insert(0, "the earthquake collapsed the tunel behind you, no where to go but forward" )
  roomData.insert(1, "You see a door to the North, a door to the East and a door to the South"  )
  roomData.insert(2, "You see a pair of running shoes on the floor, they look about your size"  )
  roomData.insert(3, "You see a sword on the floor, looks rusty"  )
  roomData.insert(4, "You see a crevace in front of you, looks really far..."  )
  roomData.insert(5, "Sweet cheesus that was close! you jumped and somehow made it"  )
  roomData.insert(6, "You see a floating eye staring menicingly... it charges you!"  )
  roomData.insert(7, "An empty room... WHERES THE LOOT! ... ugh i expected that thing to guard a treasure"  )
  roomData.insert(8, "All seems quiet... too quiet..."  )
  roomData.insert(9, "What is this, Indiana Jones??! well you made it"  )
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
  hintData.insert(4, "I wish i wasnt wearing these heavy boots" )
  hintData.insert(5, None )
  hintData.insert(6, "HINT: why fight with your bare hands?!" )
  hintData.insert(7, None )
  hintData.insert(8, "HINT: This cant be this good, right?" )
  hintData.insert(9, None )
  hintData.insert(9, "HINT: YOU WON" )      
  return hintData
#
# createUserChoices
#    return full list of what actions you can take
def createOtionsList():
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
  optionsAvailable.insert(2, (0,2, 7) )
  optionsAvailable.insert(3, (0,4, 7) )
  optionsAvailable.insert(4, (0,1,2,5) )
  optionsAvailable.insert(5, (0,1,3) )
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


# / / / / / / / / / / / / / / / / / / / / /
# Mini-boss is defeated functions
# / / / / / / / / / / / / / / / / / / / / /
    
#
# whenMiniBossIsDefeated_OA
#   rewrites optionsAvailable when miniboss is defeated

def whenMiniBossIsDefeated_OA(optionsAvailable):
  optionsAvailableList.insert(6, (0,2,3,4) )
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
def goNorth(room_in):
  if room_in == 1:
    return 2
  elif room_in == 4:
    return 1
  elif room_in == 5:
    return 4
  elif room_in == 8:
    return 6
  elif room_in == 9:
    return 8
    
#
# goSouth()
#    returns what room user is in when goes south
def goSouth(room_in):
  if room_in == 2:
    return 1
  elif room_in == 4:
    return 5
  elif room_in == 6:
    return 8
  elif room_in == 8:  
    return 9
  
  
#
# goEast()
#    returns what room user is in when goes south
def goEast(room_in):  
  if room_in == 0:
    return 1
  elif room_in == 1:
    return 3
  elif room_in == 5:
    return 6
  elif room_in == 6:
    return 7
  

#
# goWest()
#
def goWest(room_in):
  if room_in == 3:
    return 1
  elif room_in == 6:
    return 5
  elif room_in == 9:
    return 10

#
# quit()
#     suddenly ends game
# NOTE: quit is a reserved python word
def quit_game():
  return -1

#
# attemptJump(shoes_bool)
#    attempts jump, if user has shoes odds increase
def attemptJump(shoes_bool):
  if shoes_bool:
    return true #this is just a placeholder
    #odds are 50/50 with shoes
    #return true or false
  #without shoes odds are 15 success/85 failure
    #return true/false
  
#
# fight(sword_bool)
#    fights miniboss odds increase with sword 
def fight(sword_bool):
  if sword_bool:
    return true #this is just a placeholder
    #odds 95 success /5 failure 
    #return true/false
  #without odds 70/30
  #return true/false
  
  
#
# grabItem
#    returns your new inventory tuple

def grabItem(room_in, inventory_tuple):
  if room_in == 2:
    inventory_tuple = ( true , inventory_tuple[1] )
  elif room_in == 3:
    inventory_tuple = ( inventory_tuple[0], true )
  return inventory_tuple


#
# inspectRoom
#    returns false. set the trap boolean to false
def inspectRoom():
  return false







