/** Custom JPanel which contains GUILabels, used to store information on
  * the MIDI channels and interacting with them.
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       ChannelList, GUILabel, SortedList
  * @included      None
  */

package nesynth;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIItemPanel extends JPanel implements ActionListener,
                                                         Comparable {
/** Used to accomidate the way Swing handles painting passively */
    protected javax.swing.Timer guiipTimer;
/** Stores the GUILabels representing the channel's information */
    protected ChannelList guiipChanList;

/** Stores the dimensions of the ItemPanel itself */
    protected Dimension guiipMainSize;
/** Stores the dimensions of the GUILabels inside of the ItemPanel */
    protected Dimension guiipLabelSize;

/** Integer representing the ID of the GUIItemPanel */
    protected int guiipID;
/** Stores the Synth performing sounds from the constructing classes */
    protected AbstractSynth guiipPerformer;


/** Constructs the ItemPanel using a custom dimension for both the panel
  * itself as well as the labels inside
  * @param id Integer representing the channel as well as the id for sorting
  * @param inst Integer representing the instrument to construct the
  * ChannelList from
  * @param sizeA Dimension used to set the main panel's size
  * @param sizeB Dimension used to set the label's size within
  * @param performer The Synth stored from the constructig class
  */
    public GUIItemPanel(int id, int inst, Dimension sizeA, Dimension sizeB,
                                                  AbstractSynth performer) {
        super();
        this.guiipID = id;
        this.guiipMainSize = sizeA;
        this.setPreferredSize(this.guiipMainSize);
        this.guiipLabelSize = sizeB;
        this.guiipChanList = new ChannelList(id, inst, performer);
        this.guiipPerformer = performer;
        handleConstruction();
    }
 
/** Constructs the ItemPanel using a custom dimension for both the panel
  * itself as well as the labels inside
  * @param id Integer representing the channel as well as the id for sorting
  * @param cl ChannelList to set into the ItemPanel
  * @param sizeA Dimension used to set the main panel's size
  * @param sizeB Dimension used to set the label's size within
  * @param performer The Synth stored from the constructig class
  */
    public GUIItemPanel(int id, ChannelList cl, Dimension sizeA, 
                        Dimension sizeB, AbstractSynth performer) {
        super();
        this.guiipID = id;
        this.guiipChanList = cl;
        this.guiipMainSize = sizeA;
        this.setPreferredSize(this.guiipMainSize);
        this.guiipLabelSize = sizeB;
        this.guiipPerformer = performer;
        handleConstruction();
    }

/** Used in conjunction with the timer to update the panel's graphics
  * @param e ActionEvent triggered by the timer for repainting
  */
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

/** Handles construction of the Panel itself
  */
    protected void handleConstruction() {
        this.setOpaque(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.BLACK);
        GUILabel temp = (GUILabel)guiipChanList.getNext();
        if(this.guiipChanList.size() == 11) {
            for (int i = 0; i < 11; i++) {
                temp.setOpaque(true);
                if(i == 0) {
                    int x = (int)(5 * guiipLabelSize.getWidth());
                    int y = (int)guiipLabelSize.getHeight();
                    temp.setPreferredSize(new Dimension(x, y));
                } else
                    temp.setPreferredSize(guiipLabelSize);
                this.add(temp);
                temp = (GUILabel)guiipChanList.getNext();
            }
        } else {
            for (int i = 0; i < 12; i++) {
                temp.setOpaque(true);
                if(i == 0) {
                    int x = (int)(1.7 * guiipLabelSize.getWidth());
                    int y = (int)guiipLabelSize.getHeight();
                    temp.setPreferredSize(new Dimension(x, y));
                } else if (i == 1) {
                    int x = (int)(3.3 * guiipLabelSize.getWidth());
                    int y = (int)guiipLabelSize.getHeight();
                    temp.setPreferredSize(new Dimension(x, y));
                } else
                    temp.setPreferredSize(guiipLabelSize);
                this.add(temp);
                temp = (GUILabel)guiipChanList.getNext();
            }
        }
        revalidate();
        guiipTimer = new javax.swing.Timer(50, this);
//         System.err.println("Starting repaint timer");
        guiipTimer.start();
    }

/** Gets the Panel ID from the ItemPanel
  * @return The PanelID
  */
    public int getPanelID() {
        return this.guiipID;
    }
    
/** Gets the adjusted ID of the ItemPanel based upon the actual ID
  * @return The adjusted PanelID
  */
    public int getAdjPanelID() {
        switch(this.guiipID) {
            //All PanelID's associated with the left side panel
            case 1: case 2: case 3: case 4: case 5:
            case 6: case 7: case 8: case 9:
                return this.guiipID-1;
            case 11:
                return 9;
            //All ID's associated with the right side panel
            case 10:
                return 0;
            case 12: case 13: case 14: case 15: case 16:
                return this.guiipID-11;
            default:
                return SortedList.NO_COMPARE;
        }
    }

/** Gets the ChannelList from the given panel,
  * used for updating the ChannelList
  * @return The stored ChannelList
  */
    public ChannelList getChannelList() {
        return this.guiipChanList;
    }
    
/** Redefines the channel list and rebuilds the Panel
  * @param cl ChannelList to put inside
  */
    @Deprecated
    public void setChannelListAndRepaint(ChannelList cl) {
        this.guiipChanList = cl;
        handleConstruction();
    }

/** Used to set the current border to the specified color. Multi-purpose,
  * indended to display whether this channel is currently being listened to.
  * @param inColor Color to set the current outline of this GUIItemPanel
  */
    public void setOutlineTo(Color inColor) {
        this.setBorder(BorderFactory.createLineBorder(inColor));
    }

/** Gets the GUILabel from a given X and Y position on the ItemPanel
  * @param x X coordinate to search
  * @param y Y coordinate to search
  * @return The given GUILabel
  */
    public GUILabel getLabelFromPos(int x, int y) {
        Component c = this.getComponentAt(x,y);
        if (c instanceof GUILabel)
            return (GUILabel)c;
        else
            return null;
    }
    
/** Gets the GUILabel ID from a given X and Y position on the ItemPanel
  * @param x X coordinate to search
  * @param y Y coordinate to search
  * @return The given GUILabel's ID
  */
    public int getLabelIDFromPos(int x, int y) {
        Component c = this.getComponentAt(x,y);
        if (c instanceof GUILabel) {
            GUILabel temp = (GUILabel)c;
            return temp.getLabelID();
        } else
            return -1;
    }

/** Used for sorting GUIItemPanels
  * @param e Object being compared,
  * will have problems if not another GUIItemPanel
  * @return Integer used for sorting
  */
    public int compareTo(Object e) {
        if (e == null || !(e instanceof GUIItemPanel))
            return SortedList.NO_COMPARE;
        else {
            GUIItemPanel guiip = (GUIItemPanel)e;
            if (this.guiipID < guiip.guiipID)
                return -1;
            else if (this.guiipID > guiip.guiipID)
                return 1;
            else
                return 0;
        }
    }

/** Used to determine if this GUIItemPanel equals the other panel
  * @param otherPanel Other GUIItemPanel being compared
  * @return True if equal
  */
    public boolean equals(GUIItemPanel otherPanel) {
        return (this.guiipChanList.equals(otherPanel.guiipChanList)
             && this.guiipID == otherPanel.guiipID);
    }
    
/** Returns a string representation of this Panel
  * @return String representation of the ItemPanel
  */
    public String toString() {
        return "Panel #" + this.guiipID;
    }
}