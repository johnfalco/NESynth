/** The first iteration of the GUIDisplay class, used to interact with the
  * Synth with a very basic interface
  *
  * @Version       1.2
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       GUIFacePanel, GUISidePanel, KeyPosition,
  * KeyPositionList, SynthConstants, Synth
  * @included      GUIMenuListener
  */

package nesynth;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class GUIv2 extends JFrame implements GUIConstants, SynthFrameUI {

/** Holds the default size for the main frame */
    protected static final int[] SIZE = {1033, 700};

/** Holds the Strings used to identify the menu items */
    protected static final String[] MENU = {"Rebind Keys", "Save Keys",
              "Set Soundfont", "Load Sequence", "Help", "Credits", "Exit"};

/** Strnig for the toggle safemode menu item */
    protected static final String SAFEMODE = "SafeMode";

/** Synth associated with this class */
    private AbstractSynth guiSynth;
/** The menu listener */
    private GUIMenuListener guiMenuListen;

/** The KeyPositionList associated with this class */
    protected KeyPositionList guiKeyPositionList;

/** Stores the files used when rebinding sequences */
    protected static final File[] SEQFILESTORE = new File[4];

/** Custom drawn JPanel */
    protected GUIFacePanelV2 guiFaceTwo;
/** Custom panel for displaying the main keyboard MIDI
  * information and alos used to select channels to adjust */
    protected GUISidePanel guiLeftSidePanel;
/** Custom panel for displaying all other MIDI
  * information and alos used to select channels to adjust */
    protected GUISidePanel guiRightSidePanel;
    
/** Menu bar that holds all the options for the window */
    protected JMenuBar guiMenu;
/** Menu for the file operations */
    protected JMenu guiFile;
/** Menu items for rebinding keys */
    protected JMenuItem guiRebindKeys;
/** Menu Item for saving bound keys */
    protected JMenuItem guiSaveKeys;
/** Menu Item for setting soundfont */
    protected JMenuItem guiSetSoundfont;
/** Menu Item for loading sequences */
    protected JMenuItem guiLoadSequence;
/** Menu Item for toggling safe mode */
    protected JMenuItem guiToggleSafeMode;
/** Menu Item for the help */
    protected JMenuItem guiHelp;
/** Menu Item for the credits */
    protected JMenuItem guiCredits;
/** Menu Item for the exit */
    protected JMenuItem guiExit;

/** JFileChooser for loading files into the Synth */
    protected JFileChooser guiLoader;

/** Constructs the GUIDisplay
  */
    public GUIv2(char bnk) {
    	this(bnk, 1);
    }

    public GUIv2(char bnk, int synthKind) {
        this.guiKeyPositionList = new KeyPositionList(bnk);
        this.guiLoader =
            new JFileChooser(new File(System.getProperty("user.dir")));
        this.setSize(SIZE[0], SIZE[1]);
        this.setTitle("NESynth");
        this.setLayout(new BorderLayout());
        this.guiFaceTwo = new GUIFacePanelV2(1, this);
        this.add(this.guiFaceTwo, BorderLayout.CENTER);
        this.guiFaceTwo.setPreferredSize(new Dimension(625, 616));
        if (synthKind == 2) {
        	guiSynth = new SynthTwo(this.guiFaceTwo, 30);
        	guiSynth.setSoundbank(new File("soundfontData/globalsf.sf2"));
        } else
        	guiSynth = new SynthOne(this.guiFaceTwo, 30);
        new File("midiFilesData/MidiTest.mid");
        this.guiLeftSidePanel = new GUISidePanel(2, 10, this);
        this.add(this.guiLeftSidePanel, BorderLayout.WEST);
        this.guiLeftSidePanel.setPreferredSize(new Dimension(204, 700));
        this.guiRightSidePanel = new GUISidePanel(3, 6, this);
        this.add(this.guiRightSidePanel, BorderLayout.EAST);
        this.guiRightSidePanel.setPreferredSize(new Dimension(204, 700));
        guiSynth.addSynthPanelUI(this.guiFaceTwo);
        guiSynth.addSynthPanelUI(this.guiLeftSidePanel);
        guiSynth.addSynthPanelUI(this.guiRightSidePanel);
        setMenu();
        this.setResizable(false);
        this.setVisible(true);
        this.validate();
        bindKeys();
//         this.guiFaceTwo.addMouseListener(guiNoteListen);
//         this.guiFaceTwo.addKeyListener(guiNoteListen);
//         this.addKeyListener(guiNoteListen);
    }
/** Returns the Synth associated with this UI object
  * @return The associated Synth
  */
    public AbstractSynth getSynth() {
        return this.guiSynth;
    }

/** Gets the center Panel of the UI
  * @return The center Panel UI
  */
    public SynthPanelUI getCenterPanel() {
        return this.guiFaceTwo;
    }
     
/** Gets the left Panel of the UI
  * @return The left Panel UI
  */
    public SynthPanelUI getLeftPanel() {
        return this.guiLeftSidePanel;
    }

/** Gets the right Panel of the UI
  * @return The right Panel UI
  */     
    public SynthPanelUI getRightPanel() {
        return this.guiRightSidePanel;
    }

/** Gets the KeyPositionList of the UI
  * @return The KeyPositionList stored with the UI
  */
    public KeyPositionList getKeyPositionList() {
        return this.guiKeyPositionList;
    }

/** Helper method to load all KeyPositions from a file
  * @return True if loaded sucessfully
  */
    private boolean loadKeyPositionFromFile() {
        this.guiLoader.setDialogTitle("Select .kpl file to load");
        this.guiLoader.setFileFilter(
            new FileNameExtensionFilter(".kpl Files", "kpl")); 
        int loader = this.guiLoader.showOpenDialog(this);   
        if (loader == JFileChooser.APPROVE_OPTION) {
            String name = this.guiLoader.getSelectedFile().getAbsolutePath();
            this.guiKeyPositionList = new KeyPositionList(name);
            bindKeys();
            return true;
        } else {
            System.err.println("Try again");
            this.guiKeyPositionList = new KeyPositionList(null);
            bindKeys();
        }
        //all else fails
        return false;
    }

/** Helper method to bind all the keys used as default
  */
    private void bindKeys() {       
        SEQFILESTORE[0] = new File("midiFilesData/MidiTest.mid");
        SEQFILESTORE[1] = new File("midiFilesData/MidiTestTwo.mid");
        SEQFILESTORE[2] = new File("midiFilesData/MidiTestThree.mid");
        SEQFILESTORE[3] = new File("midiFilesData/MidiTest.mid");
        KeyPosition temp = this.guiKeyPositionList.getNext();
        do {
//             System.err.println("LOADED " + temp.toString());
            loadAction(temp.getKeyCode(), temp);
            temp = this.guiKeyPositionList.getNext();
        } while(temp != null);
    }

/** Helper method to load a given KeyPosition with its associated values
  * into the Synth at a given KeyCode for interaction
  * @param keyCode Integer representing the KeyCode of a KeyEvent
  * @param keyposition KeyPosition to load action from
  */
    public void loadAction(int keyCode, KeyPosition keyposition) {
        KeyPosition keyPos = 
            this.guiKeyPositionList.replaceKeyPositionByID(keyposition);
        if (keyPos == null) {
            System.err.println("Could not update to keyposition, not adding");
            return;
        }
        // switch(keyPos.getKindByIDType()) {
//           case KeyPosition.NOTEACT:
//           case KeyPosition.PERCACT:
//             int val = (values[0] == KeyPosition.NUL) 
//                 ? handleKeyValue(keyPos.getKeyID()) 
//                 : values[0];
//             int vel = (values[1] == KeyPosition.NUL) 
//                 ? DEF_VEL 
//                 : values[1];
//             int dur = (values[2] == KeyPosition.NUL) 
//                 ? DEF_DUR 
//                 : values[2];
//             int chan = (values[3] == KeyPosition.NUL) 
//                 ? DEF_CHAN 
//                 : values[3];
//             int octS = values[4]; //Always will be set accordingly
//             guiSynth.addAction(code, new Note(val, vel, dur, chan, octS));
//             break;
//           case KeyPosition.SYNSACT:
//             int action = (values[0] == KeyPosition.NUL) 
//                 ? DEF_ACT 
//                 : values[0];
//             int[] value = new int[1];
//             value[0] = (values[1] == KeyPosition.NUL) 
//                 ? 0 
//                 : values[1];
//             int[] chans = (values[2] == KeyPosition.NUL) 
//                 ? DEF_CHARRAY
//                 : new int[1];
//           /* If chans wasn't set to the default array, 
//           ** load values[2] in chans[0].
//           ** This does not have any true functionality just yet but
//           ** if such an operation is used in the future it is here.
//           */
//             if (chans.length == 1)
//                 chans[0] = values[2];
//             guiSynth.addAction(code, new SynthSys(action, value, chans));
//             break;
//           case KeyPosition.SEQUACT:
//             int seq = (values[0] == KeyPosition.NUL) 
//                 ? DEF_SEQ 
//                 : values[0];
//             int seqChan = (values[1] == KeyPosition.NUL) 
//                 ? DEF_SEQCHAN
//                 : values[1];
//             guiSynth.addAction(code, 
//             		guiSynth.convertMIDI(SEQFILESTORE[seq], seqChan));
//             break;
//           default:
//             System.err.println("BAD KIND");
//             break;
//         }
         guiSynth.addAction(keyCode, keyPos.getSynthType());
    }

/** Handles getting the KeyValue from a given KeyPosition's KeyID
  * @param keyID Integer representing the KeyID of a KeyPosition
  * @return The value associated with the KeyPosition
  */
    private int handleKeyValue(int keyID) {
        switch(keyID) {
            case 25:
                return 36;
            case 26:
                return 38;
            case 27:
                return 44;
            case 28:
                return 49;
            case 29:
                return 37;
            case 30:
                return 41;
            case 31:
                return 48;
            case 32:
                return 55;
            default:
                if (keyID >= 1 && keyID <= 24)
                    return (keyID+24);
                else if (keyID >= 33 && keyID <= 63)
                    return (keyID + 20);
                //Any other value is bad, return default (C4)
                else
                    return 60;
        }
    }
    
    private boolean saveKeys() {
        this.guiLoader.setDialogTitle("Select .kpl file to save to");
        this.guiLoader.setFileFilter(
            new FileNameExtensionFilter(".kpl Files", "kpl"));
        int loader = this.guiLoader.showOpenDialog(this);
        if (loader == JFileChooser.APPROVE_OPTION) {
            String name = this.guiLoader.getSelectedFile().getAbsolutePath();
            return this.guiKeyPositionList.saveKeyPositionList(name);
        } else
            System.err.println("Try again");
        //all else fails
        return false; 
    }


/** Handles loading a soundfont into the Synth
  * @return True if the soundfont was successfully loaded
  */
    private boolean loadSoundfont() {
        this.guiLoader.setDialogTitle("Select .sf2 file");
        this.guiLoader.setFileFilter(
            new FileNameExtensionFilter(".sf2 Files", "sf2"));
        int loader = this.guiLoader.showOpenDialog(this);
        if (loader == JFileChooser.APPROVE_OPTION) {
            File file = this.guiLoader.getSelectedFile();
            try {
                guiSynth.setSoundbank(file);
                return true;
            } catch (Exception e) {
                System.err.println("Load not done, GG");
                return false;
            }
        } else
            System.err.println("Load even more not done, GG");
        //all else fails
        return false;
    }

/** Handles loading a sequence into the Synth
  * @return True if the sequence was successfully loaded
  */
    private boolean loadSequence() {
        int channel = 0;
        int keyCode = 0;
        int storeValue = 0;
        String one = "1", two = "2", thr = "3", fou = "4";
        Object[] choice = {one, two, thr, fou};
        String useDefault = (String)JOptionPane.showInputDialog(
            null, "WARNING: This will reset the sequence's to default value!"
                + "\nWhich sequence to load into?", "Sequence Number",
                JOptionPane.WARNING_MESSAGE, null, choice, one);
        if (useDefault != null) {
            switch(useDefault.charAt(0)) {
                case '1':
                    channel = SynthConstants.SEQACHAN;
                    keyCode = KeyEvent.VK_Z;
                    storeValue = 0;
                    break;
                case '2':
                    channel = SynthConstants.SEQBCHAN;
                    keyCode = KeyEvent.VK_X;
                    storeValue = 1;
                    break;
                case '3':
                    channel = SynthConstants.SEQCCHAN;
                    keyCode = KeyEvent.VK_C;
                    storeValue = 2;
                    break;
                case '4':
                    channel = SynthConstants.SEQDCHAN;
                    keyCode = KeyEvent.VK_V;
                    storeValue = 3;
                    break;
                default:
                    return false;
            }
        } else  //File done messed up
            return false;
        this.guiLoader.setDialogTitle("Select MIDI file");
        this.guiLoader.setFileFilter(
            new FileNameExtensionFilter(".MIDI Files", "mid", "midi"));
        int loader = this.guiLoader.showOpenDialog(this);
        if (loader == JFileChooser.APPROVE_OPTION) {
            File file = this.guiLoader.getSelectedFile();
            try {
                guiSynth.addAction(keyCode, 
                		guiSynth.convertMIDI(file, channel));
                SEQFILESTORE[storeValue] = file;
                return true;
            } catch (Exception e) {
                System.err.println("Load not done, GG");
                return false;
            }
        } else
            System.err.println("Load even more not done, GG");
        //all else fails
        return false;
    }

/** Helper method for setting the MenuBar for the program
  */
    private void setMenu() {
        this.guiMenuListen = new GUIMenuListener();
        this.guiMenu = new JMenuBar();
        //File Options
        this.guiFile = new JMenu(" File ");
        this.guiMenu.add(this.guiFile);
        //RebindKeys
        /* Keys are not to be rebound in this version */
//         this.guiRebindKeys = new JMenuItem(MENU[0]);
//         this.guiFile.add(this.guiRebindKeys);
//         this.guiRebindKeys.addActionListener(this.guiMenuListen);
        //SaveKeys
        this.guiSaveKeys = new JMenuItem(MENU[1]);
        this.guiFile.add(this.guiSaveKeys);
        this.guiSaveKeys.addActionListener(this.guiMenuListen);
        //SetSoundfont
        this.guiSetSoundfont = new JMenuItem(MENU[2]);
        this.guiFile.add(this.guiSetSoundfont);
        this.guiSetSoundfont.addActionListener(this.guiMenuListen);
        //LoadSequence
        this.guiLoadSequence = new JMenuItem(MENU[3]);
        this.guiFile.add(this.guiLoadSequence);
        this.guiLoadSequence.addActionListener(this.guiMenuListen);
        //ToggleSafeMode
        this.guiToggleSafeMode = new JMenuItem("Disable " + SAFEMODE);
        this.guiFile.add(this.guiToggleSafeMode);
        this.guiToggleSafeMode.addActionListener(this.guiMenuListen);
        //Help
        this.guiHelp = new JMenuItem(MENU[4]);
        this.guiMenu.add(this.guiHelp);
        this.guiHelp.addActionListener(this.guiMenuListen);
        //Credits
        this.guiCredits = new JMenuItem(MENU[5]);
        this.guiMenu.add(this.guiCredits);
        this.guiCredits.addActionListener(this.guiMenuListen);
        //Exit
        this.guiExit = new JMenuItem(MENU[6]);
        this.guiMenu.add(this.guiExit);
        this.guiExit.addActionListener(this.guiMenuListen);
        this.guiMenu.add(Box.createGlue());
        this.guiMenu.setPreferredSize(new Dimension(SIZE[0], 16));
        //Done
        System.err.println("guiMenu and items initialized");
        this.add(guiMenu, BorderLayout.NORTH);
    }

/** Handles a click from a SynthButton created for special Synth
  * interactions.
  * @param inButton SynthButton being acted upon
  */
    public void handleClick(SynthButton inButton) {
        inButton.addActionListener(guiSynth);
        inButton.doClick();
    }

/*****************************************************************************\
|                               LISTENER SECTION                              |
\*****************************************************************************/

/** ActionListener used for menu-based interactions
  */
    private class GUIMenuListener implements ActionListener {

    /** Performs a given ActionEvent
      * @param e ActionEvent being performed
      */
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            //Rebind Keys
            if (action.equals(MENU[0])) {
                System.err.println("How did you even call this?");
//                 Object[] choices = {"Load keybindings from file",
//                                     "Rebind all keys manually",
//                                     "Go back"};
//                 String choice = handleChoiceDialog("How do you want "
//                             + "to rebind keys?", "Options", choices,
//                               JOptionPane.INFORMATION_MESSAGE);
//                 if (choice != null) {
//                     if (choice.charAt(0) == 'L') {
//                         guiSynth.cleanActions();
//                         boolean b = loadKeyPositionFromFile();
//                         if (b)
//                             System.err.println("Keys loaded");
//                         else
//                             System.err.println("Keys failed to load");
//                     } else if (choice.charAt(0) == 'R') {
//                         guiSynth.cleanActions();
//                         guiFaceTwo.toggleChannelBindingMode();
//                     }
//                 } else {
//                     System.err.println("Not doing anything!");
//                 }
            //Save Keys
            } else if (action.equals(MENU[1])) {
                boolean b = saveKeys();
                if (b)
                    System.err.println("Keys saved");
                else
                    System.err.println("Keys failed to save");
            //Set Custom Soundfont
            } else if (action.equals(MENU[2])) {
                boolean b = loadSoundfont();
                if (b)
                    System.err.println("Soundfont loaded");
                else
                    System.err.println("Soundfont failed to load");
            //Load Sequence
            } else if (action.equals(MENU[3])) {
                boolean b = loadSequence();
                if (b)
                    System.err.println("Sequence loaded");
                else
                    System.err.println("Sequence failed to load");
            //Disable SafeMode
            } else if (action.equals("Disable " + SAFEMODE)) {
                Object[] choices = {"Yes", "No, go back!"};
                String choice =
                    handleChoiceDialog("WARNING: This will allow potentially "
                                 + "harmful pitches to be produced.\n"
                                 + "Are you sure you wish to do this?",
                                   "WARNING", choices,
                                   JOptionPane.WARNING_MESSAGE);
                if (choice != null) {
                    if (choice.charAt(0) == 'Y') {
                        guiSynth.toggleSafeMode();
                        guiToggleSafeMode.setText("Enable " + SAFEMODE);
                    }
                } else {
                    System.err.println("Not doing anything!");
                }
            //Enable SafeMode
            } else if (action.equals("Enable " + SAFEMODE)) {
                Object[] choices = {"Yes, re-enable safe mode",
                                    "No, I like to live dangerous"};
                String choice =
                    handleChoiceDialog("Would you like to re-enable "
                            + "Safe Mode?", null, choices, 
                              JOptionPane.WARNING_MESSAGE);
                if (choice != null) {
                    if (choice.charAt(0) == 'Y') {
                        guiSynth.toggleSafeMode();
                        guiToggleSafeMode.setText("Disable " + SAFEMODE);
                    }
                } else {
                    System.err.println("Not doing anything!");
                }
            //Help
            } else if (action.equals(MENU[4])) {
                handleHelp();
            //Credits
            } else if (action.equals(MENU[5])) {
                handleCredits();
            //Exit
            } else if (action.equals(MENU[6])) { // Exit
                JOptionPane.showMessageDialog(null, "Exiting program",
                    "Bye", JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            } else { // Otherwise, a bad string was received.
                    throw new
                        NullPointerException("GUIMenuListener frowny face :(");
            }
        }

    /** Helper method to help concisely handle InputDialog since arrays cannot
      * be defined within a method call.
      * @param text Text to be displayed to the InputDialog
      * @param title Title to be displayed to the InputDialog
      * @param choices Choices to be displayed within the dialog
      * @param icon Represents the constant used within all JOptionPane dialogs
      * for the icon to be displayed, as defined in JOptionPane's javadocs.
      * @return The String received as the answer
      */
        private String handleChoiceDialog(String text, String title, 
                                          Object[] choices, int icon) {
            return (String)JOptionPane.showInputDialog(null, text, title,
                                           icon, null, choices, choices[0]);
        }
 
    /** Helper method to assist in displaying help text to the user
      */
        private void handleHelp() {
            System.err.println("EVENTUALLY LOAD THIS AS HTML INTO JLABEL");
            try {
                JTextArea help = new JTextArea(20, 60);
                help.read(new FileReader("documentation/Help.txt"), null);
                help.setEditable(false);
                help.setLineWrap(true);
                help.setWrapStyleWord(true);
                JScrollPane helpView = new JScrollPane(help);
                helpView.setHorizontalScrollBarPolicy(
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                JOptionPane.showMessageDialog(null, helpView, MENU[4],
                            JOptionPane.PLAIN_MESSAGE, null);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    
    /** Helper method to assist in displaying credits text to the user
      */
        private void handleCredits() {
            System.err.println("EVENTUALLY LOAD THIS AS HTML INTO JLABEL");
            try {
                JTextArea credits = new JTextArea(20, 60);
                credits.read(new FileReader("documentation/Credits.txt"), null);
                credits.setEditable(false);
                credits.setLineWrap(true);
                credits.setWrapStyleWord(true);
                JScrollPane creditView = new JScrollPane(credits);
                creditView.setHorizontalScrollBarPolicy(
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                JOptionPane.showMessageDialog(null, creditView, MENU[5],
                            JOptionPane.PLAIN_MESSAGE, null);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
//End of file