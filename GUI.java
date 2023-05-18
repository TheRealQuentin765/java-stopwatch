import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    public GUI() {
    }
    int count = 60;
    JFrame frame = new JFrame("StopWatch");
    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JLabel label = new JLabel("" + count);
    JButton button = new JButton("Count");
    public void stopWatchGUI(){
        frame.setLayout(new BorderLayout());
        panel.add(label);
        frame.setBounds(100,100,500,500);

        button.addActionListener(this);
        panel3.add(button);

        panel.setPreferredSize(new Dimension(100,50));
        panel2.setPreferredSize(new Dimension(100,100));
        frame.add(panel3,BorderLayout.SOUTH);
        frame.add(panel2,BorderLayout.NORTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        label.setText("" + count);
        count--;
    }

}
