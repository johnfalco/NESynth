/** Object to hold all information pertaining to Sequences on a
  * Synth
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SortedList, SynthType, SynthConstants
  * @included      None
  */
  
package nesynth;

import java.io.*;
import static nesynth.SynthConstants.*;

public class Sequence implements SynthType {
/** Holds the sorted list that stores all data for the sequence */
    private SortedList sStream;
/** Holds the duration for the sequence */
    private int sDur;
/** Holds the channel for the sequence */
    private int sChan;

/** Constructs the Sequence
  * @param stream SortedList storing all data for the sequence
  * @param filename The name of the file that the SortedList was converted from
  * @param duration The duration of the sequence
  * @param channel The channel the sequence is being played on
  */
    public Sequence(SortedList stream, int duration, int channel) {
        this.sStream = stream;
        this.sDur = duration;
        this.sChan = channel;
    }

/** Constructs the Sequence from a file and channel value
  * @param filename Name of File being read
  * @param channel The channel the sequence is being played on
  * @throws BadMIDISequenceException If any exception is encountered 
  * during the conversion process
  */
    public Sequence(String filename, int channel) 
                     throws BadMIDISequenceException {
        this(AbstractSynth.convertMIDI(new File(filename), channel));
    }

/** Privately constructs a sequence from another sequence
  * @param otherSequence Other Sequence being converted
  */
    protected Sequence(Sequence otherSequence) {
        this.sStream = otherSequence.sStream;
        this.sDur = otherSequence.sDur;
        this.sChan = otherSequence.sChan;
    }

/** Compares this to another object
  * @param e Object being compared
  * @return Comparable value
  */
    public int compareTo(Object e) {
        if (e == null || !(e instanceof Sequence))
            return SortedList.NO_COMPARE;

        Sequence seq = (Sequence)e;
        if (this.sChan < seq.sChan)
            return -1;
        else if (this.sChan > seq.sChan)
            return 1;
        else
            return 0;
    }

/** Gets the next data object from the SortedList
  * @return The next Object
  */
    public Object getNext() {
        return this.sStream.getNext();
    }
    
/** Resets the SortedList
  */
    public void reset() {
        this.sStream.reset();
    }

/** Compares this Sequence to another Sequence
  * @param s other Sequence being compared against
  * @return True if equal
  */
    public boolean equals(Sequence s) {
        if (s == null) 
            return false; //if null immediate false return
        else 
            return (this.sStream.equals(s.sStream) 
                 && this.sDur == s.sDur && this.sChan == s.sChan);
    }
    
/** Gets the SortedList from the Sequence
  * @return The SortedList from the given Sequence
  */
    public SortedList getStream() {
        return this.sStream;
    }
/** Gets the duration from the Sequence
  * @return The duration from the given Sequence
  */
    public int getSeqDur() {
        return this.sDur;
    }
/** Gets the channel from the Sequence
  * @return The channel from the given Sequence
  */
    public int getSeqChan() {
        return this.sChan;
    }

/** Returns all data from the SynthType as an Object array
  * @return Object Array holding all data
  */
    public Object[] getData() {
        Object[] returnArray = {this.sStream, this.sDur, this.sChan};
        return returnArray;    
    }
    
/** Method to return this object as a string
  * @return String representation
  */
    @Override
    public String toString() {
        return convertChan() + " Dur: " + this.sDur;
    }

/** Converts the channel of this sequence into a "formatted" String
  * @return The channel as a string in formatted form
  */
    private String convertChan() {
        switch(this.sChan) {
            case SEQACHAN:  return "Sequence #1";
            case SEQBCHAN:  return "Sequence #2";
            case SEQCCHAN:  return "Sequence #3";
            case SEQDCHAN:  return "Sequence #4";
            default:        return "Bad Sequence";
        }
    }
}