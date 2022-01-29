package Clasament;

import Entity.UsersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

public class CreateStage {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();

    @FXML
    public void initialize() {
        usersEntity.read();
    }

    @FXML private TextField name;
    @FXML private TextField stageLocation;
    @FXML private TextField distance;
    @FXML private TextField laps;
    @FXML private TextField date;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ActiveStage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Stages");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void insert(ActionEvent event) throws ParseException, IOException {
        if(name.getText().isEmpty()==false&&stageLocation.getText().isEmpty()==false&&distance.getText().isEmpty()==false&&laps.getText().isEmpty()==false&&date.getText().isEmpty()==false){
            String nume=name.getText();
            String locatie=stageLocation.getText();
            float distanta=Float.parseFloat(distance.getText());
            int ture=Integer.parseInt(laps.getText());
            LocalDate data= LocalDate.parse(date.getText());
            databaseConnection.insert(nume,locatie,distanta,ture,data);
            Parent root = FXMLLoader.load(getClass().getResource("ActiveStage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Stages");
            stage.setScene(scene);
            stage.show();
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("There are some empty fields!");
            alert.show();
        }
    }
}
