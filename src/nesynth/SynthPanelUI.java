/** Panel UI for all storage panels in the NESynth Project
  *
  * @Version       1.1
  * @author        John Falco
  * @id            jpfalco
  * @course        CSIS 491, MULT 500
  * @assignment    NESynth
  * @related       SynthUI
  * @included      None
  */
  
package nesynth;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public abstract class SynthPanelUI extends JPanel implements Comparable, SynthUI {
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

/** Scales BufferedImage to the size specified. Used from StackOverflow's
  * question "scale a bufferedImage the fastest and easiest way"
  * @author "Victor" https://goo.gl/1JHBfQ
  * @param src BufferedImage to be scaled
  * @param w Width to scale to
  * @param h Height to scale to
  * @return The scaled BufferedImage
  */
    protected BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img =
            new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        for (x = 0; x < w; x++) {
            for (y = 0; y < h; y++) {
                int col = src.getRGB(x * ww / w, y * hh / h);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    protected BufferedImage addText(BufferedImage old, String text) {
        BufferedImage img = new BufferedImage(
            old.getWidth(), old.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, old.getWidth(), old.getHeight(), this);
        g2d.setFont(new Font("Serif", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int xC = old.getWidth()/2;
        int yC = old.getHeight()/2;
        g2d.setPaint(determineColorByCoordinate(old, xC, yC));
        int xS = 0 + ((old.getWidth()-fm.stringWidth(text))/2);
        int yS = 0 + ((old.getHeight()-fm.getHeight())/2) + fm.getAscent();
        g2d.drawString(text, xS, yS);
        g2d.dispose();
        return img;
    }

    protected Color determineColorByCoordinate(BufferedImage image, 
                                                   int x, int y) {
//RGB Values from pixel based off of https://goo.gl/mZke8f
        int base =  image.getRGB(x,y); 
        int r = (base & 0x00ff0000) >> 16;
        int g = (base & 0x0000ff00) >> 8;
        int b = base & 0x000000ff;
//luminance data based off of https://goo.gl/YNXQMY
        double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
        return (luminance > 0.179) ? Color.black : Color.white;  
    }

}