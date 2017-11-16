package nesynth;
/** Unsortable list based off the UnsortedListInterface and the List class
*
* @Version 1.0
* @author        John Falco
* @id            jpfalco
* @course        CSIS 491, MULT 500
* @assignment    NESynth
* @related       List, UnsortedListInterface
* @included      None
*/

public class UnsortedList extends List implements UnsortedListInterface {

/** Default constructor for the sorted list
*/
  public UnsortedList() {
      super();
  }

/** Default constructor with the initial capacity specified
* @param oCap Initial capacity used
*/
  public UnsortedList(int oCap) {
      super(oCap);
  }
  
/** Adds the element into the list in a sorted position
* @param element Object being added
*/
  public void add(Object element) {
      if (this.lNum == this.lArray.length)
          expand();
      this.lArray[lNum] = element;
      this.lNum++;
  }
  
/** Removes the object from the list
* @param element Object to be removed
* @return True if the object was found and removed, else false
*/
@Override
  public boolean remove(Object element) {
      find(element);
      
      if (lFound) {
          this.lArray[this.lFoundPos] = this.lArray[this.lNum - 1];
          this.lArray[this.lNum - 1] = null;
          this.lNum--;
      }
      
      return lFound;
  }
}
