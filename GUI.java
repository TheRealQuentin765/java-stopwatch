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
    JButton button = new JButton("Timer Start");
    JButton stopWatchButton = new JButton("StopWatch");
    JTextField textField = new JTextField("30");
//    JTextField noteField = new JTextField("Note");
    JCheckBox muteButton = new JCheckBox("Mute");
    JLabel jMute = new JLabel("Mute");
    JProgressBar progressBar = new JProgressBar(0,100);
    JTabbedPane tabbedPane = new JTabbedPane();

    boolean muted = false;

    TimerTracker timer;
    public void stopWatchGUI(){

        panel.setLayout(null);

        frame.setBounds(100,100,200,300);
//        noteField.setBounds(50,0,100,40);
        label.setFont(label.getFont().deriveFont((float)12));
        stopWatchButton.setFont(label.getFont().deriveFont((float)12));
        label.setBounds(50,40,200,40);
        button.setBounds(60,90,100,30);
        textField.setBounds(20,90,textField.getPreferredSize().width,textField.getPreferredSize().height);
        muteButton.setBounds(20,135,30,20);
        jMute.setBounds(20,120,60,20);
        stopWatchButton.setBounds(60,130,100,30);
        progressBar.setBounds(25,65,150,10);

        tabbedPane.add("Timer",panel);
        tabbedPane.add("StopWatch", stopPanel);
        panel.add(progressBar);
        panel.add(stopWatchButton);
        panel.add(jMute);
        panel.add(label);
//        panel.add(noteField);
        panel.add(button);
        panel.add(textField);
        panel.add(button);
        panel.add(muteButton);

        muteButton.addItemListener(this);
        button.addActionListener(this);
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
        if (current == 1000)
            label.setText(current/1000. + " second");
        else
            label.setText(current/1000. + " seconds");
    }

    public void finish() {
        progressBar.setValue(100);
        label.setText("BEEP");
        if(!muted) Beeper.beep();
        end();
    }

    public void actionPerformed(ActionEvent e){
//        if(e.getSource() == noteField) {
//            frame.setTitle(noteField.getText());
//            noteField.setVisible(false);
         if(e.getSource() == stopWatchButton){
            if (timer == null) {
                start();
                timer.countUp();
                muteButton.setVisible(false);
                textField.setVisible(false);
                jMute.setVisible(false);
                progressBar.setVisible(false);
            }
        } else {
            if (timer == null) {
                start();
                progressBar.setVisible(true);
                timer.countDown((long) (Double.parseDouble(textField.getText()) * 1000));
            } else {
                label.setText("Stopped with " + timer.getTime() / 1000. + ".");
                end();
            }
        }
    }

    private void end() {
        button.setText("Timer");
        stopWatchButton.setVisible(true);
        muteButton.setVisible(true);
        textField.setVisible(true);
        jMute.setVisible(true);
        timer.stop();
        timer = null;
    }

    private void start() {
        timer = new TimerTracker(this, 250);
        button.setText("Stop");
        stopWatchButton.setVisible(false);
    }

    public void itemStateChanged(ItemEvent e) {
        muted = e.getStateChange() == ItemEvent.SELECTED;
    }
}
