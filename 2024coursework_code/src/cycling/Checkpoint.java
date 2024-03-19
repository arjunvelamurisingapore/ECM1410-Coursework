package cycling;

public class Checkpoint {
    public int checkpoint_id;
    public CheckpointType checkpoint_type;

    public Riders[] riders;

    public static Checkpoint [] checkpoints;

    public int getCheckpoint_id() {
        return checkpoint_id;
    }

    public Riders[] getRiders() {
        return riders;
    }
}
