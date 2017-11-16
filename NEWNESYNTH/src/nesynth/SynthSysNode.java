/** Node that acts as a SynthSys for storage in a sequence
  *
  * @Version       1.2
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SeqNode, SynthSys
  * @included      None
  */

package nesynth;

public class SynthSysNode extends SynthSys implements SeqNode {
/** Stores the tick used for sorting SeqNodes */
    private int ssnTick;

/** Creates the SynthSys node
  * @param sysID The action ID
  * @param sysVal The action value
  * @param sysChannels The channels to be impacted
  * @param tick The tick identifying the position of the SeqNode
  */  
    public SynthSysNode(int sysID, int[] sysVals,
                          int[] sysChannels, int tick) {
        super(sysID, sysVals, sysChannels);
        this.ssnTick = tick;
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
        return this.ssnTick;
    }

/** Method to return this object as a string
  * @return String representation
  */
    @Override
    public String toString() {
        return "@" + this.ssnTick + " " + super.toString();
    }
}
