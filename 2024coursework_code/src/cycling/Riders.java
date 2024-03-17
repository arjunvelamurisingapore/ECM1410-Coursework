package cycling;

public class Riders {
    public String name;
    public int team_id;
    public int id;

    public int yearofbirth;

    public static Riders[]riders;

    public Riders(int team_id,String name,int birth){
        this.team_id=team_id;
        this.name=name;
        this.yearofbirth = birth;
    }

    public int getId() {
        return id;
    }
}
