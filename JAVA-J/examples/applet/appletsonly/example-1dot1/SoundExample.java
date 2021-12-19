/* 
 * 1.1 version.
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SoundExample extends Applet 
			  implements ActionListener {
    SoundList soundList;
    String onceFile = "bark.au";
    String loopFile = "train.au";
    AudioClip onceClip;
    AudioClip loopClip;

    Button playOnce;
    Button startLoop;
    Button stopLoop;
    Button reload;

    boolean looping = false;

    public void init() {
        playOnce = new Button("Bark!");
	playOnce.addActionListener(this);
        add(playOnce);

        startLoop = new Button("Start sound loop");
        stopLoop = new Button("Stop sound loop");
        stopLoop.setEnabled(false);
	startLoop.addActionListener(this);
        add(startLoop);
	stopLoop.addActionListener(this);
        add(stopLoop);

        reload = new Button("Reload sounds");
	reload.addActionListener(this);
        add(reload);

        startLoadingSounds();
    }

    void startLoadingSounds() {
        //Start asynchronous sound loading.
        soundList = new SoundList(this, getCodeBase());
        soundList.startLoading(loopFile);
        soundList.startLoading(onceFile);
   }

    public void stop() {
        onceClip.stop();        //Cut short the one-time sound.
        if (looping) {
            loopClip.stop();    //Stop the sound loop.
        }
    }    

    public void start() {
        if (looping) {
            loopClip.loop();    //Restart the sound loop.
        }
    }    

    public void actionPerformed(ActionEvent event) {
        //PLAY BUTTON
	Object source = event.getSource();
        if (source == playOnce) {
            if (onceClip == null) {
                //Try to get the AudioClip.
                onceClip = soundList.getClip(onceFile);
            }

            if (onceClip != null) {  //If the sound is loaded:
                onceClip.play();     //Play it once.
                showStatus("Playing sound " + onceFile + ".");
            } else {
                showStatus("Sound " + onceFile + " not loaded yet.");
            }
	    return;
        }

        //START LOOP BUTTON
        if (source == startLoop) {
            if (loopClip == null) {
                //Try to get the AudioClip.
                loopClip = soundList.getClip(loopFile);
            }

            if (loopClip != null) {  //If the sound is loaded:
                looping = true;
                loopClip.loop();     //Start the sound loop.
                stopLoop.setEnabled(true);   //Enable stop button.
                startLoop.setEnabled(false); //Disable start button.
                showStatus("Playing sound " + loopFile + " continuously.");
            } else {
                showStatus("Sound " + loopFile + " not loaded yet.");
            }
            return;
        }

        //STOP LOOP BUTTON
        if (source == stopLoop) {
            if (looping) {
                looping = false;
                loopClip.stop();    //Stop the sound loop.
                startLoop.setEnabled(true); //Enable start button.
                stopLoop.setEnabled(false); //Disable stop button.
            }
            showStatus("Stopped playing sound " + loopFile + ".");
            return;
        }

        //RELOAD BUTTON
        if (source == reload) {
            if (looping) {          //Stop the sound loop.
                looping = false;
                loopClip.stop();
                startLoop.setEnabled(true); //Enable start button.
                stopLoop.setEnabled(false); //Disable stop button.
            }
            loopClip = null;        //Reset AudioClip to null.
            onceClip = null;        //Reset AudioClip to null.
            startLoadingSounds();
            showStatus("Reloading all sounds.");
            return;
        }
    }
}
