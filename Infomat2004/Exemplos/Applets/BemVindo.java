import java.applet.Applet;
import java.awt.Graphics;
public class BemVindo extends Applet{
    
    private int inits;
    private int starts;
    private int paints;
    private int stops;

    public BemVindo() {
    }
    
    public void init(){
     inits++;
    }
    
    public void start(){
    starts++;
    }
    
    public void paint(Graphics g){
        paints++;
        g.drawString("Init: "+inits,5,15);
        g.drawString("Starts: "+starts,5,30);
        g.drawString("Paint: "+paints,5,45);
        g.drawString("Starts: "+stops,5,60);
    }
    
    public void stop(){
        stops++;
    }
}
