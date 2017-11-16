/** Custom JPanel used to store the information about MIDI channels, constructs
  * differently based on the number of panels passed during construction. 10
  * panels construct GUIChannelPanels while 6 construct GUISequencePanels. Any
  * other amount construct none and the result is considered invalid.
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       GUIChannelPanel, GUIDisplay, GUIFacePanel, GUIItemPanel,
  * GUISequencePanel, GUILabel, KeyPosition, KeyPositionList
  * @included      GUISideListener
  */

package nesynth;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

public class GUISidePanel extends SynthPanelUI implements ActionListener {
/** Used to accomidate the way Swing handles painting passively */
    private javax.swing.Timer guispTimer;
/** Holds the information about the GUIItemPanels being displayed */
    private GUIItemPanel[] guispPanelStore;
/** Used to determine which panel is currently enabled for editing */
    private int guispEnabledPos;

/** Stores the ID of this Panel */
    private int guispPanelID;
    
/** The listener for the GUISidePanel */
    private GUISideListener guispListen;

/** Used to enable or disable the SideListener */
    private boolean guispListenerEnabled = false;

/** Holds the performer which was received from the constructing class */
    private SynthFrameUI guispParent;

/** Constructs the GUISidePanel from the number of panels and the
  * KeyPositionList passed in the parameter
  * @param panelID ID of the Panel
  * @param numberOfPanels Self-explanitory
  * @param parent The parent component of the panel
  */
    public GUISidePanel(int panelID, int numberOfPanels, SynthFrameUI parent) {
        super();
//         guispPanelStore = new GUIItemPanel[numberOfPanels];
        this.guispPanelID = panelID;
        this.guispParent = parent;
        guispPanelStore = createPanelArray(numberOfPanels, true);
        initListener();
        for (int i = 0; i < guispPanelStore.length; i++) {
            guispPanelStore[i].addMouseListener(guispListen);
            guispPanelStore[i].addMouseWheelListener(guispListen);
        }
        this.setOpaque(true);
        this.setLayout(new GridLayout(numberOfPanels,1,1,1));
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        guispEnabledPos = 0;
        setEnabledPanelPos(guispEnabledPos);
        guispTimer = new javax.swing.Timer(50, this);
        guispTimer.start();
    }

/** Initializes the listener and makes sure it is not initialized a second
  * time due to the way Swing handles revalidation and repainting
  */
    private void initListener() {
        if (!guispListenerEnabled) {
            guispListen = new GUISideListener();
            guispListenerEnabled = true;
        } else {
            System.err.println("TRIED RECREATING LISTENER");
        }
    }

// /** Enables the given panel at the position specified
//   * @param position Position of the panel being enabled
//   */
//     private void enablePanel(int position) {
//         guispPanelStore[position].setOutlineTo(Color.YELLOW);
//         this.guispEnabledPos = position;
//     }

/** Helper method used to initialize and create GUIItemPanel arrays used in
  * both construction and updating
  * @param numberOfPanels The number of panels to be creating
  * @param fromConstruction Flag to indicate whether it's being called from 
  * construction. If true, will add the necessary listeners to the panels.  
  * @return The array constructed from the values specified
  */
    private GUIItemPanel[] createPanelArray(int numberOfPanels, 
                                            boolean fromConstruction) {
        GUIItemPanel[] returnArray = new GUIItemPanel[numberOfPanels];
        //If there's ten panels (left one)
        if (numberOfPanels == 10) {
            for (int i = 0; i < numberOfPanels; i++) {
                if (i == 9)
                    returnArray[i] = new GUIChannelPanel(i+2, i+1, 
                                        guispParent.getSynth());
                else
                    returnArray[i] = new GUIChannelPanel(i+1, i, 
                                        guispParent.getSynth());
            } //if it's the left side and from construction, group ID is set
            if (fromConstruction)
                changeChannelGroupID(returnArray[0].getPanelID());
        //Else if there's six panels (right one)
        } else if (numberOfPanels == 6) {
            for (int i = 0; i<numberOfPanels; i++) {
                if (i > 0)
                    returnArray[i] = new GUISequencePanel(i+11, i+10, 
                                        guispParent.getSynth());
                else
                    returnArray[i] = new GUISequencePanel(i+10, 1+9, 
                                        guispParent.getSynth());
            }
        } else {
            this.setVisible(false);
        }
        
        return returnArray;
    }
        
        
/** Used with the Timer to revalidate and repaint the GUISidePanel
  * @param e ActionEvent being generated by the timer
  */
    public void actionPerformed(ActionEvent e) {
        revalidate();
        repaint();
    }

/** Returns the Synth associated with the class
  * @return The associated Synth
  */
    public AbstractSynth getSynth() {
        return this.guispParent.getSynth();
    }

/** Gets the PanelID for this Panel
  * @return The PanelID
  */
    public int getPanelID() {
        return this.guispPanelID;
    }

/** Changes the groupID of the listener for setting purposes
  * @param groupID ID to set to the listener
  */
    public void changeChannelGroupID(int groupID) {
        SynthPanelUI mainUI = guispParent.getCenterPanel();
        mainUI.changeChannelGroupID(groupID);        
    }

/** Used to toggle binding mode of the listener
  */    
    public void toggleChannelBindingMode() {
        SynthPanelUI mainUI = guispParent.getCenterPanel();
        mainUI.toggleChannelBindingMode();
    }

/** Used to update the display of this panel
  * @param reset Value to determine how to update the display by specific
  * implementation. A value of true on this panel will reset the enabled panel.
  */    
    public void updateDisplay(boolean reset) {
        GUIItemPanel[] update = createPanelArray(guispPanelStore.length, true);
        for(int i = 0; i < update.length; i++) {
            //Loop through and update each item panel that is different
            if (!guispPanelStore[i].equals(update[i]))
                updateItemPanel(update[i], i);                    
        }
        
        if (reset == true) {
            setEnabledPanelPos(0);
        }
    }


/** Used for sorting SynthPanelUI
  * @param e Object being compared
  * @return Integer used for sorting
  */
    public int compareTo(Object e) {
        if (e == null || !(e instanceof SynthPanelUI))
            return SortedList.NO_COMPARE;
        else {
            SynthPanelUI otherUI = (SynthPanelUI)e;
            if (this.getPanelID() < otherUI.getPanelID())
                return -1;
            else if (this.getPanelID() > otherUI.getPanelID())
                return 1;
            else
                return 0;
        }
    }
    
/** Paints the component over again with the stored GUIItemPanels
  * @param g Graphics being painted
  */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.removeAll();
        for (int i = 0; i<guispPanelStore.length; i++) {
            this.add(guispPanelStore[i]);
        }
        
