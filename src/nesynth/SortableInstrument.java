/** Wrapper class for Instrument that allows sorting based on the bank and
  * program of a given Instrument.
  *
  * @Version 1.0
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491 & MULT 500
  * @assignment    NESynth
  * @related       SortedList
  * @included      None
  */

package nesynth;

import javax.sound.midi.*;

public class SortableInstrument extends Instrument
                                            implements Comparable {

/** Constructs the SortableInstrument based on the Instrument in the parameter
  * @param inst Instrument being constructed from
  */
    public SortableInstrument(Instrument inst) {
        super(inst.getSoundbank(), inst.getPatch(),
              inst.getName(), (Class)inst.getData());
    }

/** Method required to be defined by the abstract class Instrument, does not
  * do anything and should not be used
  * @return Null always
  */
    @Deprecated
    public Object getData() {
        System.err.println("DONT DO THIS");
        return null;
    }

/** Compares the SortedInstrument to the Object in the parameter
  * @param e Object being compared to
  * @return A sort-compatable integer if the object is a SortableInstrument,
  * otherwise a NO_COMPARE is returned representing an error.
  */
    public int compareTo(Object e) {
        if (e == null || !(e instanceof SortableInstrument))
            return SortedList.NO_COMPARE;
        else {
            SortableInstrument si = (SortableInstrument)e;
            //Compare the sorted patches
           if (this.getPatch().getBank() < si.getPatch().getBank())
               return -1;
           else if (this.getPatch().getBank() > si.getPatch().getBank())
               return 1;
           else {
               if (this.getPatch().getProgram()
                       < si.getPatch().getProgram())
                   return -1;
               else if (this.getPatch().getProgram()
                           > si.getPatch().getProgram())
                   return 1;
               else
                   return 0;
           }
        }
    }
    
/** Compares this SortableInstrument to another based on the bank and program
  * @param bank Bank being compared
  * @param program Program being compared
  * @return A sort-compatable integer
  */
    public int compareByBankProgram(int bank, int program) {
        if (this.getPatch().getBank() > bank)
            return -1;
        else if (this.getPatch().getBank() < bank)
            return 1;
        else {
            if (this.getPatch().getProgram() > program)
                return -1;
            else if (this.getPatch().getProgram() < program)
                return 1;
            else
                return 0;
        }
    }

/** Compares this SortableInstrument to another based on the bank and program
  * of a patch
  * @param p Patch being compared to
  * @return A sort-compatable integer
  */
    public int compareByBankProgram(Patch p) {
       if (this.getPatch().getBank() > p.getBank())
           return -1;
       else if (this.getPatch().getBank() < p.getBank())
           return 1;
       else {
           if (this.getPatch().getProgram() > p.getProgram())
               return -1;
           else if (this.getPatch().getProgram() < p.getProgram())
               return 1;
           else
               return 0;
       }
    }
    
    public int getBank() {
        return this.getPatch().getBank();
    }
    
    public int getProgram() {
        return this.getPatch().getProgram();
    }
    
    public Patch getPatch() {
        return super.getPatch();
    }
    
/** Returns the name of the instrument and only that
  * @return The instrument name
  */
    public String getInstName() {
        return super.getName();
    }

/** Fully returns this SortableInstrument as a string including the
  * name, bank and program of the instrument
  * @return The full string representation of this SortableInstrument
  */
    public String toString() {
        return super.getName() + "- B:" + super.getPatch().getBank()
                               + " P:" + super.getPatch().getProgram();
    }
}