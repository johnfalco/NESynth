/** Constant interface used to divide all the major constants used for any
  * version of the GUI Interface class.
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

public interface GUIConstants {
/** Constant used for setting the default
  * velocity within the LoadAction method */
    public static final int DEF_VEL = 105;
/** Constant used for setting the default
  * duration within the LoadAction method */
    public static final int DEF_DUR = Integer.MAX_VALUE;
/** Constant used for setting the default
  * channel within the LoadAction method */
    public static final int DEF_CHAN = 0;
/** Constant used for setting the default
  * SynthSystem action within the LoadAction method */
    public static final int DEF_ACT = SynthConstants.HALTALLSYNTH;
/** Constant used for setting the default
  * SynthSystem value within the LoadAction method */ 
    public static final int DEF_ACTVAL = 0;
/** Constant used for setting the default
  * SynthSystem channels within the LoadAction method */
    public static final int[] DEF_CHARRAY = SynthConstants.ALLCHAN;
/** Constant used for setting the default
  * Sequence number within the LoadAction method */
    public static final int DEF_SEQ = 0;
/** Constant used for setting the default
  * Sequence Channel within the LoadAction method */
    public static final int DEF_SEQCHAN = SynthConstants.SEQACHAN;
//     
// /** Stores all default values for the keys used when binding keys */
//     public static final int[] MAINKEY = {
//     //Left hand keyboard 0-23 (1-24)
//      24,25,26,27,28,29,30,31,32,33,34,35,
//      36,37,38,39,40,41,42,43,44,45,46,47,
//     //Percussion keys 24-31 (25-32)
//      36, 38, 44, 49, 37, 41, 48, 55,
//     
//     //Right hand keyboard 32-62 (33-63)
//      53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,
//      69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85};
}