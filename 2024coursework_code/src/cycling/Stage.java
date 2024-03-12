package cycling;

import java.util.ArrayList;

public class Stage {
    public int stageId;
    public StageType stage_type;

    public double stage_length;
    public ArrayList<Checkpoint> checkpoints = new ArrayList<>();

    public Stage(int Stage_ID, StageType Stage_Type){
        this.stageId = Stage_ID;
        this.stage_type = Stage_Type;
        checkpoints = new ArrayList<Checkpoint>();

    }
    public static Stage [] stages;

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public double getStage_length() {
        return stage_length;
    }

    public void setStage_length(double stage_length) {
        this.stage_length = stage_length;
    }
}



