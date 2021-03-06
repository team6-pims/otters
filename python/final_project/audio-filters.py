# Audio filters

import sys
import os

###################################################################################
# Most audio filters take 2 arguments, 3 arguments if it requires an input value
# Audio object, and file path (batch folder path + file name)
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

def increaseVolume(sndObj, filePath, value):
    # Loop through applying value factor
    for sample in getSamples(sndObj):
        setSampleValue(sample, (1.0 + (value/100.0)) * getSampleValue(sample))
    
    # Output
    writeSoundTo(sndObj, filePath.replace('.wav', '_increaseVolume.wav'))

def decreaseVolume(sndObj, filePath, value):
    # Loop through applying value factor
    for sample in getSamples(sndObj):
        setSampleValue(sample, (1.0 - (value/100.0)) * getSampleValue(sample))
    
    # Output
    writeSoundTo(sndObj, filePath.replace('.wav', '_decreaseVolume.wav'))