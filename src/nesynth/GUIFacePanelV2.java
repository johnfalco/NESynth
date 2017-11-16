/** Custom JPanel used to hold all the information about the KeyPositions
  * and inteactions with them
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       GUIDisplay, KeyPositionList, KeyPosition
  * @included      GUINoteListener, LocationStore
  */

package nesynth;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

public class GUIFacePanelV2 extends SynthPanelUI implements ActionListener {
 
/** Constant to represent the Highlight ID */
    private static final int HIGHLIGHT = 11;
/** Constant to represent the De-Highlight ID */
    private static final int DEHILIGHT = 0;

/** Constant representing the X coordinate */
    private static final int X = 0;
/** Constant representing the Y coordinate */
    private static final int Y = 1;

/** Swing Timer used to accomidate passive painting in Swing */
    private javax.swing.Timer updateTimer;

/** Stores the panelID used for sorting all SynthPanelUI's */
    private int guifpPanelID;

/** Stores the Frame user interface that contains this Panel */
    protected SynthFrameUI guifpParent;

/** The listener for the FacePanel */
    private static GUINoteListener guifpListen;
    
/** Stores the information used when passively repainting the panel */
    private ArrayList<LocationStore> guifpPaintList = new ArrayList<>();

/** Constructs the GUIFacePanel with the given KeyIDList
  * @param panelID ID of the panel being constructed, used for sorting
  * @param parent The parent component constructing the GUIFacePanel
  */
    public GUIFacePanelV2(int panelID, SynthFrameUI parent) {
        super();
        updateTimer = new javax.swing.Timer(50, this);
        updateTimer.start();
        guifpListen = new GUINoteListener();
        changeChannelGroupID(DEHILIGHT);
        this.guifpPanelID = panelID;
        this.guifpParent = parent;
        this.addMouseListener(guifpListen);
        this.addKeyListener(guifpListen);
        this.setFocusable(true);
        this.paintMusic();
    }

/** Used to update the graphics of the panel with the Swing Timer
  * @param e ActionEvent triggered by the swing timer
  */
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

/** Paints the component as per the javadocs for JPanel
  * @param g Graphics to paint
  */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(LocationStore ls : guifpPaintList) {
            g.drawImage(ls.getImage(), ls.getXPos(), ls.getYPos(), this);
        }
    }

