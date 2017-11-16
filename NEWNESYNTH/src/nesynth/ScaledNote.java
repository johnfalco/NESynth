package nesynth;

public class ScaledNote extends Note {
/** Constructs a ScaledNote object
  * @param note Integer representing the pitch of the note
  * @param velocity Integer representing the velocity of the note
  * @param duration Integer representing the duration of the note
  * @param channel Integer representing the channel of the note
  * @param octaveSide Integer representing specific implementation values
  * of the note for use by the Synth
  */
    public ScaledNote(int note, int velocity, int duration,
                             int channel, int octaveSide) {
        super(note, velocity, duration, channel, octaveSide);
    }
}    