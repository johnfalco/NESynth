package nesynth;

public class ScaledSequence extends Sequence {
/** Constructs the ScaledSequence
  * @param stream SortedList storing all data for the sequence
  * @param filename The name of the file that the SortedList was converted from
  * @param duration The duration of the sequence
  * @param channel The channel the sequence is being played on
  */
    public ScaledSequence(SortedList stream, int duration, int channel) {
        super(stream, duration, channel);
    }

/** Constructs the ScaledSequence from a file and channel value
  * @param filename Name of File being read
  * @param channel The channel the sequence is being played on
  * @throws BadMIDISequenceException If any exception is encountered 
  * during the conversion process
  */
    public ScaledSequence(String filename, int channel) 
                     throws BadMIDISequenceException {
        super(filename, channel);
    }
}