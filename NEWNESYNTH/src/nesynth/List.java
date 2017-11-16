/** Custom list class that is compatable with the Iterator interface.
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

import java.util.*;

public class List implements ListInterface, Iterable<Object> {

/** Protected constant for the default capacity */
    protected static final int DEFCAP = 20;
/** Protected storage for the initial cap */
    protected int lInitCap;
/** Protected storage for the abstracted array holding the data */
    protected Object[] lArray;
/** Protected storage for the number of elements */
    protected int lNum;
/** Protected storage for the position  */
    protected int lPos;
/** Protected storage for the results of the find method */
    protected boolean lFound;
/** Protected storage for the position of the found element */
    protected int lFoundPos;
/** Protected storage of the number of compares done on this list */
    protected int lComps;
    
/** Default constructor for the list
  */
    public List() {
        this.lArray = new Object[DEFCAP];
        this.lInitCap = DEFCAP;
        this.lComps = 0;
    }

/** Default constructor with the initial capacity specified
  * @param oCap Initial capacity used
  */
    public List(int oCap) {
        this.lArray = new Object[oCap];
        this.lInitCap = oCap;
        this.lComps = 0;
    }

/** Method to make class Iterable
  */
    public Iterator<Object> iterator() {
        this.reset();
        return Arrays.asList(lArray).listIterator();
    }

/** Expands the abstract array containing the list contents
  */
    protected void expand() {
        Object[] exArray = new Object[this.lArray.length + this.lInitCap];
        
        //copies contents
        for (int i = 0; i < this.lNum; i++)
            exArray[i] = this.lArray[i];
        
        this.lArray = exArray;
    }

/** Finds the object within the list
  * @param target Object being looked for
  */
    protected void find(Object target) {
        boolean unfinished;
        this.lFoundPos = 0;
        this.lComps = 0;
        this.lFound = false;
        unfinished = (this.lFoundPos < this.lNum);
        while (unfinished && !lFound) {
            this.lComps++;
            if (this.lArray[this.lFoundPos].equals(target))
                this.lFound = true;
            else {
                this.lFoundPos++;
                unfinished = (this.lFoundPos < lNum);
            }
        }
        //if out of the logic, nothing else is needed.
    }
    
/** Returns the # of objects in this list
  * @return Int value of number of objects
  */
    public int size() {
        return this.lNum;
    }
    
/** Checks if the element specified is in the list
  * @param element Object being checked for
  * @return True if it was found, else false
  */
    public boolean contains(Object element) {
        find(element);
        return lFound;
    }

/** Emptys the list of all elements
  */
    public void empty() {
        this.lNum = 0;
        this.lComps = 0;
        this.lFoundPos = 0;
        this.lFound = false;
        this.lArray = new Object[DEFCAP];
        this.reset();
    }

/** Checks if the element specified is in the list, and removes it if found
  * @param element Object being checked for
  * @return True if it was found and removed, else false
  */
    public boolean remove(Object element) {
        find(element);
        if (!lFound) //if it coulnd't be found, return false
            return false; //could return lFound, but this is more explicit
        else {
            for (int i = lFoundPos; i <= lNum - 2; i++)
                this.lArray[i] = this.lArray[i + 1];
            this.lArray[lNum - 1] = null;
            this.lNum--;
            return true; //again, more explicit than simply returning found
        }
    }
    
/** Checks if the element specified is in the list and returns it
  * @param element Object being checked for
  * @return The parameter object if found, else nul if not found
  */
    public Object get(Object element) {
        find(element);
        //if found, return the object, else return null
        return (lFound) ? this.lArray[lFoundPos] : null;
    }
    
    public int getObjPosition(Object element) {
        find(element);
        //if found, return the object, else return null
        return (lFound) ? lFoundPos : null;
    }
        
/** Restores the current position of the list to the start
  */
    public void reset() {
        this.lPos = 0;
    }
    
    public boolean equals(List otherList) {
        return (this.lArray == otherList.lArray 
             && this.lNum == otherList.lNum);    
    }

/** Gets the next Object elementn the list, returns it, and sets the current
  * list position to one object bellow the returned object
  * @return The Object elementn the current position of the list
  */
    public Object getNext() {
        Object current = this.lArray[lPos];
        //if its the end of the list, loops back to the start
        if (this.lPos >= this.lNum - 1)
            this.lPos = 0;
        //otherwise, it sets to the next object
        else
            this.lPos++;
        
        return current;
    }

/** Gets the number of compares done for one find operation
  * @return The number of compares done with one find operation
  */
    public int getCompares() {
        return this.lComps;
    }

/** Replaces element with equivalent element
  * @param element Object being added
  * @return True if replaced successfully
  */
    public boolean replace(Object element) {
        find(element);
        if(lFound) {
            this.lArray[lFoundPos] = element;
            return true;
        } else {
            return false;
        }
    }
  
/** Builds a string from the contents of this list
  * @return String of this list
  */
    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < lNum; i++)
            temp.append(this.lArray[i] + "\n");
        
        return temp.toString();
    }
}