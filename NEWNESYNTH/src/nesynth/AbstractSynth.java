package nesynth;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static nesynth.SynthConstants.*;

public abstract class AbstractSynth implements ActionListener {

// Sequence Constants {
/** Constant to hold the default tempo of a midi sound if none can be found */
    protected static final int DEFAULTTEMPO = 90;
/** Double for holding the tick length of a quarter note, for adjusting BPM */
    protected static final double QUARTERTICK = 192;
//}

// Control Constants {
/** Constant representing the control channel state for
  * the modwheel as defined within the MIDI 1.0 specification */
    protected static final int CTRLCH_MODWHL = 1;
/** Constant representing the control channel state for
  * volume as defined within the MIDI 1.0 specification */
    protected static final int CTRLCH_VOL = 7;
/** Constant representing the control channel state for
  * sustain pedal as defined within the MIDI 1.0 specification */
    protected static final int CTRLCH_SUSTPED = 64;
/** Constant representing the control channel state for
  * reseting controllers as defined within the MIDI 1.0 specification */
    protected static final int CTRLCH_CTRLOFF = 121;
/** Constant representing the control channel state for
  * turning all notes off as defined within the MIDI 1.0 specification */
    protected static final int CTRLCH_NOTEOFF = 123;
//}

/** Holds the synthesizer within the class */
    protected Synthesizer asSynth;
/** Array for holding the channels of the synthesizer */
    protected BankChannel[] asChannels;
/** Holds the component to which the listener is tied to */
    protected final JComponent asComponent;

//     protected final ArrayList<SortableInstrument> asCurrentInst = new ArrayList<>();

// /** Timer that will free any potential memory issues */
//     protected Timer asClearMemory;
// /** ActionListener class that will activate the collection of excess threading */
//     protected ASClearMemoryListener asClearMemoryListen;


/** InstrumentList for all loaded non-drumset Instruments */
    protected InstrumentList asCurrentInst;
/** InstrumentList for all loaded drumset Instruments */
    protected InstrumentList asCurrentPercInst;
/** ArrayList for each SynthPanelUI added into this system */
    protected final ArrayList<SynthPanelUI> asSynthPanels = new ArrayList<>();
  
/** Used to determine whether inputs are being read */
    protected volatile boolean asReading = true;
/** Holds the information regarding whether safe mode is active.
  * Safe mode will disable all notes above MIDI integer #96
  * from sounding unless this value is set to false */
    protected volatile boolean asSafeMode = true;

/** Holds the information determining whether a sequence channel's program
  * is currently being overrided or not */
    protected volatile boolean[] asOverride = {false, false,
                                             false, false, false};

/** Holds the information determining whether a sequence has been completed */
    protected volatile boolean[] asSeqChanRunning = {false, false,
                                                          false, false};
    	

/** Parent constructer for all non-abstract Synth implementations
  * @param c JComponent that is read for inputs
  */
    protected AbstractSynth(JComponent c) {
        //Stores the base component that is read for inputs from
        this.asComponent = c;

        asSynth = null;
        //Try opening the Synthesizer
        try {
            asSynth = MidiSystem.getSynthesizer();
            asSynth.open();
            //Sets the channels to the amount of channels currently available
            MidiChannel[] temp = asSynth.getChannels();
            this.asChannels = new BankChannel[temp.length];
            
            for(int i=0; i<temp.length; i++) {
                this.asChannels[i] = new BankChannel(temp[i]);
            }
            //Load the soundbank
            setSoundbank(null);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            //If there's no midisystem, the program cannot function, so
            //exit the program immediately. Game over man, game over.
            System.exit(1);
        }
        
//         this.asClearMemoryListen = new ASClearMemoryListener();
//         this.asClearMemory = new Timer(10000, this.asClearMemoryListen);
//         this.asClearMemory.start();
        
	}

/** Used to clear the method of storing inputs implemented by a subclass
  * of AbstractSynth
  */
   public abstract void cleanActions();

/** Activates whenever a synth system action is activated, handles
  * iterations and activations of the actions performed
  */
   protected abstract void playSynthSysAction();
   	
