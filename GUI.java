import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener,TimerOutput,ItemListener{
    JFrame frame = new JFrame("StopWatch");
    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JLabel label = new JLabel("Enter time below, then press Start");
    JButton button = new JButton("Start");
    JTextField textField = new JTextField("30");
    JTextField noteField = new JTextField("Enter Note Here");
    JCheckBox muteButton = new JCheckBox("Mute");
    boolean muted = false;
    TimerTracker timer;
    public void stopWatchGUI(){
        frame.setLayout(new BorderLayout());
        panel.add(label);
        frame.setBounds(100,100,500,500);


        button.addActionListener(this);
        panel3.add(textField);
        panel3.add(button);
        muteButton.addItemListener(this);
        panel3.add(muteButton);
        textField.setColumns(5);

        panel4.add(noteField);

        panel.setPreferredSize(new Dimension(100,50));
        panel2.setPreferredSize(new Dimension(100,100));
        frame.add(panel3,BorderLayout.SOUTH);
        frame.add(panel2,BorderLayout.NORTH);
        frame.add(panel4,BorderLayout.NORTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void updateTimer(long current, long length) {
        label.setText(current/1000. + " / " + length/1000. + "  (" + (int)(100 - 100. * current / length) + "%)");
    }

    public void updateStopWatch(long current) {
        label.setText(current/1000. + "");
    }

    public void finish() {
        label.setText("BEEP");
        if(!muted) Beeper.beep();
        button.setText("Start");
        timer = null;
    }

    public void actionPerformed(ActionEvent e){
        if (timer == null) {
            timer = new TimerTracker(this,250);
            timer.countDown((long)(Double.parseDouble(textField.getText())*1000));
            button.setText("Stop");
        } else {
            label.setText("Stopped with " + timer.getTime()/1000. + " left.");
            timer.stop();
            timer = null;
            button.setText("Start");
        }
    }


    public void itemStateChanged(ItemEvent e) {
        muted = e.getStateChange() == ItemEvent.SELECTED;
    }
}
