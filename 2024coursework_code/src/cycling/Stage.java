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

    public boolean active_status = false;

    public boolean finish_state = false;

    public Riders[] riders;

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

    public Stage(StageType Stage_Type, String stageName, String description, double stage_length, LocalDateTime startTime){
        this.stage_type = Stage_Type;
        this.stageName = stageName;
        this.description =  description;
        this.stage_length  = stage_length;
        this.startTime  =  startTime;
        checkpoints = new ArrayList<Checkpoint>();
    }

    public static Stage [] stages;

    public static Stage getStage(int stageId) throws IDNotRecognisedException {
        boolean found = false;
        int i = 0;
        Stage curr = stages[i];
        while (!found){
            curr = stages[i];
            if (curr.stageId == stageId) {
                found = true;
            } else {
                i += 1;
            }
            if (!found) {
                throw new IDNotRecognisedException();
            }
        }
        return curr;
    }


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

    public StageType getStage_type(){
        return stage_type;
    }

    public boolean getActiveStatus(){
        return active_status;
    }

    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public static int[] stagePoints(int counter){
        int[] points = new int[15];
        if (Stage.stages[counter].stage_type == StageType.FLAT){
            points[0] = 50;
            points[1] = 30;
            points[2] = 20;
            points[3] = 18;
            points[4] = 16;
            points[5] = 14;
            points[6] = 12;
            points[7] = 10;
            points[8] = 8;
            points[9] = 7;
            points[10] = 6;
            points[11] = 5;
            points[12] = 4;
            points[13] = 3;
            points[14] = 2;
        }
        if (Stage.stages[counter].stage_type == StageType.MEDIUM_MOUNTAIN){
            points[0] = 30;
            points[1] = 25;
            points[2] = 22;
            points[3] = 19;
            points[4] = 17;
            points[5] = 15;
            points[6] = 13;
            points[7] = 11;
            points[8] = 9;
            points[9] = 7;
            points[10] = 6;
            points[11] = 5;
            points[12] = 4;
            points[13] = 3;
            points[14] = 2;
        }
        if (Stage.stages[counter].stage_type == StageType.HIGH_MOUNTAIN){
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
        }
        if (Stage.stages[counter].stage_type == StageType.TT){
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
        }
        return points;
    }


}




