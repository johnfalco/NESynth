/** Extended GUIItemPanel used to distinguish size and information contained
  * within the GUILabels it holds, used for sequences and miscellaneous MIDI
  * channels.
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491. MULT 500
  * @assignment    NESynth
  * @related       ChannelList, GUIItemPanel, Synth
  * @included      None
  */

package nesynth;

import java.awt.*;

public class GUISequencePanel extends GUIItemPanel {

/** Basic constructor, constructs the ItemPanel using the basic values required
  * within GUIDisplay for proper alignment purposes
  * @param id Integer representing the channel as well as the id for sorting
  * @param inst Integer representing the instrument to construct the
  * ChannelList from
  * @param performer The Synth stored from the constructing class
  */
    public GUISequencePanel(int id, int inst, AbstractSynth performer) {
        super(id, inst, new Dimension(200, 105), new Dimension(40, 35), 
                                                                performer);
    }
    
/** Constructs the ItemPanel using a custom dimension for
  * the labels within the itempanel
  * @param id Integer representing the channel as well as the id for sorting
  * @param inst Integer representing the instrument to construct the
  * ChannelList from
  * @param inSize Dimension set to the labels within the ItemPanel
  * @param performer The Synth stored from the constructing class
  */
    public GUISequencePanel(int id, int inst, Dimension inSize,
                                         AbstractSynth performer) {
        super(id, inst, new Dimension(200, 105), inSize, performer);
    }

/** Constructs the ItemPanel using a custom dimension for both the panel
  * itself as well as the labels inside
  * @param id Integer representing the channel as well as the id for sorting
  * @param inst Integer representing the instrument to construct the
  * ChannelList from
  * @param sizeA Dimension used to set the main panel's size
  * @param sizeB Dimension used to set the label's size within
  * @param performer The Synth stored from the constructing class
  */
    public GUISequencePanel(int id, int inst, Dimension sizeA, 
                            Dimension sizeB, AbstractSynth performer) {
        super(id, inst, sizeA, sizeB, performer);
    }
    
/** Constructs the ItemPanel using the basic values required
  * within GUIDisplay for proper alignment purposes
  * @param id Integer representing the channel as well as the id for sorting
  * @param cl ChannelList to set into the ItemPanel
  * @param performer The Synth stored from the constructing class
  */
    public GUISequencePanel(int id, ChannelList cl, 
                                AbstractSynth performer) {
        super(id, cl, new Dimension(200, 105), new Dimension(40, 35), 
                                                            performer);
    }

/** Constructs the ItemPanel using a custom dimension for
  * the labels within the itempanel
  * @param id Integer representing the channel as well as the id for sorting
  * @param cl ChannelList to set into the ItemPanel
  * ChannelList from
  * @param inSize Dimension set to the labels within the ItemPanel
  * @param performer The Synth stored from the constructing class
  */
    public GUISequencePanel(int id, ChannelList cl, Dimension inSize, 
                                                    AbstractSynth performer) {
        super(id, cl, new Dimension(200, 105), inSize, performer);
    }

/** Constructs the ItemPanel using a custom dimension for both the panel
  * itself as well as the labels inside
  * @param id Integer representing the channel as well as the id for sorting
  * @param cl ChannelList to set into the ItemPanel
  * @param sizeA Dimension used to set the main panel's size
  * @param sizeB Dimension used to set the label's size within
  * @param performer The Synth stored from the constructing class
  */
    public GUISequencePanel(int id, ChannelList cl, Dimension sizeA,
                            Dimension sizeB, AbstractSynth performer) {
        super(id, cl, sizeA, sizeB, performer);
    }
}