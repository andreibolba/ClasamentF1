package Clasament;

import Clasament.DatabaseConnection;
import Entity.UsersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class logInController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity=new UsersEntity();

    @FXML
    public void initialize() {
        usersEntity.read();
    }

    @FXML private TextField user;
    @FXML private TextField firstName;
    @FXML private TextField secondName;
    @FXML private TextField teamName;
    @FXML private TextField idTeam;
    @FXML private TextField idMonopost;

    @FXML
    void LogInButton(ActionEvent event) throws IOException {
        initialize();
        if(user.getText().isEmpty()==false) {
            String username = user.getText();
            System.out.println(username);
            this.usersEntity = databaseConnection.getUser(username);
            if (this.usersEntity.getRole().length() == "Administrator".length()) {
                usersEntity.write();
                Parent root = FXMLLoader.load(getClass().getResource("MainAdminPage.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Main Admin Page");
                stage.setScene(scene);
                stage.show();


            } else if (this.usersEntity.getRole().length() == "Concurent".length()) {
                usersEntity.write();
                Parent root = FXMLLoader.load(getClass().getResource("MainConcurentPage.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Main Concurent Page");
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid username");
                alert.setContentText("This username does not exist!");
                alert.show();
                Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Log In");
                stage.setScene(scene);
                stage.show();
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("Empty text field.");
            alert.show();
        }
        user.clear();
    }

    @FXML
    void Register(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void BackOnLogIn(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SignUpButton(ActionEvent event){
        if(firstName.getText().isEmpty()==false && secondName.getText().isEmpty()==false && idMonopost.getText().isEmpty()==false && idTeam.getText().isEmpty()==false) {
            String fName = firstName.getText();
            String lName = secondName.getText();
            int IDTeam =  Integer.parseInt(idTeam.getText());;
            int IDMonopost =  Integer.parseInt(idMonopost.getText());;
            try {
                databaseConnection.newUser(fName, lName,IDTeam,IDMonopost);

                Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("LogIn");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                System.out.println("Server does not working!");
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("There are some empty fields!");
            alert.show();
        }

    }

    @FXML
    void teamRegister(ActionEvent event){
        if(teamName.getText().isEmpty()==false) {
            String team = teamName.getText();

            try {
                databaseConnection.newTeam(team);

                Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("LogIn");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                System.out.println("Server does not working!");
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("Empty text field.");
            alert.show();
        }
    }

    @FXML
    void teamRegisterButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TeamRegister.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();

    }


}
