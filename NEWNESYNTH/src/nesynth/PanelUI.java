package nesynth;

public interface PanelUI {
	/** Gets the PanelID for this Panel
	  * @return The PanelID
	  */
	    public abstract int getPanelID();

	/** Changes the groupID of the listener for setting purposes
	  * @param groupID ID to set to the listener
	  */
	    public abstract void changeChannelGroupID(int groupID);

	/** Used to toggle binding mode of the listener
	  */    
	    public abstract void toggleChannelBindingMode();

	/** Used to update the display of this panel
	  * @param reset Value to determine how to update the display by specific
	  * implementation.
	  */
	    public abstract void updateDisplay(boolean reset);
}
