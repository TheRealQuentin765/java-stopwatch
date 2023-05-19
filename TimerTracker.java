//import static java.util.concurrent.TimeUnit.*;
import java.util.Timer;
import java.util.TimerTask;
class TimerTracker {
    private final Timer myTimer = new Timer();

    private TimerOutput output;

    private int step;
    private long time;

    public TimerTracker(TimerOutput output, int step) {
        this.output = output;
        this.step = step;
    }

    public void countDown(long length) {

        time = length - length%step;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                output.update(time,length);
                if (time > 0) {
                    time-=step;
                    time = Math.max(time,0);
                    return;
                }
                myTimer.cancel();
                if (time == 0) output.finish();
            }
        };
        myTimer.scheduleAtFixedRate(task, length % step, step);
    }

    public void stop(){
        time = -1;
    }
}