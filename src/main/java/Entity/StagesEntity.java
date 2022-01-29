package Entity;

public class StagesEntity {
    private int id;
    private String name;
    private String location;
    private float distance;
    private int laps;
    private String date;
    private boolean finished;


    public StagesEntity(int id, String name, String location, float distance, int laps, String date, boolean finished) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.laps = laps;
        this.date = date;
        this.finished = finished;
    }

    public StagesEntity(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
