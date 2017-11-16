/** Custom exception used by the SynthListener for Bad MIDI conversions
 * 
 * @Version       1.0
 * @author        John Falco
 * @id            jpfalco
 * @course        CSIS 491, MULT 500
 * @assignment    NESynth
 * @related       None
 * @included      None
 */

package nesynth;

public class BadMIDISequenceException extends RuntimeException {

/** Default constructor for the runtime exception representing
 * a bad midi sequence passed into the convertMIDI method
 */
   public BadMIDISequenceException() {
       super();
   }

/** Customizable constructor for the runtime exception representing
 * a bad midi sequence passed into the convertMIDI method with text
 * @param str String to display along with the runtime exception
 */
   public BadMIDISequenceException(String str) {
       super(str);
   }
}