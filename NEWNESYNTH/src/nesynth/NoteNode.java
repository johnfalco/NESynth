/** Node that acts as a Note for storage in a sequence
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SeqNode, SynthSys
  * @included      None
  */

package nesynth;

import static nesynth.SynthConstants.*;

public class NoteNode extends Note implements SeqNode {
/** Stores the tick used for sorting SeqNodes */
    private int nnTick;

/** Creates the NoteNode
  * @param note Integer representing the pitch of the note
  * @param velocity Integer representing the velocity of the note
  * @param duration Integer representing the duration of the note
  * @param channel Integer representing the channel of the note
  * @param tick The tick identifying the position of the SeqNode
  */    
    public NoteNode(int note, int velocity, int duration,
                                        int channel, int tick) {
        super(note, velocity, duration, channel, NULLOCTAVE);
        this.nnTick = tick;
    }

/** Compares this to another object
  * @param e Object being compared
  * @return Comparable value
  */       
    @Override
    public int compareTo(Object e) {
        if (e == null)
            return SortedList.NO_COMPARE;
        SeqNode node = (SeqNode)e;
        if (this.getTick() < node.getTick())
            return -1;
        else if (this.getTick() > node.getTick())
                return 1;
        else
            return 0;
    }

/** Gets the tick associated with the SeqNode
  * @return The associated tick
  */     
    public int getTick() {
        return this.nnTick;
    }

/** Method to return this object as a string
  * @return String representation
  */
    @Override
    public String toString() {
        return "@" + this.nnTick + " " + super.toString();
    }
}