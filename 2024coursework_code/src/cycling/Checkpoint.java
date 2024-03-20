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

}
