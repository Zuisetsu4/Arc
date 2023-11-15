module com.example.arc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.arc to javafx.fxml;
    exports com.example.arc;
}