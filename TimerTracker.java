//import static java.util.concurrent.TimeUnit.*;
import java.util.Timer;
import java.util.TimerTask;
class TimerTracker {
    private final Timer myTimer = new Timer();

    final private TimerOutput output;

    final private int step;
    private long time;
    private boolean stop;

    public TimerTracker(TimerOutput output, int step) {
        this.output = output;
        this.step = step;
    }

    public void countUp() {
        stop = false;
        time = step;
        output.updateStopWatch(0);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (stop) {
                    myTimer.cancel();
                    myTimer.purge();
                    return;
                }
                time+=step;
                output.updateStopWatch(time);
            }
        };
        myTimer.scheduleAtFixedRate(task, step, step);
    }


    public void countDown(long length) {
        stop = false;
        time = length - length%step;
        output.updateTimer(0,length);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (stop) {
                    myTimer.cancel();
                    myTimer.purge();
                    return;
                }
                if (time > 0) {
                    output.updateTimer(time,length);
                    time-=step;
                    time = Math.max(time,0);
                    return;
                }
                if (time == 0) {
                    output.finish();
                    stop = true;
                }
            }
        };
        myTimer.scheduleAtFixedRate(task, length % step, step);
    }

    public long getTime() {
        return time;
    }

    public void stop(){
        stop = true;
    }
}