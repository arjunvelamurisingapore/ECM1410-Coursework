package cycling;
import java.util.ArrayList;

public class Race {
    public String race_name;
    public String description;
    public int race_id;

    public ArrayList<Stage>stages = new ArrayList<>();
    public Riders[] riders;

    public static Race [] races;

    public Race(String race_name, String description){
        this.race_name = race_name;
        this.description = description;
        riders = new Riders[15];
    }

    public Race(String race_name, String description,int id){
        this.race_name = race_name;
        this.description = description;
        this.race_id = id;
        riders = new Riders[15];
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Stage> stages) {
        this.stages = stages;
    }
}