 /** Activates whenever sequences are activated, handles timing of notes stored
   * @param s Sequence to be played, obtained from the sequence timer
   */
   protected abstract void playSequence(Sequence s);
   	
/** Activates whenever notes are played, handles notes
  */
   protected abstract void playNotes();
   
/** Handles the creation of SynthActions from a given SynthType
  * @param keyCode Integer representing the KeyEvent associated to the action
  * @param type SynthType to be associated to the action
  */
    protected abstract void addAction(int keyCode, SynthType type);

/** Unloads an action based on its keyCode
  * @param keyCode Integer representing the KeyEvent associated to be unloaded
  */
    protected abstract void unloadAction(int keyCode);

/** Handles the performing of actions based on either the timers or a special
  * event based on the activation of a SynthButton
  * @param e ActionEvent being handled
  */
    public abstract void actionPerformed(ActionEvent e);
    
/** Unloads the synthesizer and sets the instruments contained within the file
  * to the synthesizer
  * @param file File being accessed that functions as a soundbank
  * @return True if the synthesizer was capable of loading all instruments.
  */
    public boolean setSoundbank(File file) {
        try {
            boolean returnBool;
            if (file != null) {
                asSynth.unloadAllInstruments(asSynth.getDefaultSoundbank());
                returnBool =
                    asSynth.loadAllInstruments(MidiSystem.getSoundbank(file));
            } else {
                returnBool = true;
            }
            //regardless of whether things were loaded properly,
            //generate the sorted list for patches
            handleGeneration();
            return returnBool;
        } catch (Exception e) {
            if ((e instanceof InvalidMidiDataException)) {
                System.err.println("Bad MIDI Data, could not load");
                return false;
            } else {
                e.printStackTrace();
                //If there's no midisystem, the program cannot function, so
                //exit the program immediately. Game over man, game over.
                //Any other error will also land here as well.
                System.exit(1);
                return false;
            }
        }
    }

/** Handles generation of the instrument list
  * @throws InvalidMidiDataException if an error was encountered obtaining
  * the loaded instruments from the synthesizer.
  */
    protected void handleGeneration() throws InvalidMidiDataException {
        Instrument[] insts = asSynth.getLoadedInstruments();
//        System.err.println(insts.length + " SIZE");
//        this.asCurrentInst = new InstrumentList(insts.length);
        SortableInstrument temp;
        this.asCurrentInst = new InstrumentList();
        this.asCurrentPercInst = new InstrumentList();
        int b, p;
        for (Instrument inst : insts) {
            temp = new SortableInstrument(inst);
            b = temp.getPatch().getBank();
            p = temp.getPatch().getProgram();
//             boolean functions = testBankProgram(b, p);
//             if (!functions) {
//                 System.err.println("Computer synthesizer does not support "
//                                  + "the instrument at:" + b + " " + p + ".");
//             } else 
            if (this.asCurrentInst.getPositionByBankProgram(b, p)
                                        == SortedList.NO_COMPARE
                             && !this.asCurrentInst.contains(temp)) {
                this.asCurrentInst.add(temp);
//                 System.err.println(temp.toString());
            } else {
                this.asCurrentPercInst.add(temp);
            }
        }
    }

/** Adds a SynthPanelUI for updating to the synth when a subclass of 
  * AbstractSynth is activated
  * @param ui SynthPanelUI to be added
  */
    public void addSynthPanelUI(SynthPanelUI ui) {
        this.asSynthPanels.add(ui);
        System.err.println("Synth Panel added");
    }

/** Method to clear the Panels that are to be updated when a subclass of 
  * AbstractSynth is activated, removing them all from storage
  */    
    public void clearSynthPanelUI() {
        this.asSynthPanels.clear();
        System.err.println("Synth Panels cleared from storage");
    }
    
/** Gets the instrument's name for a given position on the 
  * non-percussion instrument list
  * @param pos Position to be checking for
  * @return String representation of the name of the instrument
  */
    public String getInstName(int pos) {
        return asCurrentInst.getInstrumentFromPos(refinePos(pos)).getInstName();
    }

/** Gets the instrument's name for a given position on either the 
  * percussion instrument list or the non-percussion instrument list
  * @param pos Position to be checking for
  * @param isPerc Boolean determining which list to obtain frmo
  * @return String representation of the name of the instrument
  */
    public String getInstName(int pos, boolean isPerc) {
        if (!isPerc)
            return asCurrentInst.getInstrumentFromPos(refinePos(pos))
                                                        .getInstName();
        else
            return asCurrentPercInst.getInstrumentFromPos(refinePercPos(pos))
                                                               .getInstName();
    }    

/** Refines a position to the non-percussion instrument list
  *  depending on the current size of the list
  * @param pos Position to be refining
  * @return The "refined" position
  */
    private int refinePos(int pos) {
        int refinedPos = pos % asCurrentInst.size();
        if (refinedPos < 0)
            refinedPos += asCurrentInst.size();
        return refinedPos;
    }

/** Refines a position to the percussion instrument list
  *  depending on the current size of the list
  * @param pos Position to be refining
  * @return The "refined" position
  */
    private int refinePercPos(int pos) {
        int refinedPos = pos % asCurrentPercInst.size();
        if (refinedPos < 0)
            refinedPos += asCurrentPercInst.size();
        return refinedPos;
    }

/** Obtains the volume from a given channel
  * @param channel Channel that is being gotten from
  * @return The volume of the channel
  */    
    public int getVolumeChannel(int channel) {
        return getController(channel, CTRLCH_VOL);
    }

/** Obtains the mod wheel pitch from a given channel
  * @param channel Channel that is being gotten from
  * @return The mod wheel pitch of the channel
  */     
    public int getModWheelChannel(int channel) {
        return getController(channel, CTRLCH_MODWHL);
    }
    
/** Obtains the pitch bend from a given channel in an adjusted form
  * @param channel Channel that is being gotten from
  * @return The adjusted pitch bend of the channel
  */ 
    public int getAdjPitchChannel(int channel) {
        return getPitchChannel(channel) - 8192;
    }
    
/** Obtains the position of an instrument from a channel on the instrument
  * list, used internally.
  * @param channel Channel being gotten from
  * @return The integer representing the position from the channel
  */
    public int getInstPositionFromChannel(int channel) {
        boolean isPerc = (channel == PERCCHAN);
        int b = asChannels[channel].getBank();
        int p = asChannels[channel].getProgram();
        int pos = (!isPerc) 
            ? asCurrentInst.getPositionByBankProgram(b, p)
            : asCurrentPercInst.getPositionByBankProgram(b, p);
        if (pos != SortedList.NO_COMPARE) {
//            return convertBankProgramToValue(b, p);
            return pos;
        } else {
            return 0;
        }
    }
    
