/** Custom ActionListener used to bind keys and perform MIDI operations from
  * them. This program is the heart of the NESynth class.
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SynthButton,
  * @included      SequenceTimer, SynthSysAction,
  * SequenceAction, NoteAction, SynthSys, Sequence,
  * Note, SeqNode, SeqThread, SynthSysNode, NoteNode,
  * FinalNode
  */

package nesynth;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;

import static nesynth.SynthConstants.*;

public class SynthTwo extends AbstractSynth implements ActionListener {
/** Integer that holds the information regarding the delay
  * that this class can handle, which is set in construction */
    private final int stDelay;

/** HashMap for holding data on pressed system keys */
    private final Map<String, SynthSys> stSynSysPressed =
                            new HashMap<String, SynthSys>();
/** HashMap for holding data on pressed sequence keys */
    private final Map<String, Sequence> stSeqPressed =
                            new HashMap<String, Sequence>();
/** HashMap for holding data on pressed note keys */
    private final Map<String, Note> stNotesPressed =
                            new HashMap<String, Note>();

/** Holds information regarding octave displacement for a given "left" note */
    private int stLeftOct;
    
/** Timer for timing system actions */
    private Timer stSynthSysTimer;
/** Array of Specialized sequence timers that hold
  * sequence playback information as well as timer functionality */
    private SequenceTimer[] stSeqTimers = new SequenceTimer[4];
/** Timer for timing note actions */
    private Timer stNoteTimer;

/** SortedList for active system actions */
    private SortedList stCurrentSynSys = new SortedList(16);
/** SortedList for active sequences */
    private SortedList stCurrentSeqs = new SortedList(6);
/** SortedList for active notes */
    private SortedList stCurrentNotes = new SortedList(16);

/** Initialize the Synthesizer onto the component with the minimum delay
  * the current system can handle
  * @param c JComponent that is read for inputs
  * @param delay Integer representing the safe value of milliseconds the
  * system can sleep for as well as the values for most timers being fired.
  * If this value is improperly set, there is a strong likelihood that
  * ThreadInterruptedExceptions will be encountered when running actions set
  * on looping timers using the doSleep(int) method
  */
    public SynthTwo(JComponent c, int delay) {
        super(c);
      
        stDelay = delay;
        //Initializes the timers with the delay value and an initial delay of 0
        this.stSynthSysTimer = new Timer(this.stDelay, this);
        this.stSynthSysTimer.setInitialDelay(0);
        for (int i = 0; i < this.stSeqTimers.length; i++) {
            this.stSeqTimers[i] = new SequenceTimer(this.stDelay, this);
            this.stSeqTimers[i].setInitialDelay(0);
        }
        this.stNoteTimer = new Timer(this.stDelay, this);
        this.stNoteTimer.setInitialDelay(0);

        //Initialize the displacement of the basic keyboard's octave settings
        this.stLeftOct = 0;

        System.err.println("SynthThread Initialized");
    }
    
