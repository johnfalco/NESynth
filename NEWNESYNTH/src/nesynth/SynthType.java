/** Interface to identify all objects that store any data 
  * pertaining to the Synth class
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

import java.io.*;

public interface SynthType extends Comparable, Serializable {

/** Constant to represent type "SynthSys" */
    public static final char SYNTHSYS = 'S';
/** Constant to represent type "Note" */
    public static final char NOTE = 'N';
/** Constant to represent type "Sequence" */
    public static final char SEQUENCE = 'Q';

/** Returns all data from the SynthType as an Object array
  * @return Object Array holding all data
  */
    public Object[] getData();
}