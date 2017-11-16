/** Base UI Interface for the NESynth Project
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

public interface SynthUI {
/** Returns the SynthListener associated with this UI object
  * @return The associated SynthListener
  */
    public AbstractSynth getSynth();
}