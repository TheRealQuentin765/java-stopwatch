//import static java.util.concurrent.TimeUnit.*;
import java.util.Timer;
import java.util.TimerTask;
class TimerTracker {
    private final Timer myTimer = new Timer();

    public final int STEP = 250;
    private long time;

    public void countDown(long length) {

        time = length;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                time-=STEP;
                System.out.println(time);
                if (time > 0) return;
                myTimer.cancel();
                System.out.println("BEEP BEEP BEEP");
            }
        };
        myTimer.scheduleAtFixedRate(task, STEP, STEP);
    }
}