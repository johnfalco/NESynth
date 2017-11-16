/** Sortable list based off the SortedListInterface and the List class
  *
  * @Version 1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       List, SortedListInterface
  * @included      None
  */

package nesynth;

public class SortedList extends List implements SortedListInterface {

/** Default constructor for the sorted list
  */
    public SortedList() {
        super();
    }

/** Default constructor with the initial capacity specified
  * @param oCap Initial capacity used
  */
    public SortedList(int oCap) {
        super(oCap);
    }
    
/** Finds the targetted item through use of a binary search
  * @param target Object being searched for, "target"
  */
    @Override
    protected void find(Object target) {
        int first = 0;
        int last = this.lNum - 1;
        boolean unfinished = (first <= last);
        int compResult;
        Comparable tar = (Comparable)target;
        lFound = false;
        
        while (unfinished && !lFound) {
            lFoundPos = (first + last) / 2;
            this.lComps++;
            compResult = tar.compareTo(this.lArray[this.lFoundPos]);
            if (compResult == NO_COMPARE)
                throw new NullPointerException("Null Element Compared!");
            else if (compResult == 0)
                lFound = true;
            else { //if not equal...
                if (compResult < 0) // -1 returned
                    last = this.lFoundPos - 1;
                else
                    first = this.lFoundPos + 1;
                //better arangement of incrementation, avoids a repeat
                unfinished = (first <= last);
            }
        }
        //after finished being found, nothing else happens.
    }
    
    public boolean equals(SortedList otherList) {
        if (this.lNum != otherList.lNum)
            return false;
        else {
            for (int i = 0; i < lNum; i++) {
                if (!this.lArray[i].equals(otherList.lArray[i]))
                    return false;
            }
            //if it doesn't return false already, it's equal
            return true;
        }    
    }

    
/** Adds the element into the list in a sorted position
  * @param element Comparable object being added
  */
    public void add(Comparable element) {
        Comparable listObject;
        int thisLocation = 0;
        boolean unfinished;
        this.lComps = 0;
        //if not enough space, expand the abstract array
        if (this.lNum == this.lArray.length)
            expand();
        
        unfinished = (this.lNum > 0);
        
        while (unfinished) {
            listObject = (Comparable)lArray[thisLocation];
            this.lComps++;
            if(listObject.compareTo(element) == NO_COMPARE)
                throw new NullPointerException("Null Element Compared!");
            else if (listObject.compareTo(element) < 0) { //added > list element
                thisLocation++;
                unfinished = (thisLocation < this.lNum);
            } else
                unfinished = false;
        }
        
        for (int i = this.lNum; i > thisLocation; i--)
            this.lArray[i] = this.lArray[i-1]; //offsets elements to add in new element
        
        this.lArray[thisLocation] = element;
        this.lNum++;
    }
}