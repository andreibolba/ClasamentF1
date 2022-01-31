package Clasament;

import Entity.StagesEntity;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class StageCRUDController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();
    private int index=0;
    private List<StagesEntity> stages;
    private StagesEntity currentStage;

    @FXML private Label ID;
    @FXML private Label name;
    @FXML private Label stageLocation;
    @FXML private Label distance;
    @FXML private Label laps;
    @FXML private Label date;
    @FXML private Label finished;
    @FXML private Button next;
    @FXML private Button prev;
    @FXML private Button valid;
    @FXML private TextField nameF;
    @FXML private TextField stageLocationF;
    @FXML private TextField distanceF;
    @FXML private TextField lapsF;
    @FXML private TextField dateF;
    @FXML private Button delete;
    @FXML private Button update;
    @FXML private Button create;
    @FXML private Button validate;


    @FXML
    public void initialize() {
        usersEntity.read();
        stages=databaseConnection.getAllStages();
        view(index);
        prev.setVisible(false);
        boolean rol=usersEntity.getRole().equals("Administrator");
        update.setVisible(rol);
        create.setVisible(rol);
        delete.setVisible(rol);
        validate.setVisible(rol);
        nameF.setVisible(rol);
        stageLocationF.setVisible(rol);
        distanceF.setVisible(rol);
        lapsF.setVisible(rol);
        dateF.setVisible(rol);
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

    void view(int i){
        currentStage=stages.get(i);
        ID.setText(String.valueOf(currentStage.getId()));
        name.setText(currentStage.getName());
        stageLocation.setText(currentStage.getLocation());
        distance.setText(String.valueOf(currentStage.getDistance())+" km");
        laps.setText(String.valueOf(currentStage.getLaps()));
        date.setText(currentStage.getDate());
        if(currentStage.isFinished()==false)
        {
            finished.setText("No");
        }else{
            finished.setText("Yes");
        }
    }

    @FXML
    void onNextButton(){
        index++;
        view(index);
        if(index+1==stages.size())
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
    void validate(){
        int c=databaseConnection.count(currentStage.getId());
        int cUser=databaseConnection.countUser();
        if(c==cUser-1){
            databaseConnection.updateStageStatus(currentStage.getId());
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Update successfully!");
            alert.show();
            currentStage.setFinished(true);
            view(index);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Not enough results!");
            alert.show();
        }
    }

    @FXML
    void onInsert(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AddStage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Insert stage");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteStage(){
        databaseConnection.deleteStage(currentStage.getId());
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Al good");
        alert.setContentText("Stage is deleted");
        alert.show();
        stages.remove(currentStage);
        index=0;
        view(index);
    }

    @FXML
    void onUpdate(){
        if(nameF.getText().isEmpty()==true&&stageLocationF.getText().isEmpty()==true&&distanceF.getText().isEmpty()==true&&lapsF.getText().isEmpty()==true&&dateF.getText().isEmpty()==true){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty fields");
            alert.setContentText("Empty fields!");
            alert.show();
        }else{
            String nume,loc;
            float dist;
            int tur;
            LocalDate data;
            if(nameF.getText().isEmpty()==true)
                nume=currentStage.getName();
            else
                nume=nameF.getText();


            if(stageLocationF.getText().isEmpty()==true)
                loc=currentStage.getLocation();
            else
                loc=stageLocationF.getText();


            if(distance.getText().isEmpty()==true)
                dist=currentStage.getDistance();
            else
                dist=Float.parseFloat(distanceF.getText());


            if(lapsF.getText().isEmpty()==true)
                tur=currentStage.getLaps();
            else
                tur=Integer.parseInt(lapsF.getText());


            if(dateF.getText().isEmpty()==true)
                data=LocalDate.parse(currentStage.getDate());
            else
                data=LocalDate.parse(dateF.getText());

            databaseConnection.updateStage(currentStage.getId(), nume,loc,dist,tur,data);

            currentStage.setName(nume);
            currentStage.setLocation(loc);
            currentStage.setDistance(dist);
            currentStage.setLaps(tur);
            currentStage.setDate(String.valueOf(data));

            nameF.clear();
            stageLocationF.clear();
            distanceF.clear();
            lapsF.clear();
            dateF.clear();

            view(index);
        }
    }
}
