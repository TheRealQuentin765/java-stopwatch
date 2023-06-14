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
    JTextField textField = new JTextField("s");
    JTextField minutesField = new JTextField("m");
    JTextField hoursField = new JTextField("h");
    JCheckBox muteButton = new JCheckBox("Mute");
    JLabel jMute = new JLabel("Mute");
    JProgressBar progressBar = new JProgressBar(0,100);
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel stopLabel = new JLabel("0:0");
    boolean muted = false;
    TimerTracker timer;
    TimerTracker stopWatchTimer;
    public void stopWatchGUI(){

        panel.setLayout(null);

        frame.setBounds(100,100,200,250);
        label.setFont(label.getFont().deriveFont((float)12));
        stopWatchButton.setFont(label.getFont().deriveFont((float)12));
        label.setBounds(40,30,200,40);
        timerButton.setBounds(0,135,100,30);
        textField.setBounds(100,80,30,30);
        minutesField.setBounds(60,80,30,30);
        hoursField.setBounds(20,80,30,30);
        muteButton.setBounds(100,135,30,20);
        jMute.setBounds(100,120,60,20);
        stopWatchButton.setBounds(35,70,100,30);
        progressBar.setBounds(20,65,150,10);
        stopLabel.setBounds(20,30,150,30);

        tabbedPane.add("Timer",panel);
        tabbedPane.add("StopWatch", stopPanel);

        stopPanel.setLayout(null);
        stopPanel.add(stopWatchButton);
        stopPanel.add(label);
        stopPanel.setLayout(null);
        stopPanel.add(stopLabel);
        stopPanel.add(stopWatchButton);
        stopPanel.add(label);

        panel.add(progressBar);
        panel.add(minutesField);
        panel.add(jMute);
        panel.add(label);
        panel.add(hoursField);
        panel.add(timerButton);
        panel.add(textField);
        panel.add(timerButton);
        panel.add(muteButton);

        muteButton.addItemListener(this);
        timerButton.addActionListener(this);
        stopWatchButton.addActionListener(this);


        frame.setResizable(false);
        frame.setContentPane(tabbedPane);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void updateTimer(long current, long length) {
        label.setText(TimeFormatter.getString(current) + " (" + (int)(100 - 100. * current / length) + "%)");
        progressBar.setValue((int)(100 - 100. * current / length));
    }

    public void updateStopWatch(long current) {
        stopLabel.setText(TimeFormatter.getString(current));
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
        try {
            int parsedValue = Integer.parseInt(textField.getText());
            textField.setText(String.valueOf(parsedValue));
        } catch (NumberFormatException ex) {
            textField.setText("0");
        }
        try {
            int parsedValue = Integer.parseInt(minutesField.getText());
            minutesField.setText(String.valueOf(parsedValue));
        } catch (NumberFormatException ex) {
            minutesField.setText("0");
        }
        try {
            int parsedValue = Integer.parseInt(hoursField.getText());
            hoursField.setText(String.valueOf(parsedValue));
        } catch (NumberFormatException ex) {
            hoursField.setText("0");
        }

         if(e.getSource() == stopWatchButton){
            if (stopWatchTimer == null) {
                stopWatchTimer = new TimerTracker(this, 100);
                stopWatchButton.setText("Stop");
                stopWatchTimer.countUp();
            } else {
                stopLabel.setText("Stopped with " + stopWatchTimer.getTime() / 1000. + ".");
                stopWatchTimer.stop();
                stopWatchButton.setText("Start");
                stopWatchTimer = null;
            }
        } else {
            if (timer == null) {
                long time = (long) ((Double.parseDouble(textField.getText()) * 1000) + (Double.parseDouble(minutesField.getText())) * 60000 + (Double.parseDouble(hoursField.getText())) * 3600000);
                if (time < 10000)
                    timer = new TimerTracker(this, 100);
                else
                    timer = new TimerTracker(this, 250);
                timerButton.setText("Stop");
                timer.countDown(time);
            } else {
                label.setText("Stopped with " + TimeFormatter.getString(timer.getTime()) + ".");
                timerButton.setText("Start");
                timer.stop();
                timer = null;
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {
        muted = e.getStateChange() == ItemEvent.SELECTED;
    }
}
