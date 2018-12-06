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
  pictureToSize = shrinkPictureToSize(picture, maxArea)
  
  # collect all the pixels in a list
  allPixels = getPixels(pictureToSize)
  
  # distance() works by calculating the distance between two colors as if they were
  # mapped on a 3 dimensional cartesian graph. We'll use the keyword 'black' to 
  # represent (0,0,0). The resulting number that distance returns is saved in rawNote
  # Then we take that variable and use modulus to get a number 0-6 and append that
  # number to a list.
  for pixel in allPixels:
    rawNote = distance(getColor(pixel), black)
    note = rawNote%7
    allNotes.append(note)
  
  # return the list of numbers
  return allNotes
  
#     shrinkPictureTosize(picture, maxArea)
#
# This function requires two arguments, a variable containing raw picture data
# and an integer for the maximum picture area. 
# If the current picture area is greater than the threshold, we'll call a previously
# written function shrink() to reduce the size of the picture by half. Then in the
# same conditional, have shrinkPictureToSize() call itself to check if the shrunk 
# picture is within tolerances. If not, it'll call itself continuously until the
# picture is small enough. Then it'll return the final shrunk picture all the way
# back up to the parent function.

def shrinkPictureToSize(picture, maxArea):
  # obtain dimensions
  picHeight = getHeight(picture)
  picWidth = getWidth(picture)
  
  # obtain current area
  picArea = picHeight * picWidth
  
  # compare to threshold and either shrink, or return current image
  if picArea > maxArea:
    workingPicture = shrink(picture)
    shrinkPictureToSize(workingPicture, maxArea)

  return picture
