/** Constant interface used to divide all the major constants used for the
  * Synth class as well as other classes interacting with it.
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

public abstract class SynthConstants {
// Synth System Action IDs {
/** System key to restart synthesizer */
    public static final int HALTALLSYNTH = -255;
/** System key to perform increase octave on notes 
  * in the right octave on all channels */
    public static final int GLOBALRIGHTUPOCTAVE = -122;
/** System key to perform decrease octave on notes 
  * in the right octave on all channels */
    public static final int GLOBALRIGHTDOWNOCTAVE = -121;
/** System key to perform increase octave on notes
  * in the left octave on all channels */
    public static final int GLOBALLEFTUPOCTAVE = -120;
/** System key to perform decrease octave on notes
  * in the left octave on all channels */
    public static final int GLOBALLEFTDOWNOCTAVE = -119;
/** System key to perform local control on all channels */
    public static final int GLOBALLOCALCONTROL = -118;
/** System key to perform halt on all channels */
    public static final int GLOBALHALTCHANNEL = -117;
/** System key to perform a reset on all channels */
    public static final int GLOBALRESETCHANNEL = -116;
/** System key to perform a program change on all channels */
    public static final int GLOBALCHANGEPROGRAM = -115;
/** System key to perform mono mode toggling on all channels */
    public static final int GLOBALTOGGLEMONO = -114;
/** System key to perform solo mode toggling on all channels */
    public static final int GLOBALTOGGLESOLO = -113;
/** System key to perform mute toggling on all channels */
    public static final int GLOBALTOGGLEMUTE = -112;
/** System key to perform downward pitch bending on all channels */
    public static final int GLOBALDOWNPITCHBEND = -111;
/** System key to perform upward pitch bending on all channels */
    public static final int GLOBALUPPITCHBEND = -110;
/** System key to perform a pitch reset on all channels */
    public static final int GLOBALSETPITCHBEND = -109;
/** System key to perform lowering volume on all channels */
    public static final int GLOBALDECRVOL = -108;
/** System key to perform raising volume on all channels */
    public static final int GLOBALCRESVOL = -107;
/** System key to perform a volume set on all channels */
    public static final int GLOBALSETVOL = -106;
/** System key to perform downward mod bending on all channels */
    public static final int GLOBALDOWNMODPITCH = -105;
/** System key to perform upward mod bending on all channels */
    public static final int GLOBALUPMODPITCH = -104;
/** System key to perform a mod set on all channels */
    public static final int GLOBALSETMODPITCH = -103;
/** System key to perform toggle sustained pitch on all channels */
    public static final int GLOBALTOGGLESUSTAIN = -102;
/** System key to perform setting sustain pitch on all channels */
    public static final int GLOBALSETSUSTAIN = -101;
/** System key to perform + Octave on notes 
  * in the right octave on given channels */
    public static final int RIGHTUPOCTAVE = -22;
/** System key to perform - Octave on notes 
  * in the right octave on given channels */
    public static final int RIGHTDOWNOCTAVE = -21;
/** System key to perform + Octave onnotes 
  * in the left octave on given channels */
    public static final int LEFTUPOCTAVE = -20;
/** System key to perform - Octave on notes 
  * in the left octave on given channels */
    public static final int LEFTDOWNOCTAVE = -19;
/** System key to perform local control on given channels */
    public static final int LOCALCONTROL = -18;
/** System key to perform halt on given channels */
    public static final int HALTCHANNEL = -17;
/** System key to perform a reset on given channels */
    public static final int RESETCHANNEL = -16;
/** System key to perform a program change on given channels */
    public static final int CHANGEPROGRAM = -15;
/** System key to perform mono mode toggling given channels */
    public static final int TOGGLEMONO = -14;
/** System key to perform solo mode toggling on given channels */
    public static final int TOGGLESOLO = -13;
/** System key to perform mute toggling on given channels */
    public static final int TOGGLEMUTE = -12;
