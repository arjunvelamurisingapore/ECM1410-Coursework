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

    public Teams(String name, String description){
        this.team_name = name;
        this.team_description = description;
        team_riders = new ArrayList<Riders>();
    }


    public String getTeam_name() {
        return team_name;
    }

    public ArrayList<Riders> getTeam_riders() {
        return team_riders;
    }

    public static Teams getTeam(int team_id) throws IDNotRecognisedException{
        boolean found = false;
        int i = 0;
        Teams curr = Stage.teams.get(0);
        while (!found) {
            curr = Stage.teams.get(i);
            if (curr.team_id == team_id) {
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

    public int getTeamId(){
        return team_id;
    }

    public String getName(){
        return team_name;
    }
}
