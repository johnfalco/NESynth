/** Object class used to hold any important information regarding the display
  * and values of a given key used to interact with the Synth class
  *
  * @Version 1.0
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       KeyConstants, SynthConstants, SortedList
  * @included      None
  */

package nesynth;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import static nesynth.KeyConstants.*;

public class KeyPosition implements Comparable, Serializable {
/** Character to represent the bank to which the KeyPosition is loading images
  * from in the KeyConstants class */
    private char kpBank;
/** Integer to represent the ID of the KeyPosition */
    private int kpID;
/** Integer to represent the key type of the KeyPosition */
    private int kpType;
/** Integer to represent the x position of the KeyPosition's icon */
    private int kpXPos;
/** Integer to represent the y of the KeyPosition's icon */
    private int kpYPos;
/** Rectangle object used for storing bounds of the KeyPosition for
  * determining if a given coordinate pair is within the KeyPosition */
    private Rectangle kpRectA;
/** Rectangle object used for storing bounds of the KeyPosition for
  * determining if a given coordinate pair is within the KeyPosition */
    private Rectangle kpRectB;
/** Integer representing the KeyCode stored for use with the Synth */
    private int kpKeyCode;
/** Image representing the de-highlighted variant of the KeyPosition */
    private transient BufferedImage kpDImage;
/** Image representing the highlighted variant of the KeyPosition */
    private transient BufferedImage kpHImage;
/** SynthType holding all the necessary information about the data this
  * KeyPosition is storing that is used by a subclass of AbstractSynth */
    private SynthType kpSynthType;
/** Boolean storing whether the images displayed contain text on them or
  * are left uncaptioned. The text displayed is a conversion of the keyCode */
    private boolean kpDispText;


/** Default constructor, constructs the leftmost basic keyboard key
  */
    public KeyPosition() {
        this('A', 1, 0, 10, 188, new Rectangle(10, 188, 32, 51), 
             new Rectangle(10, 239, 40, 51), DEFKEYCODE,
             new Note(25, 105, Integer.MAX_VALUE, 0,
               SynthConstants.LEFTOCTAVE), DEFBS[0], DEFBS[1]);
        
    }

/** Basic constructor, constructs the leftmost basic keyboard key from the 
  * specified bank
  * @param keyBank Character represeting the bank to fetch images from
  */
    public KeyPosition(char keyBank) {
        this('A', 1, 0, 10, 188, new Rectangle(10, 188, 32, 51), 
             new Rectangle(10, 239, 40, 51), DEFKEYCODE,
             new Note(25, 105, Integer.MAX_VALUE, 0,
               SynthConstants.LEFTOCTAVE), 
               getBankString('A') + DEFBS[0], 
               getBankString('A') + DEFBS[1]);
        
    }


/** Basic constructor, constructs the KeyPosition without any special
  * rectangles for location
  * @param keyBank Character represeting the bank to fetch images from
  * @param keyID Integer to represent the ID of the KeyPosition
  * @param keyType Integer to represent the key type of the KeyPosition
  * @param x Integer to represent the x position of the KeyPosition's icon
  * @param y Integer to represent the y of the KeyPosition's icon
  * @param width Integer representing the width of the rectangle to build
  * @param height Interger representing the height of the rectangle to build
  * @param keyCode Integer representing the assigned KeyCode
  */
    public KeyPosition(char keyBank, int keyID,  int keyType, int x, int y,
                            int width, int height, int keyCode,
                                           SynthType synthType) {
                                           
        this(keyBank, keyID, keyType, x, y, new Rectangle(x, y, width, height),
            new Rectangle(x, y, width, height), keyCode, synthType, 
            generateString(false, keyBank, keyType, synthType),
            generateString(true, keyBank, keyType, synthType));
    }

/** Basic constructor, constructs the KeyPosition without any special
  * rectangles for location as well as custom strings for obtaining icons
  * @param keyBank Character represeting the bank to fetch images from
  * @param keyID Integer to represent the ID of the KeyPosition
  * @param keyType Integer to represent the key type of the KeyPosition
  * @param x Integer to represent the x position of the KeyPosition's icon
  * @param y Integer to represent the y of the KeyPosition's icon
  * @param width Integer representing the width of the rectangle to build
  * @param height Interger representing the height of the rectangle to build
  * @param keyCode Integer representing the assigned KeyCode
  * @param deHilight String identifier for the image to load
  * @param highlight String identifier for the image to load
  */
    public KeyPosition(char keyBank, int keyID,  int keyType, int x, int y,
                           int width, int height, int keyCode,
                           SynthType synthType, String deHilight, 
                                                String highlight) {
                                                
        this(keyBank, keyID, keyType, x, y, new Rectangle(x, y, width, height),
            new Rectangle(x, y, width, height), keyCode, synthType,
                                                deHilight, highlight);

    }

/** Constructs the KeyPosition with one rectangle storing location
  * @param keyBank Character represeting the bank to fetch images from
  * @param keyID Integer to represent the ID of the KeyPosition
  * @param keyType Integer to represent the key type of the KeyPosition
  * @param x Integer to represent the x position of the KeyPosition's icon
  * @param y Integer to represent the y of the KeyPosition's icon
  * @param rectangle Rectangle to load that stores the location of the KeyIcon
  * @param keyCode Integer representing the assigned KeyCode
  */
    public KeyPosition(char keyBank, int keyID,  int keyType, int x, int y,
                    Rectangle rectangle, int keyCode, SynthType synthType) {

        this(keyBank, keyID, keyType, x, y, rectangle, rectangle, keyCode,
                  synthType, generateString(false, keyBank, keyType, synthType),
                             generateString(true, keyBank, keyType, synthType));
    }

/** Constructs the KeyPosition with one rectangle storing location
  * as well as custom strings for obtaining icons
  * @param keyBank Character represeting the bank to fetch images from
  * @param keyID Integer to represent the ID of the KeyPosition
  * @param keyType Integer to represent the key type of the KeyPosition
  * @param x Integer to represent the x position of the KeyPosition's icon
  * @param y Integer to represent the y of the KeyPosition's icon
  * @param rectangle Rectangle to load that stores the location of the KeyIcon
  * @param keyCode Integer representing the assigned KeyCode
  * @param deHilight String identifier for the image to load
  * @param highlight String identifier for the image to load
  */
    public KeyPosition(char keyBank, int keyID,  int keyType, int x, int y,
                    Rectangle rectangle, int keyCode, SynthType synthType,
                    String deHilight, String highlight) {
        
        this(keyBank, keyID, keyType, x, y, rectangle, rectangle, keyCode, 
                                           synthType, deHilight, highlight);
    }

/** Constructs the KeyPosition with two rectangles storing location
  * @param keyBank Character represeting the bank to fetch images from
  * @param keyID Integer to represent the ID of the KeyPosition
  * @param keyType Integer to represent the key type of the KeyPosition
  * @param x Integer to represent the x position of the KeyPosition's icon
  * @param y Integer to represent the y of the KeyPosition's icon
  * @param rectA Rectangle #1 to load that stores the location of the KeyIcon
  * @param rectB Rectangle #2 to load that stores the location of the KeyIcon
  * @param keyCode Integer representing the assigned KeyCode
  */
    public KeyPosition(char keyBank, int keyID,  int keyType, int x, int y,
                    Rectangle rectA, Rectangle rectB, int keyCode, SynthType synthType) {

        this(keyBank, keyID, keyType, x, y, rectA, rectB, keyCode, synthType,
                     generateString(false, keyBank, keyType, synthType),
                     generateString(true, keyBank, keyType, synthType));
    }

/** Constructs the KeyPosition with two rectangles storing location
  * as well as custom strings for obtaining icons
  * @param keyBank Character represeting the bank to fetch images from
  * @param keyID Integer to represent the ID of the KeyPosition
  * @param keyType Integer to represent the key type of the KeyPosition
  * @param x Integer to represent the x position of the KeyPosition's icon
  * @param y Integer to represent the y of the KeyPosition's icon
  * @param rectA Rectangle #1 to load that stores the location of the KeyIcon
  * @param rectB Rectangle #2 to load that stores the location of the KeyIcon
  * @param keyCode Integer representing the assigned KeyCode
  * @param deHilight String identifier for the image to load
  * @param highlight String identifier for the image to load
  */
    public KeyPosition(char keyBank, int keyID,  int keyType, int x, int y,
                    Rectangle rectA, Rectangle rectB, int keyCode, 
                    SynthType synthType, String deHilight, String highlight) {

        this.kpDispText = true;
        this.kpBank = keyBank;
        this.kpID = keyID;
        this.kpType = keyType;
        this.kpXPos = x;
        this.kpYPos = y;
        this.kpRectA = rectA;
        this.kpRectB = rectB;
        this.kpKeyCode = keyCode;
        this.kpSynthType = synthType;
        this.kpDImage = handleString(deHilight);
        this.kpHImage = handleString(highlight);
    }

/** Constructs a new KeyPosition as a copy of the parameter KeyPosition
  * @param old KeyPosition to construct from
  */
    public KeyPosition(KeyPosition old) {
        this.kpBank = old.kpBank;
        this.kpID = old.kpID;
        this.kpType = old.kpType;
        this.kpXPos = old.kpXPos;
        this.kpYPos = old.kpYPos;
        this.kpRectA = old.kpRectA;
        this.kpRectB = old.kpRectB;
        this.kpKeyCode = old.kpKeyCode;
        this.kpSynthType = old.kpSynthType;
        this.kpDImage = old.kpDImage;
        this.kpHImage = old.kpHImage;
    }

/** Returns the ID in relation to being read as an index of an array
  * @return Integer representing the ID as index
  */
    public int getMainKey() {
        return (this.kpID - 1);
    }

/** Returns the ID of this KeyPosition
  * @return Integer representing ID
  */
    public int getKeyID() {
        return this.kpID;
    }

/** Returns the type of this KeyPosition
  * @return Integer representing type
  */
    public int getKeyType() {
        return this.kpType;
    }

/** Returns an integer to represent the kind which is used for determining 
  * actions in the SynthUI classes
  * @return Integer representing the kind
  */
    public int getKeyKind() {
        if (this.kpSynthType == null)
            return 0;
        else {
            if (this.kpSynthType instanceof Note &&
               !(this.kpType == PRC || this.kpType == AUX))
                return NOTEACT;
            else if (this.kpSynthType instanceof Note &&
                     (this.kpType == PRC || this.kpType == AUX))
                return PERCACT;
            else if (this.kpSynthType instanceof SynthSys)
                return SYNSACT;
            else if (this.kpSynthType instanceof Sequence)
                return SEQUACT;
            else
                return 0;
        }
    }

