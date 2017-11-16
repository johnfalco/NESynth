package nesynth;

import javax.sound.midi.*;

public class BankChannel {
    private MidiChannel bcChannel;
    private int bcBank;
    
    public BankChannel(MidiChannel channel) {
        this.bcChannel = channel;
        this.bcBank = 0;
    }
    
    public int getBank() {
        return this.bcBank;
    }
    
    public MidiChannel getChannel() {
        return this.bcChannel;
    }

/** Method to set the channel's bank and program AND will actually
  * store the bank of the instrument in a retrievable fashion
  * @param bank Bank to be set
  * @param program Program to be set
  */
    public void programChange(int bank, int program) { 
        this.bcChannel.programChange(bank, program);
        this.bcBank = bank;
    }

/* All methods below interface directly to the MidiChannel.
** This is because there is an error in the coding of com.sun.sound.SoftChannel
** which prevents the bank from being obtained by ANY method whatsoever.
*/
    public void allNotesOff() { 
        this.bcChannel.allNotesOff();
    }
    public void allSoundOff() {
        this.bcChannel.allSoundOff();
    }
    
    public void controlChange(int controller, int value) { 
        this.bcChannel.controlChange(controller, value);
    }
    
    public int getChannelPressure() {
        return this.bcChannel.getChannelPressure();
    }

    public int getController(int controller) { 
        return this.bcChannel.getController(controller);
    }
    
    public boolean getMono() {
        return this.bcChannel.getMono();
    }
    
    public boolean getMute() {
        return this.bcChannel.getMute();
    }
    
    public boolean getOmni() {
        return this.bcChannel.getOmni();
    }
    
    public int getPitchBend() {
        return this.bcChannel.getPitchBend();
    }
    
    public int getPolyPressure(int noteNumber) {
        return this.bcChannel.getPolyPressure(noteNumber);
    }
    
    public int getProgram() {
        return this.bcChannel.getProgram();
    }
    
    public boolean getSolo() {
        return this.bcChannel.getSolo();
    }
    
    public boolean localControl(boolean on) { 
        return this.bcChannel.localControl(on);
    }
    
    public void noteOff(int noteNumber) { 
        this.bcChannel.noteOff(noteNumber);
    }
    
    public void noteOff(int noteNumber, int velocity) {
        this.bcChannel.noteOff(noteNumber, velocity);
    }
    
    public void noteOn(int noteNumber, int velocity) {
        this.bcChannel.noteOn(noteNumber, velocity);
    }
    
    public void programChange(int program) { 
        this.bcChannel.programChange(program);
    }
    
    public void resetAllControllers() {
        this.bcChannel.resetAllControllers();
    }
    
    public void setChannelPressure(int pressure) {
        this.bcChannel.setChannelPressure(pressure); 
    }
    
    public void setMono(boolean on) {
        this.bcChannel.setMono(on);
    }
    
    public void setMute(boolean mute) {
        this.bcChannel.setMute(mute); 
    }
    
    public void setOmni(boolean on) {
        this.bcChannel.setOmni(on); 
    }
    
    public void setPitchBend(int bend) {
        this.bcChannel.setPitchBend(bend); 
    }
    
    public void setPolyPressure(int noteNumber, int pressure) {
        this.bcChannel.setPolyPressure(noteNumber, pressure); 
    }
    
    public void setSolo(boolean soloState) {
        this.bcChannel.setSolo(soloState); 
    }
}