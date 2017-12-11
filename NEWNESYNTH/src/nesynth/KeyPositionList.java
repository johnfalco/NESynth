/** List designed to build and store each KeyPosition used by the User
  * Interface and provide ways to access it in specialized ways for
  * cleaner programming.
  *
  * @Version 1.2
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       KeyPosition, SortedList
  * @included      None
  */

package nesynth;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import static nesynth.KeyConstants.*;
import static nesynth.SynthConstants.*;

public class KeyPositionList extends SortedList {

    private KeyPosition[] kplActive;
    
    private int kplActNum;
    
    private char kplBnk;
    
/** Default construtor for the KeyPositionList
  */
    public KeyPositionList() {
        this('A');
    }

    public KeyPositionList(char bnk) {
        super(93);
        this.kplBnk = bnk;
        this.kplActive = new KeyPosition[93];
        switch(bnk) {
            case 'A':
            default:
                buildDefault();
                break;
            case 'B':
                setSmall(bnk);
                break;
            case 'C':
            	setRevised(bnk);
            	break;
        }        
    }

    private void buildDefault() {
        System.err.println("Creating default list");
        setLeftKeyboard('A');
        setPercAuxiliary('A');
        setRightKeyboard('A');
        setSynthSystem('A');
        setSequence('A');
    }

