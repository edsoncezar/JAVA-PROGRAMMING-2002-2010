/*
 * 1.0 code.
 */

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.GridLayout;

public class LamePrintThread extends Applet {

    TextArea display = new TextArea(1, 80);

    public void init() {
        //Create the text area and make it uneditable.
    	display = new TextArea(1, 80);
        display.setEditable(false);

	//Set the layout manager so that the text area 
	//will be as wide as possible.
        setLayout(new GridLayout(1,0));

	//Add the text area to the applet.
        add(display);
        validate();

        addItem("init: " + threadInfo(Thread.currentThread()));
    }

    public void start() {
        addItem("start: " + threadInfo(Thread.currentThread()));
    }

    public void stop() {
        addItem("stop: " + threadInfo(Thread.currentThread()));
    }

    public void destroy() {
        addItem("destroy: " + threadInfo(Thread.currentThread()));
    }

    String threadInfo(Thread t) {
        return "thread=" + t.getName() + ", "
               + "thread group=" + t.getThreadGroup().getName();
    }
   
    void addItem(String newWord) {
        System.out.println(newWord);
        display.appendText(newWord + "\n");
        display.repaint();
    }

    //DO NOT IMPLEMENT update() and paint()!  Netscape Navigator 2.0
    //for Windows crashes if you do.  
}
