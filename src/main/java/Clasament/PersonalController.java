package Clasament;

import Entity.UsersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonalController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity=new UsersEntity();
    @FXML Label id;
    @FXML Label username;
    @FXML Label nume;
    @FXML Label prenume;
    @FXML Label echipa;
    @FXML Label monopost;
    @FXML Label compond;

    @FXML
    public void initialize() {
        usersEntity.read();
        id.setText(String.valueOf(usersEntity.getID()));
        username.setText(usersEntity.getUsername());
        nume.setText(usersEntity.getfName());
        prenume.setText(usersEntity.getlName());
        echipa.setText(usersEntity.getTeamName());
        monopost.setText(usersEntity.getMonopostName());
        compond.setText(usersEntity.getCompond());
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
    void editRemove(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Change.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Main Concurent Page");
        stage.setScene(scene);
        stage.show();
    }

}
