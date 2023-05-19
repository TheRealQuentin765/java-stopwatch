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
                Beeper.beep();
            }
        };


        TimerTracker core = new TimerTracker(output,123);
        core.countDown(1234);

        GUI stopGUI = new GUI();
        stopGUI.stopWatchGUI();

    }
}