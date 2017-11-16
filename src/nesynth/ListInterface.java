/** Interface used to identify different varieties of Lists
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

public interface ListInterface extends java.io.Serializable, Iterable<Object> {
    
/** Returns the size of the List
  * @return Size as an int
  */
    public int size();

/** Checks if the list contains the parameter object
  * @param element Object being checked for
  * @return True if found, else false
  */
    public boolean contains(Object element);
    
/** Checks if the list contains the parameter object, and removes it
  * @param element Object being checked for
  * @return True if found and removed, else false and not removed
  */
    public boolean remove(Object element);
    
/** Returns the parameter object if it is on the list
  * @param element Object being checked for
  * @return The object if found, else a null is returned
  */
    public Object get(Object element);

/** Returns the position to the start of the list
  */
    public void reset();

/** Gets the current object on the list and returns it, progressing
  * the list as well onto the next object
  * @return Object at this position
  */
    public Object getNext();

/** Returns this list as the 'almighty' and powerful String
  * @return String representation of this list
  */
    public String toString();
}