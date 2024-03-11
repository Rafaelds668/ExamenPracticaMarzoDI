module com.example.examenpracticadi {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jasperreports;


    opens com.example.examenpracticadi to javafx.fxml;
    exports com.example.examenpracticadi;
    exports com.example.examenpracticadi.Controller;
    opens com.example.examenpracticadi.Controller to javafx.fxml;
}