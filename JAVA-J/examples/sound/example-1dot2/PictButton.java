import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class PictButton extends JApplet implements ActionListener {

	AppletSoundList soundList;
	String wavFile = "bottle-open.wav";
	AudioClip onceClip;

	JButton Play;
        protected String rocket = "rocketship.gif";
	ImageIcon pb_Icon;
	
	public void init() {

		URL pict_button_URL = getURL(rocket);
	        pb_Icon = new ImageIcon(pict_button_URL);
		Play = new JButton(pb_Icon);
		Play.addActionListener(this);
		JPanel controlPanel = new JPanel();
		controlPanel.add(Play);
		getContentPane().add(controlPanel);

                startLoadingSounds();	
       }

	protected URL getURL(String filename) {
		URL codeBase = getCodeBase();
		URL url = null;

		try {
                        url = new URL(codeBase, filename);
                    } catch (java.net.MalformedURLException e) {
                        System.err.println("Couldn't create image: badly specified URL");
                        return null;
                    }
                System.out.println("url "+url);
                    return url;
         }

    void startLoadingSounds() {
        //Start asynchronous sound loading.
        soundList = new AppletSoundList(this, getCodeBase());
        soundList.startLoading(wavFile);
   }

    public void actionPerformed(ActionEvent event) {
        //PLAY BUTTON
        Object source = event.getSource();
        if (source == Play) {
                //Try to get the AudioClip.
                onceClip = soundList.getClip(wavFile);
                onceClip.play();     //Play it once.
                showStatus("Playing sound " + wavFile + ".");
            if (onceClip == null) {
                showStatus("Sound " + wavFile + " not loaded yet.");
            }
            return;
        }


    }
}