    public void addSynthPanelUI(SynthPanelUI ui) {
        this.asSynthPanels.add(ui);
        System.err.println("Synth Panel added");
    }

/** Method to clear the Panels that are to be updated when the SynthThread
  * is activated
  */    
    public void clearSynthPanelUI() {
        this.asSynthPanels.clear();
        System.err.println("Synth Panels cleared from storage");
    }

/** Helper method to completely clear the system map of key inputs, useful
  * when reloading the SynthThread with new inputs to be read
  */
    public void cleanActions() {
        InputMap inputMap = asComponent.getInputMap(JComponent.
                                                    WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = asComponent.getActionMap();
        inputMap.clear();
        actionMap.clear();
    }

/** Helper method to create SynthActions from a given SynthType
  * @param keyCode Integer representing the KeyEvent associated to the action
  * @param type SynthType to be associated to the action
  */
    public void addAction(int keyCode, SynthType type) {
        if (keyCode == KeyEvent.VK_UNDEFINED || type == null || type instanceof SynthNull) {
//             System.err.println("Not adding undefined key!");
            return;
        }
        
        Action pAction = 
            new SynthAction(convertKeyCodeToString(keyCode), type, true);
        KeyEvent pKey = new KeyEvent(this.asComponent, KeyEvent.KEY_PRESSED,
                                          (long)1, 0, keyCode, (char)keyCode);
        putActionOntoMaps(pKey, pAction);
        
        Action rAction = 
            new SynthAction(convertKeyCodeToString(keyCode), type, false);
        KeyEvent rKey = new KeyEvent(this.asComponent, KeyEvent.KEY_RELEASED,
                                           (long)1, 0, keyCode, (char)keyCode);
        putActionOntoMaps(rKey, rAction);
    }

/** Helper method used to convert a keyCode into a string that can be read
  * by the hashmaps
  * @param keyCode Integer representing the KeyCode
  * @return String representation of the KeyCode
  */
    private static String convertKeyCodeToString(int keyCode) {
        String keystr = "" + keyCode;
        int offset = keystr.lastIndexOf(" ");
        return offset == -1 ? keystr : keystr.substring(offset+1);
    }

/** Helper method designed to insert a KeyEvent and its associated action to
  * the component's maps
  * @param modStateKey KeyEvent with a specific modified state
  * @param keyAction Action associated with the modified KeyEvent
  */
    private void putActionOntoMaps(KeyEvent modStateKey, Action keyAction) {
        InputMap inputMap = asComponent.getInputMap(JComponent.
                                                    WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = asComponent.getActionMap();
        //Check if it needs to be removed
        checkAndRemoveKeyEvent(modStateKey);
        //Add it in after checked
        inputMap.put(KeyStroke.getKeyStrokeForEvent(modStateKey), modStateKey);
        actionMap.put(modStateKey, keyAction);
    }

    private void checkAndRemoveKeyEvent(KeyEvent e) {
        InputMap inputMap = asComponent.getInputMap(JComponent.
                                                    WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = asComponent.getActionMap();
        KeyStroke eks = KeyStroke.getKeyStrokeForEvent(e);
        //Check if key is present in the inputMap
        Object msksPresent = inputMap.get(eks);
        if (msksPresent != null) //if modded keystroke is present, remove
            inputMap.remove(eks);
        Action mskPresent = actionMap.get(e);
        if (mskPresent != null) //if modded key is present, remove
            actionMap.remove(e);               
    }

/** Unloads an action based on its keyCode
  * @param keyCode Integer representing the KeyEvent associated to be unloaded
  */
    public void unloadAction(int keyCode) {
        KeyEvent pKey = new KeyEvent(this.asComponent, KeyEvent.KEY_PRESSED,
                                          (long)1, 0, keyCode, (char)keyCode);
        checkAndRemoveKeyEvent(pKey);
        KeyEvent rKey = new KeyEvent(this.asComponent, KeyEvent.KEY_RELEASED,
                                           (long)1, 0, keyCode, (char)keyCode);
        checkAndRemoveKeyEvent(rKey);
    }

/** Handles the activation or deactivation of a Synth event
  * @param key String representation of the action being performed
  * @param type The SynthType that is currently being activated 
  * or deactivated
  * @param isOn Determines whether this is an activation or 
  * deactivation event
  */
    private void handleEvent(String key, SynthType type, boolean isOn) {
        //SynthType is SynthSys
        if (type instanceof SynthSys) {
            SynthSys ss = (SynthSys)type;
            if (isOn == false) {
                stSynSysPressed.remove(key);
                boolean b = stCurrentSynSys.remove(ss);
                if (b) System.err.println("\n" + ss.toString() + " removed");
            } else
                stSynSysPressed.put(key, ss);
                
            if (stSynSysPressed.size() == 1) stSynthSysTimer.start();
            if (stSynSysPressed.size() == 0) stSynthSysTimer.stop();
        //SynthType is Sequence
        } else if (type instanceof Sequence) { 
        	System.err.println("TWO");
        	Sequence seq = (Sequence)type;
//            if (isOn == false) {
//                soSeqPressed.remove(key);
//                boolean b = soCurrentSeqs.remove(seq);
//                System.err.println("\n" + seq.toString() + " removed");
//                soSeqTimers[seq.getSeqChan()-11].stop();
//            } else {
        	if (isOn != false) {
	            if (stSeqPressed.containsKey(key)) {
	                stSeqPressed.remove(key);
	                boolean b = stCurrentSeqs.remove(seq);
	                if (b) System.err.println("\n" + seq.toString() + " removed");
	                stSeqTimers[seq.getSeqChan()-11].stop();
	            } else {
	            	stSeqPressed.put(key, seq);
	            	stSeqTimers[seq.getSeqChan()-11].setSequence(seq);
	                stSeqTimers[seq.getSeqChan()-11].setDelay(seq.getSeqDur());
	                stSeqTimers[seq.getSeqChan()-11].start();
	            }
        	}
        //SynthType is Note
        } else if (type instanceof Note) {
            Note note = (Note)type;
            if (isOn == false) {
                stNotesPressed.remove(key);
                boolean b = stCurrentNotes.remove(note);
                if (b) 
                    System.err.println("\n" + note.toString() + " removed");
            } else {
                stNotesPressed.put(key, note);
            }
            
            if (stNotesPressed.size() == 1) stNoteTimer.start();
            if (stNotesPressed.size() == 0) stNoteTimer.stop();
        //Unknown implementer of SynthType, error
        } else
            System.err.println("BAD TYPE RECEIVED BY HANDLEEVENT");
    }

/** Handles the performing of actions based on either the timers or a special
  * event based on the activation of a SynthButton
  * @param e ActionEvent being handled
  */
    public void actionPerformed(ActionEvent e) {
        //Activation from a timer
        if (e.getSource() instanceof Timer) {
            Timer t = (Timer)e.getSource();
            if (t.equals(stSynthSysTimer))
                playSynthSysAction();
            else if (t instanceof SequenceTimer) {
                SequenceTimer st = (SequenceTimer)t;
                playSequence(st.getSequence());
            } else if (t.equals(stNoteTimer))
                playNotes();
        //Activation from a SynthButton
        } else if (e.getSource() instanceof SynthButton) {
            SynthButton sb = (SynthButton)e.getSource();
            if (sb.getSynthType() instanceof SynthSys)            
                activateSynthSys((SynthSys)sb.getSynthType(), false);
            else 
                handleEvent(sb.getSynthType().toString(), sb.getSynthType(),
                                                     sb.getSynthTypeState()); 
        //Unknown activation
        } else {
            System.err.println("BAD ACTIVATION RECEIVED BY ACTIONPERFORMED");
        }
        
        if (stNotesPressed.size() == 0
         && stSynSysPressed.size() == 0)
        	doUpdatePanels(false);
    }

/** Handles all updating of panels after an action has been performed
  * @param reset Used to determine whether the Synth has experienced 
  * a "reset". If it is true, it resets the front panel in addition to 
  * everything else. Otherwise, it will only update the side panels as 
  * other things aren't being reset.
  */
    private void doUpdatePanels(boolean reset) {
        for(SynthPanelUI ui : asSynthPanels) {
            ui.updateDisplay(reset);
               System.err.println("UPDATED UI");
        }
    }

/** Sets the Left octave based on the parameter value
  * @param dOct Value being changed in the left octave
  */
    private synchronized void octaveLeftSet(int dOct) {
        if (dOct >= 13)
            this.stLeftOct = 13;
        else if (dOct <= -13)
            this.stLeftOct = -13;
        else
            this.stLeftOct = dOct;
        Note temp;
        stCurrentNotes.reset();
        for (Object n : stCurrentNotes) {
        	temp = (Note)n;
            if (temp != null) {
                if (temp.getOctS() == LEFTOCTAVE)
                    stCurrentNotes.remove(temp);
            }
        }
        
        System.err.println("Left Octave = " + this.stLeftOct);
    }

/** Activates whenever a synth system action is activated, handles
  * iterations and activations of the actions performed
  */
    protected void playSynthSysAction() {
        for (SynthSys ss : stSynSysPressed.values()) {
            //Adjustable values not being set are not added to list and will
            //run on the cycle.  Every other value will iterate only once
            //and will not be called a second time
            if(stCurrentSynSys.contains(ss))
                System.err.print("."); //If not iterating don't do anything
            else {
                System.err.println("\nActivating " + ss.toString());
                //Activate the SynthSystem with a value of true to signify
                //that the action is being done from within this class and
                //not through a button press in another class
                activateSynthSys(ss, true);
            }
        }
    }

/** Activates whenever sequences are activated, handles timing of notes stored
  * @param s Sequence to be played, obtained from the sequence timer
  */
    protected void playSequence(Sequence s) {
        //If inputs are not being read, don't even try to do anything else
        if (asReading == false) return;
        //Create the Sequence that will be referenced inside the thread
        final Sequence pSeq = s;
        //Check if sequence is in the current sequences
        if (stCurrentSeqs.contains(pSeq) || checkIfRunning(pSeq)) {
            System.err.println("\nAlready playing " + pSeq.toString());
            return;
        }
        asSeqChanRunning[pSeq.getSeqChan()-SEQACHAN] = true;
        stCurrentSeqs.add(pSeq);
        System.err.println("\nPlaying " + pSeq.toString());
        //Create the sequence thread that runs through the sequence
        new Thread() {
          public void run() {
            //Check again if inputs are being read or not
            if (asReading == false) {
              pSeq.reset();
              return;
            }
            int previousTick = 0, delay = 0;
            boolean running = true;
            do {
              SeqNode orig = (SeqNode)pSeq.getNext();
              SeqNode temp = orig;
              previousTick = temp.getTick();
              do {
                //Check if reading again, just in case
                if (asReading == false) {
                  pSeq.reset();
                  return;
                }
                //Run the sequence thread that activates everything
                SeqThread sequenceThread = new SeqThread(temp);
                sequenceThread.start();
                //Offset next sequenceThread by the delay between ticks
                temp = (SeqNode)pSeq.getNext();
                delay = temp.getTick() - previousTick;
                //Delay if possible by current percision
                if (delay > stDelay)    doSleep(delay);
                //Adjust the last tick before activating the next note
                previousTick = temp.getTick();
              } while (orig.getTick() != temp.getTick() && asReading == true);
              //Check if reading yet again
              if (asReading == false) {
                pSeq.reset();
                return;
              }
              //After sequence is over, do another delay
              if (delay > stDelay)  doSleep(delay);
              //Then reset and load it again
              pSeq.reset();
              //Once it's all done, check if it's still present
              running = stCurrentSeqs.contains(pSeq);
              //If it is still present, run through again
            } while (running == true && asReading == true);
            //Check yet again to avoid any unnecessary variable creation
            if (asReading == false) {
              pSeq.reset();
              return;
            }
          //Run if entirely over
          asSeqChanRunning[pSeq.getSeqChan()-SEQACHAN] = false;
          return;
          } //Ends run()
        }.start();
    }

/** Checks if the sequence is being run
  * @param seq Sequence being checked
  * @return True if still running
  */
    private synchronized boolean checkIfRunning(Sequence seq) {
        return this.asSeqChanRunning[seq.getSeqChan()-SEQACHAN];
    }

/** Activates whenever notes are played, handles notes
  */
    protected void playNotes() {
        for (Note n : stNotesPressed.values()) {
            if(stCurrentNotes.contains(n)) {
                System.err.print(","); //if playing, do not restart note
                return;
            }
            stCurrentNotes.add(n);
            final Note nT = new Note(n);
            if (asReading == false)
                return;
            new Thread() {
                public void run() {
                    if (asReading == false) {
                        this.interrupt();
                        return;
                    }
                    System.err.println("\nPlaying " + nT.toString());
                    int adjusted = adjustNoteInt(nT);
                    //if the note is safe, play it
                    if (checkIfIntIsSafe(adjusted, nT)) {
                        asChannels[nT.getChan()].noteOn(adjusted, nT.getVel());
                        while(stCurrentNotes.contains(nT)) {
                            doSleep(stDelay);
                            if (asReading == false) {
                                this.interrupt();
                                break;
                            }
                        }
                        asChannels[nT.getChan()].noteOff(adjusted);
                    }
                    this.interrupt();
                    return;
                }
            }.start();
        }
    }

/** Adjusts the integer of a note to the value based on its octave displacement
  * @param note Note being adjusted
  * @return The adjusted value of the note
  */
    private synchronized int adjustNoteInt(Note note) {
        if (note.getOctS() == LEFTOCTAVE)
            return note.getInt() + 12*stLeftOct;
        else
            return note.getInt();
    }

/** Checks if a value of a note is safe, and if it is not it will disable
  * the note from being played
  * @param value Value being checked
  * @param note Note being disabled from playing
  * @return True if the note was safe, otherwise it will return false and
  * handle actions accordingly
  */
    private boolean checkIfIntIsSafe(int value, Note note) {
        //If the value is safe, or if safemode is not enabled, return true
        if (value <= 96 || asSafeMode == false)
            return true;
        //Otherwise...
        stNotesPressed.clear();
        stSynSysPressed.clear();
        JOptionPane.showMessageDialog(asComponent,
            "The synthesizer has attempted to play a note at a pitch that "
          + "can potentially be dangerous to the human ear.\n"
          + "If you want to play this note, please disable safe mode.",
            "Safe Mode Enabled!", JOptionPane.WARNING_MESSAGE, null);
        return false;
    }

/** Activates a SynthSys action with all the properties of the given SynthSys.
  * @param ss SynthSys being activated
  * @param fromKeyboard Boolean representing the origin of the SynthSys action.
  * If the action was from something other than the keyboard, it will be false.
  */
    private void activateSynthSys(SynthSys ss, boolean fromKeyboard) {
        switch(ss.getSSID()) {
            case DECRVOL: case CRESVOL:
            case DOWNMODPITCH: case UPMODPITCH:
            case DOWNPITCHBEND: case UPPITCHBEND:
                ctrlValue(ss.getSSChans(), ss.getSSID(), ss.getSSVal());
                break;
            case SETVOL: case SETMODPITCH: case SETPITCHBEND:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                ctrlValue(ss.getSSChans(), ss.getSSID(), ss.getSSVal());
                break;
            case GLOBALDECRVOL: case GLOBALCRESVOL:
            case GLOBALDOWNMODPITCH: case GLOBALUPMODPITCH:
            case GLOBALDOWNPITCHBEND: case GLOBALUPPITCHBEND:
                ctrlValue(ALLCHAN, ss.getSSID(), ss.getSSVal());
                break;
            case GLOBALSETVOL: case GLOBALSETMODPITCH: case GLOBALSETPITCHBEND:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                ctrlValue(ALLCHAN, ss.getSSID(), ss.getSSVal());
                break;
            case TOGGLESUSTAIN:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                toggleSustain(ss.getSSChans());
                break;
            //global set sustain
            case GLOBALTOGGLESUSTAIN:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                toggleSustain(ALLCHAN);
                break;
            case TOGGLEMONO:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                toggleMono(ss.getSSChans());
                break;
            case GLOBALTOGGLEMONO:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                toggleMono(ALLCHAN);
                break;
            case TOGGLESOLO:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                toggleSolo(ss.getSSChans());
                break;
            case GLOBALTOGGLESOLO:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                toggleSolo(ALLCHAN);
                break;
            //lowering left octave
            case LEFTDOWNOCTAVE:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                octaveLeftSet(this.stLeftOct-1);
                break;
            //raising left octave
            case LEFTUPOCTAVE:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                octaveLeftSet(this.stLeftOct+1);
                break;
            //change program
            case CHANGEPROGRAM:
                if (fromKeyboard && !(ss instanceof SynthSysNode)) {
                    stCurrentSynSys.add(ss);
                    programSet(ss.getSSChans(), ss.getSSVals());
                } else if  (!fromKeyboard && !(ss instanceof SynthSysNode)) {
                    programSet(ss.getSSChans(), ss.getSSVals());
                } else {            
                    int chan = ss.getSSChans()[0];
                    //if not overriding, change
                    if (checkOverride(chan-10) == false)
                        programSet(ss.getSSChans(), ss.getSSVals());
                    else
                        System.err.println("Overriding");
                }
                
                break;
            //global change program
            case GLOBALCHANGEPROGRAM:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                programSet(ALLCHAN, ss.getSSVal());
                break;
            //Reset channel
            case RESETCHANNEL:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                ctrlSet(ss.getSSChans(), CTRLCH_CTRLOFF, CTRLCH_CTRLOFF);
                ctrlSet(ss.getSSChans(), CTRLCH_VOL, 100);
                break;
            //Halt all sound on specified channels
            case HALTCHANNEL:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                ctrlSet(ss.getSSChans(), CTRLCH_NOTEOFF, CTRLCH_NOTEOFF);
                break;
            //Halting all sound
            case HALTALLSYNTH: default:
                if (fromKeyboard)
                    stCurrentSynSys.add(ss);
                asReading = !asReading;
                System.err.println("Sound Halted = " + asReading);
                if (asReading == false) {
                    System.err.println("Halting sound");
                     asSynth.close();
                    try {
                        asSynth.open();
                        handleGeneration();
                    } catch (Exception e) {
                        if (!(e instanceof InvalidMidiDataException)) {
                            System.err.println("BAD STUFF MAN");
                        } else {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                    System.err.println("Halt complete");
                    doUpdatePanels(true);
                }
                break;
        }

    }
    
/** Activates a note using the duration within the note itself
  * @param n Note being used
  */
    private void activateNote(Note n) {
        System.err.println("Playing " + n.toString());
        asChannels[n.getChan()].noteOn(n.getInt(), n.getVel());
        doSleep(n.getDur());
        asChannels[n.getChan()].noteOff(n.getInt());

    }
    
/** Helper method to perform sleep on thread, used for timing notes
  * @param dur Duration of sleep as integer
  */
    private void doSleep(int dur) {
//         System.err.print("DS" + dur);
        try {
            Thread.sleep(dur);
        } catch (Exception ex){
            System.err.println("Bad sleep");
        }
    }

/** Helper method to activate nodes of a sequence
  * @param node Node being activated
  */
    private void activate(SeqNode node) {
        if (node instanceof NoteNode) {
            activateNote((Note)node);
        } else if (node instanceof SynthSysNode) {
            activateSynthSys((SynthSys)node, false);
        } else if (node instanceof FinalNode) {
            System.err.println("End of sequence");
        }
    }

/******************************************************************************
                              Nested Class Section                                                                
******************************************************************************/

/** Extended Timer that stores an associated Sequence for activation
  */    
    protected class SequenceTimer extends Timer {
    /** Stores the Sequence of the given SequenceTimer */
        private Sequence stSeq;
    
    /** Creates the SequenceTimer without a Sequence
      * @param delay Delay to be given to the timer
      * @param listener ActionListener to be associated with the timer
      */
        protected SequenceTimer(int delay, ActionListener listener) {
            super(delay, listener);
            this.stSeq = null;
        }
    
    /** Creates the SequenceTimer with a Sequence
      * @param delay Delay to be given to the timer
      * @param listener ActionListener to be associated with the timer
      * @param seq Sequence to be stored into the timer
      */
        protected SequenceTimer(int delay, ActionListener listener, 
                                                    Sequence seq) {
            super(delay, listener);
            this.stSeq = seq;
        }
        
    /** Gets the Sequence stored within this sequence timer
      * @return The stored Sequence
      */
        protected Sequence getSequence() {
            return this.stSeq;
        }

    /** Sets the Sequence stored within this sequence timer
      * @param seq Sequence to be stored
      */        
        protected void setSequence(Sequence seq) {
            this.stSeq = seq;
        }
    }

/** Extended Thread that activates Nodes of a Sequence
  */
    protected class SeqThread extends Thread {
    /** Stores the internal thread used to run the process */
        private Thread stInternal;
    /** Stores the sequence node used for running actions */
        private SeqNode stNode;
    
    /** Constructs the sequence thread with the given sequence node
      * @param inNode SeqNode being constructed from
      */
        protected SeqThread(SeqNode inNode) {
            this.stNode = inNode;
        }
    
    /** Runs the sequence thread
      */    
        @Override
        public void run() {
            System.err.print("RUNNING + " + this.stNode.toString() + "   ");
            activate(this.stNode);
            this.interrupt();
        }
        
    /** Starts the sequence thread usig the internal thread
      */
        @Override
        public void start() {
            if (stInternal == null);
                stInternal = new Thread(this, "SeqThread");
                stInternal.start();
        }
    }

/** Creates an abstract action that is associated to one of three SynthTypes
  * in order to interface with the HashMaps
  */
    protected class SynthAction extends AbstractAction {
    /** Stores the SynthType associated with the action */
        private SynthType saType;
    /** Stores the state of the action, true if activated false if not */
        private boolean saIsOn;
        
    /** Constructs the SynthAction
      * @param key String used to label the action internally
      * @param type The SynthType to be associated to the action
      * @param isOn Associated state for the action
      */
        protected SynthAction(String key, SynthType type, boolean isOn) {
            super(key);
            this.saType = type;
            this.saIsOn = isOn;
        }
        
    /** Performs this abstract action at the trigger of an ActionEvent
      * @param e ActionEvent being triggered
      */        
        public void actionPerformed(ActionEvent e) {
            handleEvent((String)getValue(NAME), this.saType, this.saIsOn);
        }
    }
}