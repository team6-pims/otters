#
# convert_pic2audio
#    enterance fcn to picture -> audio 
#    conversion. takes in a filePath
#
# NOTE: get_correctSound fcn should be updated
#       so basePath points to correct audio path
#
def convert_pic2audio(filePath):
  import os
  if not os.path.exists(filePath):
    return -1
  
  try:
    picIn = makePicture(filePath)
  except:
    return -1
  
  soundValues = pixelToNotes(picIn)
   
  #all sound clips of same length
  numSamples = len(soundValues) #* get_numSamples()
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
  basePath = "D:\\Users\\Ivan\\Documents\\school\\CST 205\\CST-205\\clippedAudio\\"
  a = basePath + "clipped_A.wav"
  b = basePath + "clipped_B.wav"
  c = basePath + "clipped_C.wav"
  d = basePath + "clipped_D.wav" 
  e = basePath + "clipped_E.wav"
  f = basePath + "clipped_F.wav"
  g = basePath + "clipped_G.wav" 
  if soundIndex == 0:
    temp = a
  if soundIndex == 1:
    temp = b
  if soundIndex == 2:
    temp =  c
  if soundIndex == 3:
    temp =  d
  if soundIndex == 4:
    temp =  e
  if soundIndex == 5:
    temp =  f
  if soundIndex == 6:
    temp =  g
  return makeSound(temp)

#
# assemble_ouputSong()
#     take in clip index list and output-empty-song obj
#     returns assembled song
def assemble_ouputSong(clipIndexList, outputSong):
  for i in clipIndexList:
    currentClip = get_correctSound(i)
    outputSong = copy(currentClip, outputSong)
  return outputSong 

#
# get_clipSampleRate()
#      returns sampling rate
def get_clipSampleRate():    
  return getSamplingRate( (get_correctSound(0)))

#
# get_numSmpls()
#      get nbr of samples in a music clip
def get_numSamples():
  return getLength( (get_correctSound(0) ) )
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
  maxArea = 3000
  
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

def getPic():
  return makePicture(pickAFile())
