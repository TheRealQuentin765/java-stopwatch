public class Main {
    public static void main(String[] arg) {
        TimerOutput output = new TimerOutput() {
            @Override
            public void update(long current, long length) {
                System.out.println(100 - 100. * current / length + "%");
            }

            @Override
            public void finish() {
                System.out.println("BEEP BEEP BEEP");
            }
        };


        TimerTracker core = new TimerTracker(output,250);
        core.countDown(12345);
    }
}