package nesynth;

/** Node representing the last node of a Sequence object
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SeqNode
  * @included      None
  */

public class FinalNode implements SeqNode {
/** Stores the tick used for sorting SeqNodes */
    private int fnTick;

/** Constructs the FinalNode
  * @param tick Tick identifying the end of a Sequence
  */
    public FinalNode(int tick) {
        this.fnTick = tick;
    }

/** Compares this to another object
  * @param e Object being compared
  * @return Comparable value
  */         
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
        return this.fnTick;
    }

    public Object[] getData() {
        return new Object[]{this.fnTick};
    }
  
/** Method to return this object as a string
  * @return String representation
  */
    @Override
    public String toString() {
        return "@" + this.fnTick + " END SEQUENCE";
    }
}