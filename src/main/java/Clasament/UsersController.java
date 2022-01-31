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

public class UsersController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();
    private List<UsersEntity> users;
    private UsersEntity user;
    int index=0;

    @FXML private Label id;
    @FXML private Label username;
    @FXML private Label nume;
    @FXML private Label prenume;
    @FXML private Label monopost;
    @FXML private Label compond;
    @FXML private Label teamName;
    @FXML private Label verify;
    @FXML private Button next;
    @FXML private Button prev;
    @FXML private Button verifyButton;

    void view(int i){
        user=users.get(i);
        id.setText(String.valueOf(user.getID()));
        username.setText(user.getUsername());
        nume.setText(user.getlName());
        prenume.setText(user.getfName());
        monopost.setText(user.getMonopostName());
        compond.setText(user.getCompond());
        teamName.setText(user.getTeamName());
        if(usersEntity.getRole().equals("Administrator")) {
            if (user.isValid() == true) {
                verify.setText("Yes");
                verifyButton.setVisible(false);
            } else {
                verify.setText("No");
                verifyButton.setVisible(true);
            }
        }else{
            verifyButton.setVisible(false);
            if (user.isValid() == true) {
                verify.setText("Yes");
            } else {
                verify.setText("No");

            }
        }

    }

    @FXML
    public void initialize() {
        usersEntity.read();
        users=databaseConnection.getAllUsers();
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
        if(index+1==users.size())
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
    void verifyUser() {
        databaseConnection.validUser(user.getID());
        user.setValid(true);
        verifyButton.setVisible(false);
        view(index);
    }
}
