/** The main class for the NESynth Project, determines which
  * user interface to access via loading from a batch file
  * that contains the argument for which version to utilize
  * Passing "Original" will load up the initial GUIDisplay for the project
  * Passing "Revised" will load up the revised GUIDisplay for the project
  * Any other value passed will exit the program.
  *
  * @Version 1.0
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       GUIDisplay, GUIv2
  * @included      None
  */
  
package nesynth;

public class NESynth {

/** Executes the NESynth program
  * @param args Arguments to be passed at runtime
  */
    public static void main(String[] args) {
        if (args.length < 1) {
            // TEST
            runAnyway();
            // TEST
//            endProgram("Not enough args passed");
        } else if (args.length > 1) {
            endProgram("Too many args passed");
        } else {
            if (args[0].equals("Original")) {
                GUIDisplay initial = new GUIDisplay();
            } else if (args[0].equals("Revised")) {
                GUIv2 revised = new GUIv2();
            } else {
                endProgram("Bad args passed");
            }
        }
    }
    
/** Runs program anyway using the initial state
  */
    private static void runAnyway() {
        System.err.println("Pass better args next time, "
                         + "I'll run it anyway though :)");
            //TEST
            System.err.println("RUNNING NEW MERCY :3");
            GUIv2 newMercy = new GUIv2();
            //TEST        
        //GUIDisplay mercy = new GUIDisplay();
        
    }

/** Handles errors in a rather humorous way, considering how args are passed.
  * @param problem String detailing the error encountered while passing args
  */
    private static void endProgram(String problem) {
        System.err.println("Error has occured! You did the following wrong:"
                + "\n\n     " + problem.toUpperCase() + "\n\nToo bad! Try"
                + " not to do that next time. Maybe this won't happen ;3");
    }
}