    public int[] getInstBankProgramFromChannel(int channel) {
        int[] returnArray = {asChannels[channel].getBank(),
                             asChannels[channel].getProgram()};  
        return returnArray;
    }

/** Gets the name of the instrument from the channel on the instrument list
  * @param channel Channel being gotten from
  * @return The name of the instrument on the given channel
  */       
    public String getInstFromChannel(int channel) {
        boolean isPerc = (channel == PERCCHAN);
        int b = asChannels[channel].getBank();
        int p = asChannels[channel].getProgram();
        int pos = (!isPerc) 
            ? asCurrentInst.getPositionByBankProgram(b, p)
            : asCurrentPercInst.getPositionByBankProgram(b, p);
        if (pos != SortedList.NO_COMPARE) {
            return getInstName(pos, isPerc);
        } else {
            return "NULL";
        }
    }
    
    public int[] convertInstPosToBankProgram(int pos, boolean isPerc) {
        int refinedPos = (!isPerc) ? refinePos(pos) : refinePercPos(pos);
        if (!isPerc)
            return asCurrentInst.getBankProgramFromPos(refinedPos);
        else
            return asCurrentPercInst.getBankProgramFromPos(refinedPos);
    }
    
    public int convertInstBankProgramToPos(int[] vals, boolean isPerc) {
        if (!isPerc)
            return asCurrentInst.getPositionByBankProgram(vals[0], vals[1]);
        else
            return asCurrentPercInst.getPositionByBankProgram(vals[0],
                                                              vals[1]);
    }

/** Generates a readable Sequence object from a file that can be loaded onto
  * the channel specified in the parameters
  * @param file File to be read
  * @param channel MIDI Channel to set notes to playback on
  * @throws BadMIDISequenceException when the file cannot be converted properly
  * @return The converted Sequence object created from the sequence
  */
    public static Sequence convertMIDI(File file, int channel)
                                        throws BadMIDISequenceException {
        try {
          javax.sound.midi.Sequence sequence = MidiSystem.getSequence(file);
          SortedList builtSequence = new SortedList();
          //Store values that will be used later
          int trackNumber = 0, fullDur = 0;
          //determine defaultTempo in case no tempo messages are encountered
          int bpm = DEFAULTTEMPO;
          //Factor to account for the nature of how ticks are read inside of
          //the midi file, accounts for tempo
          double bpmFactor = (double)bpm / (double)192;
          SeqNode temp;
          for (Track t :  sequence.getTracks()) {
            trackNumber++;
            //Variables to hold for determining notes
            int noteInt = 0, noteVel = 0, noteDur = 0, currentTTick = 0;
            //Used to determine if a rest is encountered
            int lastNoteTick = 0;
            for (int i=0; i < t.size(); i++) {
              MidiEvent ev = t.get(i);
              try {
                //Get tick for the current event
                int tick = safeLongToInt(ev.getTick());
                //Store the current tick for use later
                fullDur = tick;
                //Get message from event
                MidiMessage m = ev.getMessage();
                //If the message is a shortmessage
                if (m instanceof ShortMessage) {
                  //Cast to short message
                  ShortMessage sm = (ShortMessage) m;
                  //Short message for note being played
                  if (sm.getCommand() == ShortMessage.NOTE_ON
                                      && sm.getData2() != 0) {
                    noteInt = sm.getData1(); //get int
                    noteVel = sm.getData2(); //get velocity
                    //This information is used later when determining when
                    //the note is turned off
                    
                    //logic to read rests in data
                    if (lastNoteTick != tick) { //determines if rest happened
                      //Calculate the length of the rest
                      int rest = (int)((tick - lastNoteTick)/bpmFactor);
                      lastNoteTick = tick;
                      currentTTick = (int)(tick/bpmFactor);
                      //Create new object to add to sequence
                      temp = new NoteNode(0, 0, rest, channel, currentTTick);
                      builtSequence.add(temp);
                    }
                  //Short message for note turning off (and the equivalent
                  //message of noteOn with a velocity of zero)
                  } else if (sm.getCommand() == ShortMessage.NOTE_ON
                          && sm.getData2() == 0
                          || sm.getCommand() == ShortMessage.NOTE_OFF) {
                    //If the same note is being turned off, which means
                    if (noteInt == sm.getData1()) {
                      noteDur = (int)((tick - lastNoteTick)/bpmFactor);
                      lastNoteTick = tick;
                      currentTTick = (int)(tick/bpmFactor);
                      //Once a note has been turned off,
                      //it is placed into the list
                      temp = new NoteNode(noteInt, noteVel,
                               noteDur, channel, currentTTick);
                      builtSequence.add(temp);
//                    System.err.println(temp.toString() + " added");
                    } //Otherwise do nothing
                  } else if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                    lastNoteTick = tick;
                    currentTTick = (int)(tick/bpmFactor);
                    temp = new SynthSysNode(CHANGEPROGRAM, 
                                new int[]{sm.getData2(), sm.getData1()},
                                new int[]{channel}, currentTTick);
                    builtSequence.add(temp);
//                  System.err.println(temp.toString() + " added");
//                     System.err.println("Non volume command read");
                  }
                } else if (m instanceof MetaMessage && trackNumber == 1) {
                  MetaMessage mm = (MetaMessage)m;
                  if (mm.getType() == 81) { //tempo
                    byte[] data = mm.getData();
                    //Check if data is able to be parsed for tempo
                    if (data.length >= 3) {
//                      System.err.println("Reading tempo");
                      int tempo = (data[0] & 0xff) << 16
                                | (data[1] & 0xff) << 8
                                | (data[2] & 0xff);
                      //Convert to BPM
                      bpm = 60000000 / tempo;
//                      System.err.println(bpm);
                      //Redefine factored BPM
                      bpmFactor = (double)bpm / QUARTERTICK;
                    } else {
//                      System.err.println("NOT reading tempo");
                    }
                  }
                }
              } catch (Exception ex) {
                if (ex instanceof IllegalArgumentException)
                  System.err.println("Sequence too long, load incomplete");
                else
                  System.err.println("Other Error encountered");
              }
            }
//            System.err.println(" ");
          }
          //Determine full length of the adjusted ticks for the
          //sequence which is used for looping by the system
          int fullTDur = (int)(fullDur/bpmFactor);
          //Create the ending note that will serve as the marker
          //for a completed sequence by the system
          FinalNode end = new FinalNode(fullTDur);
          //Add to the sequence
          builtSequence.add(end);
          
//          System.err.println(builtSequence.toString());
          return new Sequence(builtSequence, fullTDur, channel);
        } catch (Exception ex) {
          System.err.println(ex.getClass().getSimpleName() + " Exception from file");
          throw new BadMIDISequenceException();
        }
    }

