import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUI implements ActionListener,TimerOutput,ItemListener{
    JFrame frame = new JFrame("Timer");
    JPanel panel = new JPanel();
    JPanel stopPanel = new JPanel();
    JLabel label = new JLabel("Enter time below");
    JButton timerButton = new JButton("Start");
    JButton stopWatchButton = new JButton("StopWatch");
    JTextField textField = new JTextField("30");
//    JTextField noteField = new JTextField("Note");
    JCheckBox muteButton = new JCheckBox("Mute");
    JLabel jMute = new JLabel("Mute");
    JProgressBar progressBar = new JProgressBar(0,100);
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel stopLabel = new JLabel("0:0");
    boolean muted = false;

    TimerTracker timer;
    public void stopWatchGUI(){

        panel.setLayout(null);

        frame.setBounds(100,100,200,250);
//        noteField.setBounds(50,0,100,40);
        label.setFont(label.getFont().deriveFont((float)12));
        stopWatchButton.setFont(label.getFont().deriveFont((float)12));
        label.setBounds(50,40,200,40);
        timerButton.setBounds(60,90,100,30);
        textField.setBounds(20,90,textField.getPreferredSize().width,textField.getPreferredSize().height);
        muteButton.setBounds(20,135,30,20);
        jMute.setBounds(20,120,60,20);
        stopWatchButton.setBounds(35,70,100,30);
        progressBar.setBounds(25,65,150,10);
        stopLabel.setBounds(45,30,100,30);

        stopPanel.setLayout(null);
        stopPanel.add(stopWatchButton);
        stopPanel.add(label);

        tabbedPane.add("Timer",panel);
        tabbedPane.add("StopWatch", stopPanel);

        stopPanel.setLayout(null);
        stopPanel.add(stopLabel);
        stopPanel.add(stopWatchButton);
        stopPanel.add(label);

        panel.add(progressBar);

        panel.add(jMute);
        panel.add(label);
//        panel.add(noteField);
        panel.add(timerButton);
        panel.add(textField);
        panel.add(timerButton);
        panel.add(muteButton);

        muteButton.addItemListener(this);
        timerButton.addActionListener(this);
//        noteField.addActionListener(this);
        stopWatchButton.addActionListener(this);


        frame.setResizable(false);
        frame.setContentPane(tabbedPane);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void updateTimer(long current, long length) {
        label.setText(current/1000. + " / " + length/1000. + "  (" + (int)(100 - 100. * current / length) + "%)");
        progressBar.setValue((int)(100 - 100. * current / length));
    }

    public void updateStopWatch(long current) {
        stopLabel.setText(TimeFormatter.getString(current));
//        if (current == 1000)
//            stopLabel.setText(current/1000. + " second");
//            TimeFormatter.getString(current);
//        else
//            stopLabel.setText(current/1000. + " seconds");
//        TimeFormatter.getString(current);
    }

    public void finish() {
        progressBar.setValue(100);
        label.setText("BEEP");
        if(!muted) Beeper.beep();
        timerButton.setText("Timer");
        timer.stop();
        timer = null;
    }

    public void actionPerformed(ActionEvent e){
//        if(e.getSource() == noteField) {
//            frame.setTitle(noteField.getText());
//            noteField.setVisible(false);
         if(e.getSource() == stopWatchButton){
            if (timer == null) {
                start();
                stopWatchButton.setText("Stop");
                timer.countUp();
            } else {
                stopLabel.setText("Stopped with " + timer.getTime() / 1000. + ".");
                end();
            }
        } else {
            if (timer == null) {
                start();
                timerButton.setText("Stop");
                timer.countDown((long) (Double.parseDouble(textField.getText()) * 1000));
            } else {
                label.setText("Stopped with " + timer.getTime() / 1000. + ".");
                end();
            }
        }
    }

    private void end() {
        timerButton.setText("Timer");
        stopWatchButton.setVisible(true);
        muteButton.setVisible(true);
        textField.setVisible(true);
        jMute.setVisible(true);
        timer.stop();
        timer = null;
    }

    private void start() {
        timer = new TimerTracker(this, 125);
    }

    public void itemStateChanged(ItemEvent e) {
        muted = e.getStateChange() == ItemEvent.SELECTED;
    }
}
