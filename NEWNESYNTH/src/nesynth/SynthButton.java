/** Custom JButton used for creating SynthSystem actions for special
  * interaction with the Synth class
  *
  * @Version 1.0
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491 & MULT 500
  * @assignment    NESynth
  * @related       None
  * @included      None
  */

package nesynth;

import javax.swing.*;

public class SynthButton extends JButton {

/** Stores the SynthType being activated with the SynthButton */
    private SynthType sbType;
/** Stores whether the SynthType is being activated or deactivated */
    private boolean sbIsOn;

/** Constructs the button based upon the parameter values
  * @param type SynthType to store
  */
    public SynthButton(SynthType type) {
        super();
        this.sbType = type;
        this.sbIsOn = false;
    }

/** Constructs the button based upon the parameter values
  * @param type SynthType to store
  * @param isOn Stores whether the event activating or deactivating
  */
    public SynthButton(SynthType type, boolean isOn) {
        super();
        this.sbType = type;
        this.sbIsOn = isOn;
    }

/** Gets the type from the SynthButton 
  * @return The type from the SynthButton
  */
    public SynthType getSynthType() {
        return this.sbType;
    }

/** Sets the type to the SynthButton
  * @param type SynthType to be set
  */
    public void setSynthType(SynthType type) {
        this.sbType = type;
    }

/** Gets the state of the SynthType (activating or deactivating)
  * @return The state of the SynthType
  */
    public boolean getSynthTypeState() {
        return this.sbIsOn;
    }
}