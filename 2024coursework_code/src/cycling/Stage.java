package cycling;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Stage {
    public int stageId;
    public StageType stage_type;

    public String stageName;

    public String description;

    public LocalDateTime startTime;

    public double stage_length;
    public ArrayList<Checkpoint> checkpoints = new ArrayList<>();

    public Stage(int Stage_ID, StageType Stage_Type, String stageName,  String description, double stage_length, LocalDateTime startTime){
        this.stageId = Stage_ID;
        this.stage_type = Stage_Type;
        this.stageName = stageName;
        this.description =  description;
        this.stage_length  = stage_length;
        this.startTime  =  startTime;
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



