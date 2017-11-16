/** Constant interface used to seperate all the constants
  * from the KeyPosition class
  *
  * @Version 1.0
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       None
  * @included      None
  */

package nesynth;

public final class KeyConstants {
/** Constant used for identifying default integer values within methods */
    public static final int NUL = -256;
    
/** Constant used to represent basic C and F keyboard key subtypes */
    public static final int CF = 0;
/** Constant used to represent basic D, G and A keyboard key subtypes */
    public static final int DGA = 1;
/** Constant used to represent basic E and B keyboard key subtypes */
    public static final int EB = 2;
/** Constant used to represent basic Sharp/Flat keyboard key subtypes */
    public static final int RL = 3;
/** Constant used to represent percussion key subtypes */
    public static final int PRC = 14;
/** Constant used to represent auxillary basic key subtypes */
    public static final int AUX = 15;
/** Constant used to represent sequence key subtypes */
    public static final int SEQ = 16;

/** Constant used to represent the change octave down
  * SynthSys key subtypes */
    public static final int COD = 0;
/** Constant used to represent the change octave up
  * SynthSys key subtypes */
    public static final int COU = 1;
/** Constant used to represent the change volume down
  * SynthSys key subtypes */
    public static final int CVD = 2;
/** Constant used to represent the change volume up
  * SynthSys key subtypes */
    public static final int CVU = 3;
/** Constant used to represent the down directional pad
  * SynthSys key subtypes */
    public static final int DDP = 4;
/** Constant used to represent the left directional pad
  * SynthSys key subtypes */
    public static final int LDP = 5;
/** Constant used to represent the power
  * SynthSys key subtypes */
    public static final int PWR = 6;
/** Constant used to represent the right directional pad
  * SynthSys key subtypes */
    public static final int RDP = 7;
/** Constant used to represent the reset mod
  * SynthSys key subtypes */
    public static final int RM = 8;
/** Constant used to represent the reset pitch
  * SynthSys key subtypes */
    public static final int RP = 9;
/** Constant used to represent the reset all
  * SynthSys key subtypes */
    public static final int RST = 10;
/** Constant used to represent the reset volume Synth System key subtypes */
    public static final int RV = 11;
/** Constant used to represent the toggle sustain Synth System key subtypes */
    public static final int TS = 12;
/** Constant used to represent the up directional pad
  * SynthSys key subtypes */
    public static final int UDP = 13;

/** Constant used to identify the kind of KeyPosition.
  * Used for binding, re-valuing and regrouping KeyPositions */
    public static final int NOTEACT = -4;
/** Constant used to identify the kind of KeyPosition.
  * Used for binding, re-valuing and regrouping KeyPositions */
    public static final int PERCACT = -3;
/** Constant used to identify the kind of KeyPosition.
  * Used for binding, re-valuing and regrouping KeyPositions */
    public static final int SYNSACT = -2;
/** Constant used to identify the kind of KeyPosition.
  * Used for binding, re-valuing and regrouping KeyPositions */
    public static final int SEQUACT = -1;

/** Constant to hold the default keycode used in the default constructor
  * of the KeyPosition class */
    public static final int DEFKEYCODE = 81; //81 = KeyEvent Constant VK_Q

/** String constant used for cleaner reading, identifies the
  * Synth System folder for loading images */
    public static final String PSYSBS = "SS/";
/** 2D String Array constants used for cleaner reading, identifies the
  * loading file for SynthSystem icons. Row 0 contains all de-highlighted
  * icons and Row 1 contains all highlighted icons, with the columns
  * aligning icons with their integer constant identifiers */
    public static final String[][] SYSBS =
        {{"SSDCOD.png", "SSDCOU.png", "SSDCVD.png", "SSDCVU.png", "SSDDDP.png",
          "SSDLDP.png", "SSDPWR.png", "SSDRDP.png", "SSDRM.png",
          "SSDRP.png", "SSDRST.png", "SSDRV.png", "SSDTS.png", "SSDUDP.png"},
         {"SSHCOD.png", "SSHCOU.png", "SSHCVD.png", "SSHCVU.png", "SSHDDP.png",
          "SSHLDP.png", "SSHPWR.png", "SSHRDP.png", "SSHRM.png",
          "SSHRP.png", "SSHRST.png", "SSHRV.png", "SSHTS.png", "SSHUDP.png"}};

/** String constant used for cleaner reading, identifies the
  * Percussion folder for loading images */
    public static final String PPERCBS = "PCN/";
/** 1D String Array constants used for cleaner reading, identifies the
  * loading file for Percussion icons. First entry is the de-highlighted
  * icon, with the second being the highlighted icon */
    public static final String[] PERCBS = {"PCD.png", "PCH.png"};

/** String constant used for cleaner reading, identifies the
  * Auxiliary folder for loading images */
    public static final String PAXLBS = "AXL/";
/** 1D String Array constants used for cleaner reading, identifies the
  * loading file for Auxiliary icons. First entry is the de-highlighted
  * icon, second entry is the default channel (9) icon, third entry is the
  * secondary channel (15) icon, and the last being the highlighted icon */    
    public static final String[] AXLBS = {"AXD.png", "AX9.png",
                                          "AX15.png", "AXH.png"};

/** String constant used for cleaner reading, identifies the
  * Sequence folder for loading images */
    public static final String PSEQBS = "SEQ/";
/** 1D String Array constants used for cleaner reading, identifies the
  * loading file for Sequence icons. First entry is the de-highlighted
  * icon, the in-between entries the icons for the channels each icon 
  * represent, and the last being the highlighted icon */
    public static final String[] SEQBS = {"SEQD.png", "SEQ1.png", "SEQ2.png",
                                          "SEQ3.png", "SEQ4.png", "SEQH.png"};

/** 1D String Array constants used for cleaner reading, identifies the
  * basic keyboard folder for loading images */
    public static final String[] PKBS = {"CF/", "DGA/",
                                          "EB/", "RL/"};
/** 2D String Array constants used for cleaner reading, identifies the
  * loading file for basic keyboard icons by row. Rows 0 and 10 are
  * de-highlighted and highlighted respectively, with group 1-10 between */
    public static final String[][] KBS =
        {{"GDCF.png", "G1CF.png", "G2CF.png", "G3CF.png",
          "G4CF.png", "G5CF.png", "G6CF.png", "G7CF.png",
          "G8CF.png", "G9CF.png", "G10CF.png", "GHCF.png"},
         {"GDDGA.png", "G1DGA.png", "G2DGA.png", "G3DGA.png",
          "G4DGA.png", "G5DGA.png", "G6DGA.png", "G7DGA.png",
          "G8DGA.png", "G9DGA.png", "G10DGA.png", "GHDGA.png"},
         {"GDEB.png", "G1EB.png", "G2EB.png", "G3EB.png",
          "G4EB.png", "G5EB.png", "G6EB.png", "G7EB.png",
          "G8EB.png", "G9EB.png", "G10EB.png", "GHEB.png"},
         {"GDRL.png", "G1RL.png", "G2RL.png", "G3RL.png",
          "G4RL.png", "G5RL.png", "G6RL.png", "G7RL.png",
          "G8RL.png", "G9RL.png", "G10RL.png", "GHRL.png"}};
          
/** 1D String Array constants used for cleaner reading, identifies the
  * loading files for the default key. First entry is the de-highlighted
  * default icon, with the second being the highlighted default icon */
    public static final String[] DEFBS = {"CF/GDCF.png",
                                           "CF/GHCF.png"};

/** String constant used for cleaner reading, identifies the error image
  * loaded when a BufferedImage method goes arwy */
    public static final String BADBS = "GDEF.png";
    
    public static String getBankString(char bank) {
        return "imageData" + Character.toString(bank) + "/";
    }

}