/** System key to perform downward pitch bending on given channels */
    public static final int DOWNPITCHBEND = -11;
/** System key to perform upward pitch bending on given channels */
    public static final int UPPITCHBEND = -10;
/** System key to perform a pitch reset on given channels */
    public static final int SETPITCHBEND = -9;
/** System key to perform lowering volume on given channels */
    public static final int DECRVOL = -8;
/** System key to perform raising volume on given channels */
    public static final int CRESVOL = -7;
/** System key to perform a volume set on given channels */
    public static final int SETVOL = -6;
/** System key to perform downward mod bending on given channels */
    public static final int DOWNMODPITCH = -5;
/** System key to perform upward mod bending on given channels */
    public static final int UPMODPITCH = -4;
/** System key to perform a mod set on given channels */
    public static final int SETMODPITCH = -3;
/** System key to perform sustained pitch on given channels */
    public static final int TOGGLESUSTAIN = -2;
/** System key to perform sustained pitch on given channels */
    public static final int SETSUSTAIN = -1;
    //}

    // Modifier Constants {
/** Constant to identify max pitch bend level */
    public static final int MAXPITCHBEND = 16256;
/** Constant to identify max volume/modulation level */
    public static final int MAXVOLMP = 127;
/** Default increment to which volume and mod pitch are modified */
    public static final int DELTAVOLMP = 8;
/** Default increment to which pitch is modified */
    public static final int DELTAPITCH = 128;
    //}

    // Octave Constants {
/** Constant to identify which octave control a note should listen to */
    public static final int LEFTOCTAVE = -1;
/** Constant to identify which octave control a note should listen to */
    public static final int NULLOCTAVE = 0;
/** Constant to identify which octave control a note should listen to */
    public static final int RIGHTOCTAVE = 1;
    //}

    // Channel Constants {
/** Channel identifier for the GM Percussion channel */
    public static final int PERCCHAN = 9;
/** Channel identifier for auxilary actions if applicable */
    public static final int AUXCHAN = 15;
/** Channel identifier for stored sequence 1 */
    public static final int SEQACHAN = 11;
/** Channel identifier for stored sequence 2 */
    public static final int SEQBCHAN = 12;
/** Channel identifier for stored sequence 3 */
    public static final int SEQCCHAN = 13;
/** Channel identifier for stored sequence 4 */
    public static final int SEQDCHAN = 14;
    //}
    
/** Array to hold all channels that are considered active in Synth */
    public static final int[] ALLCHAN = {0, 1, 2, 3, 4, 5, 6, 7, 8, PERCCHAN,
                          10, SEQACHAN, SEQBCHAN, SEQCCHAN, SEQDCHAN, AUXCHAN};

/** Converts an array into a readable string
  * @param array Values to be read
  * @param edge1 String to display on the left edge of the readable string  
  * @param edge2 String to display on the right edge of the readable string
  * @param divider String to display that divides all elements being read
  * @return The array as a readable String
  */
    public static String arrayAsString(Object[] array, String edge1,
                               String edge2, String divider) {
        StringBuilder returnStr = new StringBuilder();
        int pos;
        returnStr.append(edge1);
        for (pos = 0; pos < array.length-1; pos++) {
            returnStr.append(array[pos].toString() + divider);
        }
        returnStr.append(array[pos].toString() + edge2);
        return returnStr.toString();        
    }

/** Converts an int array into a readable string
  * @param array Values to be read
  * @param edge1 String to display on the left edge of the readable string  
  * @param edge2 String to display on the right edge of the readable string
  * @param divider String to display that divides all elements being read
  * @return The array as a readable String
  */
    public static String arrayAsString(int[] array, String edge1,
                               String edge2, String divider) {
        StringBuilder returnStr = new StringBuilder();
        int pos;
        returnStr.append(edge1);
        for (pos = 0; pos < array.length-1; pos++) {
            returnStr.append(array[pos] + divider);
        }
        returnStr.append(array[pos] + edge2);
        return returnStr.toString();        
    }


}