package cycling;

import java.util.ArrayList;

public class Teams {
    public int team_id;
    public String team_name;
    public String team_description;
    public ArrayList<Riders> team_riders = new ArrayList<>();
    public static Teams[] teams;

    public Teams(int id, String name, String description){
        this.team_id = id;
        this.team_name = name;
        this.team_description = description;
        team_riders = new ArrayList<Riders>();
    }







}
