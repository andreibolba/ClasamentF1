package Clasament;

import Entity.UsersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController {
    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersEntity usersEntity = new UsersEntity();
    @FXML
    public void initialize() {
        usersEntity.read();
    }

    @FXML
    void backToMain(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("MainAdminPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Main Admin Page");
            stage.setScene(scene);
            stage.show();

    }
}
