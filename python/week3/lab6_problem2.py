def getPic():
  return makePicture(pickAFile())

# Problem 2 - Posterization
def artify():
  pic = getPic()
  
  for x in range(0, getWidth(pic)):
    for y in range(0, getHeight(pic)):
      # Get pixel details
      p = getPixel(pic, x, y)
      
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
  show(pic)

# Number filterr
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
    