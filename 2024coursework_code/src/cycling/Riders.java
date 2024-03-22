package cycling;

import java.time.LocalTime;

public class Riders {
    public String name;
    public int team_id;
    public int id;

    public int yearofbirth;

    public static Riders[]riders;

    public LocalTime start_time;
    public LocalTime finish_time;
    public Results[] results;

    public LocalTime totalElapsedTime;



    public Riders(int team_id,String name,int birth){
        this.team_id=team_id;
        this.name=name;
        this.yearofbirth = birth;
    }

    public int getId() {
        return id;
    }

    public static Riders getRider(int id) throws IDNotRecognisedException{
        boolean found = false;
        int i = 0;
        Riders curr = Riders.riders[0];
        while (!found) {
            curr = Riders.riders[i];
            if (curr.id == id) {
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
}
