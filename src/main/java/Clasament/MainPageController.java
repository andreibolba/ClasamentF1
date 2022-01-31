package Clasament;

import Clasament.DatabaseConnection;
import Entity.UsersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();
    @FXML Label label;

    @FXML
    public void initialize() {
        usersEntity.read();
        String h="Hello "+ usersEntity.getUsername()+"!";
        label.setText(h);
    }

    @FXML
    void BackOnLogIn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void personalProfileBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PersonalProfile.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
        System.out.println(usersEntity.getID());
        System.out.println(usersEntity.getUsername());
    }

    @FXML
    void onAddResult(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddResult.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Add Result");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onStanding(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Standings.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void editRemoveTeam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditRemoveTeams.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onActiveStage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ActiveStage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Active Stage");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onTeams(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Teams.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Team valid");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onValidUser(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Users.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("User valid");
        stage.setScene(scene);
        stage.show();
    }
}