/** Gets the PanelID for this Panel
  * @return The PanelID
  */
    public int getPanelID() {
        return this.guifpPanelID;
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

/** Used to update the display of this panel
  * @param reset Value to determine how to update the display by specific
  * implementation. A value of true on this panel will reset all contents.
  */
    public void updateDisplay(boolean reset) {
        if (reset == true) {
            guifpPaintList.clear();
            paintMusic();
            System.err.println("FacePanel cleared");
        }
    }

/** Changes the groupID of the listener for setting purposes
  * @param inID ID to set to the listener
  */
    public void changeChannelGroupID(int inID) {
        if (inID != 15 && inID != 9) {
            guifpListen.setGroupID(inID);
        } else {
            int auxGroup = (inID == 15) ? 2 : 1;
            guifpParent.getKeyPositionList().reset();
            KeyPosition temp =
                (KeyPosition)guifpParent.getKeyPositionList().getNext();

            do {
                if (temp.getKeyType() == KeyConstants.AUX) {
                    paintKey(temp, temp.getGroupImageByID(auxGroup));
                } 
                temp = (KeyPosition)guifpParent.getKeyPositionList().getNext();
            } while (temp != null);
        }
    }
    
/** Used to toggle binding mode of the listener
  */
    public void toggleChannelBindingMode() {
        guifpListen.toggleBinding();
    }

/** Returns the Synth associated with the class
  * @return The associated Synth
  */
    public AbstractSynth getSynth() {
        return guifpParent.getSynth();
    }

/** Paints the base image to the panel
  */
    public void paintMusic() {
        System.err.println("PAINTMUSIC CALLED");
        ImageIcon imageIcon = 
            new ImageIcon(KeyConstants.getBankString('B')+"keyboardBase.png");
        
        Image tmpImage = imageIcon.getImage();
    
        BufferedImage painting = new BufferedImage(imageIcon.getIconWidth(),
                    imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        painting.getGraphics().drawImage(tmpImage, 0, 0, null);
        tmpImage.flush();


        Graphics2D g2d = painting.createGraphics();
        g2d.drawImage(painting, 0, 0, painting.getWidth(), 
                                      painting.getHeight(), null);
        guifpParent.getKeyPositionList().reset();
        KeyPosition temp =
            (KeyPosition)guifpParent.getKeyPositionList().getNext();
        int seq = 1;
        BufferedImage paint = null;
        do {
            if (temp.getKeyType() == KeyConstants.AUX) {
                paint = temp.getGroupImageByID(1);
            } else if (temp.getKeyType() == KeyConstants.SEQ) {
                paint = temp.getGroupImageByID(seq);
                seq++;
            } else if (temp.getSynthType() instanceof Note
                    && temp.getKeyType() != KeyConstants.PRC) {
                paint = temp.getGroupImageByID(1);
            } else {   
                paint = temp.getImageByHighlight(false);
            }
                
            paint = addText(paint, temp.convertKeyText());  
            g2d.drawImage(paint, temp.getPaintingXCoord(), 
                        temp.getPaintingYCoord(), paint.getWidth(), 
                                           paint.getHeight(), null);
            temp = (KeyPosition)guifpParent.getKeyPositionList().getNext();
        } while (temp != null);
        
        g2d.dispose();
        guifpPaintList.add(new LocationStore(painting, 0, 0));
        
    }
    
/** For painting the keys
  * @param kp KeyPosition to paint from on the keyboard
  * @param highlight Determines whether to get the highlight or not of an image
  * @param groupID The group being painted onto the key
  */
    public void paintKey(KeyPosition kp, boolean highlight, int groupID) {
        BufferedImage temp = null;
        if (groupID == 0)
            temp = kp.getImageByHighlight(highlight);
        else  if (groupID > 0 && groupID <= 11  && groupID != 10) {
            temp = kp.getGroupImageByID(groupID);
        } else {
            System.err.println("Cannot group this value!");
            return;
        }

        paintKey(kp, temp);        
        repaint();
    }

    private void paintKey(KeyPosition kp, BufferedImage temp) {
        int[] paintCoord = kp.getPaintingCoordinates();
        //ALWAYS ADDING TEXT
        boolean addTheText = true; 
        System.err.println("ALWAYS ADDING TEXT");
        //ALWAYS ADDING TEXT
        BufferedImage addText = (addTheText) //(kp.displaysText() == true) 
            ? addText(temp, kp.convertKeyText())
            : temp;
        
        guifpPaintList.add(new LocationStore(addText, paintCoord[X],
                                                   paintCoord[Y]));
    }




/** Class for listening to the side panel to activate interactions with
  * individual channels */
    protected class GUINoteListener extends MouseAdapter
                                            implements KeyListener {
    /** Holds the last KeyPosition stored in the listener */
        private volatile KeyPosition lastKey;
    /** Holds the information about whether binding mode is enabled or not */
        protected volatile boolean binding;
    /** Holds the "active" KeyPositions for use when binding */
        protected volatile UnsortedList activeKeys;
    /** Holds the information on the groupID currently set in the listener */
        protected volatile int groupID;
    /** Determines whether binding mode can be turned off or not, which
      * means that a key was not bound after being swapped with another key */
        protected volatile boolean bindingComplete;
        
    /** Holds the information on the last KeyCode to be pressed for binding */
        private volatile int lastKeyCode;;
        
    /** Holds the initial coordinates of when the mouse was pressed */
        private volatile int[] pCoord = new int[2];
    /** Holds the initial coordinates of when the mouse was released */
        private volatile int[] rCoord = new int[2];
        
    /** Used to determine if a key is being rebound */
        private final int NOACTION = -4;
    /** Used to determine if reassigning value played by a key */
        private final int REVALUE = -3;
    /** Used to determine if rebinding a key */
        private final int REBIND = -2;
    /** Used to determine if regrouping a key */
        private final int REGROUP = -1;
    
    /** Constructs the Listener
      */
        public GUINoteListener() {
            super();
            this.groupID = 0;
            this.lastKeyCode = KeyEvent.VK_ESCAPE;
            this.binding = false;
//             this.lastKey = guifpParent.getKeyPositionList().getNext();
//             guifpParent.getKeyPositionList().reset();
//             this.activeKeys = 
//                 guifpParent.getKeyPositionList().getActiveKeyPositions();
            this.bindingComplete = true;
        }    
        
    /** Handles the pressing of the mouse key
      * @param e MouseEvent generated
      */
        public void mousePressed(MouseEvent e) {
            if (!binding) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    KeyPosition revalue =
                        handleFinding(e.getX(), e.getY());
                    if (revalue == null) {
                        System.err.println("Problem in handleBinding");
                        return;
                    } else {
                        if (revalue.getSynthType() instanceof Note) {
                            String answer =
                                (String)JOptionPane.showInputDialog(null,
                                "Enter the new value to play at this key.",
                                "Enter Value", JOptionPane.PLAIN_MESSAGE);
                            try {
                                int newVal = Integer.parseInt(answer);
                                lastKey = handleNextKey(revalue, REVALUE, newVal);
                            } catch (NumberFormatException nfe) {
                                lastKey = handleNextKey(revalue, NOACTION, 0);
                            }
                        }
                    }
                } else {
//                     pCoord[X] = e.getX();
//                     pCoord[Y] = e.getY();
                }
            }
            System.err.println("PREPPING RELEASED");
        }
   
    /** Handles the releasing of the mouse key
      * @param e MouseEvent generated
      */
        public void mouseReleased(MouseEvent e){
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (binding)
                    handleBinding(e);
                else
                    handleGrouping(e);
            } else
                System.err.println("nope");
        }
   
    /** Sets the groupID to the given parameter
      * @param id ID to be set
      */
        private void setGroupID(int id) {
            groupID = id;
        }

    /** Handles finding of a given KeyPosition based on the X and Y parameters
      * @param x X coordinate
      * @param y Y coordinate
      * @return The found KeyPosition if found, otherwise returns a null
      * representing no found KeyPosition
      */
        private KeyPosition handleFinding(int x, int y) {
            boolean foundLocation = false;
            guifpParent.getKeyPositionList().reset();
            KeyPosition temp = guifpParent.getKeyPositionList().getNext();
            do {
                foundLocation = temp.isCoordInKey(x, y);
                if (foundLocation)
                    return temp;
                else
                    temp = guifpParent.getKeyPositionList().getNext();
            } while (temp != null);
            System.err.println("NOT FOUND");
            return null;
        
        }
        
        private KeyPosition handleFinding(int keyCode) {
            boolean foundLocation = false;
            guifpParent.getKeyPositionList().reset();
            KeyPosition temp = guifpParent.getKeyPositionList().getNext();
            do {
                if (temp.getKeyCode() == keyCode)
                    return temp;
                else
                    temp = guifpParent.getKeyPositionList().getNext();
            } while (temp != null);
            System.err.println("NOT FOUND");
            return null; 
        }
        
    /** Handles the switching of a KeyPosition when clicking in binding mode
      * @param e MouseEvent being handled to accomplish this
      * @return True if the KeyPosition was successfully switched
      */
        private boolean handleBinding(MouseEvent e) {
//             System.err.println("Devise a way to handle this");
// //             KeyPosition keyPos = handleFinding(e.getX(), e.getY());
// //             if (keyPos == null) {
// //                 System.err.println("Problem in handleBinding");
// //                 return false;
// //             } else {
// //                 paintKey(lastKey, false, 0);
// //                 paintKey(keyPos, true, 0);
// //                 lastKey = new KeyPosition(keyPos);
// //                 return true;
// //             }    
            return false;
        }
        
    /** Toggles binding mode on or off
      */
        private void toggleBinding() {
//             //if binding is not complete
//             if (binding && !bindingComplete) {
//                 JOptionsPane.showMessageDialog(null, "Cannot exit binding "
//                   + "mode, there are orphaned actions, finish binding!",
//                     "ERROR", JOptionPane.ERROR_MESSAGE, null);                            
//                 return;
//             } else 
//                 binding = !binding;
// 
//             if (binding) {
//                 activeKP = 
//                     guifpParent.getKeyPositionList().getActiveKeyPositions();
//                 lastKey = (KeyPosition)activeKP.getNext();
//                 paintKey(lastKey, true, 0);
//             else {
//                 handlePaintGrouping(lastKey);
//             //ADD CODE TO SWITCH HIGHLIGHT KEY OFF WHEN NOT BINDING
//             //ALSO ADD CODE TO TURN ON HIGHLIGHT OF KEY WHEN BINDING
//             guifpParent.getSynth().toggleReading();
//             System.err.println("Binding = " + binding);
        }
           
        private void handlePaintGrouping(KeyPosition lastKey) {
            int groupVal = 0;
            if (lastKey.getKeyType() == KeyConstants.AUX) {
                groupVal = 1;
            } else if (lastKey.getKeyType() == KeyConstants.SEQ) {
                Sequence tempSeq = (Sequence)lastKey.getSynthType();
                groupVal = tempSeq.getSeqChan()-10;
            } else if (lastKey.getSynthType() instanceof Note
                    && lastKey.getKeyType() != KeyConstants.PRC) {
                groupVal = 1;
            } else {   
                groupVal = 0;
            }

            paintKey(lastKey, false, groupVal);
        }
        
    /** Handles the grouping of KeyPositions when clicking in non-binding mode
      * @param e MouseEvent being handled to accomplish this
      */
        private void handleGrouping(MouseEvent e) {
//             rCoord[X] = e.getX();
//             rCoord[Y] = e.getY();
            KeyPosition singleKey = handleFinding(e.getX(), e.getY());
//             KeyPosition initialKey = handleFinding(pCoord[X], pCoord[Y]);
//             KeyPosition finalKey = handleFinding(rCoord[X], rCoord[Y]);

            if (singleKey == null) {
//             if (initialKey  == null || finalKey == null) {
                System.err.println("Problem in handleGrouping");
                return;
            } else {
//                 KeyPosition first = null, last = null;
//                 int ordering = initialKey.compareTo(finalKey);
//                 if (ordering > 0) {   //if the ID of the final key is
//                     first = finalKey; //less than the id of the initial key
//                     last = initialKey;//order accordingly
//                 } else { //Otherwise order normally
//                     first = initialKey;
//                     last = finalKey;
//                 }
                KeyPosition temp = (KeyPosition)
                    guifpParent.getKeyPositionList().getAndSetPos(singleKey);
//                 KeyPosition temp = (KeyPosition)
//                     guifpParent.getKeyPositionList().getAndSetPos(first);
                int kind = temp.getKeyKind();
                int chanID = (groupID == 10) ? 10 : groupID-1;
                
                if (kind == KeyConstants.NOTEACT) {
//                 while(temp.compareTo(last) <= 0
//                    && kind == KeyConstants.NOTEACT) {
                    paintKey(temp, false, groupID);
                    temp = handleNextKey(temp, REGROUP, chanID);
//                     if (temp != null)
//                         kind = temp.getKeyKind();
//                     else
//                         kind = 0; //will exit the loop if a null is gotten
                }
            }
        }
    
    /** Handles interactions with KeyPositions and actions altering their
      * state within the Synth
      * @param current The current KeyPosition that is being altered
      * @param type The type of action being performed on the KeyPosition
      * @param newVal The new value being passsed to the given KeyPosition
      * @return The next KeyPosition on the KeyPositionList. If a bad action
      * was received, the current KeyPosition will be returned and no action
      * will take place.
      */
        private KeyPosition handleNextKey(KeyPosition current,
                                            int type, int newVal) {
            KeyPosition positioning =
                guifpParent.getKeyPositionList().getAndSetPos(current);
            if (positioning != null) {
                if (type == NOACTION) {
                    System.err.println("No Action!");
                    return guifpParent.getKeyPositionList().getNext();
                } else if (type == REBIND) {
                    guifpParent.getSynth()
                        .unloadAction(positioning.getKeyCode());
                    positioning.setKeyCode(newVal);
                    guifpParent.getKeyPositionList().replace(positioning);
                    guifpParent.loadAction(positioning.getKeyCode(), 
                                                         positioning);
                    lastKey = positioning;
                    return guifpParent.getKeyPositionList().getNext();
                } else if (type == REVALUE) {
                    if (positioning.getSynthType() instanceof Note) {
                        Note revalue = (Note)positioning.getSynthType();
                        revalue.setInt(newVal);
                        positioning.setSynthType(revalue);
                        guifpParent.loadAction(positioning.getKeyCode(),
                                                            positioning);
                        guifpParent.getKeyPositionList().replace(positioning);
                        lastKey = positioning;
                        return guifpParent.getKeyPositionList().getNext();
                    } else { 
                        System.err.println("Cannot revalue this KeyPosition!");
                        return positioning;   
                    }
                } else if (type == REGROUP) {
                    if (positioning.getSynthType() instanceof Note) {
                        Note regroup = (Note)positioning.getSynthType();
                        regroup.setChan(newVal);
                        positioning.setSynthType(regroup);
                        guifpParent.loadAction(positioning.getKeyCode(),
                                                            positioning);
                        return guifpParent.getKeyPositionList().getNext();
//                     int[] values = positioning.getDefaultValuesByKeyID();
//                     values[3] = newVal;
//                     guifpParent.loadAction(positioning.getKeyCode(), 
//                                             positioning, values);
//                     lastKey = positioning;
//                     return guifpParent.getKeyPositionList().getNext();
                    } else { 
                        System.err.println("Cannot revalue this KeyPosition!");
                        return positioning;   
                    }
                } else {
                    System.err.println("BAD HANDLENEXTKEY");
                    return current;
                }
            } else {
                System.err.println("BAD HANDLENEXTKEY");
                return current;
            }
        }
