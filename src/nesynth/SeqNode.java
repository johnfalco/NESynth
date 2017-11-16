/** Interface that determines if objects belong inside the Sequence object
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       None
  * @included      None
  */

package nesynth;

public interface SeqNode extends SynthType {
/** Gets the tick associated with the SeqNode
  * @return The associated tick
  */
    public int getTick();
}