/** Safe method to convert the long into an int
  * @param l Long that is being converted
  * @throws IllegalArgumentException when cast cannot be performed
  * @return The integer converted from the long
  */
    private static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                (l + " cannot be cast to int without changing its value.");
        }
        return (int)l;
    }
    
/** Method intended to be used by the class using SynthThread, toggles
  * whether new notes are being read as inputs or not. If "reading" is off,
  * no notes or sequences will sound until reading is back on again
  */
    public void toggleReading() {
        boolean b = asReading;
        setReading(!b);
        System.err.println("Reading = " + asReading);
    }

/** Used to set reading to the parameter
  * @param b Boolean to set to
  */
    protected synchronized void setReading(boolean b) {
        asReading = b;
    }

/** Toggles safemode based upon its given state
  */
    public synchronized void toggleSafeMode() {
        boolean b = !this.asSafeMode;
        setSafeMode(b);
        System.err.println("Safe mode = " + this.asSafeMode);
    }

/** Sets the value of the safemode to the parameter value
  * @param b Boolean to be set to the safemode value
  */
    protected synchronized void setSafeMode(boolean b) {
        this.asSafeMode = b;
    }
    
/** Changes the specified sequence's state to the value of the parameter.
  * A value of true will prevent the instrument stored within the sequence
  * from being activated, allowing it to be set to 
  * a value specified elsewhere.
  * @param seqNumber Sequence being overridden
  * @param b State to set the override to
  */
    public synchronized void changeOverride(int seqNumber, boolean b) {
        int pos = seqNumber-1;
        asOverride[pos] = b;
        System.err.println("Sequence " + seqNumber
                        + " override = " + asOverride[pos]);
    }
 
