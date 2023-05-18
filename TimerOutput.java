public interface TimerOutput {
    public void update(long current, long length);
    public void finish();
}