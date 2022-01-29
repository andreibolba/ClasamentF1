package Entity;


public class PositionEntity {
    private String nume;
    private String prenume;
    private String numeEchipa;
    private String timp;

    public PositionEntity(String nume, String prenume, String numeEchipa, String timp) {
        this.nume = nume;
        this.prenume = prenume;
        this.numeEchipa = numeEchipa;
        this.timp = timp;
    }


    public PositionEntity(){

    }

    public String getNume() {return nume;}
    public String getPrenume() {return prenume;}
    public String getNumeEchipa() {return numeEchipa;}
    public String getTimp() {return timp;}

    public void setNume(String nume) {this.nume = nume;}
    public void setPrenume(String prenume) {this.prenume = prenume;}
    public void setNumeEchipa(String numeEchipa) {this.numeEchipa = numeEchipa;}
    public void setTimp(String timp) {this.timp = timp;}
}