/** Checks if a given sequence's instrument is currently being overridden
  * @param seqNumber Sequence being checked
  * @return True is being overridden
  */
    public boolean checkOverride(int seqNumber) {
        if (seqNumber < 1)
            return false;
        else
            return asOverride[seqNumber-1];
    }

/** Sets the program (patch) onto the channel inside the parameter
  * @param channel Channel to set the program (patch) to
  * @param position The position of the instrument on the instrument list
  */
    protected synchronized void programSet(int channel, int position) {
        SortableInstrument si = (channel != PERCCHAN) 
            ? asCurrentInst.getInstrumentFromPos(refinePos(position))
            : asCurrentPercInst.getInstrumentFromPos(refinePos(position));
        asChannels[channel].programChange(si.getBank(), si.getProgram());
        System.err.println(channel + " " + getInstFromChannel(channel));
    }

/** Sets the program (patch) onto the channels inside the parameter
  * @param channels Channels to set the program (patch) to
  * @param position The position of the instrument on the instrument list
  */
    protected synchronized void programSet(int[] channels, int position) {
        for(int chan : channels)
            programSet(chan, position);
    }

/** Sets the program (patch) onto the channel inside the parameter
  * @param channel Channel to set the program (patch) to
  * @param vals The bank and program of the instrument on the instrument list
  */
    protected synchronized void programSet(int channel, int[] vals) {
        asChannels[channel].programChange(vals[0], vals[1]);
        System.err.println(channel + " " + getInstFromChannel(channel));
    }

