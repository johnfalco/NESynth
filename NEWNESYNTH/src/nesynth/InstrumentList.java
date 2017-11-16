/** Extended SortedList used for sorting through SortableInstruments for
  * display purposes, emptying the list as well as obtaining Instruments
  * based on their position on the list.
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SortableInstrument, SortedList
  * @included      None
  */

package nesynth;

public class InstrumentList extends SortedList {

/** Constant used to represent the Bank in the getBankProgramByPos method */
    public static final int BANK = 0;
/** Constant used to represent the Program in the getBankProgramByPos method */
    public static final int PROGRAM = 1;
    
/** Basic constructor of the Instrument list
  */
    public InstrumentList() {
        super();
    }

/** Constructs the InstrumentList with the parameter size
  * @param oCap Capacity to initially construct with
  */
    public InstrumentList(int oCap) {
        super(oCap);
    }

/** Emptys the list of all SortedInstruments
  * @return True if emptied without any problems. Should always return true.
  */
    public boolean emptyList() {
        Object[] array = new Object[this.size()];
        this.lArray = array;
        reset();
        return true;
    }

/** Returns the SortableInstrument from the parameter position
  * @param pos Position being obtained from
  * @return The SortableInstrument at the given position
  */
    public SortableInstrument getInstrumentFromPos(int pos) {
        return (SortableInstrument)this.lArray[pos];
    }

/** Returns an array representing the Bank and Program of an instrument
  * based on the position being checked from
  * @param pos Position being checked
  * @return Integer array in the format of {bank, program}
  */
    public int[] getBankProgramFromPos(int pos) {
        SortableInstrument temp = getInstrumentFromPos(pos);
        int[] returnArray = {temp.getPatch().getBank(),
                             temp.getPatch().getProgram()};
        return returnArray;
    }

/** Returns the position of a SortedInstrument based on the bank and program
  * of the instrument.
  * @param bank Bank of the instrument in its soundfont file
  * @param program Program of the instrument in its soundfont file
  * @return The position on the list
  */
    public int getPositionByBankProgram(int bank, int program) {
        int first = 0;
        int last = this.lNum - 1;
        boolean unfinished = (first <= last);
        int compResult;
        lFound = false;
        SortableInstrument temp;
        while (unfinished && !lFound) {
            lFoundPos = (first + last) / 2;
            this.lComps++;
            temp = (SortableInstrument)this.lArray[this.lFoundPos];
            compResult = temp.compareByBankProgram(bank, program);
            if (compResult == NO_COMPARE)
                throw new NullPointerException("Null Element Compared!");
            else if (compResult == 0)
                return this.lFoundPos;
            else { //if not equal...
                if (compResult < 0) // -1 returned
                    last = this.lFoundPos - 1;
                else
                    first = this.lFoundPos + 1;
                //better arangement of incrementation, avoids a repeat
                unfinished = (first <= last);
            }
        }
        return NO_COMPARE;
    }
}