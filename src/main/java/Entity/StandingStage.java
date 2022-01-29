package Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StandingStage {
    private StringProperty numeE;
    private StringProperty prenumeE;
    private StringProperty numeEchipaE;
    private StringProperty timpE;

    public StandingStage(String nume, String prenume, String numeEchipa, String timp) {
        this.numeE = new SimpleStringProperty(nume);
        this.prenumeE = new SimpleStringProperty(prenume);
        this.numeEchipaE = new SimpleStringProperty(numeEchipa);
        this.timpE = new SimpleStringProperty(timp);
    }

    public StandingStage(){

    }

    public String getNumeE() {
        return numeE.get();
    }

    public StringProperty numeEProperty() {
        return numeE;
    }

    public String getNumeEchipaE() {
        return numeEchipaE.get();
    }

    public StringProperty numeEchipaEProperty() {
        return numeEchipaE;
    }

    public String getPrenumeE() {
        return prenumeE.get();
    }

    public StringProperty prenumeEProperty() {
        return prenumeE;
    }

    public void setPrenumeE(String prenumeE) {
        this.prenumeE.set(prenumeE);
    }

    public String getTimpE() {
        return timpE.get();
    }

    public StringProperty timpEProperty() {
        return timpE;
    }
}