/** Sets the program (patch) onto the channels inside the parameter
  * @param channel Channel to set the program (patch) to
  * @param vals The bank and program of the instrument on the instrument list
  */
    protected synchronized void programSet(int[] channels, int[] vals) {
        for(int chan : channels)
            programSet(chan, vals);
    }

/** Sets the controller at the specified event to the specified value on
  * the channel inside the parameter
  * @param channel Channel to set the event value to
  * @param eID ID of the event to set the value to
  * @param eVal Value to set
  */
    protected synchronized void ctrlSet(int channel, int eID, int eVal) {
        asChannels[channel].controlChange(eID, eVal);
//         System.err.println("CTRL: " + channel + ">" + eID + "_" + eVal);
    }

/** Sets the controller at the specified event to the specified value on
  * the channels inside the parameter
  * @param channels Channels to set the event value to
  * @param eID ID of the event to set the value to
  * @param eVal Value to set
  */
    protected synchronized void ctrlSet(int[] channels, int eID, int eVal) {
        for(int chan : channels)
            ctrlSet(chan, eID, eVal);
    }

/** Gets the controller value set at the specified event inside the channel
  * @param channel Channel to get controller value from
  * @param eID ID of the event to get the value from
  * @return The value to return from the controller
  */
    public int getController(int channel, int eID) {
        return asChannels[channel].getController(eID);
    }
    
