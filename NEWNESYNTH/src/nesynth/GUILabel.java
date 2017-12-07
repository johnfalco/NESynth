/** Custom JLabel which is used to hold and display information pertaining to
  * the MIDI channels.
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SortedList
  * @included      None
  */

package nesynth;

import java.awt.*;
import javax.swing.*;

public class GUILabel extends JLabel implements Comparable {

/** Constant to identify the color of the Label */
    public static final int ChanColorA = 1;
/** Constant to identify the color of the Label */
    public static final int ChanColorB = 2;
/** Constant to identify the color of the Label */
    public static final int ChanColorC = 3;
/** Constant to identify the color of the Label */
    public static final int ChanColorD = 4;
/** Constant to identify the color of the Label */
    public static final int ChanColorE = 5;
/** Constant to identify the color of the Label */
    public static final int ChanColorF = 6;
/** Constant to identify the color of the Label */
    public static final int ChanColorG = 7;
/** Constant to identify the color of the Label */
    public static final int ChanColorH = 8;
/** Constant to identify the color of the Label */
    public static final int ChanColorI = 9;
/** Constant to identify the color of the Label */
    public static final int ChanColorJ = 10;
/** Constant to identify the color of the Label */
    public static final int ChanColorK = 11;
/** Constant to identify the color of the Label */
    public static final int ChanColorL = 12;
/** Constant to identify the color of the Label */
    public static final int ChanColorM = 13;
/** Constant to identify the color of the Label */
    public static final int ChanColorN = 14;
/** Constant to identify the color of the Label */
    public static final int ChanColorO = 15;
/** Constant to identify the color of the Label */
    public static final int ChanColorP = 16;

/** Constant to identify the label reading OFF */
    public static final int OFF = -2;
/** Constant to identify the label reading ON */
    public static final int ON = -1;
/** Constant to identify no text-converted integer reading */
    public static final int NONE = 0;

/** Constant to identify the title of "C #" from construction */
    public static final String CHAN = "C #";
/** Constant to identify the title of "S #" from construction */
    public static final String SEQU = "S #";
/** Constant to identify the title of "Not Overriding" from construction */
    public static final String NOV = "Not Overriding";
/** Constant to identify the title of "Percussion" from construction */
    public static final String PERC = "Percussion";
/** Constant to identify the title of "Aux" from construction */
    public static final String AUX = "Aux";
/** Constant to identify the title of "Vol" from construction */
    public static final String VOL = "Vol";
/** Constant to identify the title of "Mod" from construction */
    public static final String MOD = "Mod";
/** Constant to identify the title of "Pitch" from construction */
    public static final String PIT = "Pitch";
/** Constant to identify the title of "Mono" from construction */
    public static final String MON = "Mono";
/** Constant to identify the title of "Solo" from construction */
    public static final String SOL = "Solo";

/** Constant to identify original values based off of the ID,
  * which is used for "resets" of the GUILabel
  */
    private static final int[][] ORIGINS = {
    /* Label ID #1  */  {JLabel.LEFT, 0},
    /* Label ID #2  */  {JLabel.LEFT, 0},
    /* Label ID #3  */  {GUILabel.NONE, 0},
    /* Label ID #4  */  {GUILabel.NONE, 0},
    /* Label ID #5  */  {GUILabel.NONE, 0},
    /* Label ID #6  */  {GUILabel.NONE, 0},
    /* Label ID #7  */  {GUILabel.NONE, 0},
    /* Label ID #8  */  {100, 100},
    /* Label ID #9  */  {0, 0},
    /* Label ID #10 */  {0, 8192},
    /* Label ID #11 */  {GUILabel.OFF, 0},
    /* Label ID #12 */  {GUILabel.OFF, 0}};

/** Stores the ID of the label, used for sorting labels */
    private int guilID;
/** Stores the text being displayed on the label, is affected by the textInt */
    private String guilTextStr;
/** Stores the text-representing integer which determines either positioning
  * or a number to display onto the panel by calling setTextToTextInt */
    private int guilTextInt;
/** Stores the group ID of the label, used for setting color */
    private int guilGroupID;
/** Stores the action ID of the label for Synthesizer actions */
    private int guilActionID;
/** Stores the action value of the label for Synthesizer actions */
    private int[] guilActionVals;

/** Basic constructor for the GUILabel with integer text
  * @param id Integer for the id of the label
  * @param textInt Integer for handling integer-based text display
  * @param groupID Integer for the channel number and setting label color
  */
    public GUILabel(int id, int textInt, int groupID) {
        super();
        this.guilID = id;
        this.guilTextInt = textInt;
        this.setTextToTextInt();
        handleOffsetting(textInt);
        this.guilGroupID = groupID;
        setColorByGroupID();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setOpaque(true);
        this.guilActionID = 0;
        this.guilActionVals = new int[2];
    }

/** Constructor for the GUILabel with integer text and action values
  * @param id Integer for the id of the label
  * @param textInt Integer for handling integer-based text display
  * @param groupID Integer for the channel number and setting label color
  * @param actID Integer for the action's ID
  * @param actVals Integer for the action's value
  */
    public GUILabel(int id, int textInt, int groupID,
                    int actID, int[] actVals) {
        super();
        this.guilID = id;
        this.guilTextInt = textInt;
        this.setTextToTextInt();
        handleOffsetting(textInt);
        this.guilGroupID = groupID;
        setColorByGroupID();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setOpaque(true);
        this.guilActionID = actID;
        this.guilActionVals = actVals;
    }

/** Basic constructor for the GUILabel with String text
  * @param id Integer for the id of the label
  * @param textInt Integer for handling integer-based text display
  * @param groupID Integer for the channel number and setting label color
  * @param textStr String representing the text displayed on the label
  */
    public GUILabel(int id, int textInt, int groupID, String textStr) {
        super(textStr);
        handleOffsetting(textInt);
        this.guilID = id;
        this.guilTextStr = textStr;
        this.guilTextInt = textInt;
        this.guilGroupID = groupID;
        setColorByGroupID();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setOpaque(true);
        this.guilActionID = 0;
        this.guilActionVals = new int[2];
    }

/** Constructor for the GUILabel with String text and action values
  * @param id Integer for the id of the label
  * @param textInt Integer for handling text alignment
  * @param groupID Integer for the channel number and setting label color
  * @param textStr String representing the text displayed on the label
  * @param actID Integer for the action's ID
  * @param actVals Integer for the action's value
  */
    public GUILabel(int id, int textInt, int groupID,
                    String textStr, int actID, int[] actVals) {
        super(textStr);
        handleOffsetting(textInt);
        this.guilID = id;
        this.guilTextStr = textStr;
        this.guilTextInt = textInt;
        this.guilGroupID = groupID;
        setColorByGroupID();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setOpaque(true);
        this.guilActionID = actID;
        this.guilActionVals = actVals;
    }

/** Used to align text based on the value specified
  * @param value Constant used for label alignment
  */
    private void handleOffsetting(int value) {
        if (value == JLabel.LEFT)
            super.setHorizontalAlignment(JLabel.LEFT);
        else
            super.setHorizontalAlignment(JLabel.CENTER);
    }

/** Sets the color by the group ID and returns the Color set
  * @return The color set based on the groupID
  */
    public Color setColorByGroupID() {
        Color returnColor;
        switch(this.guilGroupID) {
            case ChanColorA:
                returnColor = new Color(191, 64, 64);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorB:
                returnColor = new Color(191,128, 64);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorC:
                returnColor = new Color(191,191, 64);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorD:
                returnColor = new Color(128,191, 64);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorE:
                returnColor = new Color( 64,191,128);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorF:
                returnColor = new Color( 64,191,191);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorG:
                returnColor = new Color( 64,128,191);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorH:
                returnColor = new Color(128, 64,191);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorI:
                returnColor = new Color(191, 64,191);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorJ:
                returnColor = new Color(128,128,128);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorK:
                returnColor = new Color(191, 64,128);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorL:
                returnColor = new Color(255,128,  0);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorM:
                returnColor = new Color(128,255,  0);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorN:
                returnColor = new Color(  0,128,255);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorO:
                returnColor = new Color(128,  0,255);
                super.setBackground(returnColor);
                return returnColor;
            case ChanColorP:
                returnColor = new Color( 64, 64, 64);
                super.setBackground(returnColor);
                return returnColor;
            default:
                returnColor = new Color(0,0,0);
                super.setBackground(returnColor);
                return returnColor;
        }
    }

/** Compares this object with the other object for sorting purposes
  * @param e Object being compared to
  * @return Integer representing compared result
  */
    public int compareTo(Object e) {
        if (e == null)
            return SortedList.NO_COMPARE;
        //else - not null, continue
        GUILabel guilabel = (GUILabel)e;
        if (this.guilID < guilabel.guilID)
            return -1;
        else if (this.guilID > guilabel.guilID)
            return 1;
        else
            return 0;
    }

/** Determines if this GUILabel equals the parameter GUILabel
  * @param otherLabel GUILabel being checked against
  * @return True if equal
  */
    public boolean equals(GUILabel otherLabel) {
        if (otherLabel == null)
            return false;
        else
            return (this.guilID == otherLabel.guilID
                 && this.guilTextInt == otherLabel.guilTextInt
                 && this.guilGroupID == otherLabel.guilGroupID
                 && this.guilTextStr.equals(otherLabel.guilTextStr)
                 && this.guilActionID == otherLabel.guilActionID
                 && equalActionVals(otherLabel));
    }
    
