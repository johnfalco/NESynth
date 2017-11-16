/** SortedListInterface based upon the ListInteface
  *
  * @Version 1.0
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       ListInterface
  * @included      None
  *
  */

package nesynth;

public interface SortedListInterface extends ListInterface {
    public static final int NO_COMPARE = -2;

/** Adds this object onto the end of the list and sorts it
  * @param element Comparable Object being added
  */
    public void add(Comparable element);;
}