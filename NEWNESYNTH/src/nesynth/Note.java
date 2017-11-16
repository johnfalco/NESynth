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

import static nesynth.SynthConstants.*;

public class Note implements SynthType {  
/** Stores the integer representing the pitch of the Note */
    private int nInt;
/** Stores the velocity of the Note */
    private int nVel;
/** Stores the duration of the Note */
    private int nDur;
/** Stores the channel of the Note */
    private int nChan;
/** Stores the octave side of the Note */
    private int nOctS;

/** Constructs a new Note from an already existing note
  * @param n Note being constructed from
  */  
    public Note(Note n) {
        this.nInt = n.nInt;
        this.nVel = n.nVel;
        this.nDur = n.nDur;
        this.nChan = n.nChan;
        this.nOctS = n.nOctS;
    }

/** Constructs a Note object
  * @param note Integer representing the pitch of the note
  * @param velocity Integer representing the velocity of the note
  * @param duration Integer representing the duration of the note
  * @param channel Integer representing the channel of the note
  * @param octaveSide Integer representing specific implementation values
  * of the note for use by the Synth
  */
    public Note(int note, int velocity, int duration,
                             int channel, int octaveSide) {
        this.nInt = note;
        this.nVel = velocity;
        this.nDur = duration;
        this.nChan = channel;
        this.nOctS = octaveSide;
    }
    
/** Compares this to another object
  * @param e Object being compared
  * @return Comparable value
  */    
    public int compareTo(Object e) {
        if (e == null)
            return SortedList.NO_COMPARE;
        //else - not null, continue
        Note otherNote = (Note)e;
        if (this.nInt < otherNote.nInt)
            return -1;
        else if (this.nInt > otherNote.nInt)
            return 1;
        else
            return 0;
    }


/** Compares this Note to another Note
  * @param n The other Note being compared against
  * @return True if equal
  */   
    public boolean equals(Note n) {
        if (n == null) 
            return false; //if null immediate false return
        else
            return (this.nInt == n.nInt && this.nVel == n.nVel
                 && this.nDur == n.nDur && this.nChan == n.nChan
                                        && this.nOctS == n.nOctS);
    }
    
/** Gets the int representing the pitch from the Note
  * @return The int representing the pitch from the Note
  */
    public int getInt() {
        return this.nInt;
    }

/** Gets the velocity from the Note
  * @return The velocity from the Note
  */
    public int getVel() {
        return this.nVel;
    }
    
/** Gets the duration from the Note
  * @return The duration from the Note
  */
    public int getDur() {
        return this.nDur;
    }
    
/** Gets the channel from the Note
  * @return The channel from the Note
  */
    public int getChan() {
        return this.nChan;
    }
    
/** Gets the Octave Side from the Note
  * @return The Octave Side from the Note
  */
    public int getOctS() {
        return this.nOctS;
    }
    
/** Sets the int representing the pitch of the Note
  * @param inInt New int representing the pitch for the Note
  */
    public void setInt(int inInt) {
        this.nInt = inInt;
    }

/** Sets the velocity of the Note
  * @param inVel New velocity for the Note
  */
    public void setVel(int inVel) {
        this.nVel = inVel;
    }
    
/** Sets the duration of the Note
  * @param inDur New duration for the Note
  */
    public void setDur(int inDur) {
        this.nDur = inDur;
    }
    
/** Sets the channel of the Note
  * @param inChan New channel for the Note
  */
    public void setChan(int inChan) {
        this.nChan = inChan;
    }
    
/** Sets the Octave Side of the Note
  * @param inOctS New Octave Side for the Note
  */
    public void setOctS(int inOctS) {
        this.nOctS = inOctS;
    }

/** Returns all data from the SynthType as an Object array
  * @return Object Array holding all data
  */
    public Object[] getData() {
        return new Object[]{this.nInt, this.nVel, this.nDur,
                                       this.nChan, this.nOctS};
    }
    
/** Method to return this object as a string
  * @return String representation
  */
    @Override
    public String toString() {
        return "Note: " + nInt + " Vel: " + nVel + " Dur: "
             + convertDur() + " Chan: " + nChan + " OctS: " + nOctS;
    }
    
/** Converts the duration into a "formatted" string
  * @return The duration as a "formatted" string
  */
    private String convertDur() {
        return (this.nDur == Integer.MAX_VALUE) ? "MAX" 
                                                : "" + this.nDur;
    }
}