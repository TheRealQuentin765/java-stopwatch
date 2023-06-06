public class TimeFormatter {
    public static int getMil(long val) {
        return (int) (val % 1000);
    }

    public static int getSec(long val) {
        return (int) ((val % 60000) / 1000);
    }

    public static int getMin(long val) {
        return (int) ((val % 3600000) / 60000);
    }

    public static int getHour(long val) {
        return (int) (val / 3600000);
    }

    public static String getString(long val) {
        String result = "";

        int hour = getHour(val);
        if (hour != 0) result += hour + "h ";
        int min = getMin(val);
        if (min != 0) result += min + ":";
        int sec = getSec(val);
        if (sec != 0) result += sec + ".";
        int mil = getMil(val);
        if (mil != 0) result += mil + " ";

        int length = result.length();
        if (length == 0)
            return "0";
        else
            return result.substring(0, length-1);
    }
}