        setEnabledPanelPos(guispEnabledPos);
    }

/** Updates a given ItemPanel onto the stored array and repaints the panel
  * @param update The updated panel
  * @param pos The position on the stored array the update ins being placed
  */
    public void updateItemPanel(GUIItemPanel update, int pos) {
        this.guispPanelStore[pos] = update;
        this.guispPanelStore[pos].addMouseListener(guispListen);
        this.guispPanelStore[pos].addMouseWheelListener(guispListen);
        System.err.println("UPDATED POS " + pos);
        revalidate();
        repaint();
    }
    
/** Sets the enabled panel to the value specified
  * @param val Position to be enabled
  */
    public void setEnabledPanelPos(int val) {
        guispPanelStore[val].setOutlineTo(Color.YELLOW);
        this.guispEnabledPos = val;
    }
    
/** Gets the currently enabled panel
  * @return The currently enabled GUIItemPanel
  */
    public GUIItemPanel getEnabledPanelPos() {
        return this.guispPanelStore[guispEnabledPos];
    }

/** Class for guispListening to the side panels to activate interactions with
  * individual channels, whether they are normal channels or otherwise
  */
    private class GUISideListener extends MouseAdapter {

    /** Stores the last time an event occured, used to make sure events aren't
      * handled twice */
        private long lastEventWhen = 0;
        
    /** Helper method to figure out if an event has been triggered twice
      * @param e MouseEvent being checked for duplicate triggers
      * @return True if the event hasn't been already triggered
      */
        private boolean handleGetWhen(MouseEvent e) {
            if (e.getWhen() == lastEventWhen) {
                System.err.println("STOPPING DOUBLE TRIGGERS");
                return false;
            } else {
                System.err.println("SETTING PREVENT DOUBLE TRIGGERS");
                this.lastEventWhen = e.getWhen();
                return true;
            }
        }
        
    /** Handles the pressing of a mouse
      * @param e MouseEvent being handled
      */
        public void mousePressed(MouseEvent e) {
            System.err.println(e.getWhen() + " OCCURED");
            Component c1 = e.getComponent();
            Container c2 = c1.getParent();
            if (handleGetWhen(e) == false)
                return;
            if (c1 instanceof GUIItemPanel && c2 instanceof GUISidePanel) {
                GUIItemPanel temp = (GUIItemPanel)c1;
                GUISidePanel parent = (GUISidePanel)c2;
                GUIItemPanel enabled =
                    parent.getEnabledPanelPos();
                if (enabled.getPanelID() == temp.getPanelID()) {
                    GUILabel origin = temp.getLabelFromPos(e.getX(), e.getY());
//                     System.err.println("ORIGIN");
//                     System.err.println(origin.toString());
                    ChannelList cl = temp.getChannelList();
                    GUILabel newLabel = handleSwitching(origin, temp);
                    if (newLabel.equals(origin)) //No change, do nothing
                        return;
//                     System.err.println("NEW");
//                     System.err.println(newGUILabel.toString());
                    cl.replace(newLabel);
                    cl.reset();
//                     System.err.println("\n\nCHANNEL LIST");
//                     System.err.println(cl.toString());
                    if (temp instanceof GUIChannelPanel)
                        temp = new GUIChannelPanel(temp.getPanelID(), 
                                    cl, guispParent.getSynth());
                    else if (temp instanceof GUISequencePanel)
                        temp = new GUISequencePanel(temp.getPanelID(), 
                                    cl, guispParent.getSynth());
                    temp.setOutlineTo(Color.YELLOW);
                    int[] channels = {newLabel.getGroupID()-1};
                    parent.updateItemPanel(temp, temp.getAdjPanelID());
                    SynthSys type = new SynthSys(newLabel.getActionID(),
                                                 newLabel.getActionVals(),
                                                                   channels);
                    guispParent.handleClick(new SynthButton(type));
                } else {
                    enabled.setOutlineTo(Color.BLACK);
                    temp.setOutlineTo(Color.YELLOW);
                    parent.updateItemPanel(enabled,
                                        enabled.getAdjPanelID());
                    parent.updateItemPanel(temp,
                                        temp.getAdjPanelID());
                    parent.setEnabledPanelPos(
                                            temp.getAdjPanelID());
                    if (temp instanceof GUIChannelPanel)
                        changeChannelGroupID(temp.getPanelID());
                }
            }
        }

    /** Handles the scrolling of a mousewheel
      * @param e MouseWheelEvent being handled
      */
        public void mouseWheelMoved(MouseWheelEvent e) {
            System.err.println(e.getWhen() + " OCCURED");
            Component c1 = e.getComponent();
            Container c2 = c1.getParent();
            if (handleGetWhen(e) == false)
                return;
            if (c1 instanceof GUIItemPanel && c2 instanceof GUISidePanel) {
                GUIItemPanel temp = (GUIItemPanel)c1;
                GUISidePanel parent = (GUISidePanel)c2;
                GUIItemPanel enabled =
                    parent.getEnabledPanelPos();
                if (enabled.getPanelID() == temp.getPanelID()) {
                    GUILabel origin = temp.getLabelFromPos(e.getX(), e.getY());
                    if (origin == null) //If obtained label is null, do nothing
                        return;
//                     System.err.println("ORIGIN");
//                     System.err.println(origin.toString());
                    ChannelList cl = temp.getChannelList();
                    GUILabel newLabel = handleScrolling(origin, temp, e);
                    if (newLabel.equals(origin)) //No change, do nothing
                        return;
//                     System.err.println("NEW");
//                     System.err.println(newLabel.toString());
                    cl.replace(newLabel);
                    cl.reset();
//                     System.err.println("\n\nCHANNEL LIST");
//                     System.err.println(cl.toString());
                    if (temp instanceof GUIChannelPanel)
                        temp = new GUIChannelPanel(temp.getPanelID(), 
                                     cl, guispParent.getSynth());
                    else if (temp instanceof GUISequencePanel)
                        temp = new GUISequencePanel(temp.getPanelID(),
                                     cl, guispParent.getSynth());
                    temp.setOutlineTo(Color.YELLOW);
                    int[] channels = {newLabel.getGroupID()-1};
                    parent.updateItemPanel(temp, temp.getAdjPanelID());
                    SynthSys type = new SynthSys(newLabel.getActionID(),
                                                 newLabel.getActionVals(),
                                                                   channels);
                    guispParent.handleClick(new SynthButton(type));
                }
            }
        }
        
    /** Helper method to handle the switching (pressed event) of a GUILabel
      * @param old The original GUILabel being handled upon
      * @param parent The parent GUIItemPanel holding the original GUILabel
      * @return The updated GUILabel if applicable
      */
        private GUILabel handleSwitching(GUILabel old, GUIItemPanel parent) {
            int orig = old.getTextInt();
            int pID = parent.getPanelID();
            switch(old.getLabelID()) {
                case 2:
                    if (parent instanceof GUISequencePanel) {
                        if (pID > 11 && pID < 16) {
                            return handleSwitchingSequence(old, pID);
                         } else if (pID == 16) {
                            return handleSwitchingAuxiliary(old);
                         }
                    } else if (parent instanceof GUIChannelPanel) {
                        return new GUILabel(old.getLabelID(), orig,
                                    old.getGroupID(),
                                    guispParent.getSynth()
                                                .getInstName(0),
                                    old.getActionID(), new int[]{0, 0});
                    
                    } else {
                        System.err.println("BAD SWITCH");
                        return old;
                    }
                case 8:
                case 9:
                case 10:
                    int[] originalValues =
                        old.getOriginalValuesByID(old.getLabelID());
                    return new GUILabel(old.getLabelID(), originalValues[0],
                                old.getGroupID(), old.getActionID(),
                                new int[]{originalValues[1], 0});
                case 11:
                case 12:
                    return (orig == GUILabel.OFF)
                        ? new GUILabel(old.getLabelID(), GUILabel.ON,
                                old.getGroupID(), old.getActionID(),
                                old.getActionVals())
                        : new GUILabel(old.getLabelID(), GUILabel.OFF,
                                old.getGroupID(), old.getActionID(),
                                old.getActionVals());
                default:
                    System.err.println("BAD SWITCH");
                    return old;
            }
        }

    /** Submethod fo assisting switching in the sequence GUIItemPanels
      * @param old Original GUILabel being switched
      * @param chanID The sequence ID being changed
      * @return The updated GUILabel
      */
        private GUILabel handleSwitchingSequence(GUILabel old, int chanID) {
            if (old.getTextStr().equals(GUILabel.NOV)) {
                guispParent.getSynth()
                            .changeOverride(chanID-11, true);
                return new GUILabel(old.getLabelID(), GUILabel.NONE,
                    old.getGroupID(), guispParent.getSynth()
                                                  .getInstName(0),
                    old.getActionID(), new int[]{0, 0});
            } else {
                guispParent.getSynth()
                            .changeOverride(chanID-11, false);
                return new GUILabel(old.getLabelID(), JLabel.LEFT,
                    old.getGroupID(), GUILabel.NOV, old.getActionID(),
                                                      new int[]{0, 0});
            }
        }
        
    /** Submethod fo assisting switching in the auxiliary GUIItemPanel
      * @param old Original GUILabel being switched
      * @return The updated GUILabel
      */
        private GUILabel handleSwitchingAuxiliary(GUILabel old) {
            //Aux Keys = 29, 30, 31, 32
            KeyPosition temp;
            if (old.getTextStr().equals(GUILabel.NOV)) {
                guispParent.getSynth()
                            .changeOverride(5, true);                
                //Activating, turn AUX keys to Channel 15
                for (int i = 29; i <= 32; i++) {
                    temp = guispParent.getKeyPositionList()
                                       .getKeyPositionFromPos(i);
                    Note regroup = (Note)temp.getSynthType();
                    regroup.setChan(SynthConstants.AUXCHAN);
                    temp.setSynthType(regroup);
                    guispParent.getKeyPositionList().replace(temp);
                    guispParent.loadAction(temp.getKeyCode(), temp);
                    changeChannelGroupID(15);
                }
                //Update display
                return new GUILabel(old.getLabelID(), GUILabel.NONE,
                    old.getGroupID(), guispParent.getSynth()
                                                  .getInstName(0),
                    old.getActionID(), new int[]{0, 0});
            } else {
                guispParent.getSynth()
                            .changeOverride(5, false);   
                for (int i = 29; i <= 32; i++) {
                    temp = guispParent.getKeyPositionList()
                                       .getKeyPositionFromPos(i);
                    Note regroup = (Note)temp.getSynthType();
                    regroup.setChan(SynthConstants.PERCCHAN);
                    temp.setSynthType(regroup);
                    guispParent.getKeyPositionList().replace(temp);
                    guispParent.loadAction(temp.getKeyCode(), temp);
                    changeChannelGroupID(9);
                }
                //Update display
                return new GUILabel(old.getLabelID(), JLabel.LEFT,
                    old.getGroupID(), GUILabel.NOV, old.getActionID(), 
                                                   new int[]{0, 0});
            }
        }

    /** Helper method to return an interger from the wheel rotation.
      * @param e MouseWheelEvent being checked from
      * @return 1 if scrolling up, -1 if scrolling down.
      */
        private int getScrolling(MouseWheelEvent e) {
            return ((int)Math.ceil(-e.getPreciseWheelRotation()) > 0) ? 1
                                                                      : -1;
        }
        
    /** Helper method to handle the scrolling (scrolling event) of a GUILabel a
      * @param old The original GUILabel being handled upon
      * @param parent The parent GUIItemPanel holding the original GUILabel
      * @param e MouseWheelEvent being utilized in the helper method
      * @return The updated GUILabel if applicable
      */
        private GUILabel handleScrolling(GUILabel old, GUIItemPanel parent,
                                                        MouseWheelEvent e) {
            int orig = old.getActionVals()[0];
            int pID = parent.getPanelID();
            int val = getScrolling(e);
            int adjVal;
            switch(old.getLabelID()) {
                //Instrument
                case 2: //Check which side it's activating from
                    int origPos = guispParent.getSynth()
                      .convertInstBankProgramToPos(old.getActionVals(), false);
                    //Right side
                    if (parent instanceof GUISequencePanel) {
                        if (pID > 11 && pID < 16) {
                            return (guispParent.getSynth()
                                                .checkOverride(pID-11))
                                //If active, change instrument
                                ? new GUILabel(old.getLabelID(),
                                    old.getTextInt(), old.getGroupID(),
                                    guispParent.getSynth()
                                            .getInstName(origPos+val, false),
                                    old.getActionID(), 
                                    guispParent.getSynth()
                                      .convertInstPosToBankProgram(origPos+val,
                                                                       false))
                                //If not active, don't
                                : old;
                        } else if (pID == 16) {
                            return (!old.getTextStr().equals(GUILabel.NOV))
                                //If active, change instrument
                                ? new GUILabel(old.getLabelID(),
                                    old.getTextInt(), old.getGroupID(),
                                    guispParent.getSynth()
                                            .getInstName(origPos+val, false),
                                    old.getActionID(), 
                                    guispParent.getSynth()
                                      .convertInstPosToBankProgram(origPos+val, 
                                                                       false))
                                //If not active, don't
                                : old;
                        } else if (pID == 10) {
                            origPos = guispParent.getSynth()
                              .convertInstBankProgramToPos(old.getActionVals(),
                                                                         true);
                            return new GUILabel(old.getLabelID(),
                                old.getTextInt(), old.getGroupID(),
                                guispParent.getSynth()
                                            .getInstName(origPos+val, true),
                                old.getActionID(), 
                                guispParent.getSynth()
                                    .convertInstPosToBankProgram(origPos+val, 
                                                                    true));
                        }
                    //Left side
                    } else if (parent instanceof GUIChannelPanel) {
                        return new GUILabel(old.getLabelID(),
                            old.getTextInt(), old.getGroupID(),
                            guispParent.getSynth()
                                        .getInstName(origPos+val, false),
                            old.getActionID(), 
                            guispParent.getSynth()
                                .convertInstPosToBankProgram(origPos+val, false));
                    //No side
                    } else {
                        System.err.println("BAD SCROLL");
                        return old;
                    }
                //Volume and Modulation
                case 8:
                case 9:
                    adjVal = (val < 0)
                        ? AbstractSynth.incrementBoundedValue(
                            orig, -Math.abs(8*val), SynthConstants.MAXVOLMP)
                        : AbstractSynth.incrementBoundedValue(
                            orig, Math.abs(8*val), SynthConstants.MAXVOLMP);
                    return new GUILabel(old.getLabelID(),
                         adjVal, old.getGroupID(),
                         old.getActionID(), new int[]{adjVal, 0});
                //Pitch
                case 10:
                    int factor = 
                        (orig == SynthConstants.MAXPITCHBEND) ? 897 : 1024;
                    adjVal = (val < 0)
                        ? AbstractSynth.incrementBoundedValue(orig,
                            -Math.abs(factor*val), SynthConstants.MAXPITCHBEND)
                        : AbstractSynth.incrementBoundedValue(orig,
                            Math.abs(factor*val), SynthConstants.MAXPITCHBEND);
                    return new GUILabel(old.getLabelID(),
                         adjVal-8192, old.getGroupID(),
                         old.getActionID(), new int[]{adjVal, 0});
                default:
                    System.err.println("BAD SCROLL");
                    return old;
            }
        }
    }
}