    public void setDisplayText(boolean display) {
        this.kpDispText = display;
    }

/** Returns the X and Y coordinate for image painting as an int array
  * @return Integer array for X and Y coordinates
  */
    public int[] getPaintingCoordinates() {
        int[] returnArray = {this.kpXPos, this.kpYPos};
        return returnArray;
    }
    
/** Returns the X coordinate for image painting
  * @return Integer representing the X coordinate
  */
    public int getPaintingXCoord() {
        return this.kpXPos;
    }

/** Returns the Y coordinate for image painting
  * @return Integer representing the Y coordinate
  */
    public int getPaintingYCoord() {
        return this.kpYPos;
    }

/** Returns the "octave" for the side this KeyPosition represents
  * @return The constant representing the octave of the KeyPosition
  */
    private int getKeyPosOctave() {
        if (this.kpSynthType instanceof Note) {
            Note temp = (Note)this.kpSynthType;
            return temp.getOctS();
        } else
            return SynthConstants.NULLOCTAVE;
    }
    
    public SynthType getSynthType() {
        return this.kpSynthType;
    }
    
    public void setSynthType(SynthType synthType) {
        this.kpSynthType = synthType;
    }

/** Sets the default KeyCode of this KeyPosition to the specified value
  * @param keyCode Integer representing the new KeyCode to be set
  */
    public void setKeyCode(int keyCode) {
        this.kpKeyCode = keyCode;
        reloadImages(true);
    }

/** Gets the KeyCode for the KeyPosition
  * @return The KeyCode assigned to this KeyPosition by default
  */
    public int getKeyCode() {
        return this.kpKeyCode;
    }

/** Determines whether a specific coordinate is within the given KeyPosition
  * @param x X coordinate to check
  * @param y Y coordinate to check
  * @return True if the coordinate is in the KeyPosition
  */
    public boolean isCoordInKey(int x, int y) {
        //return true if either rectangle contains the coordinate
        return (kpRectA.contains(x,y) || kpRectB.contains(x,y));
    }
   
