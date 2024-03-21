package cycling;

import java.time.LocalTime;

public class Results {
    //public LocalTime start;
    //public LocalTime finish;
    public static Riders[] riders;
    public static Checkpoint[] checkpoints;
    public static Stage[] stages;
    public static LocalTime[] elapsedTimes;

    //public static Results[] results;
    public static LocalTime sumLocalTimes(LocalTime[] times) {
        int totalSeconds = 0;

        // Sum up the total seconds from all the LocalTime objects
        for (LocalTime time : times) {
            totalSeconds += time.toSecondOfDay();
        }

        // Convert the total seconds back to LocalTime
        return LocalTime.ofSecondOfDay(totalSeconds);
    }

    public static int[] convertToTotalSecondsArray(LocalTime[] times) {
        int[] totalSecondsArray = new int[times.length];
        for (int i = 0; i < times.length; i++) {
            totalSecondsArray[i] = times[i].toSecondOfDay();
        }
        return totalSecondsArray;
    }


}
