package Clasament;

import Entity.TeamEntity;
import Entity.UsersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TeamsController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();
    private List<TeamEntity> teams;
    private TeamEntity team;
    int index=0;

    @FXML private Label id;
    @FXML private Label teamName;
    @FXML private Label verify;
    @FXML private Label membersNumber;
    @FXML private Button next;
    @FXML private Button prev;
    @FXML private Button verifyButton;

    void view(int i){
        team=teams.get(i);
        id.setText(String.valueOf(team.getId()));
        teamName.setText(team.getName());
        if(usersEntity.getRole().equals("Administrator")) {
            if (team.isVerify() == true) {
                verify.setText("Yes");
                verifyButton.setVisible(false);
            } else {
                verify.setText("No");
                verifyButton.setVisible(true);
            }
        }else{
            verifyButton.setVisible(false);
            if (team.isVerify() == true) {
                verify.setText("Yes");
            } else {
                verify.setText("No");

            }
        }
        membersNumber.setText(String.valueOf(team.getMemebers()));
    }

    @FXML
    public void initialize() {
        usersEntity.read();
        teams=databaseConnection.getAllTeams();
        prev.setVisible(false);
        view(index);
    }

    @FXML
    void backToMain(ActionEvent event) throws IOException {
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
    void onNextButton(){
        index++;
        view(index);
        if(index+1==teams.size())
            next.setVisible(false);
        prev.setVisible(true);
    }

    @FXML
    void onPrevButton(){
        index--;
        view(index);
        if(index==0)
            prev.setVisible(false);
        next.setVisible(true);
    }

    @FXML
    void verifyTeam(){
        if(team.getMemebers()>=2&&team.getMemebers()<=5){
            databaseConnection.validTeam(team.getId());
            team.setVerify(true);
            verifyButton.setVisible(false);
            view(index);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Memebrs");
            alert.setContentText("Number of members is not valid");
        }
    }
}
