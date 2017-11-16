/** An extension of the SortedList used for constructing a list of GUILabels
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       GUILabel, SortedList
  * @included      None
  */

package nesynth;

import javax.swing.JLabel;

public class ChannelList extends SortedList {

/** Constructs the list with default values
  * @param group Integer represeting the channel group and the set color
  * @param channel Integer representing the channel being constructed from
  * @param performer The SynthListener where information is being received from
  */
    public ChannelList(int group, int channel, AbstractSynth performer) {
        super(12);
        int[] convertedPos = performer.getInstBankProgramFromChannel(channel);
        if (group == 10) {
            convertedPos = 
                performer.getInstBankProgramFromChannel(9);
            this.add(new GUILabel(1, JLabel.LEFT, group,
                                     GUILabel.PERC));        
            this.add(new GUILabel(2, JLabel.LEFT, group,
                    performer.getInstFromChannel(9),
                    SynthConstants.CHANGEPROGRAM, convertedPos));         
        } else if (group > 11 && group < 16) {
            this.add(new GUILabel(1, JLabel.LEFT, group,
                                     GUILabel.SEQU + (group - 11)));
            handleSeqLabel(group, channel, convertedPos, performer);
        } else if (group == 16) {
            this.add(new GUILabel(1, JLabel.LEFT, group,
                                     GUILabel.AUX));
            handleSeqLabel(group, channel, convertedPos, performer);
        } else {
            this.add(new GUILabel(1, JLabel.LEFT, group,
                                     GUILabel.CHAN + group));
            this.add(new GUILabel(2, JLabel.LEFT, group,
                    performer.getInstFromChannel(channel),
                    SynthConstants.CHANGEPROGRAM, convertedPos)); 
        }
                   
        this.add(new GUILabel(3 , GUILabel.NONE, group, GUILabel.VOL));
        this.add(new GUILabel(4 , GUILabel.NONE, group, GUILabel.MOD));
        this.add(new GUILabel(5 , GUILabel.NONE, group, GUILabel.PIT));
        this.add(new GUILabel(6 , GUILabel.NONE, group, GUILabel.MON));
        this.add(new GUILabel(7 , GUILabel.NONE, group, GUILabel.SOL));
        this.add(new GUILabel(8 , performer.getVolumeChannel(channel),
                        group, SynthConstants.SETVOL, 
                        new int[]{performer.getVolumeChannel(channel), 0}));
        this.add(new GUILabel(9 , performer.getModWheelChannel(channel),
                        group, SynthConstants.SETMODPITCH, 
                        new int[]{performer.getModWheelChannel(channel), 0}));
        this.add(new GUILabel(10, performer.getAdjPitchChannel(channel),
                        group, SynthConstants.SETPITCHBEND, 
                        new int[]{performer.getPitchChannel(channel), 0}));
        this.add(new GUILabel(11, 
                        convertStatus(performer.getMonoChannel(channel)),
                        group, SynthConstants.TOGGLEMONO, new int[]{0, 0}));
        this.add(new GUILabel(12,
                        convertStatus(performer.getSoloChannel(channel)),
                        group, SynthConstants.TOGGLESOLO, new int[]{0, 0}));
    }

/** Helper method to handle overrided channels and their labeling
  */
    private void handleSeqLabel(int group, int channel, int[] convertedPos,
                                                     AbstractSynth performer) {
        if (performer.checkOverride(group-11) == true) {
            this.add(new GUILabel(2, GUILabel.NONE, group, 
                    performer.getInstFromChannel(channel),
                    SynthConstants.CHANGEPROGRAM, convertedPos));
         } else {
             this.add(new GUILabel(2, JLabel.LEFT, group, GUILabel.NOV,
                        SynthConstants.CHANGEPROGRAM, convertedPos));         
         }
    }

/** Helper method to convert the status of a true/false value from the 
  * performer into the proper label value used within GUILabel's construction.
  * @param status Status to convert
  * @return The converted status
  */
    private int convertStatus(boolean status) {
        return (status == true) ? GUILabel.ON : GUILabel.OFF;
    }
    
/** Compares this ChannelList to another to check if they are equal
  * @param otherList Other List being compared
  * @return True if equal
  */
    public boolean equals(ChannelList otherList) {
        if (this.lNum != otherList.lNum)
            return false;
        else {
            GUILabel a, b;
            for (int i = 0; i < lNum; i++) {
                if (!(this.lArray[i] instanceof GUILabel)
                 || !(otherList.lArray[i] instanceof GUILabel))
                    return false;
                else {
                    a = (GUILabel)this.lArray[i];
                    b = (GUILabel)otherList.lArray[i];
                    if (!a.equals(b))
                        return false;
                }
            }
            //if it doesn't return false already, it's equal
            return true;
        }    
    }
}