    public UnsortedList getActiveKeyPositions() {
        UnsortedList returnList = new UnsortedList(this.lNum);
        KeyPosition temp = (KeyPosition)this.getNext(); 
        do {
            if (temp.getSynthType() != null)
                returnList.add(temp);
            temp = (KeyPosition)this.getNext();
        } while (temp != null);
        return returnList;   
    }

/** Basic constructor for the KeyPositionList, loads the data from a file
  * @param filename File to attempt a load from
  */
    public KeyPositionList(String filename) {
        super();
        if (filename == null) {
            //TEST
            setSmall('B');            
            //TEST
//             buildDefault('A');            
        } else {
            try {
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream loader = new ObjectInputStream(file);
                int size = loader.readInt();
                for (int i=0; i<size; i++) {
                    this.add((KeyPosition)loader.readObject());
                }
                reset();
                loader.close();
                file.close();
            } catch (Exception ex) {
                ex.printStackTrace();               
                System.err.print("File had issues, ");
                buildDefault();
            }
        }       
    }

/** Helper method to set the left keyboard keys
  */
    private void setLeftKeyboard(char bnk) {
        //Left Keyboard Key #1
        this.add(new KeyPosition(bnk, 1 , CF, 10 ,188, 
          new Rectangle(10 ,188,32,51), new Rectangle(10 ,239,40,51), 
          KeyEvent.VK_Q, 
          new Note(24, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #2
        this.add(new KeyPosition(bnk, 2 , RL, 42 ,188,
          new Rectangle(42 ,188,16,51), KeyEvent.VK_W, 
          new Note(25, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #3
        this.add(new KeyPosition(bnk, 3 , DGA,50 ,188,
          new Rectangle(58 ,188,24,51), new Rectangle(50 ,239,40,51),
          KeyEvent.VK_E, 
          new Note(26, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #4
        this.add(new KeyPosition(bnk, 4 , RL, 82 ,188,
          new Rectangle(82 ,188,16,51), KeyEvent.VK_R, 
          new Note(27, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #5
        this.add(new KeyPosition(bnk, 5 , EB, 90 ,188,
          new Rectangle(98 ,188,32,51), new Rectangle(90 ,239,40,51),
          KeyEvent.VK_T, 
          new Note(28, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #6
        this.add(new KeyPosition(bnk, 6 , CF,  130,188,
          new Rectangle(130,188,32,51), new Rectangle(130,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(29, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #7
        this.add(new KeyPosition(bnk, 7 , RL, 162,188,
          new Rectangle(162,188,16,51), KeyEvent.VK_UNDEFINED, 
          new Note(30, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));        
        //Left Keyboard Key #8
        this.add(new KeyPosition(bnk, 8 , DGA,170,188,
          new Rectangle(178,188,24,51), new Rectangle(178,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(31, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #9
        this.add(new KeyPosition(bnk, 9 , RL, 202,188,
          new Rectangle(202,188,16,51), KeyEvent.VK_UNDEFINED, 
          new Note(32, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #10
        this.add(new KeyPosition(bnk, 10, DGA,210,188,
          new Rectangle(218,188,24,51), new Rectangle(218,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(33, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));       
        //Left Keyboard Key #11
        this.add(new KeyPosition(bnk, 11, RL, 242,188,
          new Rectangle(242,188,16,51), KeyEvent.VK_UNDEFINED, 
          new Note(34, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));     
        //Left Keyboard Key #12
        this.add(new KeyPosition(bnk, 12, EB, 250,188,
          new Rectangle(258,188,32,51), new Rectangle(250,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(35, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #13
        this.add(new KeyPosition(bnk, 13, CF,  290,188,
          new Rectangle(290,188,32,51), new Rectangle(290,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(36, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #14
        this.add(new KeyPosition(bnk, 14, RL, 322,188,
          new Rectangle(322,188,16,51), new Rectangle(322,188,16,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(37, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #15
        this.add(new KeyPosition(bnk, 15, DGA,330,188,
          new Rectangle(338,188,24,51), new Rectangle(338,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(38, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #16
        this.add(new KeyPosition(bnk, 16, RL, 362,188,
          new Rectangle(362,188,16,51), new Rectangle(362,188,16,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(39, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #17
        this.add(new KeyPosition(bnk, 17, EB, 370,188,
          new Rectangle(378,188,32,51), new Rectangle(378,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(40, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #18
        this.add(new KeyPosition(bnk, 18, CF,  410,188,
          new Rectangle(410,188,32,51), new Rectangle(410,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(41, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #19
        this.add(new KeyPosition(bnk, 19, RL, 442,188,
          new Rectangle(442,188,16,51), new Rectangle(442,188,16,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(42, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #20
        this.add(new KeyPosition(bnk, 20, DGA,450,188,
          new Rectangle(458,188,24,51), new Rectangle(458,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(43, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #21
        this.add(new KeyPosition(bnk, 21, RL, 482,188,
          new Rectangle(482,188,16,51), new Rectangle(482,188,16,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(44, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #22
        this.add(new KeyPosition(bnk, 22, DGA,490,188,
          new Rectangle(498,188,24,51), new Rectangle(490,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(45, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #23
        this.add(new KeyPosition(bnk, 23, RL, 522,188,
          new Rectangle(522,188,16,51), new Rectangle(522,188,16,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(46, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Left Keyboard Key #24
        this.add(new KeyPosition(bnk, 24, EB, 530,188,
          new Rectangle(310,188,24,51), new Rectangle(530,239,40,51),
          KeyEvent.VK_UNDEFINED, 
          new Note(47, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
    }

/** Helper method to set percussion and Auxiliary keys
  */
    private void setPercAuxiliary(char bnk) {
        //Percussion Key #1
        this.add(new KeyPosition(bnk, 25, PRC, 
          10,118, 78, 44, KeyEvent.VK_A, 
          new Note(36, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Percussion Key #2
        this.add(new KeyPosition(bnk, 26, PRC, 
          67, 64, 78, 44,KeyEvent.VK_S,
          new Note(38, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Percussion Key #3
        this.add(new KeyPosition(bnk, 27, PRC,
          158, 56, 78, 44,KeyEvent.VK_D,
          new Note(44, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Percussion Key #4
        this.add(new KeyPosition(bnk, 28, PRC,
          229,107, 78, 44,KeyEvent.VK_F,
          new Note(49, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #1
        this.add(new KeyPosition(bnk, 29, AUX,
          172, 10, 31, 33,KeyEvent.VK_G,
          new Note(37, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #2
        this.add(new KeyPosition(bnk, 30, AUX,
          218, 10, 31, 33,KeyEvent.VK_H,
          new Note(41, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #3
        this.add(new KeyPosition(bnk, 31, AUX,
          263, 20, 31, 33,KeyEvent.VK_J,
          new Note(48, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #4
        this.add(new KeyPosition(bnk, 32, AUX,
          300, 43, 31, 33,KeyEvent.VK_K,
          new Note(55, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
    }

/** Helper method to set the right keyboard keys
  */
    private void setRightKeyboard(char bnk) {
        //Right Keyboard Key #1
        this.add(new KeyPosition(bnk, 33, CF, 50,500,
          new Rectangle( 50,500,25,40), new Rectangle( 50,540,31,40),
          KeyEvent.VK_Y, new ScaledNote(53, 105, Integer.MAX_VALUE, 0,
                                         RIGHTOCTAVE)));
        //Right Keyboard Key #2
        this.add(new KeyPosition(bnk, 34, RL,  75,500,
          new Rectangle( 75,500,12,40), KeyEvent.VK_UNDEFINED,
          new ScaledNote(54, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #3
        this.add(new KeyPosition(bnk, 35, DGA, 81,500,
          new Rectangle( 87,500,19,40), new Rectangle( 81,540,31,40),
          KeyEvent.VK_U, new ScaledNote(55, 105, Integer.MAX_VALUE, 0,
                                         RIGHTOCTAVE)));
        //Right Keyboard Key #4
        this.add(new KeyPosition(bnk, 36, RL, 106,500,
          new Rectangle(106,500,12,40), KeyEvent.VK_UNDEFINED,
          new ScaledNote(56, 105, Integer.MAX_VALUE, 0,
                          RIGHTOCTAVE)));
        //Right Keyboard Key #5
        this.add(new KeyPosition(bnk, 37, DGA,112,500,
          new Rectangle(118,500,19,40), new Rectangle(112,540,31,40),
          KeyEvent.VK_I, new ScaledNote(57, 105, Integer.MAX_VALUE, 0,
                                         RIGHTOCTAVE)));
        //Right Keyboard Key #6
        this.add(new KeyPosition(bnk, 38, RL, 137,500,
          new Rectangle(137,500,12,40), KeyEvent.VK_UNDEFINED,
          new ScaledNote(58, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #7
        this.add(new KeyPosition(bnk, 39, EB, 143,500,
          new Rectangle(149,500,25,40), new Rectangle(143,540,31,40),
          KeyEvent.VK_O, new ScaledNote(59, 105, Integer.MAX_VALUE, 0,
                                          RIGHTOCTAVE)));
        //Right Keyboard Key #8
        this.add(new KeyPosition(bnk, 40, CF,  174,500,
          new Rectangle(174,500,25,40), new Rectangle(174,540,31,40),
          KeyEvent.VK_P, new ScaledNote(60, 105, Integer.MAX_VALUE, 0,
                                          RIGHTOCTAVE)));
        //Right Keyboard Key #9
        this.add(new KeyPosition(bnk, 41, RL, 199,500,
          new Rectangle(199,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(61, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #10
        this.add(new KeyPosition(bnk, 42, DGA,205,500,
          new Rectangle(211,500,19,40), new Rectangle(205,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(62, 105, Integer.MAX_VALUE, 0,
                                                 RIGHTOCTAVE)));
        //Right Keyboard Key #11
        this.add(new KeyPosition(bnk, 43, RL, 230,500,
          new Rectangle(230,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(63, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #12
        this.add(new KeyPosition(bnk, 44, EB, 236,500,
          new Rectangle(242,500,25,40), new Rectangle(236,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(64, 105, Integer.MAX_VALUE, 0,
                                                 RIGHTOCTAVE)));
        //Right Keyboard Key #13
        this.add(new KeyPosition(bnk, 45, CF,  267,500,
          new Rectangle(267,500,25,40), new Rectangle(267,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(65, 105, Integer.MAX_VALUE, 0,
                                                 RIGHTOCTAVE)));
        //Right Keyboard Key #14
        this.add(new KeyPosition(bnk, 46, RL, 292,500,
          new Rectangle(292,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(66, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #15
        this.add(new KeyPosition(bnk, 47, DGA,298,500,
          new Rectangle(304,500,19,40), new Rectangle(298,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(67, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));      
        //Right Keyboard Key #16
        this.add(new KeyPosition(bnk, 48, RL, 323,500,
          new Rectangle(323,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(54, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #17
        this.add(new KeyPosition(bnk, 49, DGA,329,500,
          new Rectangle(335,500,19,40), new Rectangle(329,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(69, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));
        //Right Keyboard Key #18
        this.add(new KeyPosition(bnk, 50, RL, 354,500,
          new Rectangle(354,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(70, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #19
        this.add(new KeyPosition(bnk, 51, EB, 360,500,
          new Rectangle(366,500,25,40), new Rectangle(360,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(71, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));
        //Right Keyboard Key #20
        this.add(new KeyPosition(bnk, 52, CF,  391,500,
          new Rectangle(391,500,25,40), new Rectangle(391,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(72, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));
        //Right Keyboard Key #21
        this.add(new KeyPosition(bnk, 53, RL, 416,500,
          new Rectangle(416,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(73, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #22
        this.add(new KeyPosition(bnk, 54, DGA,422,500,
          new Rectangle(428,500,19,40), new Rectangle(422,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(74, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));
        //Right Keyboard Key #23
        this.add(new KeyPosition(bnk, 55, RL, 447,500,
          new Rectangle(447,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(75, 105, Integer.MAX_VALUE, 0,
                              RIGHTOCTAVE)));
        //Right Keyboard Key #24
        this.add(new KeyPosition(bnk, 56, EB, 453,500,
          new Rectangle(459,500,25,40), new Rectangle(453,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(76, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));
        //Right Keyboard Key #25
        this.add(new KeyPosition(bnk, 57, CF,  484,500,
          new Rectangle(484,500,25,40), new Rectangle(484,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(77, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #26
        this.add(new KeyPosition(bnk, 58, RL, 509,500,
          new Rectangle(509,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(78, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #27
        this.add(new KeyPosition(bnk, 59, DGA,515,500,
          new Rectangle(521,500,19,40), new Rectangle(515,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(79, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));
        //Right Keyboard Key #28
        this.add(new KeyPosition(bnk, 60, RL, 540,500,
          new Rectangle(540,500,12,40),KeyEvent.VK_UNDEFINED,
          new ScaledNote(80, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #29
        this.add(new KeyPosition(bnk, 61, DGA,546,500,
          new Rectangle(552,500,19,40), new Rectangle(546,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(81, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #30
        this.add(new KeyPosition(bnk, 62, RL, 571,500,
          new Rectangle(571,500,12,40), KeyEvent.VK_UNDEFINED,
          new ScaledNote(82, 105, Integer.MAX_VALUE, 0,
                            RIGHTOCTAVE)));
        //Right Keyboard Key #31
        this.add(new KeyPosition(bnk, 63, EB, 577,500,
          new Rectangle(583,500,25,40), new Rectangle(577,540,31,40),
          KeyEvent.VK_UNDEFINED, new ScaledNote(83, 105, Integer.MAX_VALUE, 0,
                                                RIGHTOCTAVE)));   
    }
    
/** Helper method to set percussion and Auxiliary keys
  */
    private void setSynthSystem(char bnk) {
        //Synth System Key #1
        this.add(new KeyPosition(bnk, 64,  KeyConstants.RM, 371, 94, 
          new Rectangle(371,108,23,23), new Rectangle(384, 94,24,24),
          KeyEvent.VK_9, new SynthSys(SETMODPITCH, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #2
        this.add(new KeyPosition(bnk, 65, RP,
          440, 94, new Rectangle(440, 94,24,24), new Rectangle(454,108,23,23),
          KeyEvent.VK_BACK_SPACE, new SynthSys(SETPITCHBEND, 
                                new int[]{8192, 0}, ALLCHAN)));
        //Synth System Key #3
        this.add(new KeyPosition(bnk, 66, UDP ,415,119,18,22, 
          KeyEvent.VK_8, new SynthSys(UPMODPITCH,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #4
        this.add(new KeyPosition(bnk, 67, RDP ,431,139,22,18, 
          KeyEvent.VK_EQUALS, new SynthSys(UPPITCHBEND, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #5
        this.add(new KeyPosition(bnk, 68, DDP ,415,155,18,22, 
          KeyEvent.VK_7, new SynthSys(DOWNMODPITCH, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #6
        this.add(new KeyPosition(bnk, 69, LDP ,394,139,22,18, 
          KeyEvent.VK_MINUS, new SynthSys(DOWNPITCHBEND, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #7
        this.add(new KeyPosition(bnk, 70, RV,462,148,32,32, 
          KeyEvent.VK_6, new SynthSys(SETVOL, 
                                new int[]{100, 0}, ALLCHAN)));
        //Synth System Key #8
        this.add(new KeyPosition(bnk, 71, TS,497,148,32,32, 
          KeyEvent.VK_0, new SynthSys(TOGGLESUSTAIN, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #9
        this.add(new KeyPosition(bnk, 72, CVU ,536,102,80,36, 
          KeyEvent.VK_4, new SynthSys(CRESVOL, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #10
        this.add(new KeyPosition(bnk, 73, CVD ,536,142,80,36, 
          KeyEvent.VK_5, new SynthSys(DECRVOL, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #11
        this.add(new KeyPosition(bnk, 74, COU,576,202,32,32,
          KeyEvent.VK_NUMPAD7, new SynthSys(LEFTUPOCTAVE,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #12
        this.add(new KeyPosition(bnk, 75, COD,576,242,32,32, 
          KeyEvent.VK_NUMPAD1, new SynthSys(LEFTDOWNOCTAVE,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #13
        this.add(new KeyPosition(bnk, 76, COU, 10,506,32,32, 
          KeyEvent.VK_NUMPAD9, new SynthSys(RIGHTUPOCTAVE,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #14
        this.add(new KeyPosition(bnk, 77, COD, 10,545,32,32,
          KeyEvent.VK_NUMPAD3, new SynthSys(RIGHTDOWNOCTAVE,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #15
        this.add(new KeyPosition(bnk, 78, CVU, 7,418,80,36, 
          KeyEvent.VK_UNDEFINED, new SynthSys(CRESVOL, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #16
        this.add(new KeyPosition(bnk, 79, CVD, 7,458,80,36,
          KeyEvent.VK_UNDEFINED, new SynthSys(DECRVOL, 
                                new int[]{0, 0}, ALLCHAN))); 
        //Synth System Key #17
        this.add(new KeyPosition(bnk, 80, RST, 15,346,118,38,
          KeyEvent.VK_3, new SynthSys(RESETCHANNEL, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #18
        this.add(new KeyPosition(bnk, 81, RM, 334,399, 
          new Rectangle(334,413,23,23), new Rectangle(334,399,24,24),
          KeyEvent.VK_UNDEFINED, new SynthSys(SETMODPITCH, 
                                new int[]{0, 0}, ALLCHAN)));    
        //Synth System Key #19
        this.add(new KeyPosition(bnk, 82, RP, 403,399, 
          new Rectangle(403,399,24,24), new Rectangle(417,413,23,23),
          KeyEvent.VK_UNDEFINED, new SynthSys(SETPITCHBEND, 
                                new int[]{8192, 0}, ALLCHAN)));
        //Synth System Key #20
        this.add(new KeyPosition(bnk, 83, UDP, 378,424,18,22,
          KeyEvent.VK_UNDEFINED, new SynthSys(UPMODPITCH,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #21
        this.add(new KeyPosition(bnk, 84, RDP, 394,444,22,18, 
          KeyEvent.VK_UNDEFINED, new SynthSys(UPPITCHBEND, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #22
        this.add(new KeyPosition(bnk, 85, DDP, 378,460,18,22,
          KeyEvent.VK_UNDEFINED, new SynthSys(DOWNMODPITCH, 
                                new int[]{0, 0}, ALLCHAN)));       
        //Synth System Key #23
        this.add(new KeyPosition(bnk, 86, LDP, 357,444,22,18,
          KeyEvent.VK_UNDEFINED, new SynthSys(DOWNPITCHBEND, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #24
        this.add(new KeyPosition(bnk, 87, RV, 425,453,32,32, 
          KeyEvent.VK_UNDEFINED, new SynthSys(SETVOL, 
                                new int[]{100, 0}, ALLCHAN)));
        //Synth System Key #25
        this.add(new KeyPosition(bnk, 88, TS, 460,453,32,32, 
          KeyEvent.VK_UNDEFINED, new SynthSys(TOGGLESUSTAIN, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #26
        this.add(new KeyPosition(bnk, 89, PWR, 495,346,118,38, 
          KeyEvent.VK_1, new SynthSys(HALTALLSYNTH, 
                                new int[]{0, 0}, ALLCHAN)));
    }

/** Helper method to set Sequence Keys
  */
    private void setSequence(char bnk) {
        //Sequence Key #1
        this.add(new KeyPosition(bnk, 90, SEQ,179,427,15,57,
          KeyEvent.VK_Z, new Sequence("midiFilesData/MidiTest.mid",
                                             SEQACHAN)));
        //Sequence Key #2
        this.add(new KeyPosition(bnk, 91, SEQ, 206,427,15,57, 
          KeyEvent.VK_X, new Sequence("midiFilesData/MidiTestTwo.mid",
                                             SEQBCHAN)));
        //Sequence Key #3
        this.add(new KeyPosition(bnk, 92, SEQ, 233,427,15,57,
          KeyEvent.VK_C, new Sequence("midiFilesData/MidiTestThree.mid",
                                             SEQCCHAN)));
        //Sequence Key #4
        this.add(new KeyPosition(bnk, 93, SEQ, 260,427,15,57, 
          KeyEvent.VK_V, new Sequence("midiFilesData/MidiTest.mid",
                                             SEQDCHAN)));
    }

/** Helper method to initialize the smallen sized KeyPositionList
  */
    private void setSmall(char bnk) {
        this.add(new KeyPosition(bnk, 1 , CF, 76 , 27 ,
            new Rectangle(76 , 27 , 29 , 29), KeyEvent.VK_ESCAPE, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 2 , CF, 139, 27 ,
            new Rectangle(139, 27 , 29 , 29), KeyEvent.VK_F1,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 3 , CF, 170, 27 ,
            new Rectangle(170, 27 , 29 , 29), KeyEvent.VK_F2, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 4 , CF, 201, 27 ,
            new Rectangle(201, 27 , 29 , 29), KeyEvent.VK_F3, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 5 , CF, 232, 27 ,
            new Rectangle(232, 27 , 29 , 29), KeyEvent.VK_F4, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 6 , CF, 279, 27 ,
            new Rectangle(279, 27 , 29 , 29), KeyEvent.VK_F5,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 7 , CF, 310, 27 ,
            new Rectangle(310, 27 , 29 , 29), KeyEvent.VK_F6, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 8 , CF, 341, 27 ,
            new Rectangle(341, 27 , 29 , 29), KeyEvent.VK_F7, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 9 , CF, 372, 27 ,
            new Rectangle(372, 27 , 29 , 29), KeyEvent.VK_F8, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 10, CF, 417, 27 ,
            new Rectangle(417, 27 , 29 , 29), KeyEvent.VK_F9, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 11, CF, 448, 27 ,
            new Rectangle(448, 27 , 29 , 29), KeyEvent.VK_F10, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 12, CF, 479, 27 ,
            new Rectangle(479, 27 , 29 , 29), KeyEvent.VK_F11, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 13, CF, 510, 27 ,
            new Rectangle(510, 27 , 29 , 29), KeyEvent.VK_F12, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 14, CF, 76 , 73 ,
            new Rectangle(76 , 73 , 29 , 29),KeyEvent.VK_BACK_QUOTE,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 15, PWR, 107, 73 ,
            new Rectangle(107, 73 , 29 , 29), KeyEvent.VK_1,
            new ScaledSynthSys(HALTALLSYNTH, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 16, CF, 138, 73 ,
            new Rectangle(138, 73 , 29 , 29), KeyEvent.VK_2,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 17, RST, 169, 73 ,
            new Rectangle(169, 73 , 29 , 29), KeyEvent.VK_3,
            new ScaledSynthSys(RESETCHANNEL, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 18, CF, 200, 73 ,
            new Rectangle(200, 73 , 29 , 29), KeyEvent.VK_4, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 19, TS, 231, 73 ,
            new Rectangle(231, 73 , 29 , 29), KeyEvent.VK_5,
            new ScaledSynthSys(TOGGLESUSTAIN, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 20, SEQ, 262, 73 ,
            new Rectangle(262, 73 , 29 , 29), KeyEvent.VK_6,
            new ScaledSequence("midiFilesData/MidiTest.mid", SEQACHAN)));
        this.add(new KeyPosition(bnk, 21, SEQ, 293, 73 ,
            new Rectangle(293, 73 , 29 , 29), KeyEvent.VK_7,
            new ScaledSequence("midiFilesData/MidiTestTwo.mid", SEQBCHAN)));
        this.add(new KeyPosition(bnk, 22, SEQ, 324, 73 ,
            new Rectangle(324, 73 , 29 , 29), KeyEvent.VK_8,
            new ScaledSequence("midiFilesData/MidiTestThree.mid", SEQCCHAN)));
        this.add(new KeyPosition(bnk, 23, SEQ, 355, 73 ,
            new Rectangle(355, 73 , 29 , 29), KeyEvent.VK_9,
            new ScaledSequence("midiFilesData/MidiTest.mid", SEQDCHAN)));
        this.add(new KeyPosition(bnk, 24, CF, 386, 73 ,
            new Rectangle(386, 73 , 29 , 29), KeyEvent.VK_0, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 25, LDP, 417, 73 ,
            new Rectangle(417, 73 , 29 , 29), KeyEvent.VK_MINUS,
            new ScaledSynthSys(DOWNPITCHBEND, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 26, RDP, 448, 73 ,
            new Rectangle(448, 73 , 29 , 29), KeyEvent.VK_EQUALS,
            new ScaledSynthSys(UPPITCHBEND, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 27, RP, 479, 73 ,
            new Rectangle(479, 73 , 60 , 29), KeyEvent.VK_BACK_SPACE,
            new ScaledSynthSys(SETPITCHBEND, new int[]{8192, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 28, CF, 76 , 104,
            new Rectangle(76 , 104, 45 , 29), KeyEvent.VK_TAB,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 29, CF, 123, 104,
            new Rectangle(123, 104, 29 , 29), KeyEvent.VK_Q, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 30, CF, 154, 104,
            new Rectangle(154, 104, 29 , 29), KeyEvent.VK_W,
            new ScaledNote(61, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 31, CF, 185, 104,
            new Rectangle(185, 104, 29 , 29), KeyEvent.VK_E,
            new ScaledNote(63, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 32, CF, 216, 104,
            new Rectangle(216, 104, 29 , 29), KeyEvent.VK_R, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 23, CF, 247, 104,
            new Rectangle(247, 104, 29 , 29), KeyEvent.VK_T,
            new ScaledNote(66, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 34, CF, 278, 104,
            new Rectangle(278, 104, 29 , 29), KeyEvent.VK_Y,
            new ScaledNote(68, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 35, CF, 309, 104,
            new Rectangle(309, 104, 29 , 29), KeyEvent.VK_U,
            new ScaledNote(70, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 36, CF, 340, 104,
            new Rectangle(340, 104, 29 , 29), KeyEvent.VK_I,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 37, COU, 371, 104,
            new Rectangle(371, 104, 29 , 29), KeyEvent.VK_O,
            new ScaledSynthSys(LEFTUPOCTAVE, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 38, CF, 402, 104,
            new Rectangle(402, 104, 29 , 29), KeyEvent.VK_P, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 39, DDP, 433, 104,
            new Rectangle(433, 104, 29 , 29), KeyEvent.VK_OPEN_BRACKET,
            new ScaledSynthSys(DOWNMODPITCH, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 40, UDP, 464, 104,
            new Rectangle(464, 104, 29 , 29), KeyEvent.VK_CLOSE_BRACKET,
            new ScaledSynthSys(UPMODPITCH, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 41, RM, 495, 104,
            new Rectangle(495, 104, 44 , 29), KeyEvent.VK_BACK_SLASH,
            new ScaledSynthSys(SETMODPITCH, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 42, CF, 76 , 135,
            new Rectangle(76 , 135, 53 , 29), KeyEvent.VK_CAPS_LOCK, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 43, CF, 131, 135,
            new Rectangle(131, 135, 29 , 29), KeyEvent.VK_A,
            new ScaledNote(60, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 44, CF, 162, 135,
            new Rectangle(162, 135, 29 , 29), KeyEvent.VK_S,
            new ScaledNote(62, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 45, CF, 193, 135,
            new Rectangle(193, 135, 29 , 29), KeyEvent.VK_D,
            new ScaledNote(64, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 46, CF, 224, 135,
            new Rectangle(224, 135, 29 , 29), KeyEvent.VK_F,
            new ScaledNote(65, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 47, CF, 255, 135,
            new Rectangle(255, 135, 29 , 29), KeyEvent.VK_G,
            new ScaledNote(67, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 48, CF, 286, 135,
            new Rectangle(286, 135, 29 , 29), KeyEvent.VK_H,
            new ScaledNote(69, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 49, CF, 317, 135,
            new Rectangle(317, 135, 29 , 29), KeyEvent.VK_J,
            new ScaledNote(71, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 50, CF, 348, 135,
            new Rectangle(348, 135, 29 , 29), KeyEvent.VK_K,
            new ScaledNote(72, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        this.add(new KeyPosition(bnk, 51, COD, 379, 135,
            new Rectangle(379, 135, 29 , 29), KeyEvent.VK_L,
            new ScaledSynthSys(LEFTDOWNOCTAVE, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 52, CVD, 410, 135,
            new Rectangle(410, 135, 29 , 29), KeyEvent.VK_SEMICOLON,
            new ScaledSynthSys(DECRVOL, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 53, CVU, 441, 135,
            new Rectangle(441, 135, 29 , 29), KeyEvent.VK_QUOTE,
            new ScaledSynthSys(CRESVOL, new int[]{0, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 54, RV, 472, 135,
            new Rectangle(472, 135, 67 , 29), KeyEvent.VK_ENTER,
            new ScaledSynthSys(SETVOL, new int[]{100, 0}, ALLCHAN)));
        this.add(new KeyPosition(bnk, 55, CF, 76 , 166,
            new Rectangle(76 , 166, 69 , 29), KeyEvent.VK_SHIFT, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 56, PRC, 147, 166,
            new Rectangle(147, 166, 29 , 29), KeyEvent.VK_Z,
            new ScaledNote(36, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 57, PRC, 178, 166,
            new Rectangle(178, 166, 29 , 29), KeyEvent.VK_X,
            new ScaledNote(38, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 58, PRC, 209, 166,
            new Rectangle(209, 166, 29 , 29), KeyEvent.VK_C,
            new ScaledNote(44, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 59, PRC, 240, 166,
            new Rectangle(240, 166, 29 , 29), KeyEvent.VK_V,
            new ScaledNote(49, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 60, AUX, 271, 166,
            new Rectangle(271, 166, 29 , 29), KeyEvent.VK_B,
            new ScaledNote(37, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 61, AUX, 302, 166,
            new Rectangle(302, 166, 29 , 29), KeyEvent.VK_N,
            new ScaledNote(41, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 62, AUX, 333, 166,
            new Rectangle(333, 166, 29 , 29), KeyEvent.VK_M,
            new ScaledNote(48, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 63, AUX, 364, 166,
            new Rectangle(364, 166, 29 , 29), KeyEvent.VK_COMMA,
            new ScaledNote(55, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        this.add(new KeyPosition(bnk, 64, CF, 395, 166,
            new Rectangle(395, 166, 29 , 29), KeyEvent.VK_PERIOD, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 65, CF, 426, 166,
            new Rectangle(426, 166, 29 , 29), KeyEvent.VK_SLASH,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 66, CF, 457, 166,
            new Rectangle(457, 166, 82 , 29), KeyEvent.VK_SHIFT, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 67, CF, 76 , 197,
            new Rectangle(76 , 197, 35 , 29), KeyEvent.VK_CONTROL, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 68, CF, 113, 197,
            new Rectangle(113, 197, 35 , 29), KeyEvent.VK_WINDOWS, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 69, CF, 150, 197,
            new Rectangle(150, 197, 35 , 29), KeyEvent.VK_ALT, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 70, CF, 187, 197,
            new Rectangle(187, 197, 204 , 29), KeyEvent.VK_SPACE, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 71, CF, 393, 197,
            new Rectangle(393, 197, 35 , 29), KeyEvent.VK_ALT, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 72, CF, 430, 197,
            new Rectangle(430, 197, 35 , 29), KeyEvent.VK_CONTEXT_MENU,
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 73, CF, 467, 197,
            new Rectangle(467, 197, 35 , 29), KeyEvent.VK_WINDOWS, 
            new ScaledSynthNull()));
        this.add(new KeyPosition(bnk, 74, CF, 504, 197,
            new Rectangle(504, 197, 35 , 29), KeyEvent.VK_CONTROL, 
            new ScaledSynthNull()));
    }


    
    private void setRevised(char bnk) {
        //Keyboard Key #1
        this.add(new KeyPosition(bnk, 1 , CF, 10 ,188, 
          new Rectangle(10 ,188,32,51), new Rectangle(10 ,239,40,51), 
          KeyEvent.VK_Q, 
          new Note(48, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #2
        this.add(new KeyPosition(bnk, 2 , RL, 42 ,188,
          new Rectangle(42 ,188,16,51), KeyEvent.VK_W, 
          new Note(49, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #3
        this.add(new KeyPosition(bnk, 3 , DGA,50 ,188,
          new Rectangle(58 ,188,24,51), new Rectangle(50 ,239,40,51),
          KeyEvent.VK_E, 
          new Note(50, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #4
        this.add(new KeyPosition(bnk, 4 , RL, 82 ,188,
          new Rectangle(82 ,188,16,51), KeyEvent.VK_R, 
          new Note(51, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #5
        this.add(new KeyPosition(bnk, 5 , EB, 90 ,188,
          new Rectangle(98 ,188,32,51), new Rectangle(90 ,239,40,51),
          KeyEvent.VK_T, 
          new Note(52, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #6
        this.add(new KeyPosition(bnk, 6 , CF,  130,188,
          new Rectangle(130,188,32,51), new Rectangle(130,239,40,51),
          KeyEvent.VK_Y, 
          new Note(53, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #7
        this.add(new KeyPosition(bnk, 7 , RL, 162,188,
          new Rectangle(162,188,16,51), KeyEvent.VK_U, 
          new Note(54, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));        
        //Keyboard Key #8
        this.add(new KeyPosition(bnk, 8 , DGA,170,188,
          new Rectangle(178,188,24,51), new Rectangle(178,239,40,51),
          KeyEvent.VK_I, 
          new Note(55, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #9
        this.add(new KeyPosition(bnk, 9 , RL, 202,188,
          new Rectangle(202,188,16,51), KeyEvent.VK_O, 
          new Note(56, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #10
        this.add(new KeyPosition(bnk, 10, DGA,210,188,
          new Rectangle(218,188,24,51), new Rectangle(218,239,40,51),
          KeyEvent.VK_P, 
          new Note(57, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));       
        //Keyboard Key #11
        this.add(new KeyPosition(bnk, 11, RL, 242,188,
          new Rectangle(242,188,16,51), KeyEvent.VK_OPEN_BRACKET, 
          new Note(58, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));     
        //Keyboard Key #12
        this.add(new KeyPosition(bnk, 12, EB, 250,188,
          new Rectangle(258,188,32,51), new Rectangle(250,239,40,51),
          KeyEvent.VK_CLOSE_BRACKET, 
          new Note(59, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #13
        this.add(new KeyPosition(bnk, 13, CF,  290,188,
          new Rectangle(290,188,32,51), new Rectangle(290,239,40,51),
          KeyEvent.VK_A, 
          new Note(60, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #14
        this.add(new KeyPosition(bnk, 14, RL, 322,188,
          new Rectangle(322,188,16,51), new Rectangle(322,188,16,51),
          KeyEvent.VK_S, 
          new Note(61, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #15
        this.add(new KeyPosition(bnk, 15, DGA,330,188,
          new Rectangle(338,188,24,51), new Rectangle(338,239,40,51),
          KeyEvent.VK_D, 
          new Note(62, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #16
        this.add(new KeyPosition(bnk, 16, RL, 362,188,
          new Rectangle(362,188,16,51), new Rectangle(362,188,16,51),
          KeyEvent.VK_F, 
          new Note(63, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #17
        this.add(new KeyPosition(bnk, 17, EB, 370,188,
          new Rectangle(378,188,32,51), new Rectangle(378,239,40,51),
          KeyEvent.VK_G, 
          new Note(64, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #18
        this.add(new KeyPosition(bnk, 18, CF,  410,188,
          new Rectangle(410,188,32,51), new Rectangle(410,239,40,51),
          KeyEvent.VK_H, 
          new Note(65, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #19
        this.add(new KeyPosition(bnk, 19, RL, 442,188,
          new Rectangle(442,188,16,51), new Rectangle(442,188,16,51),
          KeyEvent.VK_J, 
          new Note(66, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #20
        this.add(new KeyPosition(bnk, 20, DGA,450,188,
          new Rectangle(458,188,24,51), new Rectangle(458,239,40,51),
          KeyEvent.VK_K, 
          new Note(67, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #21
        this.add(new KeyPosition(bnk, 21, RL, 482,188,
          new Rectangle(482,188,16,51), new Rectangle(482,188,16,51),
          KeyEvent.VK_L, 
          new Note(68, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #22
        this.add(new KeyPosition(bnk, 22, DGA,490,188,
          new Rectangle(498,188,24,51), new Rectangle(490,239,40,51),
          KeyEvent.VK_SEMICOLON, 
          new Note(69, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #23
        this.add(new KeyPosition(bnk, 23, RL, 522,188,
          new Rectangle(522,188,16,51), new Rectangle(522,188,16,51),
          KeyEvent.VK_QUOTE, 
          new Note(70, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));
        //Keyboard Key #24
        this.add(new KeyPosition(bnk, 24, EB, 530,188,
          new Rectangle(310,188,24,51), new Rectangle(530,239,40,51),
          KeyEvent.VK_ENTER, 
          new Note(71, 105, Integer.MAX_VALUE, 0, LEFTOCTAVE)));

        //Percussion Key #1
        this.add(new KeyPosition(bnk, 25, PRC, 
          10,118, 78, 44, KeyEvent.VK_Z, 
          new Note(36, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Percussion Key #2
        this.add(new KeyPosition(bnk, 26, PRC, 
          67, 64, 78, 44,KeyEvent.VK_X,
          new Note(38, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Percussion Key #3
        this.add(new KeyPosition(bnk, 27, PRC,
          158, 56, 78, 44,KeyEvent.VK_C,
          new Note(44, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Percussion Key #4
        this.add(new KeyPosition(bnk, 28, PRC,
          229,107, 78, 44,KeyEvent.VK_V,
          new Note(49, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #1
        this.add(new KeyPosition(bnk, 29, AUX,
          172, 10, 31, 33,KeyEvent.VK_B,
          new Note(37, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #2
        this.add(new KeyPosition(bnk, 30, AUX,
          218, 10, 31, 33,KeyEvent.VK_N,
          new Note(41, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #3
        this.add(new KeyPosition(bnk, 31, AUX,
          263, 20, 31, 33,KeyEvent.VK_M,
          new Note(48, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        //Auxiliary Key #4
        this.add(new KeyPosition(bnk, 32, AUX,
          300, 43, 31, 33,KeyEvent.VK_COMMA,
          new Note(55, 105, Integer.MAX_VALUE, PERCCHAN, 0)));
        
        //Synth System Key #1
        this.add(new KeyPosition(bnk, 33,  KeyConstants.RM, 371, 94, 
          new Rectangle(371,108,23,23), new Rectangle(384, 94,24,24),
          KeyEvent.VK_3, new SynthSys(SETMODPITCH, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #2
        this.add(new KeyPosition(bnk, 34, RP,
          440, 94, new Rectangle(440, 94,24,24), new Rectangle(454,108,23,23),
          KeyEvent.VK_6, new SynthSys(SETPITCHBEND, 
                                new int[]{8192, 0}, ALLCHAN)));
        //Synth System Key #3
        this.add(new KeyPosition(bnk, 35, UDP ,415,119,18,22, 
          KeyEvent.VK_4, new SynthSys(UPMODPITCH,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #4
        this.add(new KeyPosition(bnk, 36, RDP ,431,139,22,18, 
          KeyEvent.VK_7, new SynthSys(UPPITCHBEND, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #5
        this.add(new KeyPosition(bnk, 37, DDP ,415,155,18,22, 
          KeyEvent.VK_5, new SynthSys(DOWNMODPITCH, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #6
        this.add(new KeyPosition(bnk, 38, LDP ,394,139,22,18, 
          KeyEvent.VK_8, new SynthSys(DOWNPITCHBEND, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #7
        this.add(new KeyPosition(bnk, 39, RV,462,148,32,32, 
          KeyEvent.VK_9, new SynthSys(SETVOL, 
                                new int[]{100, 0}, ALLCHAN)));
        //Synth System Key #8
        this.add(new KeyPosition(bnk, 40, TS,497,148,32,32, 
          KeyEvent.VK_0, new SynthSys(TOGGLESUSTAIN, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #9
        this.add(new KeyPosition(bnk, 41, CVU ,536,102,80,36, 
          KeyEvent.VK_MINUS, new SynthSys(CRESVOL, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #10
        this.add(new KeyPosition(bnk, 42, CVD ,536,142,80,36, 
          KeyEvent.VK_EQUALS, new SynthSys(DECRVOL, 
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #11
        this.add(new KeyPosition(bnk, 43, COD,576,242,32,32, 
          KeyEvent.VK_NUMPAD1, new SynthSys(LEFTDOWNOCTAVE,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #12
        this.add(new KeyPosition(bnk, 44, COU,576,202,32,32,
          KeyEvent.VK_NUMPAD7, new SynthSys(LEFTUPOCTAVE,
                                new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #13
        this.add(new KeyPosition(bnk, 45, RST, 11, 14,118,38,
                KeyEvent.VK_2, new SynthSys(RESETCHANNEL, 
                                      new int[]{0, 0}, ALLCHAN)));
        //Synth System Key #14
        this.add(new KeyPosition(bnk, 46, PWR,491, 14,118,38, 
                KeyEvent.VK_1, new SynthSys(HALTALLSYNTH, 
                                      new int[]{0, 0}, ALLCHAN)));
        //Sequence Key #1
        this.add(new KeyPosition(bnk, 47, SEQ,370, 31,23,47,
          KeyEvent.VK_F9, new Sequence("midiFilesData/MidiTest.mid",
                                             SEQACHAN)));
        //Sequence Key #2
        this.add(new KeyPosition(bnk, 48, SEQ,399, 31,23,47, 
          KeyEvent.VK_F10, new Sequence("midiFilesData/MidiTestTwo.mid",
                                             SEQBCHAN)));
        //Sequence Key #3
        this.add(new KeyPosition(bnk, 49, SEQ,428, 31,23,47,
          KeyEvent.VK_F11, new Sequence("midiFilesData/MidiTestThree.mid",
                                             SEQCCHAN)));
        //Sequence Key #4
        this.add(new KeyPosition(bnk, 50, SEQ,457, 31,23,47, 
          KeyEvent.VK_F12, new Sequence("midiFilesData/MidiTest.mid",
                                             SEQDCHAN)));
    }
    
/** Get the bank of the images this Key Position List reads from
  * @return The bank being read from
  */
    public char getImageBank() {
    	return this.kplBnk;
    }
    
/** Gets KeyPosition and sets current position
  * to the next object's position if found
  * @param element KeyPosition being gotten
  * @return The parameter if it was found
  */
    public KeyPosition getAndSetPos(KeyPosition element) {
        find(element);
        //if found, return the object, else return null
        if (lFound) {
            this.lPos = this.lFoundPos+1;
            return (KeyPosition)this.lArray[this.lFoundPos];
        } else
            return null;
    }

/** Sets the KeyPosition into the list, overriding the previous stored value
  * @param element KeyPosition being inserted
  * @return The parameter if it was found and replaced
  */
    public KeyPosition replaceKeyPositionByID(KeyPosition element) {
        find(element);
        //if found, return the object, else return null
        if (lFound) {
            this.lArray[this.lFoundPos] = element;
            return (KeyPosition)this.lArray[this.lFoundPos];
        } else
            return null;   
    }

/** Creates a file to save all KeyPositions stored inside the KeyPositionList
  * @param filename Name of file to save to
  * @return True if sucessfully saved
  */
    public boolean saveKeyPositionList(String filename) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream saver = new ObjectOutputStream(file);
            saver.writeInt(this.size());
            for (int i=0; i<this.size(); i++) {
                saver.writeObject((KeyPosition)this.lArray[i]);
            }
            saver.close();
            file.close();
            return true;
        } catch (Exception ex) {
            System.err.println("Failed to save");
            return false;
        }
    }

/** Obtains a KeyPosition from a specific index on the KeyPositionList
  * @param pos Position to get KeyPosition from
  * @return KeyPosition at the given position, if nonexistent or an invalid
  * typecast is performed, a null is returned.
  */
    public KeyPosition getKeyPositionFromPos(int pos) {
        try {
            return (KeyPosition)this.lArray[pos-1];
        } catch (Exception e) {
            System.err.println("Undefined at POS or could not cast");
            return null;
        }
    }

    public int[] getSequenceKeycodes() {
    	int[] sequenceInts = new int[4];
    	int i = 0;
    	KeyPosition temp = getNext();
    	while(temp != null) {
    		if (temp.getSynthType() instanceof Sequence) {
    			sequenceInts[i] = temp.getKeyCode();
    			i++;
    		}
    		temp = getNext();
    	}
    	return sequenceInts;
    }
    
/** Gets next KeyPosition object. If the end of the list is reached,
  * this method returns a null to signify the end of finding in other classes
  * @return Next KeyPosition, unless end of list, in which case null is gotten
  */
    @Override
    public KeyPosition getNext() {
        //if its the end of the list, loops back to the start
        //it will then return a null meaning the end has been reached
        if (this.lPos >= this.lNum) {
            this.lPos = 0;
            return null;
        //otherwise, it sets to the next object
        } else {
            KeyPosition temp = (KeyPosition)this.lArray[lPos];
            this.lPos++;
            return temp;
        }
    }
}