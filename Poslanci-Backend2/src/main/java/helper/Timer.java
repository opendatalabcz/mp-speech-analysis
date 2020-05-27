package helper;

public class Timer {
    private long startTime = -1;
    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public String getTime() {
        long durationInMillis = System.currentTimeMillis() - startTime;
        long millis = durationInMillis % 1000;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;
        long days = (durationInMillis / (1000*60*60*24));

        String time;
        if(days > 0)
            time = String.format("%d days, %02d:%02d:%02d.%03d", days, hour, minute, second, millis);
        else
            time = String.format("%02d:%02d:%02d.%03d", hour, minute, second, millis);
        return time;
    }
}
