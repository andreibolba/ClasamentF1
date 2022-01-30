package Clasament;

import Entity.UsersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();
    @FXML private TextField newfName;
    @FXML private TextField newlName;
    @FXML private TextField newTeamID;
    @FXML private TextField newMonopostID;
    @FXML
    private TextField oldTeamName;
    @FXML private TextField newTeamName;

    @FXML
    public void initialize() {
        usersEntity.read();
    }

    @FXML
    void back(ActionEvent event) throws IOException{
        if(usersEntity.getRole().length()=="Administrator".length())
        {
            Parent root = FXMLLoader.load(getClass().getResource("MainAdminPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Main Admin Page");
            stage.setScene(scene);
            stage.show();
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("MainConcurentPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Main Concurent Page");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void updateAccount(){
        if(newfName.getText().isEmpty()==true&&newlName.getText().isEmpty()==true&&newTeamID.getText().isEmpty()==true&&newMonopostID.getText().isEmpty()==true){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("Empty text field.");
            alert.show();

        }else{
            String nume,prenume;
            int ID= usersEntity.getID(), teamID,monopostID;
            if(newfName.getText().isEmpty())
                nume= usersEntity.getfName();
            else
                nume=newfName.getText();

            if(newlName.getText().isEmpty())
                prenume= usersEntity.getlName();
            else
                prenume=newlName.getText();

            if(newTeamID.getText().isEmpty())
                teamID=usersEntity.getIdEchipa();
            else
                teamID=Integer.parseInt(newTeamID.getText());

            if(newMonopostID.getText().isEmpty())
                monopostID= usersEntity.getIdMonopost();
            else
                monopostID=Integer.parseInt(newMonopostID.getText());
            databaseConnection.updateAccount(ID,nume,prenume,teamID,monopostID);
            usersEntity= databaseConnection.getUserById(usersEntity.getID());
            usersEntity.write();
        }
    }

    @FXML
    void deleteTeam(){
        if(oldTeamName.getText().isEmpty()==false){
            String name=oldTeamName.getText();
            databaseConnection.deleteTeam(name);
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("Empty text field.");
            alert.show();
        }
    }

    @FXML
    void updateTeam(){
        if(oldTeamName.getText().isEmpty()==false&&newTeamName.getText().isEmpty()==false){
            String name=oldTeamName.getText();
            String newName=newTeamName.getText();
            databaseConnection.updateTeam(newName,name);
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("Empty text field.");
            alert.show();
        }
    }

    @FXML
    void deleteAccount(ActionEvent event) throws IOException {
        databaseConnection.deleteAccount(usersEntity.getID());
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }
}
