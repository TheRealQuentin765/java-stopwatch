import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUI implements ActionListener,TimerOutput,ItemListener{
    JFrame frame = new JFrame("Stopwatch++");
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Enter time below");
    JButton button = new JButton("Start Timer");
    JButton stopWatchButton = new JButton("Start Stopwatch");
    JTextField textField = new JTextField("30");
    JTextField noteField = new JTextField("Note");
    JCheckBox muteButton = new JCheckBox("Mute");
    JLabel jMute = new JLabel("Mute");
    boolean muted = false;

    TimerTracker timer;
    public void stopWatchGUI(){

        panel.setLayout(null);

        frame.setBounds(100,100,200,200);
        noteField.setBounds(50,0,100,40);
        label.setFont(label.getFont().deriveFont((float)12));
        stopWatchButton.setFont(label.getFont().deriveFont((float)12));
        label.setBounds(50,50,200,40);
        button.setBounds(60,90,100,30);
        textField.setBounds(20,90,textField.getPreferredSize().width,textField.getPreferredSize().height);
        muteButton.setBounds(20,135,30,20);
        jMute.setBounds(20,120,60,20);
        stopWatchButton.setBounds(60,130,100,30);

        panel.add(stopWatchButton);
        panel.add(jMute);
        panel.add(label);
        button.addActionListener(this);
        noteField.addActionListener(this);
        stopWatchButton.addActionListener(this);
        panel.add(button);
        panel.add(textField);
        panel.add(button);
        panel.add(muteButton);
        muteButton.addItemListener(this);

        panel.add(noteField);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void updateTimer(long current, long length) {
        label.setText(current/1000. + " / " + length/1000. + "  (" + (int)(100 - 100. * current / length) + "%)");
    }

    public void updateStopWatch(long current) {
        if (current == 1000)
            label.setText(current/1000. + " second");
        else
            label.setText(current/1000. + " seconds");
    }

    public void finish() {
        label.setText("BEEP");
        if(!muted) Beeper.beep();
        button.setText("Start Timer");
        timer = null;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == noteField) {
            frame.setTitle(noteField.getText());
            noteField.setVisible(false);
        }
        if(e.getSource() == stopWatchButton){
            if (timer == null) {
                timer = new TimerTracker(this, 250);
                timer.countUp();
                button.setText("Stop Stopwatch");
            } else {
                label.setText("Stopped with " + timer.getTime() / 1000. + " seconds.");
                timer.stop();
                timer = null;
                button.setText("Start Stopwatch");
            }
        }
        else {
            if (timer == null) {
                timer = new TimerTracker(this, 250);
                timer.countDown((long) (Double.parseDouble(textField.getText()) * 1000));
                button.setText("Stop Timer");
            } else {
                label.setText("Stopped with " + timer.getTime() / 1000. + " left.");
                timer.stop();
                timer = null;
                button.setText("Start Timer");
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {
        muted = e.getStateChange() == ItemEvent.SELECTED;
    }
}
