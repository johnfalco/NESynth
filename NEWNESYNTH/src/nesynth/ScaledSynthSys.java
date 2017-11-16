package nesynth;

public class ScaledSynthSys extends SynthSys {
/** Constructs the ScaledSynthSys object 
  * @param sysID The action ID
  * @param sysVal The action value
  * @param sysChannels The channels to be impacted
  */
    public ScaledSynthSys(int sysID, int[] sysVals, int[] sysChannels) {
        super(sysID, sysVals, sysChannels);
    }
}