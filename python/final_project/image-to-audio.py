# Image to audio algorithm placeholder

#     pixelToNotes(picture)
#
# takes in one argument, a variable that has been assigned the contents of makePicture()
# processes the pixel data to create an array containing integers ranging from 0-6,
# representing musical tones ranging from middle C to B in a Cmajor scale.
# This function calls shrinkPictureToSize() to shrink the image if need be. This is 
# done to ensure the resulting "song" is not too long.

def pixelToNotes(picture)
  # declare list to contain all note integers
  allNotes = list()
  # threshold for picture size to be passed as an argument
  maxArea = 5000
  
  # resize the picture if need be.
  factor = findShrinkFactor(picture, maxArea, 1)
  
  # collect all the pixels in a list
  allPixels = getPixels(pictureToSize)
  
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
  # obtain dimensions
  picHeight = getHeight(picture) / factor
  picWidth = getWidth(picture) / factor
  
  # obtain current area
  picArea = picHeight * picWidth
  
  # compare to threshold and either shrink, or return current image
  if picArea > maxArea:
    factor += 1
    findShrinkFactor(workingPicture, maxArea, factor)

  return factor
