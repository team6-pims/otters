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
  
  sndIdxArray = pixelToNotes(picIn)
   
  #all sound clips of same length
  outputSong = makeEmptySound( len(sndIdxArray) * get_numSmpls() , get_clipSampleRate() )  
  return assemble_ouputSong(sndIdxArray, outputSong)
  
  
#
# get_correctSound(sndIdx)
#    returns sound clip corresponding to sndIdx
#    should be updated to reflect your local machine
#    path to sounds
def get_correctSound(sndIdx):
  basePath = r"/home/captain/CSUMB/CST205/Final/Rawmusicnotes/"
  a = basePath + "clipped_A.wav"
  b = basePath + "clipped_B.wav"
  c = basePath + "clipped_C.wav"
  d = basePath + "clipped_D.wav" 
  e = basePath + "clipped_E.wav"
  f = basePath + "clipped_F.wav"
  g = basePath + "clipped_G.wav" 
  if sndIdx == 0:
    tmp = a
  if sndIdx == 1:
    tmp = b
  if sndIdx == 2:
    tmp =  c
  if sndIdx == 3:
    tmp =  d
  if sndIdx == 4:
    tmp =  e
  if sndIdx == 5:
    tmp =  f
  if sndIdx == 6:
    tmp =  g
  return makeSound(tmp)

#
# assemble_ouputSong()
#     take in clip index list and output-empty-song obj
#     returns assembled song
def assemble_ouputSong(clpIdxLst, outputSong ):
  for i in clpIdxLst:
    currClp = get_correctSound(i)
    outputSong = copy(currClp, outputSong)
  return outputSong 

#
# get_clipSampleRate()
#      returns sampling rate
def get_clipSampleRate():    
  return getSamplingRate( makeSound(get_correctSound(0)))

#
# get_numSmpls()
#      get nbr of samples in a music clip
def get_numSmpls():
  return getLength( makeSound(get_correctSound(0) ) )
#
#  copy()
#    Source is the source sound (the short clip)
#    Target is the target sound
def copy(source , target, start = 0):  
  sourLen = getLength(source)
  for i in range(0 , sourLen ):
    setSampleValueAt(target, start + i, getSampleValueAt(source, i))    
  return target



#     pixelToNotes(picture)
#
# takes in one argument, a variable that has been assigned the contents of makePicture()
# processes the pixel data to create an array containing integers ranging from 0-6,
# representing musical tones ranging from middle C to B in a Cmajor scale.
# This function calls findShrinkFactor() to 'shrink' the image if need be. This is 
# done to ensure the resulting "song" is not too long.

def pixelToNotes(picture)
  # declare list to contain all note integers
  allNotes = list()
  # threshold for picture size to be passed as an argument
  maxArea = 5000
  
  # find the factor which is how big a step we're going to iterate through in the following
  # nested loops
  factor = findShrinkFactor(picture, maxArea, 1)
  
  # distance() works by calculating the distance between two colors as if they were
  # mapped on a 3 dimensional cartesian graph. We'll use the keyword 'black' to 
  # represent (0,0,0). The resulting number that distance returns is saved in rawNote
  # Then we take that variable and use modulus to get a number 0-6 and append that
  # number to a list.
  for x in range(0, getWidth(picture), factor):
    for y in range(0, getHeight(picture), factor):
      pixel = getPixel(picture, x, y) 
      rawNote = distance(getColor(pixel), black)
      note = rawNote%7
      allNotes.append(note)
  
  # return the list of numbers
  return allNotes
  
#     shrinkPictureTosize(picture, maxArea, factor)
#
# This function requires three arguments, a variable containing raw picture data
# an integer for the maximum picture area and an integer that represents how many
# pixels are to be skipped for each loop iteration. 
# If the current picture area after being divided by the initial factor 1,
# is greater than the threshold, we'll increase the factor by 1, and have the function
# call itself with the updated value. It'll continue until the picture is 'shrunk'
# to size. 
# Returns: one integer to be used in parent function

def findShrinkFactor(picture, maxArea, factor):
  # obtain dimensions, then consider odd lengthed pictures
  picHeight = getHeight(picture) / factor
  picWidth = getWidth(picture) / factor
  
  if picWidth%2 == 1:
    picWidth -= 1
  if picHeight%2 == 1:
    picHeight -= 1
  
  # obtain current area
  picArea = picHeight * picWidth
  
  # compare to threshold and either 'shrink' if the conditional evaluates true
  # if not, pass back current value for factor
  if picArea > maxArea:
    factor += 1
    findShrinkFactor(workingPicture, maxArea, factor)

  return factor