/** Helper method to control specific instances of System actions on a given
  * channel that directly interface with variable values and not states
  * @param channel Integer representing the channel affected.
  * @param id Identifier of the type of action taking place
  * @param value Value being adjusted, used by setting values
  */
    protected synchronized void ctrlValue(int channel, int id, int value) {
        int dVal = 0;
        switch(id) {
            case SETPITCHBEND:
            case GLOBALSETPITCHBEND:
                dVal = (value >= MAXPITCHBEND) ? MAXPITCHBEND
                           : (value <= 0) ? 0 : value;
                // value must be < 16383= and >= 0
                pitchSet(channel, dVal);
                break;
            case DOWNPITCHBEND:
            case GLOBALDOWNPITCHBEND:
                dVal = getPitchChannel(channel);
                dVal = (dVal == MAXPITCHBEND) ? MAXPITCHBEND
                          : (dVal <= 0) ? 0 : dVal - DELTAPITCH;
                if (dVal <= 0)
                    dVal = 0;
                pitchSet(channel, dVal);
                break;
            case UPPITCHBEND:
            case GLOBALUPPITCHBEND:
                dVal = getPitchChannel(channel);
                dVal += DELTAPITCH;
                if (dVal >= MAXPITCHBEND)
                    dVal = MAXPITCHBEND;
                pitchSet(channel, dVal);
                break;
            case SETVOL:
            case GLOBALSETVOL:
                dVal = (value >= MAXVOLMP) ? MAXVOLMP
                            : (value <= 0) ? 0 : value;
                // value must be <= 127 and >= 0
                ctrlSet(channel, CTRLCH_VOL, dVal);
                break;
            //Lowering Volume
            case DECRVOL:
            case GLOBALDECRVOL:
                dVal = getController(channel, CTRLCH_VOL);
                dVal = dVal >= MAXVOLMP ? 120
                            : (dVal <= 0) ? 0
                                          : dVal - DELTAVOLMP;
                ctrlSet(channel, CTRLCH_VOL, dVal);
                break;
            //Raising Volume
            case CRESVOL:
            case GLOBALCRESVOL:
                dVal = getController(channel, CTRLCH_VOL);
                dVal = (dVal >= MAXVOLMP) ? MAXVOLMP : dVal + DELTAVOLMP;
                ctrlSet(channel, CTRLCH_VOL, dVal);
                break;
            case SETMODPITCH:
            case GLOBALSETMODPITCH:
                dVal = (value >= MAXVOLMP) ? MAXVOLMP
                            : (value <= 0) ? 0 : value;
                // value must be <= 127 and >= 0
                ctrlSet(channel, CTRLCH_MODWHL, dVal);
                break;
            case DOWNMODPITCH:
            case GLOBALDOWNMODPITCH:
                dVal = getController(channel, CTRLCH_MODWHL);
                dVal = dVal >= MAXVOLMP ? 120
                            : (dVal <= 0) ? 0
                                          : dVal - DELTAVOLMP;
                ctrlSet(channel, CTRLCH_MODWHL, dVal);
                break;
            case UPMODPITCH:
            case GLOBALUPMODPITCH:
                dVal = getController(channel, CTRLCH_MODWHL);
                dVal = (dVal >= MAXVOLMP) ? MAXVOLMP : dVal + DELTAVOLMP;
                ctrlSet(channel, CTRLCH_MODWHL, dVal);
                break;
            default:
                System.err.println("BAD ID RECEIVED, CAN'T CONTROL, GIT GUD");
                break;
        }
    }
    
/** Helper method to control specific instances of System actions on multiple
  * channels that directly interface with variable values and not states
  * @param chArray Integer array representing the channels affected. If only
  * one channel is affected, the array will be of size 1
  * @param id Identifier of the type of action taking place
  * @param value Value being adjusted, used by setting values
  */
    protected synchronized void ctrlValue(int[] chArray, int id, int value) {
        //Both modWheel and volume encompass the same range of valid integers
        //so they have been paired together
        for (int chan : chArray)
            ctrlValue(chan, id, value);
    }

/** Helper method designed to return an integer modified by a delta within
  * the bounds of zero and a maximized value
  * @param origin Integer representing the original value being modified
  * @param delta Integer representing the delta to be applied to the original
  * @param max Integer representing the max value binding the original
  * @return The properly modified bounded value
  */
    public static int incrementBoundedValue(int origin, int delta, int max) {
        if (delta > 0) //Increment positive
            return (origin+delta >= max) ? max : origin+delta;
        else { //Increment negative
            if (origin == max)
                return origin + (delta+1);
            else
                return (origin+delta <= 0) ? 0 : origin+delta;
        }
    }


/** Sets the pitch bend to the value specified on the channel in 
  * the parameter
  * @param channel Channel to set pitch bend into
  * @param pVal Value to set
  */
    protected synchronized void pitchSet(int channel, int pVal) {
        asChannels[channel].setPitchBend(pVal);
//         System.err.println(asChannels[channel].getPitchBend()
//                                  + " is found from " + pVal);
    }
    
/** Sets the pitch bend to the value specified on the channels 
  * in the parameter
  * @param channels Channels to set pitch bend into
  * @param pVal Value to set
  */
    protected synchronized void pitchSet(int[] channels, int pVal) {
        for(int chan : channels)
            pitchSet(chan, pVal);
    }

/** Gets the pitch bend from the channel in the parameter
  * @param channel Channel to get pitch bend from
  * @return The current pitch bend on the channel
  */
    public int getPitchChannel(int channel) {
        return asChannels[channel].getPitchBend();
    }

