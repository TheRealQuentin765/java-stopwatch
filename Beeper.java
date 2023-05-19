import javax.sound.sampled.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Beeper {
    public static void beep(){
        try {
            File file = new File("resources/alarm.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
            Timer myTimer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        sound.close();
                        clip.close();
                        clip.stop();
                    } catch (Exception e) {
                        System.out.println("oh no 2");
                    }
                }
            };

            myTimer.schedule(task,1000);
        } catch (Exception e) {
            System.out.println("oh no");
        }
    }
}
