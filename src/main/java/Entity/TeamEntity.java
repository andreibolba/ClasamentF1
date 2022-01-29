package Entity;

public class TeamEntity {
    private int id;
    private String name;
    private boolean verify;
    private int memebers;

    public TeamEntity(int id, String name, boolean verify, int memebers) {
        this.id = id;
        this.name = name;
        this.verify = verify;
        this.memebers = memebers;
    }

    public TeamEntity(){

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

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public int getMemebers() {
        return memebers;
    }

    public void setMemebers(int memebers) {
        this.memebers = memebers;
    }
}
