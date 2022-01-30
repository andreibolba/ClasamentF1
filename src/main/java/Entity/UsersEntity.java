package Entity;

import java.io.*;
import java.util.Scanner;

public class UsersEntity {
    private int ID;
    private String username;
    private String fName;
    private String lName;
    private String teamName;
    private String monopostName;
    private String role;
    private String compond;
    private Integer idEchipa;
    private int idMonopost;
    private boolean valid;

    public UsersEntity(){

    }

    public UsersEntity(int id, String username, String fName, String lName, String teamName, String monopostName, String role, String compond, Integer idEchipa, int idMonopost) {
        ID = id;
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.teamName = teamName;
        this.monopostName = monopostName;
        this.role = role;
        this.compond=compond;
        this.idEchipa=idEchipa;
        this.idMonopost = idMonopost;
    }

    public int getID() {return ID;}
    public String getUsername() {return username;}
    public String getfName() {return fName;}
    public String getlName() {return lName;}
    public String getTeamName() {return teamName;}
    public String getMonopostName() {return monopostName;}
    public String getRole() {return role;}
    public String getCompond() {return compond;}

    public void setID(int ID) {this.ID = ID;}
    public void setUsername(String username) {this.username = username;}
    public void setfName(String fName) {this.fName = fName;}
    public void setlName(String lName) {this.lName = lName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}
    public void setMonopostName(String monopostName) {this.monopostName = monopostName;}
    public void setRole(String role) {this.role = role;}
    public void setCompond(String compond) {this.compond = compond;}

    public void write(){
        try {
            FileOutputStream fileOutputStream= new FileOutputStream("I:\\FMI-AnII\\Semestrul_1\\MIP\\ClasamentF1\\src\\main\\java\\Entity\\userInformationLogIn.txt");
            PrintWriter printWriter=new PrintWriter(fileOutputStream);
            printWriter.write(ID+"\n"+username+"\n"+fName+"\n"+lName+"\n"+teamName+"\n"+monopostName+"\n"+role+"\n"+compond+"\n"+idEchipa+"\n"+idMonopost);
            printWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void read(){
        try {
            String echipa;
            File myObj = new File("I:\\FMI-AnII\\Semestrul_1\\MIP\\ClasamentF1\\src\\main\\java\\Entity\\userInformationLogIn.txt");
            Scanner myReader = new Scanner(myObj);
            ID=Integer.parseInt(myReader.nextLine());
            username=myReader.nextLine();
            fName=myReader.nextLine();
            lName=myReader.nextLine();
            teamName=myReader.nextLine();
            monopostName=myReader.nextLine();
            role=myReader.nextLine();
            compond=myReader.nextLine();
            echipa=myReader.nextLine();
            idEchipa=Integer.parseInt(echipa);
            idMonopost=Integer.parseInt(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Integer getIdEchipa() {
        return idEchipa;
    }

    public void setIdEchipa(Integer idEchipa) {
        this.idEchipa = idEchipa;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getIdMonopost() {
        return idMonopost;
    }

    public void setIdMonopost(int idMonopost) {
        this.idMonopost = idMonopost;
    }
}
