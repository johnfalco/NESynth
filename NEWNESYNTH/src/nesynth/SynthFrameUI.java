/** Interface for the main JFrame of the NESynth Project
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       KeyPosition, SynthUI, SynthPanelUI 
  * @included      None
  */

package nesynth;

public interface SynthFrameUI extends SynthUI {
/** Helper method to load a given KeyPosition with its associated values
  * into the Synth at a given KeyCode for interaction
  * @param keyCode Integer representing the KeyCode of a KeyEvent
  * @param keyPos KeyPosition to load action from
  * Synth actions
  */
    public void loadAction(int keyCode, KeyPosition keyPos);

/** Handles a click from a SynthButton created for special Synth
  * interactions.
  * @param inButton SynthButton being acted upon
  */
    public void handleClick(SynthButton inButton);
     
/** Gets the center Panel of the UI
  * @return The center Panel UI
  */
    public SynthPanelUI getCenterPanel();
/** Gets the left Panel of the UI
  * @return The left Panel UI
  */
    public SynthPanelUI getLeftPanel();

/** Gets the right Panel of the UI
  * @return The right Panel UI
  */     
    public SynthPanelUI getRightPanel();
    
/** Gets the KeyPositionList of the UI
  * @return The KeyPositionList stored with the UI
  */
    public KeyPositionList getKeyPositionList();
}