/** Toggles mono on a given channel
  * @param channel Channel to toggle on
  */
    protected synchronized void toggleMono(int channel) {
        boolean current = asChannels[channel].getMono();
        monoSet(channel, !current);
    }

/** Toggles mono on given channels
  * @param channels Channels to toggle on
  */
    protected synchronized void toggleMono(int[] channels) {
        for (int chan : channels) {
            toggleMono(chan);
        }
    }
    
/** Sets mono via boolean onto the channel in the parameter
  * @param channel Channel to toggle
  * @param b Boolean to determine if sustain is turning on or off
  */
    protected synchronized void monoSet(int channel, boolean b) {
        asChannels[channel].setMono(b);
        System.err.println(asChannels[channel].getMono()
                         + "is found from " + b);
    }

/** Sets mono via boolean onto the channels in the parameter
  * @param channels Channels to toggle
  * @param b Boolean to determine if sustain is turning on or off
  */
    protected synchronized void monoSet(int[] channels, boolean b) {
        for (int chan : channels)
            monoSet(chan, b);
    }

/** Gets the boolean representing if mono is on or off on given channel
  * @param channel Channel to get mono from
  * @return True if mono is on
  */
    public boolean getMonoChannel(int channel) {
        return asChannels[channel].getMono();
    }

/** Toggles solo on a given channel
  * @param channel Channel to toggle on
  */
    protected synchronized void toggleSolo(int channel) {
        boolean current = asChannels[channel].getSolo();
        soloSet(channel, !current);
    }

/** Toggles solo on given channels
  * @param channels Channels to toggle on
  */
    protected synchronized void toggleSolo(int[] channels) {
        for (int chan : channels)
            toggleSolo(chan);
    }
    
/** Sets solo via boolean onto the channel in the parameter
  * @param channel Channel to toggle
  * @param b Boolean to determine if sustain is turning on or off
  */
    protected synchronized void soloSet(int channel, boolean b) {
        asChannels[channel].setSolo(b);
        System.err.println(asChannels[channel].getSolo()
                         + "is found from " + b);
    }

/** Gets the boolean representing if solo is on or off on given channel
  * @param channel Channel to get solo from
  * @return True if solo is on
  */
    public boolean getSoloChannel(int channel) {
        return asChannels[channel].getSolo();
    }

/** Toggles sustain on and off onto the channel in the parameter
  * @param channel Channel to toggle
  */
	protected synchronized void toggleSustain(int channel) {
		boolean b = !getSustainChannel(channel);
		setSustain(channel, b);
    }

/** Toggles sustain onto the channels in the parameter
  * @param channels Channels to toggle sustain on
  */
    protected synchronized void toggleSustain(int[] channels) {
        for(int chan : channels)
            toggleSustain(chan);
    }
/** Sets sustain via boolean onto the channel in the parameter
  * @param channel Channel to toggle
  * @param b Boolean to determine if sustain is turning on or off
  */
    protected synchronized void setSustain(int channel, boolean b) {
        //if not on, set to zero, if on set to sustain
        int temp = (!b) ? 0 : CTRLCH_SUSTPED;
        asChannels[channel].controlChange(CTRLCH_SUSTPED, temp);
//         System.err.println("Sustain channel " + channel + " " + b);
    }
	
/** Sets sustain via boolean onto the channels in the parameter
  * @param channels Channels to toggle
  * @param b Boolean to determine if sustain is turning on or off
  */
	protected synchronized void setSustain(int[] channels, boolean b) {
		for(int chan : channels)
			setSustain(chan, b);
	}

/** Returns whether sustain is active on a given channel
  * @param channel Channel to get value from
  * @return True if sustain is on
  */
    public boolean getSustainChannel(int channel) {
        return (getController(channel, CTRLCH_SUSTPED) == CTRLCH_SUSTPED);
    }
// 
//     protected class ASClearMemoryListener implements ActionListener {
//     
//         public void actionPerformed(ActionEvent e) {
//             System.err.println("FREEMEMORY");
//             Runtime.getRuntime().gc();
//         }
//     }
}
