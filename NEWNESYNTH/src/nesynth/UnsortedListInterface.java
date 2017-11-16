/** Unsortable list interface based off the ListInterface
  *
  * @Version 1.0
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       ListInterface
  * @included      None
  */

package nesynth;

public interface UnsortedListInterface extends ListInterface {
/** Adds this object onto the end of the list, unsorted
  * @param element Object being added
  */
    public void add(Object element);
}