    private boolean equalActionVals(GUILabel otherLabel) {
        if (this.guilActionVals.length != otherLabel.guilActionVals.length)
            return false;
        else {
            for (int i=0; i<this.guilActionVals.length; i++) {
                if (this.guilActionVals[i] 
                 != otherLabel.guilActionVals[i])
                    return false;
            }
            return true;
        }
    }

/** Gets the Label ID from the GUILabel
  * @return Integer representing the Label ID
  */
    public int getLabelID() {
        return this.guilID;
    }

/** Gets the text-representing Integer from the GUILabel
  * @return Integer representing the text-representing Integer
  */
    public int getTextInt() {
        return this.guilTextInt;
    }

/** Returns the text from the GUILabel
  * @return String representing the label
  */
    public String getTextStr() {
        return this.guilTextStr;
    }

/** Returns array representing original TextInt and ActValA by the ID
  * @param id ID to be obtained from
  * @return The array representing the base values of the label
  */
    public int[] getOriginalValuesByID(int id) {
        return this.ORIGINS[id-1];
    }
    
/** Gets the group ID from the GUILabel
  * @return Integer representing the group ID
  */
    public int getGroupID() {
        return this.guilGroupID;
    }

/** Gets the action ID from the GUILabel
  * @return Integer representing the action ID
  */
    public int getActionID() {
        return this.guilActionID;
    }

/** Gets the action value from the GUILabel
  * @return Integer representing the first action value
  */
    public int[] getActionVals() {
        return this.guilActionVals;
    }

/** Sets Text to the TextInt to the TextInt value
  */
    public void setTextToTextInt() {
        if (this.guilTextInt == OFF) {
            super.setText("OFF");
            this.guilTextStr = "OFF";
        } else if (this.guilTextInt == ON) {
            super.setText("ON");
            this.guilTextStr = "ON";
        } else {
            super.setText(this.guilTextInt + "");
            this.guilTextStr = this.guilTextInt + "";
        
        }
    }

/** Sets the text-representing integer to the specified value
  * @param value Value to be set
  */
    public void setTextInt(int value) {
        this.guilTextInt = value;
    }

/** Sets the group ID to the specified value
  * @param value Value to be set
  */
    public void setGroupID(int value) {
        this.guilGroupID = value;
    }
    
/** Displays the GUILabel as a string
  * @return String representation of this label
  */
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();

        returnString.append("ID:" + this.guilID);
        returnString.append("\n");
        returnString.append("TextStr:" + this.guilTextStr);
        returnString.append("\n");
        returnString.append("TextInt:" + this.guilTextInt);
        returnString.append("\n");
        returnString.append("GroupID:" + this.guilGroupID);
        returnString.append("\n");
        returnString.append("ActionID:" + this.guilActionID);
        returnString.append("\n");
        returnString.append("ActionVal:" 
          + SynthConstants.arrayAsString(this.guilActionVals, "{", "}", ","));
        
        return returnString.toString();
    }
}