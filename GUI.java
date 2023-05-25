import javax.swing.*;
import javax.xml.transform.Source;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener,TimerOutput{
    JFrame frame = new JFrame("StopWatch");
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Enter time below");
    JButton button = new JButton("Start");
    JTextField textField = new JTextField("30");
    JTextField noteField = new JTextField("Note");

    TimerTracker timer;
    public void stopWatchGUI(){

        panel.setLayout(null);

        frame.setBounds(100,100,200,200);
        noteField.setBounds(50,0,100,40);
        label.setFont(label.getFont().deriveFont((float)12));
        label.setBounds(50,50,200,40);
        button.setBounds(60,90,100,30);
        textField.setBounds(20,90,textField.getPreferredSize().width,textField.getPreferredSize().height);

        panel.add(label);
        button.addActionListener(this);
        noteField.addActionListener(this);
        panel.add(button);
        panel.add(textField);
        panel.add(button);
//        textField.setColumns(5);

        panel.add(noteField);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void update(long current, long length) {
        label.setText(current/1000. + " / " + length/1000. + "  (" + (int)(100 - 100. * current / length) + "%)");
    }

    public void finish() {
        label.setText("BEEP");
        Beeper.beep();
        button.setText("Start");
        timer = null;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == noteField) {
            frame.setTitle(noteField.getText());
            noteField.setVisible(false);
        }else {

            if (timer == null) {
                timer = new TimerTracker(this, 250);
                timer.countDown((long) (Double.parseDouble(textField.getText()) * 1000));
                button.setText("Stop");
            } else {
                label.setText("Stopped with " + timer.getTime() / 1000. + " left.");
                timer.stop();
                timer = null;
                button.setText("Start");
            }
        }
    }

}
