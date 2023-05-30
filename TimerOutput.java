public interface TimerOutput {
    void updateTimer(long current, long length);
    void updateStopWatch(long current);
    void finish();
}