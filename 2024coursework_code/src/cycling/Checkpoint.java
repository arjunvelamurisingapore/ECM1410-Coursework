package cycling;

import java.util.zip.Checksum;

public class Checkpoint {
    public int checkpoint_id;
    public CheckpointType checkpoint_type;

    public double location;

    public double averageGradient;

    public double length;

    public Riders[] riders;

    public static Checkpoint [] checkpoints;

    public int getCheckpoint_id() {
        return checkpoint_id;
    }

    public Riders[] getRiders() {
        return riders;
    }

    public Checkpoint(double location, CheckpointType type, double averageGradient, double length){
        this.location = location;
        this.checkpoint_type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    public Checkpoint(double location){
        this.location = location;
    }

    public static int[] checkpointSprintPoints(){
        int[] points = new int[15];
            points[0] = 20;
            points[1] = 17;
            points[2] = 15;
            points[3] = 13;
            points[4] = 11;
            points[5] = 10;
            points[6] = 9;
            points[7] = 8;
            points[8] = 7;
            points[9] = 6;
            points[10] = 5;
            points[11] = 4;
            points[12] = 3;
            points[13] = 2;
            points[14] = 1;
        return points;
    }

    public static int[] C4Points(){
        int[] points = new int[8];
        points[0] = 1;
        points[1] = 0;
        points[2] = 0;
        points[3] = 0;
        points[4] = 0;
        points[5] = 0;
        points[6] = 0;
        points[7] = 0;
        return points;
    }

    public static int[] C3Points(){
        int[] points = new int[8];
        points[0] = 2;
        points[1] = 1;
        points[2] = 0;
        points[3] = 0;
        points[4] = 0;
        points[5] = 0;
        points[6] = 0;
        points[7] = 0;
        return points;
    }

    public static int[] C2Points(){
        int[] points = new int[8];
        points[0] = 5;
        points[1] = 3;
        points[2] = 2;
        points[3] = 1;
        points[4] = 0;
        points[5] = 0;
        points[6] = 0;
        points[7] = 0;
        return points;
    }

    public static int[] C1Points(){
        int[] points = new int[8];
        points[0] = 10;
        points[1] = 8;
        points[2] = 6;
        points[3] = 4;
        points[4] = 2;
        points[5] = 1;
        points[6] = 0;
        points[7] = 0;
        return points;
    }

    public static int[] HCPoints(){
        int[] points = new int[8];
        points[0] = 20;
        points[1] = 15;
        points[2] = 12;
        points[3] = 10;
        points[4] = 8;
        points[5] = 6;
        points[6] = 4;
        points[7] = 2;
        return points;
    }


}
