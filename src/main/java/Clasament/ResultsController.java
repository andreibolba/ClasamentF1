package Clasament;

import Entity.PositionEntity;
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
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ResultsController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();
    @FXML private TextField idEtapa;
    @FXML private TextField hours;
    @FXML private TextField minutes;
    @FXML private TextField seconds;
    @FXML private TextField etapa;

    @FXML
    public void initialize() {
        usersEntity.read();
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
    void addResult() {
        if (idEtapa.getText().isEmpty() == false && hours.getText().isEmpty() == false && minutes.getText().isEmpty() == false && seconds.getText().isEmpty() == false) {
            Integer etapa = Integer.parseInt(idEtapa.getText());
            Integer echipa= usersEntity.getIdEchipa();
            Integer user=usersEntity.getID();
            String h=hours.getText();
            String m=minutes.getText();
            String s=seconds.getText();
            String myDateString = h+":"+m+":"+s;
            LocalTime localTime = LocalTime.parse(myDateString, DateTimeFormatter.ofPattern("HH:mm:ss"));
            System.out.println(usersEntity.getID() + "\n" + etapa + "\n" + localTime);
            databaseConnection.addResult(user,etapa,echipa,localTime);
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty text field");
            alert.setContentText("Empty text field.");
            alert.show();
        }
    }

    @FXML
    void showStanding() throws SQLException {
        List<PositionEntity> result=new ArrayList<>();
        result=databaseConnection.standings(Integer.parseInt(etapa.getText()));
        int i=1;
        int res=databaseConnection.finish(Integer.parseInt(etapa.getText()));
        if(res==1) {
            for (PositionEntity positionEntity : result) {
                System.out.println(i + ". " + positionEntity.getNume() + " " + positionEntity.getPrenume() + " " + positionEntity.getNumeEchipa() + " " + positionEntity.getTimp());
                i++;
            }
        }else if(res==0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unfinished stage");
            alert.setContentText("Unfinished stage!");
            alert.show();
        }
    }
}