    private static String generateString(boolean highlight, char bank,
                                           int keyType, SynthType synthType) {
        if (synthType instanceof Note
                && !(keyType == PRC || keyType == AUX)) {
            int typeID = (!highlight) ? 0
                                      : 11;
            return PKBS[keyType] + KBS[keyType][typeID];
        } else if (synthType instanceof Note && keyType == PRC) {
            //Determine whether a highlight is being gotten
            int typeID = (!highlight) ? 0 : 1;
            //Offset type by 4 when handling percussion KeyPositions
            return PPERCBS + PERCBS[typeID];
        } else if (synthType instanceof Note && keyType == AUX) {
            int typeID = (!highlight) ? 0 : 3;
            return PAXLBS + AXLBS[typeID];
        } else if (synthType instanceof SynthSys) {
            //Determine whether a highlight is being gotten
            int typeID = (!highlight) ? 0 : 1;
            return PSYSBS + SYSBS[typeID][keyType];
        } else if (synthType instanceof Sequence) {
            //Determine whether a highlight is being gotten
            int typeID = (!highlight) ? 0 : 5;
            return PSEQBS + SEQBS[typeID];
        } else
            return BADBS;
    }

/** Loads a bufferedImage based on a filename from a string with transparency
  * @param filename String representing the File to get from.
  * @return The specified BufferedImage from the string
  */
    private BufferedImage handleString(String filename) {
//         System.err.println("Loading " + filename);
        ImageIcon imageIcon =
            new ImageIcon(getBankString(this.kpBank) + filename);
        Image tmpImage = imageIcon.getImage();
    
        BufferedImage image = new BufferedImage(imageIcon.getIconWidth(),
                    imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(tmpImage, 0, 0, null);
        tmpImage.flush();

        BufferedImage scale = null; 
        
        if (this.kpSynthType instanceof ScaledSynthNull
         || this.kpSynthType instanceof ScaledNote
         || this.kpSynthType instanceof ScaledSynthSys
         || this.kpSynthType instanceof ScaledSequence) {
            int[] scaledSize = getPaintingSize();
            scale = scale(image, scaledSize[0], scaledSize[1]);
        } else
            scale = image;
    
//         if (this.kpDispText == true)
//             return addText(scale, convertKeyText(this.kpKeyCode));
//         else
        return scale;    
    }

    public boolean displaysText() {
        return this.kpDispText;
    }
    
    public void setDisplayingText(boolean display) {
        this.kpDispText = display;
    }
    
    public String convertKeyText() {
//        String returnStr = KeyEvent.getKeyText(this.kpKeyCode);
//        return "NP" + returnStr.substring(returnStr.length()-1);
        switch(this.kpKeyCode) {
            case KeyEvent.VK_UNDEFINED:
                return " ";
            case KeyEvent.VK_ESCAPE:
                return "Esc";
            case KeyEvent.VK_TAB:
                return "Tab";
            case KeyEvent.VK_CAPS_LOCK:
                return "Caps\nLock";
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_INSERT:
                return "NP0";
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_END:
                return "NP1";
            case KeyEvent.VK_NUMPAD2:  
            case KeyEvent.VK_DOWN:
                return "NP2";
            case KeyEvent.VK_NUMPAD3:  
            case KeyEvent.VK_PAGE_DOWN:
                return "NP3";
            case KeyEvent.VK_NUMPAD4:  
            case KeyEvent.VK_LEFT:
                return "NP4";
            case KeyEvent.VK_NUMPAD5:  
            case KeyEvent.VK_CLEAR:
                return "NP5";
            case KeyEvent.VK_NUMPAD6:  
            case KeyEvent.VK_RIGHT:
                return "NP6";
            case KeyEvent.VK_NUMPAD7:  
            case KeyEvent.VK_HOME:
                return "NP7";
            case KeyEvent.VK_NUMPAD8:  
            case KeyEvent.VK_UP:
                return "NP8";
            case KeyEvent.VK_NUMPAD9:  
            case KeyEvent.VK_PAGE_UP:
                return "NP9";
            case KeyEvent.VK_DIVIDE:
                return "NP/";
            case KeyEvent.VK_MULTIPLY:
                return "NP*";
//             case KeyEvent.VK_MINUS:
//                 return "NP-";
            case KeyEvent.VK_ADD:
                return "NP+";
            case KeyEvent.VK_DECIMAL:
            case KeyEvent.VK_DELETE:
                return "NP.";
            case KeyEvent.VK_OPEN_BRACKET:
                return "[";
            case KeyEvent.VK_CLOSE_BRACKET:
                return "]";
            case KeyEvent.VK_QUOTE:
                return "'";
            case KeyEvent.VK_SEMICOLON:
                return ";";
            case KeyEvent.VK_SLASH:
                return "/";
            case KeyEvent.VK_PERIOD:
                return ".";
            case KeyEvent.VK_COMMA:
                return ",";
            case KeyEvent.VK_SHIFT:
                return "Shift";
            case KeyEvent.VK_MINUS:
                return "-";
            case KeyEvent.VK_BACK_SLASH:
                return "\\";
            case KeyEvent.VK_BACK_SPACE:
                return "<-";
            case KeyEvent.VK_F1:
                return "F1";
            case KeyEvent.VK_F2:
                return "F2";
            case KeyEvent.VK_F3:
                return "F3";
            case KeyEvent.VK_F4:
                return "F4";
            case KeyEvent.VK_F5:
                return "F5";
            case KeyEvent.VK_F6:
                return "F6";
            case KeyEvent.VK_F7:
                return "F7";
            case KeyEvent.VK_F8:
                return "F8";
            case KeyEvent.VK_F9:
                return "F9";
            case KeyEvent.VK_F10:
                return "F10";
            case KeyEvent.VK_F11:
                return "F11";
            case KeyEvent.VK_F12:
                return "F12";
            case KeyEvent.VK_F13:
                return "F13";
            case KeyEvent.VK_F14:
                return "F14";
            case KeyEvent.VK_F15:
                return "F15";
            case KeyEvent.VK_F16:
                return "F16";
            case KeyEvent.VK_F17:
                return "F17";
            case KeyEvent.VK_F18:
                return "F18";
            case KeyEvent.VK_F19:
                return "F19";
            case KeyEvent.VK_F20:
                return "F20";
            case KeyEvent.VK_F21:
                return "F21";
            case KeyEvent.VK_F22:
                return "F22";
            case KeyEvent.VK_SPACE:
                return "Space Bar";
            case KeyEvent.VK_BACK_QUOTE:
                return "~";
            case KeyEvent.VK_CONTROL:
                return "Ctrl";
            case KeyEvent.VK_WINDOWS:
                return "" + (char)0x007F;
            case KeyEvent.VK_ALT:
                return "Alt";
            case KeyEvent.VK_ENTER:
            	return "Enter";
            default:
                return "" + (char)this.kpKeyCode;
        }
    }

    public int[] getPaintingSize() {
        Rectangle a = ((kpRectA.getX() == Math.min(kpRectA.getX(),
                                                   kpRectB.getX()))
                    || (kpRectA.getY() == Math.min(kpRectA.getY(),
                                                   kpRectB.getY())))
            ? kpRectA : kpRectB;
        Rectangle b = ((kpRectA.getX() == Math.max(kpRectA.getX(),
                                                   kpRectB.getX()))
                    && (kpRectA.getY() == Math.max(kpRectA.getY(),
                                                   kpRectB.getY())))
            ? kpRectA : kpRectB;
        if (a.getX() == b.getX() && a.getY() == b.getY())
            return new int[]{Math.max( 
                /* Width  */ (int)a.getWidth(),
                             (int)b.getWidth()),
                             Math.max(
                /* Height */ (int)a.getHeight(),
                             (int)b.getHeight())};
        else {
            int width = 0;
            //CASE 1: Width of A encompasses B's origin and width entirely 
            if ((
                  (a.getX() <= b.getX()) &&
                  (a.getX() + a.getWidth() > b.getX()) 
                )
             && (a.getX() + a.getWidth() >= b.getX() + b.getWidth())) {
                width = (int)(a.getWidth());
            //CASE 2: Width of A encompasses B's origin but not B's width
            } else if ((
                         (a.getX() <= b.getX()) &&
                         (a.getX() + a.getWidth() > b.getX()) 
                       )
                    && (a.getX() + a.getWidth() < b.getX() + b.getWidth())) {
                width = (int)((b.getX() - a.getX()) + b.getWidth());
            //CASE 3: Width of B encompasses A's origin and width entirely
            } else if ((
                         (a.getX() > b.getX()) &&
                         (a.getX() + a.getWidth() <= b.getX() + b.getWidth())
                       )
                      ) {
                width = (int)b.getWidth(); 
            //CASE 4: Width of A does not encompass B's origin
            } else {
                width = (int)(a.getWidth() + b.getWidth());
            }
            
            int height = 0;
            //CASE 1: Height of A encompasses B's origin and height entirely
            if ((
                  (a.getY() <= b.getY()) &&
                  (a.getY() + a.getHeight() > b.getY())
                ) 
             && (a.getY() + a.getHeight() >= b.getY() + b.getHeight())) {
                height = (int)(a.getHeight());
            //CASE 2: Height of A encompasses B's origin but not B's height
            } else if ((
                         (a.getY() <= b.getY()) && 
                         (a.getY() + a.getHeight() > b.getY())
                       )
                    && (a.getY() + a.getHeight() < b.getY() + b.getHeight())) {
                height = (int)((b.getY() - a.getY()) + b.getHeight());
            //CASE 4: Height of A does not encompass B's origin
            } else {
                height = (int)(a.getHeight() + b.getHeight());
            }
            
            return new int[]{width, height};
//         if (!this.kpRectA.equals(this.kpRectB)) {
//             return new int[]{Math.max( 
//                 /* Width  */ (int)this.kpRectA.getWidth(),
//                              (int)this.kpRectB.getWidth()),
//                 /* Height */ (int)this.kpRectA.getHeight()
//                            + (int)this.kpRectB.getHeight()};
//         } else {
//             return new int[]{
//                 /* Width  */ (int)this.kpRectB.getWidth(),
//                 /* Height */ (int)this.kpRectB.getHeight()};
//         }
        }
    }
    
/** Scales BufferedImage to the size specified. Used from StackOverflow's
  * question "scale a bufferedImage the fastest and easiest way"
  * @author "Victor" https://goo.gl/1JHBfQ
  * @param src BufferedImage to be scaled
  * @param w Width to scale to
  * @param h Height to scale to
  * @return The scaled BufferedImage
  */
    private BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img =
            new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        for (x = 0; x < w; x++) {
            for (y = 0; y < h; y++) {
                int col = src.getRGB(x * ww / w, y * hh / h);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

/** Returns the stored image representing the highlight of a given KeyPosition
  * @param highlight True if highlight is desired, false if not
  * @return The desired BufferedImage for the KeyPosition
  */
    public BufferedImage getImageByHighlight(boolean highlight) {
//        System.err.println(this.kpID + " " + this.kpType);
        //If obtaining highlight, return highlight, otherwise de-highlight
        reloadImages(false);
        return (highlight) ? this.kpHImage : this.kpDImage;
    }

    private void reloadImages(boolean override) {
        if (this.kpDImage == null || override == true)
            this.kpDImage = handleString(generateString(false, this.kpBank,
                                           this.kpType, this.kpSynthType));
        if (this.kpHImage == null || override == true)
            this.kpHImage = handleString(generateString(false, this.kpBank,
                                           this.kpType, this.kpSynthType));
    }
    
    public BufferedImage getImageBorder() {
    	return handleString("BORDER.png");
    }

/** Returns the image representing the group assigned to the KeyPosition
  * Only will return a valid image if the image is of groupable "type"
  * @param groupID Integer representing the group to get the image for
  * @return The valid "grouped" BufferedImage to get for a given KeyPosition
  */
    public BufferedImage getGroupImageByID(int groupID) {
        //if the type is not of the groupable type (CF, DGA, EB, RL)
        //then return the BADBS image
        switch(this.kpType) {
            case CF:
            case DGA:
            case EB:
            case RL:
                //Check if the id is within bounds of the groupable icons
                if (groupID > 0 && groupID < 11)
                    return handleString(PKBS[this.kpType]
                                 + KBS[this.kpType][groupID]);
                //if not return BADBS image
                else
                    return handleString(BADBS);  
            case AUX:
                if (groupID > 0 && groupID < 3)
                    return handleString(PAXLBS + AXLBS[groupID]);
                else
                    return handleString(BADBS);
            case SEQ:
                if (groupID > 0 && groupID < 5)
                    return handleString(PSEQBS
                                 + SEQBS[groupID]);
                else
                   return handleString(BADBS);
            default:
                return handleString(BADBS);
        }
    }
    
    public int compareTo(Object e) {
        if (e == null)
            return SortedList.NO_COMPARE;
        KeyPosition kp = (KeyPosition)e;
        if (this.kpID < kp.kpID)
            return -1;
        else if (this.kpID > kp.kpID)
            return 1;
        else
            return 0;
    }

/** Displays this KeyPosition as a String
  * @return The String representation of the KeyPosition
  */
    public String toString() {
//         return this.kpID + "/" + this.kpType + " " + this.kpKeyCode;
        return this.kpID + "/" + this.kpType + "/"
             + this.kpXPos + "/" + this.kpYPos + "/" 
             + (int)this.kpRectA.getX() + "/" 
             + (int)this.kpRectA.getY() + "/"
             + (int)this.kpRectA.getWidth() + "/" 
             + (int)this.kpRectA.getHeight() + "/"
             + (int)this.kpRectB.getX() + "/" 
             + (int)this.kpRectB.getY() + "/"
             + (int)this.kpRectB.getWidth() + "/" 
             + (int)this.kpRectB.getHeight() + "/"
             + this.kpKeyCode;
    }
}
