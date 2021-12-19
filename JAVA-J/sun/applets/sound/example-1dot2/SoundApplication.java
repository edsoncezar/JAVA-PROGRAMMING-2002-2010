import java.applet.AudioClip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.awt.GridBagLayout;

public class SoundApplication extends JPanel
                              implements ActionListener,
                                         ItemListener {
    SoundList soundList;
    String auFile = "spacemusic.au";
    String aiffFile = "flute+hrn+mrmba.aif";
    String midiFile = "trippygaia1.mid";
    String rmfFile = "jungle.rmf";
    String wavFile = "bottle-open.wav";
    String chosenFile;
        
    AudioClip onceClip, loopClip;
    URL codeBase;

    JComboBox formats;
    JButton playButton, loopButton, stopButton;
    JLabel status;
        
    boolean looping = false;

    public SoundApplication() {
        String [] fileTypes = {auFile,
                               aiffFile,
                               midiFile,        
                               rmfFile,
                               wavFile};
        formats = new JComboBox(fileTypes);
        formats.setSelectedIndex(0);
        chosenFile = (String)formats.getSelectedItem();
        formats.addItemListener(this);

        playButton = new JButton("Play");
        playButton.addActionListener(this);

        loopButton = new JButton("Loop");
        loopButton.addActionListener(this);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        
        status = new JLabel(
                    "Click Play or Loop to play the selected sound file.");
                
        JPanel controlPanel = new JPanel();
        controlPanel.add(formats);
        controlPanel.add(playButton);
        controlPanel.add(loopButton);
        controlPanel.add(stopButton);           
                
        JPanel statusPanel = new JPanel();
        statusPanel.add(status);
                
        add(controlPanel);
        add(statusPanel);

        startLoadingSounds();
    }

    public void itemStateChanged(ItemEvent e){
        chosenFile = (String)formats.getSelectedItem();
        soundList.startLoading(chosenFile);
    }

    void startLoadingSounds() {
        //Start asynchronous sound loading.
        try {
            codeBase = new URL("file:" + System.getProperty("user.dir") + "/");
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        soundList = new SoundList(codeBase);
        soundList.startLoading(auFile);
        soundList.startLoading(aiffFile);
        soundList.startLoading(midiFile);
        soundList.startLoading(rmfFile);
        soundList.startLoading(wavFile);
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
        if (source == playButton) {
            //Try to get the AudioClip.
            onceClip = soundList.getClip(chosenFile);
            stopButton.setEnabled(true);
            onceClip.play();     //Play it once.
            status.setText("Playing sound " + chosenFile + ".");
            if (onceClip == null) {
                status.setText("Sound " + chosenFile + " not loaded yet.");
            }
            return;
        }

        //START LOOP BUTTON
        if (source == loopButton) {
            loopClip = soundList.getClip(chosenFile);

            looping = true;
            loopClip.loop();     //Start the sound loop.
            loopButton.setEnabled(false); //Disable start button.
            stopButton.setEnabled(true);
            status.setText("Playing sound " + chosenFile + " continuously.");
            if (loopClip == null) {
                status.setText("Sound " + chosenFile + " not loaded yet.");
            }
            return;
        }

        //STOP LOOP BUTTON
        if (source == stopButton) {
            if (looping) {
                looping = false;
                loopClip.stop();    //Stop the sound loop.
                loopButton.setEnabled(true); //Enable start button.
            } else if (onceClip != null) {
                onceClip.stop();
            }
            stopButton.setEnabled(false);
            status.setText("Stopped playing " + chosenFile + ".");
            return;
        }
    }

    public static void main(String s[]) {
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        };
        JFrame f = new JFrame("SoundApplication");
        f.addWindowListener(l);
        f.getContentPane().add(new SoundApplication());
        f.setSize(new Dimension(400,100));
        f.show();
    }
}
