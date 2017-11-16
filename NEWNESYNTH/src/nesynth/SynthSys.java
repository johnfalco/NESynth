/** Object to hold all information pertaining to System actions on a
  * Synth
  *
  * @Version       1.2
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SynthType, SynthConstants
  * @included      None
  */

package nesynth;
import static nesynth.SynthConstants.*;

public class SynthSys implements SynthType {
/** Stores the ID that identifies the action stored to the SynthSys */
    private int ssID;
/** Stores the value that changes how the action is carried out */
    private int[] ssVals;
/** Stores the channels that are impacted by the action */
    private int[] ssChans;
    
/** Constructs the SynthSys object 
  * @param sysID The action ID
  * @param sysVal The action value
  * @param sysChannels The channels to be impacted
  */
    public SynthSys(int sysID, int[] sysVals, int[] sysChannels) {
        this.ssID = sysID;
        this.ssVals = sysVals;
        this.ssChans = sysChannels;
    }
    
/** Compares this to another object
  * @param e Object being compared
  * @return Comparable value
  */
    public int compareTo(Object e) {
        if (e == null || !(e instanceof SynthSys))
            return SortedList.NO_COMPARE;
        else {
            SynthSys synSys = (SynthSys)e;
            if (this.ssID < synSys.ssID)
                return -1;
            else if (this.ssID > synSys.ssID)
                return 1;
            else
                return 0;
        }
    }

/** Compares this SynthSys to another SynthSys
  * @param synSys Other SynthSys being compared against
  * @return True if equal
  */
    public boolean equals(SynthSys synSys) {
        if (synSys == null) return false; //if null immediate false return
        if (this.ssID == synSys.ssID && this.ssVals == synSys.ssVals
         && this.ssChans == synSys.ssChans)
            return true;
        else
            return false;
    
    }

/** Returns the ID from the SynthSys
  * @return The ID associated to the SynthSys
  */
    public int getSSID() {
        return this.ssID;
    }

/** Returns the value from the SynthSys
  * @return The value associated to the SynthSys
  */    
    public int getSSVal() {
        return this.ssVals[0];
    }
    
/** Returns the values from the SynthSys
  * @return The values associated to the SynthSys
  */
    public int[] getSSVals() {
        return this.ssVals;
    }

/** Returns the channels from the SynthSys
  * @return The channels associated to the SynthSys
  */
    public int[] getSSChans() {
        return this.ssChans;
    }

/** Returns all data from the SynthType as an Object array
  * @return Object Array holding all data
  */
    public Object[] getData() {
        return new Object[]{this.ssID, this.ssVals, this.ssChans};
    }

/** Method to return this object as a string
  * @return String representation
  */
    @Override
    public String toString() {
        return this.convertConstant() 
                + "-V: " + arrayAsString(this.ssVals, "[", ",", "]")
                + " C: " + arrayAsString(this.ssChans, "[", ",", "]");
    }

/** Gets a string based on the type of ActionID associated with the given
  * SynthSys.
  * @return The string representation of the ActionID
  */
    private String convertConstant() {
        switch(this.ssID) {
            case HALTALLSYNTH:          return "HALTALLSYNTH";
            case GLOBALRIGHTUPOCTAVE:   return "GLOBALRIGHTUPOCTAVE";
            case GLOBALRIGHTDOWNOCTAVE: return "GLOBALRIGHTDOWNOCTAVE";
            case GLOBALLEFTUPOCTAVE:    return "GLOBALLEFTUPOCTAVE";
            case GLOBALLEFTDOWNOCTAVE:  return "GLOBALLEFTDOWNOCTAVE";
            case GLOBALLOCALCONTROL:    return "GLOBALLOCALCONTROL";
            case GLOBALHALTCHANNEL:     return "GLOBALHALTCHANNEL";
            case GLOBALRESETCHANNEL:    return "GLOBALRESETCHANNEL";
            case GLOBALCHANGEPROGRAM:   return "GLOBALCHANGEPROGRAM";
            case GLOBALTOGGLEMONO:      return "GLOBALTOGGLEMONO";
            case GLOBALTOGGLESOLO:      return "GLOBALTOGGLESOLO";
            case GLOBALTOGGLEMUTE:      return "GLOBALTOGGLEMUTE";
            case GLOBALDOWNPITCHBEND:   return "GLOBALDOWNPITCHBEND";
            case GLOBALUPPITCHBEND:     return "GLOBALUPPITCHBEND";
            case GLOBALSETPITCHBEND:    return "GLOBALSETPITCHBEND";
            case GLOBALDECRVOL:         return "GLOBALDECRVOL";
            case GLOBALCRESVOL:         return "GLOBALCRESVOL";
            case GLOBALSETVOL:          return "GLOBALSETVOL";
            case GLOBALDOWNMODPITCH:    return "GLOBALDOWNMODPITCH";
            case GLOBALUPMODPITCH:      return "GLOBALUPMODPITCH";
            case GLOBALSETMODPITCH:     return "GLOBALSETMODPITCH";
            case GLOBALTOGGLESUSTAIN:   return "GLOBALTOGGLESUSTAIN";
            case GLOBALSETSUSTAIN:      return "GLOBALSETSUSTAIN";
            case RIGHTUPOCTAVE:         return "RIGHTUPOCTAVE";
            case RIGHTDOWNOCTAVE:       return "RIGHTDOWNOCTAVE";
            case LEFTUPOCTAVE:          return "LEFTUPOCTAVE";
            case LEFTDOWNOCTAVE:        return "LEFTDOWNOCTAVE";
            case LOCALCONTROL:          return "LOCALCONTROL";
            case HALTCHANNEL:           return "HALTCHANNEL";
            case RESETCHANNEL:          return "RESETCHANNEL";
            case CHANGEPROGRAM:         return "CHANGEPROGRAM";
            case TOGGLEMONO:            return "TOGGLEMONO";
            case TOGGLESOLO:            return "TOGGLESOLO";
            case TOGGLEMUTE:            return "TOGGLEMUTE";
            case DOWNPITCHBEND:         return "DOWNPITCHBEND";
            case UPPITCHBEND:           return "UPPITCHBEND";
            case SETPITCHBEND:          return "SETPITCHBEND";
            case DECRVOL:               return "DECRVOL";
            case CRESVOL:               return "CRESVOL";
            case SETVOL:                return "SETVOL";
            case DOWNMODPITCH:          return "DOWNMODPITCH";
            case UPMODPITCH:            return "UPMODPITCH";
            case SETMODPITCH:           return "SETMODPITCH";
            case TOGGLESUSTAIN:         return "TOGGLESUSTAIN";
            case SETSUSTAIN:            return "SETSUSTAIN";
            default:                    return "Bad Action ID";
        }
    }
    
//     private String convertVals(String edge1, String edge2, String divider) {
//         StringBuilder returnStr = new StringBuilder();
//         int pos;
//         returnStr.append(edge1);
//         for (pos = 0; pos < this.ssVals.length-1; pos++) {
//             returnStr.append(this.ssVals[pos] + divider);
//         }
//         returnStr.append(this.ssVals[pos] + edge2);
//         return returnStr.toString();        
//     }
//     
//     private String convertChans(String edge1, String edge2, String divider) {
//         StringBuilder returnStr = new StringBuilder();
//         int pos;
//         returnStr.append(edge1);
//         for (pos = 0; pos < this.ssChans.length-1; pos++) {
//             returnStr.append(this.ssChans[pos] + divider);
//         }
//         returnStr.append(this.ssChans[pos] + edge2);
//         return returnStr.toString();
//     }
}