/*   
        private volatile KeyPosition lastKey;
        protected volatile boolean binding;
        protected volatile UnsortedList activeKeys;
        protected volatile int groupID;
*/
    /** Handles KeyEvents, used for binding keys when in binding mode
      * @param e KeyEvent being handled
      */
        public void keyPressed(KeyEvent e) {
//             if (binding == true) {
//                 int keyCode = e.getKeyCode();
//                 //Key to bind previous key to
//                 KeyPosition found = handleFinding(keyCode);
//                 if (found == null) {
//                     JOptionsPane.showMessageDialog(null, "This key cannot be 
//                         "bound in this version of the program", "ERROR",
//                         JOptionPane.ERROR_MESSAGE, null);
//                 //If bound key is the exact same as before 
//                 } else if (lastKey.equals(found)) {
//                     System.err.println("Binding key the same");
//                     lastKey = activeKeys.getNext();
//                     activeKeys.remove(found);
//                 } else {
//                     System.err.println("Binding key different");
//                     //get synth type from the found key
//                     SynthType swaping = found.getSynthType();
//                     //If a null is not being put into the old key,
//                     //binding incomplete (orphaned action)
//                     if (swaping != null)
//                         bindingComplete = false;
//                     //dehighlight the old key
//                     handlePaintGrouping(lastKey);
//                     //unload the old key action
//                     guifpParent.getSynth()
//                         .unloadAction(lastKey.getKeyCode());
//                     //unload the action from the found key
//                     guifpParent.getSynth()
//                         .unloadAction(found.getKeyCode());
//                     //load the synth type into the new key
//                     found.setSynthType(lastKey.getSynthType());
//                     //Set the swapped synth type into the last key
//                     lastKey.setSynthType(swaping);
//                     //replace both on the list
//                     guifpParent.getKeyPositionList().replace(lastKey);
//                     guifpParent.getKeyPositionList().replace(found);
//                     //load both actions into the synthesizer
// 
//                     guifpParent.loadAction(lastKey.getKeyCode(), 
//                                                          lastKey);
//                     lastKey = positioning;                    
//                     
// //                     paintKey(lastKey, true, 0);
// /*
// process
// 
// first key in list is highlighted
// 
// new key pressed
// 
// synthtypes are swapped
// 
// if old KeyPosition's synthtype is not null
// 
// added to the end of the list
// 
// if is null
// 
// KeyPosition is removed from the active list
// */                     
// //                 //If a second escape is received, end binding mode
// //                 if (action == lastKeyCode)
// //                     toggleBinding();
// //                 else if (action == KeyEvent.VK_ESCAPE
// //                       && action != lastKeyCode) {
// //                     paintKey(lastKey, false, 0);
// //                     lastKey = handleNextKey(lastKey, NOACTION, e.getKeyCode());
// //                     paintKey(lastKey, true, 0);
// //                 } else {
// //                     paintKey(lastKey, false, 0);
// //                     lastKey = handleNextKey(lastKey, REBIND, e.getKeyCode());
// //                     paintKey(lastKey, true, 0);
// //                 }
//                 
//             }
        }
       
        public void keyReleased(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
    }
/** Basic object class to hold information used
  * when painting objects to the main panel
  */
    protected class LocationStore {
    /** Stores the Image itself */
        private Image lsImage;
    /** Stores the X Position of the Image */
        private int lsXPos;
    /** Stores the Y Position of the Image */
        private int lsYPos;

    /** Constructs the Object
      * @param image Image being stored
      * @param x X Position of the image being stored
      * @param y Y Position of the image being stored
      */
        protected LocationStore(Image image, int x, int y) {
            this.lsImage = image;
            this.lsXPos = x;
            this.lsYPos = y;
        }
    /** Gets the image
      * @return The image
      */
        protected Image getImage() {
            return this.lsImage;
        }

    /** Gets the X Position
      * @return The X Position
      */
        protected int getXPos() {
            return this.lsXPos;
        }

    /** Gets the Y Position
      * @return The Y Position
      */
        protected int getYPos() {
            return this.lsYPos;
        }
    }
}