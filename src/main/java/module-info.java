module com.example.clasamentf1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens Clasament to javafx.fxml;
    exports Clasament;
}