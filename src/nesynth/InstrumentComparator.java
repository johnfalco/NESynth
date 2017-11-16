package nesynth;

import java.util.*;

public class InstrumentComparator implements Comparator<SortableInstrument> {
    public int compare(SortableInstrument a, SortableInstrument b) {
        return a.compareTo(b);
    }
}